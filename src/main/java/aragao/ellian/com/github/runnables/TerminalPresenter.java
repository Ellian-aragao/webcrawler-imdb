package aragao.ellian.com.github.runnables;

import aragao.ellian.com.github.ApplicationContext;
import aragao.ellian.com.github.core.model.MovieReviews;
import aragao.ellian.com.github.core.model.Review;

import java.util.List;

public class TerminalPresenter {
    public static void main(String[] args) {
        ApplicationContext.initializeContexts();
        final var findLowestRatedMoviesUsecase = ApplicationContext.getFindLowestRatedMoviesUsecase();
        final var sortMoviesUsecase = ApplicationContext.getSortMoviesUsecase();
        final var lowestRatedMovies = findLowestRatedMoviesUsecase.findLowestRatedMovies();
        final var lowestRatedMoviesSortedDescending = sortMoviesUsecase.sortDescendingMoviesByRating(lowestRatedMovies);
        printMovies(lowestRatedMoviesSortedDescending);
    }

    private static void printMovies(List<MovieReviews> movieReviews) {
        for (int i = 0; i < movieReviews.size(); i++) {
            final var movieReviewsI = movieReviews.get(i);
            final var movie = movieReviewsI.movie();
            System.out.printf("%02d. %s  %s%n", i + 1, movie.name(), movie.score());
            System.out.printf("    Directors: %s%n", String.join(", ", movie.directors()));
            System.out.printf("    Top Cast: %s%n", String.join(", ", movie.topCast()));
            System.out.println("    Reviews:");
            movieReviewsI.reviews()
                         .stream()
                         .limit(1)
                         .forEach(TerminalPresenter::printReview);
        }
    }

    private static void printReview(Review review) {
        System.out.printf("      - Title: %s%n", review.title());
        System.out.printf("        Score: %s%n", review.score());
        System.out.printf("        Content: %s%n", review.content());
    }
}
