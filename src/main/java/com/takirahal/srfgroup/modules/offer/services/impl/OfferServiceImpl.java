package com.takirahal.srfgroup.modules.offer.services.impl;

import com.takirahal.srfgroup.enums.TypeOffer;
import com.takirahal.srfgroup.exceptions.ResouorceNotFoundException;
import com.takirahal.srfgroup.exceptions.UnauthorizedException;
import com.takirahal.srfgroup.modules.cart.dto.OrderDTO;
import com.takirahal.srfgroup.modules.cart.repositories.CartRepository;
import com.takirahal.srfgroup.modules.commentoffer.repositories.CommentOfferRepository;
import com.takirahal.srfgroup.modules.offer.models.CountOffersByUser;
import com.takirahal.srfgroup.modules.offer.repositories.OfferImagesRepository;
import com.takirahal.srfgroup.modules.user.entities.User;
import com.takirahal.srfgroup.modules.user.exceptioins.AccountResourceException;
import com.takirahal.srfgroup.modules.offer.dto.OfferDTO;
import com.takirahal.srfgroup.modules.offer.dto.filter.OfferFilter;
import com.takirahal.srfgroup.modules.offer.entities.Offer;
import com.takirahal.srfgroup.modules.offer.mapper.CustomOfferMapper;
import com.takirahal.srfgroup.modules.offer.repositories.OfferRepository;
import com.takirahal.srfgroup.modules.offer.services.OfferService;
import com.takirahal.srfgroup.services.impl.ResizeImage;
import com.takirahal.srfgroup.services.impl.StorageService;
import com.takirahal.srfgroup.modules.user.dto.filter.UserOfferFilter;
import com.takirahal.srfgroup.utils.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import jakarta.persistence.criteria.Predicate;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OfferServiceImpl implements OfferService {

    private final Logger log = LoggerFactory.getLogger(OfferServiceImpl.class);

    @Autowired
    OfferRepository offerRepository;

    @Autowired
    CustomOfferMapper customOfferMapper;

    @Autowired
    StorageService storageService;

    @Autowired
    ResizeImage resizeImage;

    @Autowired
    CommentOfferRepository commentOfferRepository;

    @Autowired
    OfferImagesRepository offerImagesRepository;

    @Autowired
    CartRepository cartRepository;

    @Override
    public void uploadImages(List<MultipartFile> multipartFiles, @RequestParam("offerId") Long offerId) {
        log.debug("Request to upload images for offer id: {}", offerId);
        String pathAddProduct = storageService.getBaseStorageProductImages() + offerId;
        if (storageService.existPath(pathAddProduct)) { // Upload for Update offer
            storeImages(multipartFiles, pathAddProduct);
        } else { // Upload for new offer
            storageService.init(pathAddProduct);
            storeImages(multipartFiles, pathAddProduct);
        }
    }

    @Override
    public Resource loadFile(Long offerId, String filename) {
        Path rootLocation = Paths.get(storageService.getBaseStorageProductImages() + offerId);
        return  storageService.loadFile(filename, rootLocation);
    }

    @Override
    @Cacheable(value="Offer", key="#id")
    public Optional<OfferDTO> findOne(Long id) {
        log.debug("Request to get Offer : {}", id);
        return offerRepository.findById(id).map(customOfferMapper::toDtoDetailsOffer);
    }

    @Override
    public Page<OfferDTO> getOffersByCurrentUser(OfferFilter offerFilter, Pageable pageable) {
        log.debug("Request to get Offers for current user : {}", offerFilter);
        Long useId = SecurityUtils
                .getIdByCurrentUser();

        UserOfferFilter userOfferFilter = new UserOfferFilter();
        userOfferFilter.setId(useId);
        offerFilter.setUser(userOfferFilter);
        offerFilter.setBlockedByReported(null);

        return findByCriteria(offerFilter, pageable);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete Offer : {}", id);

        Offer offer = offerRepository.findById(id)
                .orElseThrow(() -> new ResouorceNotFoundException("Entity not found with id"));

        Long useId = SecurityUtils
                .getIdByCurrentUser();
        if (!Objects.equals(useId, offer.getUser().getId())) {
            throw new UnauthorizedException("Unauthorized action");
        }

        offer.setAvailable(false);
        offerRepository.save(offer);
        /*
        // Delete folder for images
        String pathAddProduct = storageService.getBaseStorageProductImages() + id;
        if (storageService.existPath(pathAddProduct)) {
            Path rootLocation = Paths.get(pathAddProduct);
            storageService.deleteFiles(rootLocation);
        }

        // Delete all comments
        commentOfferRepository.deleteCommentsByOfferId(id);

        // Delete all images_offer
        offerImagesRepository.deleteImagesByOfferId(id);

        // Delete entity
        offerRepository.deleteById(id);
        */
    }

    @Override
    public CountOffersByUser countAllOffersByUser(Long userId) {
        User user = new User();
        user.setId(userId);
        CountOffersByUser countOffersByUser = new CountOffersByUser();
        countOffersByUser.setCountSellOffers(offerRepository.countByTypeOfferAndUser(TypeOffer.SellOffer.toString(), user));
        countOffersByUser.setCountRentOffers(offerRepository.countByTypeOfferAndUser(TypeOffer.RentOffer.toString(), user));
        countOffersByUser.setCountFindOffers(offerRepository.countByTypeOfferAndUser(TypeOffer.FindOffer.toString(), user));
        return countOffersByUser;
    }

    @Override
    public Page<OfferDTO> getMostRequestedOffers() {
        cartRepository.getMostCarts(PageRequest.of(0,18));
        HashSet<OfferDTO> set = new HashSet();
        cartRepository.getMostCarts(PageRequest.of(0,18))
                .stream().forEach(cart -> {
                    set.add(customOfferMapper.toDtoSearchOffer(cart.getSellOffer()));
        });
        ArrayList<OfferDTO> offer_array
                = new ArrayList<>(set);
        Page<OfferDTO> offerDTOPage = new PageImpl<>(
                offer_array,
                PageRequest.of(0,18),
                set.size()
        );
        return offerDTOPage;
    }

    @Override
    // @Cacheable(value="Offers")
    public Page<OfferDTO> getPublicOffers(OfferFilter offerFilter, Pageable pageable) {
        offerFilter.setBlockedByReported(Boolean.FALSE);
        return findByCriteria(offerFilter, pageable);
    }

    private Page<OfferDTO> findByCriteria(OfferFilter offerFilter, Pageable page) {
        log.debug("find offers by criteria : {}, page: {}", page);
        return offerRepository.findAll(createSpecification(offerFilter), page).map(customOfferMapper::toDtoSearchOffer);
    }

    private void storeImages(List<MultipartFile> multipartFiles, String pathAddProduct){
        Path rootLocation = Paths.get(pathAddProduct);
        for(MultipartFile file : multipartFiles) {
            storageService.store(file, rootLocation);

            // Rotate Exif
            resizeImage.rotateImages(pathAddProduct + "/" + file.getOriginalFilename());

            resizeImage.resizeImageOffer(pathAddProduct + "/" + file.getOriginalFilename());
        }
    }

    protected Specification<Offer> createSpecification(OfferFilter offerFilter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (offerFilter.getTitle() != null && !offerFilter.getTitle().isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("title"), "%" + offerFilter.getTitle() + "%"));
            }

            if (offerFilter.getTypeOffer() != null && !offerFilter.getTypeOffer().isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("typeOffer"), offerFilter.getTypeOffer()));
            }

            if ( offerFilter.getUser() != null && offerFilter.getUser().getId() != null ) {
                predicates.add(criteriaBuilder.equal(root.get("user").get("id"), offerFilter.getUser().getId()));
            }

            if ( offerFilter.getAddress() != null && offerFilter.getAddress().getId() != null ) {
                predicates.add(criteriaBuilder.equal(root.get("address").get("id"), offerFilter.getAddress().getId()));
            }

            if ( offerFilter.getCategory() != null && offerFilter.getCategory().getId() != null ) {
                predicates.add(criteriaBuilder.equal(root.get("category").get("id"), offerFilter.getCategory().getId()));
            }

            if (offerFilter.getBlockedByReported() != null ) {
                 predicates.add(criteriaBuilder.equal(root.get("blockedByReported"), offerFilter.getBlockedByReported()));
            }

            query.orderBy(criteriaBuilder.desc(root.get("id")));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
