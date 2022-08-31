package aragao.ellian.com.github.infra.adapters;

import aragao.ellian.com.github.core.model.MovieReviews;
import aragao.ellian.com.github.core.usecase.ports.ImdbWorstMoviesPort;
import aragao.ellian.com.github.infra.crawler.CrawlerProcessor;

import java.util.List;

public class ImdbWorstMoviePort implements ImdbWorstMoviesPort {

    private final CrawlerProcessor crawlerProcessor;

    public ImdbWorstMoviePort(CrawlerProcessor crawlerProcessor) {
        this.crawlerProcessor = crawlerProcessor;
    }

    @Override
    public List<MovieReviews> getWorstMoviesData() {
        return crawlerProcessor.processWorstMovies();
    }
}
