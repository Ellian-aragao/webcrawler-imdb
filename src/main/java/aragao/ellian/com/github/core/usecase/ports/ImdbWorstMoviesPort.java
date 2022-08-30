package aragao.ellian.com.github.core.usecase.ports;

import aragao.ellian.com.github.core.model.MovieReviews;

import java.util.List;

public interface ImdbWorstMoviesPort {
    List<MovieReviews> getWorstMoviesData();
}
