package com.takirahal.srfgroup.modules.offer.services.impl;

import com.takirahal.srfgroup.exceptions.BadRequestAlertException;
import com.takirahal.srfgroup.modules.favoriteuser.dto.filter.FavoriteUserFilter;
import com.takirahal.srfgroup.modules.favoriteuser.entities.FavoriteUser;
import com.takirahal.srfgroup.modules.offer.controllers.AdvertisingPerPeriodController;
import com.takirahal.srfgroup.modules.offer.dto.AdvertisingPerPeriodDTO;
import com.takirahal.srfgroup.modules.offer.dto.OfferDTO;
import com.takirahal.srfgroup.modules.offer.entities.AdvertisingPerPeriod;
import com.takirahal.srfgroup.modules.offer.mapper.AdvertisingPerPeriodMapper;
import com.takirahal.srfgroup.modules.offer.repositories.AdvertisingPerPeriodRepository;
import com.takirahal.srfgroup.modules.offer.services.AdvertisingPerPeriodService;
import com.takirahal.srfgroup.modules.offer.services.OfferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AdvertisingPerPeriodServiceImpl implements AdvertisingPerPeriodService {

    private final Logger log = LoggerFactory.getLogger(AdvertisingPerPeriodServiceImpl.class);

    @Autowired
    AdvertisingPerPeriodRepository advertisingPerPeriodRepository;

    @Autowired
    AdvertisingPerPeriodMapper advertisingPerPeriodMapper;

    @Autowired
    OfferService offerService;

    @Override
    public AdvertisingPerPeriodDTO save(AdvertisingPerPeriodDTO advertising) {
        log.debug("Request to save AdvertisingPerPeriod : {}", advertising);
        Optional<AdvertisingPerPeriod> checkAdvertisingPerPeriod = advertisingPerPeriodRepository.findByAvailable(true);
        if(checkAdvertisingPerPeriod.isPresent()){
            throw new BadRequestAlertException("Already exist another offer, please remove this before add a new offer");
        }
        AdvertisingPerPeriod advertisingPerPeriod = advertisingPerPeriodRepository.save(advertisingPerPeriodMapper.toEntity(advertising));
        return advertisingPerPeriodMapper.toDto(advertisingPerPeriod);
    }

    @Override
    public Page<AdvertisingPerPeriodDTO> findByCriteria(Pageable pageable) {
        return advertisingPerPeriodRepository.findAll(createSpecification(), pageable).map(advertisingPerPeriodMapper::toDto);
    }

    @Override
    public void unavailable(Long id) {
        Optional<AdvertisingPerPeriod> advertisingPerPeriod = advertisingPerPeriodRepository.findById(id);
        if( advertisingPerPeriod.isPresent() ){
            AdvertisingPerPeriod advertisingPerPeriodNew = advertisingPerPeriod.get();
            advertisingPerPeriodNew.setAvailable(Boolean.FALSE);
            advertisingPerPeriodRepository.save(advertisingPerPeriodNew);
        }
    }

    @Override
    public OfferDTO getAdvertisingPerPeriod() {
        Optional<AdvertisingPerPeriod> checkAdvertisingPerPeriod = advertisingPerPeriodRepository.findByAvailable(true);
        if(checkAdvertisingPerPeriod.isPresent()){
            Optional<OfferDTO> offerDTO = offerService.findOne(checkAdvertisingPerPeriod.get().getOffer().getId());
            if(offerDTO.isPresent() ){
                return offerDTO.get();
            }
        }
        return null;
    }

    protected Specification<AdvertisingPerPeriod> createSpecification() {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            query.orderBy(criteriaBuilder.desc(root.get("available")));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
