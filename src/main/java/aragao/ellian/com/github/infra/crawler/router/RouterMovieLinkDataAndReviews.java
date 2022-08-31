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

    private final ParserReviewMovieBodyPage parserReviewMovieBodyPage;
    private final ParserMovieBodyPage parserMovieBodyPage;
    private final ImdbDocumentConsumer imdbDocumentConsumer;
    private final Pattern patternQueryParameter;
    private final String queryParamSortingBetterComment;
    private final String reviewsUrlPath;

    public RouterMovieLinkDataAndReviews(ParserReviewMovieBodyPage parserReviewMovieBodyPage, ParserMovieBodyPage parserMovieBodyPage, ImdbDocumentConsumer imdbDocumentConsumer) {
        this.parserReviewMovieBodyPage = parserReviewMovieBodyPage;
        this.parserMovieBodyPage = parserMovieBodyPage;
        this.imdbDocumentConsumer = imdbDocumentConsumer;
        patternQueryParameter = Pattern.compile("/\\?");
        queryParamSortingBetterComment = "?sort=userRating&dir=desc&ratingFilter=0";
        reviewsUrlPath = "/reviews";
    }

    public MovieReviews processLinkMovie(String url) {
        final var urlWithoutQueryParams = removeQueryParameters(url);
        return consumeLinkMovie(urlWithoutQueryParams);
    }

    private String removeQueryParameters(String url) {
        return patternQueryParameter.split(url)[0];
    }

    private MovieReviews consumeLinkMovie(String pathUrl) {
        final var movie = consumeLinkMoviePage(pathUrl);
        final var reviews = consumeLinkMovieReviews(pathUrl.concat(reviewsUrlPath + queryParamSortingBetterComment));
        return MovieReviews.builder()
                           .movie(movie)
                           .reviews(reviews)
                           .build();
    }

    private Movie consumeLinkMoviePage(String pathUrl) {
        final var doc = imdbDocumentConsumer.getDocumentFromImdbWithPath(pathUrl);
        return parserMovieBodyPage.parseMovieBodyPage(doc.body());
    }

    private List<Review> consumeLinkMovieReviews(String pathUrl) {
        final var doc = imdbDocumentConsumer.getDocumentFromImdbWithPath(pathUrl);
        return parserReviewMovieBodyPage.consumeMovieReviewsPage(doc.body());
    }
}
