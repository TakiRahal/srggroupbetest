package com.takirahal.srfgroup.modules.home.services.impl;

import com.takirahal.srfgroup.constants.SrfGroupConstants;
import com.takirahal.srfgroup.enums.SourceConnectedDevice;
import com.takirahal.srfgroup.exceptions.BadRequestAlertException;
import com.takirahal.srfgroup.exceptions.ResouorceNotFoundException;
import com.takirahal.srfgroup.exceptions.UnauthorizedException;
import com.takirahal.srfgroup.modules.cart.services.impl.CartServiceImpl;
import com.takirahal.srfgroup.modules.home.dto.TopHomeSlidesImageDTO;
import com.takirahal.srfgroup.modules.home.entities.TopHomeSlidesImage;
import com.takirahal.srfgroup.modules.home.mapper.TopHomeSlidesImageMapper;
import com.takirahal.srfgroup.modules.home.repositories.TopHomeSlidesImageRepository;
import com.takirahal.srfgroup.modules.home.services.TopHomeSlidesImageService;
import com.takirahal.srfgroup.utils.RequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TopHomeSlidesImageServiceImpl implements TopHomeSlidesImageService {

    private final Logger log = LoggerFactory.getLogger(TopHomeSlidesImageServiceImpl.class);

    @Autowired
    TopHomeSlidesImageRepository topHomeSlidesImageRepository;

    @Autowired
    TopHomeSlidesImageMapper topHomeSlidesImageMapper;

    @Override
    public TopHomeSlidesImageDTO save(TopHomeSlidesImageDTO topHomeSlidesImageDTO) {

        if (topHomeSlidesImageDTO.getId() != null) {
            throw new BadRequestAlertException("A new faq cannot already have an ID idexists");
        }

        TopHomeSlidesImage topHomeSlidesImage = topHomeSlidesImageMapper.toEntity(topHomeSlidesImageDTO);
        topHomeSlidesImage = topHomeSlidesImageRepository.save(topHomeSlidesImage);
        return topHomeSlidesImageMapper.toDto(topHomeSlidesImage);
    }

    @Override
    public Page<TopHomeSlidesImageDTO> findByCriteria(Pageable pageable) {
        return topHomeSlidesImageRepository.findAll(pageable).map(item -> {
            if( RequestUtil.getHeaderAttribute(SrfGroupConstants.SOURCE_CONNECTED_DEVICE).equals(SourceConnectedDevice.WEB_BROWSER.toString()) ){
                item.setImageMobile("");
            }
            else if( RequestUtil.getHeaderAttribute(SrfGroupConstants.SOURCE_CONNECTED_DEVICE).equals(SourceConnectedDevice.MOBILE_BROWSER.toString()) ){
                item.setImageDesktop("");
            }
            return topHomeSlidesImageMapper.toDto(item);
        });
    }

    @Override
    public Optional<TopHomeSlidesImageDTO> findOne(Long id) {
        return topHomeSlidesImageRepository.findById(id).map(topHomeSlidesImageMapper::toDto);
    }

    @Override
    public TopHomeSlidesImageDTO update(Long id, TopHomeSlidesImageDTO topHomeSlidesImageDTO) {

        if (!Objects.equals(id, topHomeSlidesImageDTO.getId())) {
            throw new UnauthorizedException("Unauthorized action");
        }

        TopHomeSlidesImage topHomeSlidesImage = topHomeSlidesImageRepository.findById(id)
                .orElseThrow(() -> new ResouorceNotFoundException("Entity not found with id"));
        topHomeSlidesImage.setDescriptionAr(topHomeSlidesImageDTO.getDescriptionAr());
        topHomeSlidesImage.setDescriptionFr(topHomeSlidesImageDTO.getDescriptionFr());
        topHomeSlidesImage.setDescriptionEn(topHomeSlidesImageDTO.getDescriptionEn());
        topHomeSlidesImage.setImageDesktop(topHomeSlidesImageDTO.getImageDesktop());
        topHomeSlidesImage.setImageMobile(topHomeSlidesImageDTO.getImageMobile());

        topHomeSlidesImage = topHomeSlidesImageRepository.save(topHomeSlidesImage);
        return topHomeSlidesImageMapper.toDto(topHomeSlidesImage);
    }

    @Override
    public Page<TopHomeSlidesImageDTO> getTopHomeSlidesByAdmin(Pageable pageable) {
        return topHomeSlidesImageRepository.findAll(pageable).map(topHomeSlidesImageMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TopHomeSlidesImage : {}", id);
        TopHomeSlidesImage topHomeSlidesImage = topHomeSlidesImageRepository.findById(id)
                .orElseThrow(() -> new ResouorceNotFoundException("Entity not found with id"));
        topHomeSlidesImageRepository.deleteById(id);
    }

}
