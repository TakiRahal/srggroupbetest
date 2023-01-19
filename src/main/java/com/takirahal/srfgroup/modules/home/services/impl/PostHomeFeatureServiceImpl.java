package com.takirahal.srfgroup.modules.home.services.impl;

import com.takirahal.srfgroup.exceptions.ResouorceNotFoundException;
import com.takirahal.srfgroup.exceptions.UnauthorizedException;
import com.takirahal.srfgroup.modules.home.dto.PostHomeFeatureDTO;
import com.takirahal.srfgroup.modules.home.entities.PostHomeFeature;
import com.takirahal.srfgroup.modules.home.mapper.PostHomeFeatureMapper;
import com.takirahal.srfgroup.modules.home.repositories.PostHomeFeatureRepository;
import com.takirahal.srfgroup.modules.home.services.PostHomeFeatureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class PostHomeFeatureServiceImpl implements PostHomeFeatureService {

    private final Logger log = LoggerFactory.getLogger(PostHomeFeatureServiceImpl.class);

    @Autowired
    PostHomeFeatureRepository postHomeFeatureRepository;

    @Autowired
    PostHomeFeatureMapper postHomeFeatureMapper;

    @Override
    public PostHomeFeatureDTO save(PostHomeFeatureDTO postHomeFeatureDTO) {
        PostHomeFeature postHomeFeature = postHomeFeatureMapper.toEntity(postHomeFeatureDTO);
        postHomeFeature = postHomeFeatureRepository.save(postHomeFeature);
        return postHomeFeatureMapper.toDto(postHomeFeature);
    }

    @Override
    public Optional<PostHomeFeatureDTO> findOne(Long id) {
        return postHomeFeatureRepository.findById(id).map(postHomeFeatureMapper::toDto);
    }

    @Override
    public Page<PostHomeFeatureDTO> findByCriteria(Pageable pageable) {
        return postHomeFeatureRepository.findAll(pageable).map(postHomeFeatureMapper::toDto);
    }

    @Override
    public Optional<PostHomeFeatureDTO> findLastOne() {
        log.debug("Request to get PostHomeFeature : {}");
        return postHomeFeatureRepository.findTopByOrderByIdDesc().map(postHomeFeatureMapper::toDto);
    }

    @Override
    public PostHomeFeatureDTO update(Long id, PostHomeFeatureDTO postHomeFeatureDTO) {

        if (!Objects.equals(id, postHomeFeatureDTO.getId())) {
            throw new UnauthorizedException("Unauthorized action");
        }

        PostHomeFeature postHomeFeature = postHomeFeatureRepository.findById(id)
                .orElseThrow(() -> new ResouorceNotFoundException("Entity not found with id"));

        postHomeFeature.setDescriptionAr(postHomeFeatureDTO.getDescriptionAr());
        postHomeFeature.setDescriptionFr(postHomeFeatureDTO.getDescriptionFr());
        postHomeFeature.setDescriptionEn(postHomeFeatureDTO.getDescriptionEn());
        postHomeFeature.setImage(postHomeFeatureDTO.getImage());

        postHomeFeature = postHomeFeatureRepository.save(postHomeFeature);
        return postHomeFeatureMapper.toDto(postHomeFeature);
    }
}
