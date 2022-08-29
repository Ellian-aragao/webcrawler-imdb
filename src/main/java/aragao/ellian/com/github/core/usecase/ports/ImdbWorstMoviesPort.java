package aragao.ellian.com.github.core.usecase.ports;

import aragao.ellian.com.github.core.model.MovieReviews;

import java.util.List;

public interface ImdbWorstMoviesPort {
    default List<MovieReviews> getWorstMoviesData() {
        return getWorstMoviesData(10L);
    }

    List<MovieReviews> getWorstMoviesData(Long limit);
}
