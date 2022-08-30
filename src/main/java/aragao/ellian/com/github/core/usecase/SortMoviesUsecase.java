package aragao.ellian.com.github.core.usecase;

import aragao.ellian.com.github.core.model.MovieReviews;

import java.util.List;

public interface SortMoviesUsecase {
    List<MovieReviews> sortMoviesByRating(List<MovieReviews> movieReviews, boolean isAscending);

    default List<MovieReviews> sortAscendingMoviesByRating(List<MovieReviews> movieReviews) {
        return sortMoviesByRating(movieReviews, true);
    }
}
