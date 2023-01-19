package com.takirahal.srfgroup.modules.offer.controllers;

import com.takirahal.srfgroup.modules.offer.dto.AdvertisingPerPeriodDTO;
import com.takirahal.srfgroup.modules.offer.dto.OfferDTO;
import com.takirahal.srfgroup.modules.offer.services.AdvertisingPerPeriodService;
import com.takirahal.srfgroup.utils.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/advertising-per-period/")
public class AdvertisingPerPeriodController {

    private final Logger log = LoggerFactory.getLogger(AdvertisingPerPeriodController.class);

    @Autowired
    AdvertisingPerPeriodService advertisingPerPeriodService;

    /**
     *
     * @param advertising
     * @return
     */
    @PostMapping("admin/create")
    public ResponseEntity<AdvertisingPerPeriodDTO> createAdvertisingPerPeriod(@RequestBody AdvertisingPerPeriodDTO advertising){
        log.info("REST request to save AdvertisingPerPeriod : {}", advertising);
        AdvertisingPerPeriodDTO result = advertisingPerPeriodService.save(advertising);
        return new ResponseEntity<>(result, HeaderUtil.createAlert("add_offer.message_create_advertising_per_period_succefull", ""), HttpStatus.CREATED);
    }

    /**
     *
     * @param pageable
     * @return
     */
    @GetMapping("admin")
    public ResponseEntity<Page<AdvertisingPerPeriodDTO>> getAllAdvertisingPerPeriod(Pageable pageable) {
        log.info("REST request to get AdvertisingPerPeriod by criteria: {}", pageable);
        Page<AdvertisingPerPeriodDTO> page = advertisingPerPeriodService.findByCriteria(pageable);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @PutMapping("admin/{id}")
    public ResponseEntity<Boolean> unavailableAdvertisingPerPeriod(@PathVariable Long id) {
        log.debug("REST request to update unavailable AdvertisingPerPeriod : {}", id);
        advertisingPerPeriodService.unavailable(id);
        return new ResponseEntity<>(true, HeaderUtil.createAlert("favorite.user.message_remove_favorite_user", id.toString()), HttpStatus.OK);
    }


    @GetMapping("public/advertising-search")
    public ResponseEntity<OfferDTO> getAdvertisingPerPeriod() {
        log.info("REST request to get OfferDTO by advertising: {}");
        OfferDTO result = advertisingPerPeriodService.getAdvertisingPerPeriod();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
