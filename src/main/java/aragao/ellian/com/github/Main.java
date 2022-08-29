package aragao.ellian.com.github;

import aragao.ellian.com.github.model.Movie;
import aragao.ellian.com.github.model.Review;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {
    private static final String IMDB_BASE_URL = "https://www.imdb.com";

    public static void main(String[] args) {
        executeProcess();
    }

    private static void executeProcess() {
        final var doc = getDocumentFromImdbWithPath("/chart/bottom");
        consumeLowerstRatedMoviePage(doc.body());
    }

    private static Document getDocumentFromImdbWithPath(String path) {
        try {
            return Jsoup
                    .connect(IMDB_BASE_URL.concat(path))
                    .header("Accept-Language", "en-US,en")
                    .get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void consumeLowerstRatedMoviePage(Element body) {
        Objects.requireNonNull(body);
        final var data = body
                .getElementsByTag("tbody")
                .first();
        consumeList(data);
    }

    private static void consumeList(Element element) {
        Objects.requireNonNull(element, "should not pass element null");
        element.getElementsByTag("tr")
               .stream()
               .limit(10)
               .forEach(Main::consumeRow);
    }

    private static void consumeRow(Element element) {
        Objects.requireNonNull(element, "should not pass element null");
        final var linksFromMovies = element
                .getElementsByTag("td")
                .stream()
                .map(trs -> trs.getElementsByTag("a"))
                .map(x -> x.attr("href"))
                .filter(x -> !x.isBlank())
                .collect(Collectors.toSet());

        processLinkMovies(linksFromMovies);
    }

    private static void processLinkMovies(Set<String> linksFromMovies) {
        linksFromMovies
                .stream()
                .map(x -> x.split("/\\?")[0])
                .forEach(Main::consumeLinkMovie);
    }

    private static void consumeLinkMovie(String pathUrl) {
        Objects.requireNonNull(pathUrl, "should not pass element null");
        final var movie = consumeMoviePage(pathUrl);
        System.out.println(movie);
        consumeLinkMovieReviews(pathUrl.concat("/reviews?sort=userRating&dir=desc&ratingFilter=0"));
    }

    private static Movie consumeMoviePage(String pathUrl) {
        final var doc = getDocumentFromImdbWithPath(pathUrl);
        final var bodyMoviePage = doc.body();
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

    private static void consumeLinkMovieReviews(String pathUrl) {
        Objects.requireNonNull(pathUrl, "should not pass element null");
        final var doc = getDocumentFromImdbWithPath(pathUrl);
        consumeMovieReviewsPage(doc.body());
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

    private static void consumeMovieReviewsPage(Element body) {
        Objects.requireNonNull(body, "should not pass element null");
        body.getElementsByClass("lister-item-content").forEach(Main::consumeMovieReviewItem);
    }

    private static Review consumeMovieReviewItem(Element element) {
        final var listRootElementeReview = getElementRootFromListReview(element);
        final var score = getScoreReview(listRootElementeReview);
        final var title = getTitleReview(listRootElementeReview);
        final var content = getContentReview(listRootElementeReview);

        return Review.builder()
                     .score(score)
                     .title(title)
                     .content(content)
                     .build();
    }

    private static Elements getElementRootFromListReview(Element element) {
        return element
                .getElementsByClass("lister-item-content");
    }

    private static String getScoreReview(Elements elements) {
        return elements
                .first()
                .getElementsByClass("rating-other-user-rating")
                .first()
                .children()
                .next()
                .first()
                .text();
    }

    private static String getTitleReview(Elements element) {
        return element
                .first()
                .getElementsByClass("title")
                .first()
                .text();
    }

    private static String getContentReview(Elements elements) {
        return elements
                .first()
                .getElementsByClass("content")
                .first()
                .text();
    }
}
