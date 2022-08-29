package aragao.ellian.com.github.infra.adapters;

import aragao.ellian.com.github.core.model.Review;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;
import java.util.Objects;

public class ParserReviewMovieBodyPage {
    public static List<Review> consumeMovieReviewsPage(Element body) {
        Objects.requireNonNull(body, "should not pass element null");
        return body.getElementsByClass("lister-item-content")
                   .stream()
                   .map(ParserReviewMovieBodyPage::consumeMovieReviewItem)
                   .toList();
    }

    private static Review consumeMovieReviewItem(Element element) {
        final var listRootElementeReview = element.getElementsByClass("lister-item-content");

        final var score = getScoreReview(listRootElementeReview);
        final var title = getTitleReview(listRootElementeReview);
        final var content = getContentReview(listRootElementeReview);

        return Review.builder()
                     .score(score)
                     .title(title)
                     .content(content)
                     .build();
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
