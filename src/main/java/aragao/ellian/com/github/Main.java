package aragao.ellian.com.github;

import aragao.ellian.com.github.core.model.Movie;
import aragao.ellian.com.github.core.model.Review;
import aragao.ellian.com.github.infra.adapters.ImdbDocumentConsumer;
import aragao.ellian.com.github.infra.adapters.ParserMovieBodyPage;
import aragao.ellian.com.github.infra.adapters.ParserReviewMovieBodyPage;
import org.jsoup.nodes.Element;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        executeProcess();
    }

    private static void executeProcess() {
        final var doc = ImdbDocumentConsumer.getDocumentFromImdbWithPath("/chart/bottom");
        consumeLowerstRatedMoviePage(doc.body());
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
        final var movie = consumeMoviePage(pathUrl);
        System.out.println(movie);
        consumeLinkMovieReviews(pathUrl.concat("/reviews?sort=userRating&dir=desc&ratingFilter=0"));
    }

    private static Movie consumeMoviePage(String pathUrl) {
        final var doc = ImdbDocumentConsumer.getDocumentFromImdbWithPath(pathUrl);
        return ParserMovieBodyPage.parseMovieBodyPage(doc.body());
    }

    private static List<Review> consumeLinkMovieReviews(String pathUrl) {
        final var doc = ImdbDocumentConsumer.getDocumentFromImdbWithPath(pathUrl);
        return ParserReviewMovieBodyPage.consumeMovieReviewsPage(doc.body());
    }
}
