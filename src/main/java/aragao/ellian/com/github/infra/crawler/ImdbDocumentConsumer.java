package aragao.ellian.com.github.infra.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class ImdbDocumentConsumer {
    private static final String IMDB_BASE_URL = "https://www.imdb.com";

    public static Document getDocumentFromImdbWithPath(String path) {
        try {
            return Jsoup
                    .connect(IMDB_BASE_URL.concat(path))
                    .header("Accept-Language", "en-US,en")
                    .get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
