package ru.zserg.translate;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ru.zserg.translate.model.Pair;
import ru.zserg.translate.model.Phrase;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DictionaryService {

    private final RestTemplate restTemplate;
    private final TranslateService translateService;

    public DictionaryService(RestTemplate restTemplate, TranslateService translateService) {
        this.restTemplate = restTemplate;
        this.translateService = translateService;
    }

    public Phrase parseForPhrase(){
        String word = translateService.getRandomQuestion();
        log.info("word: {}", word);
        try {
            ResponseEntity<String> response = restTemplate.getForEntity("https://en.bab.la/dictionary/english-russian/" + word, String.class);
            if (response.getStatusCodeValue() == 200 && response.getBody() != null) {
                String html = response.getBody();
                return parseForPhrase(html)
                        .map(p -> new Phrase(word, p))
                        .orElse(Phrase.empty(word));
            }else{
                return Phrase.empty(word);
            }
        } catch (RestClientException e) {
            return Phrase.empty(word);
        }
    }

    private Optional<Pair> parseForPhrase(String html) {
        Document doc = Jsoup.parse(html);
        log.info("doc: {}", doc);
        Element practicalexample = doc.getElementById("practicalexamples");
        if (practicalexample != null) {
            Elements elements = practicalexample.getElementsByClass("dict-example");
            List<Pair> result = elements.stream().map(e -> {
                Elements es = e.getElementsByClass("dict-source");
                es.get(0).getElementsByTag("strong").unwrap();
                String eng = es.get(0).ownText();
                String rus = e.getElementsByClass("dict-result").get(0).ownText();
                return new Pair(rus, eng);
            }).collect(Collectors.toList());
            Random random = new Random();
            return Optional.of(result.get(random.nextInt(result.size())));
        } else {
            return Optional.empty();
        }
    }


}
