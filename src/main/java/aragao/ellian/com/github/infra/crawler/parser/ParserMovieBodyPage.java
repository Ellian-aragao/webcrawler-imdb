package aragao.ellian.com.github.infra.crawler.parser;

import aragao.ellian.com.github.core.model.Movie;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;

public class ParserMovieBodyPage {
    public static Movie parseMovieBodyPage(Element bodyMoviePage) {
        final var name = getMovieName(bodyMoviePage);
        final var score = getMovieScore(bodyMoviePage);
        final var directors = getMovieDirectors(bodyMoviePage);
        final var starts = getMovieStars(bodyMoviePage);
        final var topCast = getMovieTopCast(bodyMoviePage);

        return Movie.builder()
                    .name(name)
                    .score(score)
                    .directors(directors)
                    .starts(starts)
                    .topCast(topCast)
                    .build();
    }

    private static String getMovieName(Element bodyMoviePage) {
        return bodyMoviePage
                .getElementsByAttributeValue("data-testid", "hero-title-block__title")
                .first()
                .text();
    }

    private static String getMovieScore(Element bodyMoviePage) {
        return bodyMoviePage
                .getElementsByAttributeValue("data-testid", "hero-rating-bar__aggregate-rating")
                .first()
                .getElementsByAttributeValue("data-testid", "hero-rating-bar__aggregate-rating__score")
                .first()
                .getElementsByTag("span")
                .first()
                .text();
    }

    private static List<String> getMovieDirectors(Element bodyMoviePage) {
        return bodyMoviePage
                .getElementsByAttributeValue("data-testid", "title-pc-principal-credit")
                .first()
                .getElementsByTag("div")
                .first()
                .getElementsByTag("li")
                .stream()
                .map(Element::text)
                .toList();
    }

    private static List<String> getMovieStars(Element bodyMoviePage) {
        return bodyMoviePage
                .getElementsByAttributeValue("data-testid", "title-pc-principal-credit")
                .last()
                .getElementsByTag("div")
                .first()
                .getElementsByTag("li")
                .stream()
                .map(Element::text)
                .toList();
    }

    private static List<String> getMovieTopCast(Element bodyMoviePage) {
        return bodyMoviePage
                .getElementsByAttributeValue("data-testid", "title-cast")
                .first()
                .getElementsByAttributeValue("data-testid", "title-cast-item")
                .stream()
                .map(data -> data.getElementsByAttributeValue("data-testid", "title-cast-item__actor"))
                .map(Elements::first)
                .map(Element::text)
                .toList();
    }
}
