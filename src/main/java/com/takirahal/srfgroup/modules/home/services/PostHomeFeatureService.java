package com.takirahal.srfgroup.modules.home.services;

import com.takirahal.srfgroup.modules.home.dto.PostHomeFeatureDTO;
import com.takirahal.srfgroup.modules.home.entities.PostHomeFeature;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PostHomeFeatureService {

    /**
     *
     * @param postHomeFeatureDTO
     * @return
     */
    PostHomeFeatureDTO save(PostHomeFeatureDTO postHomeFeatureDTO);

    /**
     *
     * @param id
     * @return
     */
    Optional<PostHomeFeatureDTO> findOne(Long id);

    Page<PostHomeFeatureDTO> findByCriteria(Pageable pageable);

    Optional<PostHomeFeatureDTO> findLastOne();

    PostHomeFeatureDTO update(Long id, PostHomeFeatureDTO postHomeFeatureDTO);
}
