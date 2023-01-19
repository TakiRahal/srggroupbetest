package com.takirahal.srfgroup.modules.offer.mapper;

import com.takirahal.srfgroup.modules.offer.dto.OfferDTO;
import com.takirahal.srfgroup.modules.offer.entities.FindOffer;
import com.takirahal.srfgroup.modules.offer.entities.Offer;
import com.takirahal.srfgroup.modules.offer.entities.RentOffer;
import com.takirahal.srfgroup.modules.offer.entities.SellOffer;
import com.takirahal.srfgroup.enums.TypeOffer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomOfferMapper {

    @Autowired
    RentOfferMapper rentOfferMapper;

    @Autowired
    FindOfferMapper findOfferMapper;

    @Autowired
    SellOfferMapper sellOfferMapper;

    @Autowired
    OfferMapper offerMapper;

    public OfferDTO toDto(Offer offer) {
        if (offer == null) {
            return null;
        }

        if (offer.getTypeOffer().equalsIgnoreCase(TypeOffer.SellOffer.toString())) {
            SellOffer sellOffer = (SellOffer) offer;
            return sellOfferMapper.toDto(sellOffer);
        } else if (offer.getTypeOffer().equalsIgnoreCase(TypeOffer.RentOffer.toString())) {
            RentOffer rentOffer = (RentOffer) offer;
            return rentOfferMapper.toDto(rentOffer);
        } else if (offer.getTypeOffer().equalsIgnoreCase(TypeOffer.FindOffer.toString())) {
            FindOffer findOffer = (FindOffer) offer;
            return findOfferMapper.toDto(findOffer);
        }

        return offerMapper.toDto(offer);
    }


    public OfferDTO toDtoSearchOffer(Offer offer) {
        if (offer == null) {
            return null;
        }

        if (offer.getTypeOffer().equalsIgnoreCase(TypeOffer.SellOffer.toString())) {
            SellOffer sellOffer = (SellOffer) offer;
            return sellOfferMapper.toDtoSearchOffers(sellOffer);
        } else if (offer.getTypeOffer().equalsIgnoreCase(TypeOffer.RentOffer.toString())) {
            RentOffer rentOffer = (RentOffer) offer;
            return rentOfferMapper.toDtoSearchOffers(rentOffer);
        } else if (offer.getTypeOffer().equalsIgnoreCase(TypeOffer.FindOffer.toString())) {
            FindOffer findOffer = (FindOffer) offer;
            return findOfferMapper.toDtoSearchOffers(findOffer);
        }

        return offerMapper.toDto(offer);
    }


    public OfferDTO toDtoDetailsOffer(Offer offer) {
        if (offer == null) {
            return null;
        }

        if (offer.getTypeOffer().equalsIgnoreCase(TypeOffer.SellOffer.toString())) {
            SellOffer sellOffer = (SellOffer) offer;
            return sellOfferMapper.toDtoDetailsOffer(sellOffer);
        } else if (offer.getTypeOffer().equalsIgnoreCase(TypeOffer.RentOffer.toString())) {
            RentOffer rentOffer = (RentOffer) offer;
            return rentOfferMapper.toDtoDetailsOffer(rentOffer);
        } else if (offer.getTypeOffer().equalsIgnoreCase(TypeOffer.FindOffer.toString())) {
            FindOffer findOffer = (FindOffer) offer;
            return findOfferMapper.toDtoDetailsOffer(findOffer);
        }

        return offerMapper.toDto(offer);
    }
}
