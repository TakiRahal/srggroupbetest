package com.takirahal.srfgroup.modules.commentoffer.services.impl;

import com.takirahal.srfgroup.exceptions.ResouorceNotFoundException;
import com.takirahal.srfgroup.exceptions.UnauthorizedException;
import com.takirahal.srfgroup.modules.notification.enums.ModuleNotification;
import com.takirahal.srfgroup.modules.user.dto.UserDTO;
import com.takirahal.srfgroup.modules.user.entities.User;
import com.takirahal.srfgroup.modules.user.entities.UserOneSignal;
import com.takirahal.srfgroup.modules.user.exceptioins.AccountResourceException;
import com.takirahal.srfgroup.exceptions.BadRequestAlertException;
import com.takirahal.srfgroup.modules.commentoffer.dto.CommentOfferDTO;
import com.takirahal.srfgroup.modules.commentoffer.dto.filter.CommentOfferFilter;
import com.takirahal.srfgroup.modules.commentoffer.entities.CommentOffer;
import com.takirahal.srfgroup.modules.commentoffer.mapper.CommentOfferMapper;
import com.takirahal.srfgroup.modules.commentoffer.repositories.CommentOfferRepository;
import com.takirahal.srfgroup.modules.commentoffer.services.CommentOfferService;
import com.takirahal.srfgroup.modules.notification.dto.NotificationDTO;
import com.takirahal.srfgroup.modules.notification.services.NotificationService;
import com.takirahal.srfgroup.modules.offer.dto.OfferDTO;
import com.takirahal.srfgroup.modules.user.repositories.UserRepository;
import com.takirahal.srfgroup.modules.user.services.UserOneSignalService;
import com.takirahal.srfgroup.modules.user.services.UserService;
import com.takirahal.srfgroup.security.UserPrincipal;
import com.takirahal.srfgroup.services.impl.OneSignalService;
import com.takirahal.srfgroup.utils.CommonUtil;
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
import java.util.stream.Collectors;

@Service
public class CommentOfferServiceImpl implements CommentOfferService {

    private final Logger log = LoggerFactory.getLogger(CommentOfferServiceImpl.class);

    @Autowired
    CommentOfferRepository commentOfferRepository;

    @Autowired
    CommentOfferMapper commentOfferMapper;

    @Autowired
    NotificationService notificationService;

    @Autowired
    OneSignalService oneSignalService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MessageSource messageSource;

    @Autowired
    UserOneSignalService userOneSignalService;

    @Override
    public CommentOfferDTO save(CommentOfferDTO commentOfferDTO) {
        log.debug("Request to save CommentOffer : {}", commentOfferDTO);
        if (commentOfferDTO.getId() != null) {
            throw new BadRequestAlertException("A new commentOffer cannot already have an ID idexists");
        }

        UserPrincipal currentUser = SecurityUtils.getCurrentUser().orElseThrow(() -> new AccountResourceException("Current user login not found"));

        UserDTO currentUserDTO = new UserDTO();
        currentUserDTO.setId(currentUser.getId());
        currentUserDTO.setFirstName(currentUser.getFirstName());
        currentUserDTO.setLastName(currentUser.getLastName());
        currentUserDTO.setEmail(currentUser.getEmail());
        commentOfferDTO.setUser(currentUserDTO);

        CommentOffer commentOffer = commentOfferMapper.toEntity(commentOfferDTO);
        commentOffer = commentOfferRepository.save(commentOffer);

        // Add notification
        if (!commentOfferDTO.getOffer().getUser().getId().equals(currentUser.getId())) {

            Optional<User> userDestination = userRepository.findById(commentOfferDTO.getOffer().getUser().getId());
            Locale locale = Locale.forLanguageTag(userDestination.get().getLangKey()!=null && !userDestination.get().getLangKey().equals("") ? userDestination.get().getLangKey() : "fr");
            String messageCommentOffer = CommonUtil.getFullNameUser(currentUserDTO)+" "+messageSource.getMessage("comment.comment_offer.new", null, locale);
            log.debug("messageCommentOffer : {}", messageCommentOffer);

            // Create notification
            NotificationDTO notificationDTO = new NotificationDTO();
            notificationDTO.setDateCreated(Instant.now());
            notificationDTO.setContent(messageCommentOffer);
            notificationDTO.setModule(ModuleNotification.CommentOfferNotification.name());
            notificationDTO.setIsRead(Boolean.FALSE);
            notificationDTO.setUser(commentOfferDTO.getOffer().getUser());
            notificationDTO.setOffer(commentOfferDTO.getOffer());
            notificationService.save(notificationDTO);

            // Send Push Notif
            userOneSignalService.sendPushNotifForUser(userDestination.get(), messageCommentOffer);

//            List<UserOneSignal> listUserOneSignals = userOneSignalService.findByUser(userDestination.get());
//            if(listUserOneSignals.size()>0){
//                String result = listUserOneSignals.stream().map(UserOneSignal::getIdOneSignal)
//                        .collect(Collectors.joining("\",\""));
//                oneSignalService.sendPushNotifToUser(result, messageCommentOffer);
//            }
        }

        return commentOfferMapper.toDto(commentOffer);
    }

    @Override
    public Page<CommentOfferDTO> findByCriteria(CommentOfferFilter criteria, Pageable pageable, Long offerId) {
        log.debug("find comments offer by criteria : {}, page: {}", pageable, offerId);
        OfferDTO offerDTO = new OfferDTO();
        offerDTO.setId(offerId);
        criteria.setOffer(offerDTO);
        criteria.setBlockedByReported(Boolean.FALSE);
        return commentOfferRepository.findAll(createSpecification(criteria), pageable).map(commentOfferMapper::toDto);
    }

    @Override
    public CommentOfferDTO updateCommentOffer(CommentOfferDTO commentOfferDTO, Long id) {
        log.debug("update offer by commentOfferDTO : {}, id: {}", commentOfferDTO, id);
        if (commentOfferDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id null");
        }
        if (!Objects.equals(id, commentOfferDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID id invalid");
        }

        CommentOffer commentOffer = commentOfferRepository.findById(id)
                .orElseThrow(() -> new BadRequestAlertException("Entity not found with id"));


        Long useId = SecurityUtils
                .getIdByCurrentUser();
        if (!Objects.equals(useId, commentOffer.getUser().getId())) {
            throw new UnauthorizedException("Unauthorized action");
        }

        commentOffer.setContent(commentOfferDTO.getContent());
        CommentOffer commentOfferUpdate = commentOfferRepository.save(commentOffer);
        return commentOfferMapper.toDto(commentOfferUpdate);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CommentOffer : {}", id);

        CommentOffer commentOffer = commentOfferRepository.findById(id)
                .orElseThrow(() -> new ResouorceNotFoundException("Entity not found with id"));

        Long useId = SecurityUtils
                .getIdByCurrentUser();
        if (!Objects.equals(useId, commentOffer.getUser().getId())) {
            throw new UnauthorizedException("Unauthorized action");
        }

        commentOfferRepository.deleteById(id);
    }

    private Specification<CommentOffer> createSpecification(CommentOfferFilter criteria) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if ( criteria.getOffer() != null && criteria.getOffer().getId() != null ) {
                predicates.add(criteriaBuilder.equal(root.get("offer").get("id"), criteria.getOffer().getId()));
            }

            if (criteria.getBlockedByReported() != null ) {
                predicates.add(criteriaBuilder.equal(root.get("blockedByReported"), criteria.getBlockedByReported()));
            }

            query.orderBy(criteriaBuilder.desc(root.get("id")));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }


}
