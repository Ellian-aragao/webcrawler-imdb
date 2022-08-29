package aragao.ellian.com.github.core.model;

public record Review(String score, String title, String content) {
    public static Review of(String score, String title, String content) {
        return new Review(score, title, content);
    }

    public static ReviewBuilder builder() {
        return new ReviewBuilder();
    }

    public static class ReviewBuilder {
        private String score;
        private String title;
        private String content;

        public ReviewBuilder score(String score) {
            this.score = score;
            return this;
        }

        public ReviewBuilder title(String title) {
            this.title = title;
            return this;
        }

        public ReviewBuilder content(String content) {
            this.content = content;
            return this;
        }

        public Review build() {
            return new Review(score, title, content);
        }
    }
}
