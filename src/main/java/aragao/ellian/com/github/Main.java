package aragao.ellian.com.github;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
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
        consumeMoviePage(pathUrl);
        consumeLinkMovieReviews(pathUrl.concat("/reviews?sort=userRating&dir=desc&ratingFilter=0"));
    }

    private static void consumeLinkMovieReviews(String pathUrl) {
        Objects.requireNonNull(pathUrl, "should not pass element null");
        final var doc = getDocumentFromImdbWithPath(pathUrl);
        consumeMovieReviewsPage(doc.body());
    }

    private static void consumeMovieReviewsPage(Element body) {
        Objects.requireNonNull(body, "should not pass element null");
        body.getElementsByClass("lister-item-content").forEach(Main::consumeMovieReviewItem);
    }

    private static void consumeMovieReviewItem(Element element) {
        final var listRootElementeReview = getElementRootFromListReview(element);
        getScoreReview(listRootElementeReview);
        getTitleReview(listRootElementeReview);
        getContentReview(listRootElementeReview);
    }

    private static Elements getElementRootFromListReview(Element element) {
        return element
                .getElementsByClass("lister-item-content");
    }

    private static void getScoreReview(Elements elements) {
        final var score = elements
                .first()
                .getElementsByClass("rating-other-user-rating")
                .first()
                .children()
                .next()
                .first()
                .text();
        System.out.println(score);
    }

    private static void getTitleReview(Elements element) {
        final var title = element
                .first()
                .getElementsByClass("title")
                .first()
                .text();
        System.out.println(title);
    }

    private static void getContentReview(Elements elements) {
        final var title = elements
                .first()
                .getElementsByClass("content")
                .first()
                .text();
        System.out.println(title);
    }

    private static void consumeMoviePage(String pathUrl) {
        final var doc = getDocumentFromImdbWithPath(pathUrl);
        final var bodyMoviePage = doc.body();
        getMovieName(bodyMoviePage);
        getMovieScore(bodyMoviePage);
        getMovieDirectors(bodyMoviePage);
        getMovieStars(bodyMoviePage);
        getMovieTopCast(bodyMoviePage);
    }

    private static void getMovieName(Element bodyMoviePage) {
        final var name = bodyMoviePage
                .getElementsByAttributeValue("data-testid", "hero-title-block__title")
                .first()
                .text();
        System.out.println(name);
    }

    private static void getMovieScore(Element bodyMoviePage) {
        final var score = bodyMoviePage
                .getElementsByAttributeValue("data-testid", "hero-rating-bar__aggregate-rating")
                .first()
                .getElementsByAttributeValue("data-testid", "hero-rating-bar__aggregate-rating__score")
                .first()
                .getElementsByTag("span")
                .first()
                .text();
        System.out.println(score);
    }

    private static void getMovieDirectors(Element bodyMoviePage) {
        final var directors = bodyMoviePage
                .getElementsByAttributeValue("data-testid", "title-pc-principal-credit")
                .first()
                .getElementsByTag("div")
                .first()
                .getElementsByTag("li")
                .stream()
                .map(Element::text)
                .toList();
        System.out.println(directors);
    }

    private static void getMovieStars(Element bodyMoviePage) {
        final var movieStars = bodyMoviePage
                .getElementsByAttributeValue("data-testid", "title-pc-principal-credit")
                .last()
                .getElementsByTag("div")
                .first()
                .getElementsByTag("li")
                .stream()
                .map(Element::text)
                .toList();
        System.out.println(movieStars);
    }

    private static void getMovieTopCast(Element bodyMoviePage) {
        final var topCast = bodyMoviePage
                .getElementsByAttributeValue("data-testid", "title-cast")
                .first()
                .getElementsByAttributeValue("data-testid", "title-cast-item")
                .stream()
                .map(data -> data.getElementsByAttributeValue("data-testid", "title-cast-item__actor"))
                .map(Elements::first)
                .map(Element::text)
                .toList();
        System.out.println(topCast);
    }
}
