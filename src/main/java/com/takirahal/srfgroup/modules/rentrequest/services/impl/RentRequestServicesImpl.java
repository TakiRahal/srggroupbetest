package com.takirahal.srfgroup.modules.rentrequest.services.impl;

import com.takirahal.srfgroup.exceptions.BadRequestAlertException;
import com.takirahal.srfgroup.exceptions.ResouorceNotFoundException;
import com.takirahal.srfgroup.exceptions.UnauthorizedException;
import com.takirahal.srfgroup.modules.notification.entities.Notification;
import com.takirahal.srfgroup.modules.notification.enums.ModuleNotification;
import com.takirahal.srfgroup.modules.notification.repositories.NotificationRepository;
import com.takirahal.srfgroup.modules.offer.entities.RentOffer;
import com.takirahal.srfgroup.modules.offer.mapper.RentOfferMapper;
import com.takirahal.srfgroup.modules.offer.repositories.RentOfferRepository;
import com.takirahal.srfgroup.modules.rentrequest.dto.RentRequestDTO;
import com.takirahal.srfgroup.modules.rentrequest.dto.filter.RentRequestFilter;
import com.takirahal.srfgroup.modules.rentrequest.entities.RentRequest;
import com.takirahal.srfgroup.modules.rentrequest.enums.StatusRentRequest;
import com.takirahal.srfgroup.modules.rentrequest.mapper.RentRequestMapper;
import com.takirahal.srfgroup.modules.rentrequest.repositories.RentRequestRepository;
import com.takirahal.srfgroup.modules.rentrequest.services.RentRequestService;
import com.takirahal.srfgroup.modules.user.dto.UserDTO;
import com.takirahal.srfgroup.modules.user.dto.filter.UserFilter;
import com.takirahal.srfgroup.modules.user.entities.User;
import com.takirahal.srfgroup.modules.user.exceptioins.AccountResourceException;
import com.takirahal.srfgroup.modules.user.mapper.UserMapper;
import com.takirahal.srfgroup.modules.user.repositories.UserRepository;
import com.takirahal.srfgroup.modules.user.services.UserOneSignalService;
import com.takirahal.srfgroup.security.UserPrincipal;
import com.takirahal.srfgroup.utils.CommonUtil;
import com.takirahal.srfgroup.utils.RequestUtil;
import com.takirahal.srfgroup.utils.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jakarta.persistence.criteria.Predicate;
import java.time.Instant;
import java.util.*;

@Service
public class RentRequestServicesImpl implements RentRequestService {

    private final Logger log = LoggerFactory.getLogger(RentRequestServicesImpl.class);

    @Autowired
    RentRequestRepository rentRequestRepository;

    @Autowired
    RentRequestMapper rentRequestMapper;

    @Autowired
    RentOfferMapper rentOfferMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MessageSource messageSource;

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    UserOneSignalService userOneSignalService;

    @Autowired
    UserMapper userMapper;

    @Autowired
    RentOfferRepository rentOfferRepository;

    @Override
    public RentRequestDTO save(RentRequestDTO rentRequestDTO) {
        log.debug("Request to save RentRequest : {}", rentRequestDTO);

        UserPrincipal currentUser = SecurityUtils.getCurrentUser().orElseThrow(() -> new AccountResourceException("Current user login not found"));

        if( rentRequestDTO.getReceiverUser().getId().equals(currentUser.getId())){
            throw new BadRequestAlertException("Not allowed");
        }

        UserDTO userDTO = new UserDTO();
        User user = new User();
        user.setId(currentUser.getId());
        userDTO.setId(currentUser.getId());

        RentOffer rentOffer = rentOfferMapper.toEntity(rentRequestDTO.getRentOffer());

        Optional<RentRequest> rentRequest = rentRequestRepository.findByRentOfferAndSenderUser(rentOffer, user);

        if( rentRequest.isPresent() &&
                (rentRequest.get().getRentOffer().getAvailable()==null || !rentRequest.get().getRentOffer().getAvailable()) ){
            throw new BadRequestAlertException(RequestUtil.messageTranslate("details_offer.not_available_offer"));
        }

        if( !rentRequest.isPresent() ){
            rentRequestDTO.setSenderUser(userDTO);
            rentRequestDTO.setSendDate(Instant.now());
            rentRequestDTO.setStatus(StatusRentRequest.STANDBY.toString());
            RentRequest rentRequestNew = rentRequestMapper.toEntity(rentRequestDTO);
            rentRequestNew = rentRequestRepository.save(rentRequestNew);


            User userReceiver = userRepository.findById(rentRequestDTO.getReceiverUser().getId())
                    .orElseThrow(() -> new ResouorceNotFoundException("Entity not found with id"));
            Locale locale = Locale.forLanguageTag(!userReceiver.getLangKey().equals("") ? userReceiver.getLangKey() : "fr");
            String messageForUserReceiver = CommonUtil.getFullNameUser(userMapper.toCurrentUserPrincipal(currentUser))+" "+messageSource.getMessage("rentrequest.message_for_user_receiver", null, locale);

            // Save notification
            sendNotificationForUserReceiver(userReceiver, messageForUserReceiver);

            // Send Push notif
            userOneSignalService.sendPushNotifForUser(userReceiver, messageForUserReceiver);

            return rentRequestMapper.toDto(rentRequestNew);
        }

        return rentRequestMapper.toDto(rentRequest.get());
    }

    @Override
    public Page<RentRequestDTO> getCartsByCurrentUserSent(RentRequestFilter rentRequestFilter, Pageable page) {
        log.debug("Request to get all RentRequest for current user : {}", rentRequestFilter);
        Long currentUserId = SecurityUtils.getIdByCurrentUser();
        UserFilter userFilter = new UserFilter();
        userFilter.setId(currentUserId);
        rentRequestFilter.setSenderUser(userFilter);
        return findByCriteria(rentRequestFilter, page);
    }

