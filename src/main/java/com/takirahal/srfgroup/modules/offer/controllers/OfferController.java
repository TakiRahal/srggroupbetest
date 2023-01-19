package com.takirahal.srfgroup.modules.offer.controllers;

import com.takirahal.srfgroup.modules.offer.dto.OfferDTO;
import com.takirahal.srfgroup.modules.offer.dto.filter.OfferFilter;
import com.takirahal.srfgroup.exceptions.ResouorceNotFoundException;
import com.takirahal.srfgroup.modules.offer.dto.OfferWithMyFavoriteUserDTO;
import com.takirahal.srfgroup.modules.favoriteuser.services.FavoriteUserService;
import com.takirahal.srfgroup.modules.offer.models.CountOffersByUser;
import com.takirahal.srfgroup.modules.offer.services.OfferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/offer/")
public class OfferController {

    private final Logger log = LoggerFactory.getLogger(OfferController.class);

    @Autowired
    OfferService offerService;

    @Autowired
    FavoriteUserService favoriteUserService;

    /**
     * {@code GET  /offers} : get all the offers.
     *
     * @param pageable the pagination information.
     // * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of offers in body.
     */
    @GetMapping("public")
    public ResponseEntity<Page<OfferDTO>> getAllOffers(OfferFilter offerFilter, Pageable pageable) {
        log.info("REST request to get Offers by criteria: {}", pageable);
        Page<OfferDTO> page = offerService.getPublicOffers(offerFilter, pageable);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    /**
     * {@code GET  /offers/:id} : get the "id" offer.
     *
     * @param id the id of the offerDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the offerDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("public/{id}")
    public ResponseEntity<OfferWithMyFavoriteUserDTO> getOfferWithFavorite(@PathVariable Long id) {
        log.info("REST request to get Offer with id: {}", id);
        Optional<OfferDTO> offerDTO = offerService.findOne(id);
        if(!offerDTO.isPresent()){
            throw new ResouorceNotFoundException("Not found offer with id");
        }
        OfferWithMyFavoriteUserDTO offerWithMyFavoriteUserDTO = favoriteUserService.getOfferWithMyFavoriteUser(offerDTO);
        return new ResponseEntity<>(offerWithMyFavoriteUserDTO, HttpStatus.OK);
    }


    /**
     * {@code GET  /offers} : get all the offers.
     *
     * @param pageable the pagination information.
     * @param offerFilter the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of offers in body.
     */
    @GetMapping("current-user")
    public ResponseEntity<Page<OfferDTO>> getOffersByCurrentUser(OfferFilter offerFilter, Pageable pageable) {
        log.info("REST request to get Offers by criteria: {}", offerFilter);
        Page<OfferDTO> page = offerService.getOffersByCurrentUser(offerFilter, pageable);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }


    @GetMapping("public/most-requested")
    public ResponseEntity<Page<OfferDTO>> getOffersMostRequested() {
        log.info("REST request to get Most Requested Offers by criteria: {}");
        Page<OfferDTO> page = offerService.getMostRequestedOffers();
        return new ResponseEntity<>(page, HttpStatus.OK);
    }


    /**
     * {@code DELETE  /offers/:id} : delete the "id" offer.
     *
     * @param id the id of the offerDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteOffer(@PathVariable Long id) {
        log.info("REST request to delete Offer : {}", id);
        offerService.delete(id);
        return new ResponseEntity<>("true", HttpStatus.OK);
    }


    /**
     * {@code GET  /offers/:id} : get the "id" offer.
     *
     * @param id the id of the offerDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the offerDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("{id}")
    public ResponseEntity<OfferDTO> getOffer(@PathVariable Long id) {
        log.info("REST request to get Offer : {}", id);
        Optional<OfferDTO> offerDTO = offerService.findOne(id);
        return new ResponseEntity<>(offerDTO.get(), HttpStatus.OK);
    }


    @GetMapping("public/count-offers-by-user/{id}")
    public ResponseEntity<CountOffersByUser> countAllOffersByUser(@PathVariable Long id) {
        log.info("REST request to get count Offers : {}", id);
        return new ResponseEntity<>(offerService.countAllOffersByUser(id), HttpStatus.OK);
    }

    /**
     *
     * @param multipartFiles
     * @param offerId
     * @return
     */
    @PostMapping("upload-images")
    public ResponseEntity<Boolean> uploadFiles(@RequestParam("files")List<MultipartFile> multipartFiles, @RequestParam("offerId") Long offerId) {
        log.info("REST request to upload images offer : {}", offerId);
        offerService.uploadImages(multipartFiles, offerId);
        return ResponseEntity.ok().body(true);
    }


    /**
     *
     * @param offerId
     * @param filename
     * @return
     */
    @GetMapping("public/files/{offerId}/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable Long offerId, @PathVariable String filename) {
        Resource file = offerService.loadFile(offerId, filename);
        if(file!=null){
            return ResponseEntity
                    .ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                    .body(file);
        }
        else{
            return ResponseEntity
                    .ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=")
                    .body(null);
        }
    }
}
