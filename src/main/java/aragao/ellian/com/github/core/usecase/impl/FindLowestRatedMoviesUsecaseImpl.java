package aragao.ellian.com.github.core.usecase.impl;

import aragao.ellian.com.github.core.model.MovieReviews;
import aragao.ellian.com.github.core.usecase.FindLowestRatedMoviesUsecase;
import aragao.ellian.com.github.core.usecase.ports.ImdbWorstMoviesPort;

import java.util.List;

public class FindLowestRatedMoviesUsecaseImpl implements FindLowestRatedMoviesUsecase {

    private final ImdbWorstMoviesPort imdbWorstMoviesPort;

    public FindLowestRatedMoviesUsecaseImpl(ImdbWorstMoviesPort imdbWorstMoviesPort) {
        this.imdbWorstMoviesPort = imdbWorstMoviesPort;
    }

    @Override
    public List<MovieReviews> findLowestRatedMovies() {
        return imdbWorstMoviesPort.getWorstMoviesData();
    }
}
