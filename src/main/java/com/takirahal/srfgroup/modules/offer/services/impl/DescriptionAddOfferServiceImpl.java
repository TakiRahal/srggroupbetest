package com.takirahal.srfgroup.modules.offer.services.impl;

import com.takirahal.srfgroup.exceptions.BadRequestAlertException;
import com.takirahal.srfgroup.modules.offer.dto.DescriptionAddOfferDTO;
import com.takirahal.srfgroup.modules.offer.entities.DescriptionAddOffer;
import com.takirahal.srfgroup.modules.offer.mapper.DescriptionAddOfferMapper;
import com.takirahal.srfgroup.modules.offer.repositories.DescriptionAddOfferRepository;
import com.takirahal.srfgroup.modules.offer.services.DescriptionAddOfferService;
import com.takirahal.srfgroup.modules.permission.enums.EPermission;
import com.takirahal.srfgroup.modules.user.services.AuthorityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

/**
 * Service Implementation for managing {@link DescriptionAddOffer}.
 */
@Service
@Transactional
public class DescriptionAddOfferServiceImpl implements DescriptionAddOfferService {

    private final Logger log = LoggerFactory.getLogger(DescriptionAddOfferServiceImpl.class);

    @Autowired
    DescriptionAddOfferRepository descriptionAddOfferRepository;

    @Autowired
    DescriptionAddOfferMapper descriptionAddOfferMapper;

    @Autowired
    AuthorityService authorityService;

    @Override
    public DescriptionAddOfferDTO save(DescriptionAddOfferDTO descriptionAddOfferDTO) {
        log.debug("Request to save DescriptionAddOffer : {}", descriptionAddOfferDTO);

        // Check Permission
        authorityService.checkForPermissions(EPermission.CRUD_DESCRIPTION_NEW_OFFER);

        if (descriptionAddOfferDTO.getId() != null) {
            throw new BadRequestAlertException("A new descriptionAddOffer cannot already have an ID idexists");
        }
        DescriptionAddOffer descriptionAddOffer = descriptionAddOfferMapper.toEntity(descriptionAddOfferDTO);
        descriptionAddOffer = descriptionAddOfferRepository.save(descriptionAddOffer);
        return descriptionAddOfferMapper.toDto(descriptionAddOffer);
    }

    @Override
    public Optional<DescriptionAddOfferDTO> partialUpdate(DescriptionAddOfferDTO descriptionAddOfferDTO) {
        log.debug("Request to partially update DescriptionAddOffer : {}", descriptionAddOfferDTO);

        // Check Permission
        authorityService.checkForPermissions(EPermission.CRUD_DESCRIPTION_NEW_OFFER);

        return descriptionAddOfferRepository
            .findById(descriptionAddOfferDTO.getId())
            .map(
                existingDescriptionAddOffer -> {
                    descriptionAddOfferMapper.partialUpdate(existingDescriptionAddOffer, descriptionAddOfferDTO);
                    return existingDescriptionAddOffer;
                }
            )
            .map(descriptionAddOfferRepository::save)
            .map(descriptionAddOfferMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DescriptionAddOfferDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DescriptionAddOffers");
        return descriptionAddOfferRepository.findAll(pageable).map(descriptionAddOfferMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DescriptionAddOfferDTO> findOne(Long id) {
        log.debug("Request to get DescriptionAddOffer : {}", id);
        return descriptionAddOfferRepository.findById(id).map(descriptionAddOfferMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DescriptionAddOfferDTO> findLastPublic() {
        log.debug("Request to get last public DescriptionAddOffer : {}", "findLastPublic");
        return descriptionAddOfferRepository.findTopByOrderByIdDesc().map(descriptionAddOfferMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DescriptionAddOffer : {}", id);
        descriptionAddOfferRepository.deleteById(id);
    }

    @Override
    public DescriptionAddOfferDTO update(DescriptionAddOfferDTO descriptionAddOfferDTO, Long id) {
        if (descriptionAddOfferDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id idnull");
        }
        if (!Objects.equals(id, descriptionAddOfferDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID idinvalid");
        }

        if (!descriptionAddOfferRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found idnotfound");
        }

        DescriptionAddOffer descriptionAddOffer = descriptionAddOfferMapper.toEntity(descriptionAddOfferDTO);
        descriptionAddOffer = descriptionAddOfferRepository.save(descriptionAddOffer);
        return descriptionAddOfferMapper.toDto(descriptionAddOffer);
    }

    @Override
    public Page<DescriptionAddOfferDTO> findByCriteria(Pageable pageable) {
        return descriptionAddOfferRepository.findAll(pageable).map(descriptionAddOfferMapper::toDto);
    }
}
