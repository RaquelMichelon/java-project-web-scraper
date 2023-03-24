package com.raquelmichelon.homeapi.service;

import com.raquelmichelon.homeapi.model.ResponseDTO;

import java.util.Set;

public interface ScraperService {

    Set<ResponseDTO> getHomeByTitle(String homeTitle);

}
