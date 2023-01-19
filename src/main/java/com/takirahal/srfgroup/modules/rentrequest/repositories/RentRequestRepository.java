package com.takirahal.srfgroup.modules.rentrequest.repositories;

import com.takirahal.srfgroup.modules.offer.entities.RentOffer;
import com.takirahal.srfgroup.modules.rentrequest.entities.RentRequest;
import com.takirahal.srfgroup.modules.user.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RentRequestRepository extends JpaRepository<RentRequest, Long>, JpaSpecificationExecutor<RentRequest> {

    Optional<RentRequest> findByRentOfferAndSenderUser(RentOffer rentOffer, User senderUser);
}
