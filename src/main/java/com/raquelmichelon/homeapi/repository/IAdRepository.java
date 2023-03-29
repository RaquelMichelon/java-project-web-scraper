package com.raquelmichelon.homeapi.repository;

import com.raquelmichelon.homeapi.model.Ad;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IAdRepository extends PagingAndSortingRepository<Long, Ad> {

}
