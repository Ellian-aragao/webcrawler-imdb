package aragao.ellian.com.github.core.usecase;

import aragao.ellian.com.github.core.model.MovieReviews;

import java.util.List;

public interface FindLowestRatedMoviesUsecase {
    List<MovieReviews> findLowestRatedMovies();
}
