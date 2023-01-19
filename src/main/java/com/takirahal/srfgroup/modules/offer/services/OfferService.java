package com.takirahal.srfgroup.modules.offer.services;

import com.takirahal.srfgroup.modules.offer.dto.OfferDTO;
import com.takirahal.srfgroup.modules.offer.dto.filter.OfferFilter;
import com.takirahal.srfgroup.modules.offer.models.CountOffersByUser;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface OfferService {

    /**
     *
     * @param offerFilter
     * @param pageable
     * @return
     */
    Page<OfferDTO> getPublicOffers(OfferFilter offerFilter, Pageable pageable);

    /**
     *
     * @param multipartFiles
     * @param offerId
     */
    void uploadImages(List<MultipartFile> multipartFiles, Long offerId);

    /**
     *
     * @param offerId
     * @param filename
     * @return
     */
    Resource loadFile(Long offerId, String filename);

    /**
     *
     * @param id
     * @return
     */
    Optional<OfferDTO> findOne(Long id);

    /**
     *
     * @param offerFilter
     * @param pageable
     * @return
     */
    Page<OfferDTO> getOffersByCurrentUser(OfferFilter offerFilter, Pageable pageable);

    /**
     *
     * @param id
     */
    void delete(Long id);

    CountOffersByUser countAllOffersByUser(Long id);

    /**
     * Get most requested offers
     * @return
     */
    Page<OfferDTO> getMostRequestedOffers();
}
