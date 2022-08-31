package aragao.ellian.com.github.infra.crawler.parser;

import aragao.ellian.com.github.core.model.Review;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;

public class ParserReviewMovieBodyPage {

    public List<Review> consumeMovieReviewsPage(Element body) {
        return body.getElementsByClass("lister-item-content")
                   .stream()
                   .map(this::consumeMovieReviewItem)
                   .toList();
    }

    private Review consumeMovieReviewItem(Element element) {
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

    private String getScoreReview(Elements elements) {
        return elements
                .first()
                .getElementsByClass("rating-other-user-rating")
                .first()
                .children()
                .next()
                .first()
                .text();
    }

    private String getTitleReview(Elements element) {
        return element
                .first()
                .getElementsByClass("title")
                .first()
                .text();
    }

    private String getContentReview(Elements elements) {
        return elements
                .first()
                .getElementsByClass("content")
                .first()
                .text();
    }
}
