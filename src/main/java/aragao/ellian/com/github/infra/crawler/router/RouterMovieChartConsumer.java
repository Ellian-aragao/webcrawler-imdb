package aragao.ellian.com.github.infra.crawler.router;

import aragao.ellian.com.github.core.model.MovieReviews;
import aragao.ellian.com.github.infra.crawler.parser.ParserChartRatedMoviesPage;
import org.jsoup.nodes.Document;

import java.util.List;

public class RouterMovieChartConsumer {

    private final RouterMovieLinkDataAndReviews routerMovieLinkDataAndReviews;
    private final ParserChartRatedMoviesPage parserChartRatedMoviesPage;
    public RouterMovieChartConsumer(RouterMovieLinkDataAndReviews routerMovieLinkDataAndReviews, ParserChartRatedMoviesPage parserChartRatedMoviesPage) {
        this.routerMovieLinkDataAndReviews = routerMovieLinkDataAndReviews;
        this.parserChartRatedMoviesPage = parserChartRatedMoviesPage;
    }

    public List<MovieReviews> processChartRatedMovie(Document chartRatedMovie) {
        return parserChartRatedMoviesPage
                .consumeChartRatedMoviesPage(chartRatedMovie.body())
                .stream()
                .map(routerMovieLinkDataAndReviews::processLinkMovie)
                .toList();
    }
}
