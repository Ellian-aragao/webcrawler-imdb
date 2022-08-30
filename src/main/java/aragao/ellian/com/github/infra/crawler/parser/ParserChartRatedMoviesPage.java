package aragao.ellian.com.github.infra.crawler.parser;

import org.jsoup.nodes.Element;

import java.util.Set;
import java.util.stream.Collectors;

public class ParserChartRatedMoviesPage {
    public static Set<String> consumeChartRatedMoviesPage(Element body) {
        final var data = body
                .getElementsByTag("tbody")
                .first();
        return consumeListMovies(data);
    }

    private static Set<String> consumeListMovies(Element element) {
        return element.getElementsByTag("tr")
                      .stream()
                      .limit(10)
                      .map(ParserChartRatedMoviesPage::consumeRowMovie)
                      .findFirst()
                      .orElseThrow(IllegalStateException::new);
    }

    private static Set<String> consumeRowMovie(Element element) {
        return element
                .getElementsByTag("td")
                .stream()
                .map(trs -> trs.getElementsByTag("a"))
                .map(x -> x.attr("href"))
                .filter(x -> !x.isBlank())
                .collect(Collectors.toSet());
    }
}
