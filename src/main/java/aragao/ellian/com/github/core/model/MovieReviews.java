package aragao.ellian.com.github.core.model;

import java.util.List;

public record MovieReviews(Movie movie, List<Review> reviews) {
    public static MovieReviews of(Movie movie, List<Review> reviews) {
        return new MovieReviews(movie, reviews);
    }

    public static MovieReviewsBuilder builder() {
        return new MovieReviewsBuilder();
    }

    public static class MovieReviewsBuilder {
        private Movie movie;
        private List<Review> reviews;

        public MovieReviewsBuilder movie(Movie movie) {
            this.movie = movie;
            return this;
        }

        public MovieReviewsBuilder reviews(List<Review> reviews) {
            this.reviews = reviews;
            return this;
        }

        public MovieReviews build() {
            return MovieReviews.of(movie, reviews);
        }
    }
}
