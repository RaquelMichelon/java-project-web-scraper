package com.raquelmichelon.homeapi.service;

import com.raquelmichelon.homeapi.model.ResponseDTO;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ScraperServiceImpl implements ScraperService {

    @Value("#{'${website.urls}'.split(',')}")
    List<String> urls;

    @Override
    public Set<ResponseDTO> getHomeByTitle(String homeTitle) {
        Set<ResponseDTO> responseDTOSet = new HashSet<>();
        for (String url : urls) {
            if (url.contains("olx")) {
                extractDataFromOlx(responseDTOSet, url, homeTitle);
            } else if (url.contains("ibagy")) {
                extractDataFromIbagy(responseDTOSet, url, homeTitle);
            }
        }
        return responseDTOSet;
    }

    private void extractDataFromIbagy(Set<ResponseDTO> responseDTOSet, String url, String homeTitle) {
        try {
            Document document = Jsoup.connect(url).get();
            Element element = document.getElementById("imovel-boxes");
            //Element element = document.getElementsByClass("list--3NxGO").first();
            Elements elements = element.getElementsByTag("a");

            for(Element ad : elements) {
                ResponseDTO responseDTO = new ResponseDTO();
                if(StringUtils.isNotEmpty(ad.attr("href"))) {

                    Elements elementByTag = ad.getElementsByTag("h3");

                    if (elementByTag.text().toUpperCase().contains(homeTitle.toUpperCase())) {
                        responseDTO.setTitle(elementByTag.text());
                        responseDTO.setUrl(String.format("%s", ad.attr("href")));
                    }

                }

                if(responseDTO.getUrl() != null) responseDTOSet.add(responseDTO);
            }


        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }


    private void extractDataFromOlx(Set<ResponseDTO> responseDtoSet, String url, String homeTitle) {

        try {
            Document document = Jsoup.connect(url).get();
            Element element = document.getElementById("ad-list");
            //Element element = document.getElementsByClass("list--3NxGO").first();
            Elements elements = element.getElementsByTag("a");

            for(Element ad : elements) {
                ResponseDTO responseDTO = new ResponseDTO();
                if(StringUtils.isNotEmpty(ad.attr("href"))) {

                    Elements elementByTag = ad.getElementsByTag("h2");

                    if (elementByTag.attr("aria-label").contains(homeTitle)) {
                        responseDTO.setTitle(elementByTag.text());
                        responseDTO.setUrl(String.format("%s", ad.attr("href")));
                    }

                }

                if(responseDTO.getUrl() != null) responseDtoSet.add(responseDTO);
            }


        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }
}
