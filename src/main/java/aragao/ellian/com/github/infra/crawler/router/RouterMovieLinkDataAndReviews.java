package aragao.ellian.com.github.infra.crawler.router;

import aragao.ellian.com.github.core.model.Movie;
import aragao.ellian.com.github.core.model.MovieReviews;
import aragao.ellian.com.github.core.model.Review;
import aragao.ellian.com.github.infra.crawler.ImdbDocumentConsumer;
import aragao.ellian.com.github.infra.crawler.parser.ParserMovieBodyPage;
import aragao.ellian.com.github.infra.crawler.parser.ParserReviewMovieBodyPage;

import java.util.List;
import java.util.regex.Pattern;

public class RouterMovieLinkDataAndReviews {
    private static final Pattern patternQueryParameter = Pattern.compile("/\\?");
    private static final String queryParamSortingBetterComment = "?sort=userRating&dir=desc&ratingFilter=0";
    private static final String reviewsUrlPath = "/reviews";

    public static MovieReviews processLinkMovie(String url) {
        final var urlWithoutQueryParams = removeQueryParameters(url);
        return consumeLinkMovie(urlWithoutQueryParams);
    }

    private static String removeQueryParameters(String url) {
        return patternQueryParameter.split(url)[0];
    }

    private static MovieReviews consumeLinkMovie(String pathUrl) {
        final var movie = consumeLinkMoviePage(pathUrl);
        final var reviews = consumeLinkMovieReviews(pathUrl.concat(reviewsUrlPath + queryParamSortingBetterComment));
        return MovieReviews.builder()
                           .movie(movie)
                           .reviews(reviews)
                           .build();
    }

    private static Movie consumeLinkMoviePage(String pathUrl) {
        final var doc = ImdbDocumentConsumer.getDocumentFromImdbWithPath(pathUrl);
        return ParserMovieBodyPage.parseMovieBodyPage(doc.body());
    }

    private static List<Review> consumeLinkMovieReviews(String pathUrl) {
        final var doc = ImdbDocumentConsumer.getDocumentFromImdbWithPath(pathUrl);
        return ParserReviewMovieBodyPage.consumeMovieReviewsPage(doc.body());
    }
}
