package com.takirahal.srfgroup.modules.offer.services.impl;

import com.takirahal.srfgroup.modules.offer.dto.OfferImagesDTO;
import com.takirahal.srfgroup.modules.offer.entities.OfferImages;
import com.takirahal.srfgroup.modules.offer.mapper.OfferImagesMapper;
import com.takirahal.srfgroup.modules.offer.dto.filter.OfferImagesFilter;
import com.takirahal.srfgroup.modules.offer.repositories.OfferImagesRepository;
import com.takirahal.srfgroup.modules.offer.services.OfferImagesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class OfferImagesServiceImpl implements OfferImagesService {

    private final Logger log = LoggerFactory.getLogger(OfferImagesServiceImpl.class);

    @Autowired
    OfferImagesRepository offerImagesRepository;

    @Autowired
    OfferImagesMapper offerImagesMapper;

    @Override
    public OfferImagesDTO save(OfferImagesDTO offerImagesDTO) {
        log.debug("Request to save OfferImages : {}", offerImagesDTO);
        OfferImages offerImages = offerImagesMapper.toEntity(offerImagesDTO);
        offerImages = offerImagesRepository.save(offerImages);
        return offerImagesMapper.toDto(offerImages);
    }

    @Override
    public Page<OfferImages> getListExistOfferImages(OfferImagesFilter offerImagesFilter, Pageable pageable) {
        log.debug("find by criteria : {}, page: {}", offerImagesFilter, pageable);
        Page<OfferImages> listExistOfferImages = offerImagesRepository.getListExistOfferImages(pageable);
        return listExistOfferImages;
    }
}
