package com.takirahal.srfgroup.modules.offer.services.impl;

import com.takirahal.srfgroup.exceptions.BadRequestAlertException;
import com.takirahal.srfgroup.exceptions.ResouorceNotFoundException;
import com.takirahal.srfgroup.modules.offer.dto.ReportOfferDTO;
import com.takirahal.srfgroup.modules.offer.entities.Offer;
import com.takirahal.srfgroup.modules.offer.entities.ReportOffer;
import com.takirahal.srfgroup.modules.offer.mapper.OfferMapper;
import com.takirahal.srfgroup.modules.offer.mapper.ReportOfferMapper;
import com.takirahal.srfgroup.modules.offer.repositories.OfferRepository;
import com.takirahal.srfgroup.modules.offer.repositories.ReportOfferRepository;
import com.takirahal.srfgroup.modules.offer.services.ReportOfferService;
import com.takirahal.srfgroup.modules.user.dto.UserDTO;
import com.takirahal.srfgroup.modules.user.exceptioins.AccountResourceException;
import com.takirahal.srfgroup.modules.user.mapper.UserMapper;
import com.takirahal.srfgroup.utils.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportOfferServiceImpl implements ReportOfferService {

    private final Logger log = LoggerFactory.getLogger(ReportOfferServiceImpl.class);

    @Autowired
    ReportOfferRepository reportOfferRepository;

    @Autowired
    UserMapper userMapper;

    @Autowired
    ReportOfferMapper reportOfferMapper;

    @Autowired
    OfferMapper offerMapper;

    @Autowired
    OfferRepository offerRepository;

    @Override
    public ReportOfferDTO save(ReportOfferDTO reportOfferDTO) {

        log.debug("Request to save Favorite User: {}", reportOfferDTO);

        if (reportOfferDTO.getId() != null) {
            throw new BadRequestAlertException("A new ReportOffer cannot already have an ID idexists");
        }

        UserDTO currentUser = SecurityUtils.getCurrentUser()
                .map(userMapper::toCurrentUserPrincipal)
                .orElseThrow(() -> new AccountResourceException("Current user login not found"));

        Offer offer = offerMapper.toEntity(reportOfferDTO.getOffer());

        // Check if exist
        if( reportOfferRepository.existsByOfferAndUser(offer, userMapper.toEntity(currentUser)) ){
            throw new BadRequestAlertException("Already reported");
        }

        reportOfferDTO.setUser(currentUser);
        ReportOffer reportOffer = reportOfferMapper.toEntity(reportOfferDTO);
        reportOffer = reportOfferRepository.save(reportOffer);

        // Update status report offer
        if(reportOfferRepository.nbeReportedByOfferId(reportOfferDTO.getOffer().getId()) >=2){
            Offer offerUpdate = offerRepository.findById(reportOfferDTO.getOffer().getId())
                    .orElseThrow(() -> new ResouorceNotFoundException("Offer not found"));
            offerUpdate.setBlockedByReported(true);
            offerRepository.save(offerUpdate);
        }

        return reportOfferMapper.toDto(reportOffer);
    }
}
