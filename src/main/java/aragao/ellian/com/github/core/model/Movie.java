package aragao.ellian.com.github.core.model;

import java.util.List;

public record Movie(
        String name,
        String score,
        List<String> directors,
        List<String> starts,
        List<String> topCast
) {
    public static Movie of(
            String name,
            String score,
            List<String> directors,
            List<String> starts,
            List<String> topCast
    ) {
        return new Movie(name, score, directors, starts, topCast);
    }

    public static MovieBuilder builder() {
        return new MovieBuilder();
    }

    public static class MovieBuilder {
        private String name;
        private String score;
        private List<String> directors;
        private List<String> starts;
        private List<String> topCast;

        public MovieBuilder name(String name) {
            this.name = name;
            return this;
        }

        public MovieBuilder score(String score) {
            this.score = score;
            return this;
        }

        public MovieBuilder directors(List<String> directors) {
            this.directors = directors;
            return this;
        }

        public MovieBuilder starts(List<String> starts) {
            this.starts = starts;
            return this;
        }

        public MovieBuilder topCast(List<String> topCast) {
            this.topCast = topCast;
            return this;
        }

        public Movie build() {
            return Movie.of(name, score, directors, starts, topCast);
        }
    }
}
