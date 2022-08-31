package aragao.ellian.com.github.infra.crawler;

import aragao.ellian.com.github.core.model.MovieReviews;
import aragao.ellian.com.github.infra.crawler.router.RouterMovieChartConsumer;

import java.util.List;

public class CrawlerProcessor {

    private final ImdbDocumentConsumer imdbDocumentConsumer;
    private final RouterMovieChartConsumer routerMovieChartConsumer;

    public CrawlerProcessor(ImdbDocumentConsumer imdbDocumentConsumer, RouterMovieChartConsumer routerMovieChartConsumer) {
        this.imdbDocumentConsumer = imdbDocumentConsumer;
        this.routerMovieChartConsumer = routerMovieChartConsumer;
    }

    public List<MovieReviews> processWorstMovies() {
        final var document = imdbDocumentConsumer.getDocumentFromImdbWithPath("/chart/bottom");
        return routerMovieChartConsumer.processChartRatedMovie(document);
    }
}