    @Override
    public Page<RentRequestDTO> getCartsByCurrentUserReceived(RentRequestFilter rentRequestFilter, Pageable page) {
        log.debug("Request to get all RentRequest for current user : {}", rentRequestFilter);
        Long currentUserId = SecurityUtils.getIdByCurrentUser();
        UserFilter userFilter = new UserFilter();
        userFilter.setId(currentUserId);
        rentRequestFilter.setReceiverUser(userFilter);
        return findByCriteria(rentRequestFilter, page);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete RentRequest : {}", id);

        RentRequest rentRequest = rentRequestRepository.findById(id)
                .orElseThrow(() -> new ResouorceNotFoundException("Entity not found with id"));

        Long useId = SecurityUtils.getIdByCurrentUser();
        if (!Objects.equals(useId, rentRequest.getSenderUser().getId())) {
            throw new UnauthorizedException("Unauthorized action");
        }

        if ( !rentRequest.getStatus().equals(StatusRentRequest.STANDBY.toString()) ) {
            throw new UnauthorizedException("Unauthorized action");
        }

        rentRequestRepository.deleteById(id);

    }

    @Override
    public void refusedRentRequestReceived(Long id) {
        RentRequest rentRequest = rentRequestRepository.findById(id)
                .orElseThrow(() -> new ResouorceNotFoundException("Entity not found with id"));

        Long useId = SecurityUtils.getIdByCurrentUser();
        if (!Objects.equals(useId, rentRequest.getReceiverUser().getId())) {
            throw new UnauthorizedException("Unauthorized action");
        }

        rentRequest.setStatus(StatusRentRequest.REFUSED.toString());
        rentRequestRepository.save(rentRequest);
    }

    @Override
    public void acceptRentRequestReceived(Long id, RentRequestDTO rentRequestDTO) {
        RentRequest rentRequest = rentRequestRepository.findById(id)
                .orElseThrow(() -> new ResouorceNotFoundException("Entity not found with id"));

        UserPrincipal currentUser = SecurityUtils.getCurrentUser().orElseThrow(() -> new AccountResourceException("Current user login not found"));

        if (!Objects.equals(currentUser.getId(), rentRequest.getReceiverUser().getId())) {
            throw new UnauthorizedException("Unauthorized action");
        }

        if( !Objects.equals(rentRequestDTO.getRentOffer().getAmount(), null) ||
            !Objects.equals(rentRequestDTO.getRentOffer().getStartDate(), null) ||
            !Objects.equals(rentRequestDTO.getRentOffer().getEndDate(), null) ||
            !Objects.equals(rentRequestDTO.getRentOffer().getTypePeriodRent(), null) ){
            RentOffer rentOffer = rentRequest.getRentOffer();
            rentOffer.setAmount(rentRequestDTO.getRentOffer().getAmount());
            rentOffer.setStartDate(rentRequestDTO.getRentOffer().getStartDate());
            rentOffer.setEndDate(rentRequestDTO.getRentOffer().getEndDate());
            rentOffer.setTypePeriodRent(rentRequestDTO.getRentOffer().getTypePeriodRent());
            rentOfferRepository.save(rentOffer);
        }

        rentRequest.setStatus(StatusRentRequest.ACCEPTED.toString());
        rentRequest.setImageSignatureReceived(rentRequestDTO.getImageSignatureReceived());
        rentRequestRepository.save(rentRequest);

        User userSent = userRepository.findById(rentRequestDTO.getSenderUser().getId())
                .orElseThrow(() -> new ResouorceNotFoundException("Entity not found with id"));
        Locale locale = Locale.forLanguageTag(!userSent.getLangKey().equals("") ? userSent.getLangKey() : "fr");
        String messageForAcceptUserReceiver = CommonUtil.getFullNameUser(userMapper.toCurrentUserPrincipal(currentUser))+" "+messageSource.getMessage("rentrequest.message_accept_for_user_receiver", null, locale);

        // Save notification
        sendNotificationForUserReceiver(userSent, messageForAcceptUserReceiver);

        // Send Push notif
        userOneSignalService.sendPushNotifForUser(userSent, messageForAcceptUserReceiver);
    }

    private Page<RentRequestDTO> findByCriteria(RentRequestFilter rentRequestFilter, Pageable page) {
        return rentRequestRepository.findAll(createSpecification(rentRequestFilter), page).map(rentRequestMapper::toDto);
    }

    private Specification<RentRequest> createSpecification(RentRequestFilter rentRequestFilter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if( rentRequestFilter.getStatus() != null){
                predicates.add(criteriaBuilder.equal(root.get("status"), rentRequestFilter.getStatus()));
            }

            if ( rentRequestFilter.getSenderUser() != null && rentRequestFilter.getSenderUser().getId() != null ) {
                predicates.add(criteriaBuilder.equal(root.get("senderUser").get("id"), rentRequestFilter.getSenderUser().getId()));
            }

            if ( rentRequestFilter.getReceiverUser() != null && rentRequestFilter.getReceiverUser().getId() != null ) {
                predicates.add(criteriaBuilder.equal(root.get("receiverUser").get("id"), rentRequestFilter.getReceiverUser().getId()));
            }

            query.orderBy(criteriaBuilder.desc(root.get("id")));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private void sendNotificationForUserReceiver(User user, String messageForUserReceiver){
        Notification notification = new Notification();
        notification.setDateCreated(Instant.now());
        notification.setContent(messageForUserReceiver);
        notification.setModule(ModuleNotification.RentRequestNotification.name());
        notification.setUser(user);
        notificationRepository.save(notification);
    }
}
