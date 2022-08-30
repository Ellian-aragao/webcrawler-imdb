package aragao.ellian.com.github.infra.crawler.router;

import aragao.ellian.com.github.core.model.MovieReviews;
import aragao.ellian.com.github.infra.crawler.ImdbDocumentConsumer;
import aragao.ellian.com.github.infra.crawler.parser.ParserChartRatedMoviesPage;
import org.jsoup.nodes.Document;

import java.util.List;

public class RouterMovieChartConsumer {

    public static List<MovieReviews> processChartRatedMovie(Document chartRatedMovie) {
        return ParserChartRatedMoviesPage
                .consumeChartRatedMoviesPage(chartRatedMovie.body())
                .stream()
                .map(RouterMovieLinkDataAndReviews::processLinkMovie)
                .toList();
    }
}
