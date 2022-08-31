package aragao.ellian.com.github;

import aragao.ellian.com.github.core.usecase.FindLowestRatedMoviesUsecase;
import aragao.ellian.com.github.core.usecase.SortMoviesUsecase;
import aragao.ellian.com.github.core.usecase.impl.FindLowestRatedMoviesUsecaseImpl;
import aragao.ellian.com.github.core.usecase.impl.SortMoviesUsecaseImpl;
import aragao.ellian.com.github.infra.adapters.ImdbWorstMoviePort;
import aragao.ellian.com.github.infra.crawler.CrawlerProcessor;
import aragao.ellian.com.github.infra.crawler.ImdbDocumentConsumer;
import aragao.ellian.com.github.infra.crawler.parser.ParserChartRatedMoviesPage;
import aragao.ellian.com.github.infra.crawler.parser.ParserMovieBodyPage;
import aragao.ellian.com.github.infra.crawler.parser.ParserReviewMovieBodyPage;
import aragao.ellian.com.github.infra.crawler.router.RouterMovieChartConsumer;
import aragao.ellian.com.github.infra.crawler.router.RouterMovieLinkDataAndReviews;

import java.util.Objects;

public abstract class ApplicationContext {

    private static SortMoviesUsecase sortMoviesUsecase;
    private static FindLowestRatedMoviesUsecase findLowestRatedMoviesUsecase;

    public static void initializeContexts() {
        final var parserChartRatedMoviesPage = new ParserChartRatedMoviesPage();
        final var parserMovieBodyPage = new ParserMovieBodyPage();
        final var parserReviewMovieBodyPage = new ParserReviewMovieBodyPage();
        final var imdbDocumentConsumer = new ImdbDocumentConsumer("https://www.imdb.com");
        final var routerMovieLinkDataAndReviews = new RouterMovieLinkDataAndReviews(parserReviewMovieBodyPage, parserMovieBodyPage, imdbDocumentConsumer);
        final var routerMovieChartConsumer = new RouterMovieChartConsumer(routerMovieLinkDataAndReviews, parserChartRatedMoviesPage);
        final var crawlerProcessor = new CrawlerProcessor(imdbDocumentConsumer, routerMovieChartConsumer);
        final var imdbWorstMoviePort = new ImdbWorstMoviePort(crawlerProcessor);

        sortMoviesUsecase = new SortMoviesUsecaseImpl();
        findLowestRatedMoviesUsecase = new FindLowestRatedMoviesUsecaseImpl(imdbWorstMoviePort);
    }

    public static SortMoviesUsecase getSortMoviesUsecase() {
        if (Objects.nonNull(sortMoviesUsecase)) {
            return sortMoviesUsecase;
        }
        throw new RuntimeException("Context not initialized");
    }

    public static FindLowestRatedMoviesUsecase getFindLowestRatedMoviesUsecase() {
        if (Objects.nonNull(findLowestRatedMoviesUsecase)) {
            return findLowestRatedMoviesUsecase;
        }
        throw new RuntimeException("Context not initialized");
    }
}
