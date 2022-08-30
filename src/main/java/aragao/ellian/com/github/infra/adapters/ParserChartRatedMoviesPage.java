package aragao.ellian.com.github.infra.adapters;

import aragao.ellian.com.github.core.model.MovieReviews;
import org.jsoup.nodes.Element;

import java.util.List;
import java.util.stream.Collectors;

public class ParserChartRatedMoviesPage {
    public static List<MovieReviews> consumeChartRatedMoviesPage(Element body) {
        final var data = body
                .getElementsByTag("tbody")
                .first();
        return consumeListMovies(data);
    }

    private static List<MovieReviews> consumeListMovies(Element element) {
        return element.getElementsByTag("tr")
                      .stream()
                      .limit(10)
                      .map(ParserChartRatedMoviesPage::consumeRowMovie)
                      .toList();
    }

    private static MovieReviews consumeRowMovie(Element element) {
        return element
                .getElementsByTag("td")
                .stream()
                .map(trs -> trs.getElementsByTag("a"))
                .map(x -> x.attr("href"))
                .filter(x -> !x.isBlank())
                .collect(Collectors.toSet())
                .stream()
                .map(ConsumerMovieLink::processLinkMovie)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
