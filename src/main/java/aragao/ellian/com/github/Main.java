package aragao.ellian.com.github;

import aragao.ellian.com.github.infra.adapters.ImdbDocumentConsumer;
import aragao.ellian.com.github.infra.adapters.ParserChartRatedMoviesPage;

public class Main {

    public static void main(String[] args) {
        executeProcess();
    }

    private static void executeProcess() {
        final var doc = ImdbDocumentConsumer.getDocumentFromImdbWithPath("/chart/bottom");
        System.out.println(ParserChartRatedMoviesPage.consumeChartRatedMoviesPage(doc.body()));
    }

}
