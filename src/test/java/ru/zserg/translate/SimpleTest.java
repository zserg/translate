package ru.zserg.translate;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;
import static org.junit.jupiter.api.Assertions.*;

public class SimpleTest {

    @Test
    public void test1() throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/text.html"), StandardCharsets.UTF_8))) {
            String html = reader.lines().collect(Collectors.joining());
            Document doc = Jsoup.parse(html);
            Element practicalexample = doc.getElementById("practicalexamples");
            Elements elements = practicalexample.getElementsByClass("dict-example");
            List<List<String>> result = elements.stream().map(e -> {
                Elements es = e.getElementsByClass("dict-source");
                es.get(0).getElementsByTag("strong").unwrap();
                String eng = es.get(0).ownText();
                String rus = e.getElementsByClass("dict-result").get(0).ownText();
                return List.of(rus, eng);
            }).collect(Collectors.toList());
            assertEquals("Delete the text in the 'Filter' box and type 'mail.server.default.fetch_by_chunks'", result.get(0).get(1));
        }
    }


}

