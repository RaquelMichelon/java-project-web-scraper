package com.raquelmichelon.homeapi.controller;

import com.raquelmichelon.homeapi.model.ResponseDTO;
import com.raquelmichelon.homeapi.service.ScraperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/")
public class ScraperController {

    @Autowired
    ScraperService scraperService;

    @GetMapping("/{title}")
    public Set<ResponseDTO> getAds(@PathVariable String title) {
        return scraperService.getHomeByTitle(title);
    }
}
