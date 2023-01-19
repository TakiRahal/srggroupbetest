package com.takirahal.srfgroup.modules.aboutus.services;

import com.takirahal.srfgroup.modules.aboutus.dto.AboutUsDTO;
import com.takirahal.srfgroup.modules.aboutus.dto.filter.AboutUsFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface AboutUsService {

    /**
     *
     * @param aboutUsDTO
     * @return
     */
    AboutUsDTO save(AboutUsDTO aboutUsDTO);


    /**
     *
     * @param aboutUsFilter
     * @param pageable
     * @return
     */
    Page<AboutUsDTO> findByCriteria(AboutUsFilter aboutUsFilter, Pageable pageable);


    /**
     *
     * @return
     */
    String findLastOne();
}
