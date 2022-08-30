package aragao.ellian.com.github.infra.crawler;

import aragao.ellian.com.github.core.model.MovieReviews;
import aragao.ellian.com.github.infra.crawler.router.RouterMovieChartConsumer;

import java.util.List;

public class CrawlerProcessor {
    public static List<MovieReviews> processWorstMovies() {
        final var document = ImdbDocumentConsumer.getDocumentFromImdbWithPath("/chart/bottom");
        return RouterMovieChartConsumer.processChartRatedMovie(document);
    }
}
