package aragao.ellian.com.github.infra.crawler.parser;

import org.jsoup.nodes.Element;

import java.util.List;
import java.util.stream.Collectors;

public class ParserChartRatedMoviesPage {

    public List<String> consumeChartRatedMoviesPage(Element body) {
        final var tbody = body
                .getElementsByTag("tbody")
                .first();
        return consumeListMovies(tbody);
    }

    private List<String> consumeListMovies(Element element) {
        return element
                .getElementsByTag("tr")
                .stream()
                .limit(10)
                .map(this::consumeRowMovie)
                .toList();
    }

    private String consumeRowMovie(Element element) {
        return element
                .getElementsByTag("td")
                .stream()
                .map(trs -> trs.getElementsByTag("a"))
                .map(x -> x.attr("href"))
                .filter(x -> !x.isBlank())
                .collect(Collectors.toSet())
                .stream()
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }
}
