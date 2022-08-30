package aragao.ellian.com.github.core.usecase.impl;

import aragao.ellian.com.github.core.model.MovieReviews;
import aragao.ellian.com.github.core.usecase.SortMoviesUsecase;

import java.util.ArrayList;
import java.util.List;

public class SortMoviesUsecaseImpl implements SortMoviesUsecase {

    private final List immutableListInstance = List.of();

    @Override
    public List<MovieReviews> sortMoviesByRating(List<MovieReviews> movieReviews, boolean isAscending) {
        final var movieReviewToSort = verifyIfImmutableList(movieReviews);
        movieReviewToSort.sort((movieReview1, movieReview2) -> {
            final var movie1Rating = movieReview1.movie().score();
            final var movie2Rating = movieReview2.movie().score();
            return isAscending ? movie1Rating.compareTo(movie2Rating) : movie2Rating.compareTo(movie1Rating);
        });
        return movieReviewToSort;
    }

    private List<MovieReviews> verifyIfImmutableList(List<MovieReviews> movieReviews) {
        if (movieReviews.getClass().isInstance(immutableListInstance)) {
            return new ArrayList<>(movieReviews);
        }
        return movieReviews;
    }
}
