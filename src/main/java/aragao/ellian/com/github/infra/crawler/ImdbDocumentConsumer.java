package aragao.ellian.com.github.infra.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class ImdbDocumentConsumer {
    private final String imdbBaseUrl;

    public ImdbDocumentConsumer(String imdbBaseUrl) {
        this.imdbBaseUrl = imdbBaseUrl;
    }

    public Document getDocumentFromImdbWithPath(String path) {
        try {
            return Jsoup
                    .connect(imdbBaseUrl.concat(path))
                    .header("Accept-Language", "en-US,en")
                    .get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
