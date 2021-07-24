package ru.zserg.translate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.zserg.translate.model.Phrase;

@Controller
public class TranslateController {

    @Autowired
    private TranslateService questionService;

    @Autowired
    private DictionaryService dictionaryService;


    @GetMapping(value = "/q")
    public String getQuestion(Model model) {
        Phrase phrase = dictionaryService.parseForPhrase();
        model.addAttribute("word", phrase.getWord());
        model.addAttribute("rus", phrase.getRus());
        model.addAttribute("eng", phrase.getEng());
        return "question";
    }


}
