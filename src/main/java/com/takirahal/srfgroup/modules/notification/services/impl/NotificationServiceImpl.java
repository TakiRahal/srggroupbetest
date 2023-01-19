package com.takirahal.srfgroup.modules.notification.services.impl;

import com.takirahal.srfgroup.modules.user.dto.filter.UserOfferFilter;
import com.takirahal.srfgroup.modules.user.exceptioins.AccountResourceException;
import com.takirahal.srfgroup.modules.notification.dto.NotificationDTO;
import com.takirahal.srfgroup.modules.notification.dto.filter.NotificationFilter;
import com.takirahal.srfgroup.modules.notification.entities.Notification;
import com.takirahal.srfgroup.modules.notification.mapper.NotificationMapper;
import com.takirahal.srfgroup.modules.notification.repositories.NotificationRepository;
import com.takirahal.srfgroup.modules.notification.services.NotificationService;
import com.takirahal.srfgroup.modules.user.dto.UserDTO;
import com.takirahal.srfgroup.modules.user.mapper.UserMapper;
import com.takirahal.srfgroup.utils.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final Logger log = LoggerFactory.getLogger(NotificationServiceImpl.class);

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    NotificationMapper notificationMapper;

    @Autowired
    UserMapper userMapper;

    @Override
    public NotificationDTO save(NotificationDTO notificationDTO) {
        log.debug("Request to save Notification : {}", notificationDTO);
        Notification notification = notificationMapper.toEntity(notificationDTO);
        notification = notificationRepository.save(notification);
        return notificationMapper.toDto(notification);
    }

    @Override
    public Optional<NotificationDTO> partialUpdate(NotificationDTO notificationDTO) {
        log.debug("Request to partially update Notification : {}", notificationDTO);

        return notificationRepository
                .findById(notificationDTO.getId())
                .map(
                        existingNotification -> {
                            notificationMapper.partialUpdate(existingNotification, notificationDTO);
                            return existingNotification;
                        }
                )
                .map(notificationRepository::save)
                .map(notificationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NotificationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Notifications");
        return notificationRepository.findAll(pageable).map(notificationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<NotificationDTO> findOne(Long id) {
        log.debug("Request to get Notification : {}", id);
        return notificationRepository.findById(id).map(notificationMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Notification : {}", id);
        notificationRepository.deleteById(id);
    }

    @Override
    public Page<NotificationDTO> findByCriteria(NotificationFilter criteria, Pageable pageable) {
        log.debug("Request to get all Notifications : {} ", criteria);
        UserDTO currentUser = SecurityUtils.getCurrentUser()
                .map(userMapper::toCurrentUserPrincipal)
                .orElseThrow(() -> new AccountResourceException("Current user not found"));

        UserOfferFilter userOfferFilter = new UserOfferFilter();
        userOfferFilter.setId(currentUser.getId());
        criteria.setUser(userOfferFilter);
        return notificationRepository.findAll(createSpecification(criteria), pageable).map(notificationMapper::toDto);
    }

    @Override
    public void setIsReadNotifications(List<NotificationDTO> notificationDTOS) {

        Long userId = SecurityUtils.getIdByCurrentUser();

        for (NotificationDTO notif: notificationDTOS) {
            notificationRepository.updateSeeToNotification(notif.getId(), userId);
        }
    }

    @Override
    public Long getNotReadNotifications() {
        return 1L; // notificationRepository.getNotReadNotifications();
    }

    private Specification<Notification> createSpecification(NotificationFilter criteria) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if ( criteria.getUser() != null && criteria.getUser().getId() != null ) {
                predicates.add(criteriaBuilder.equal(root.get("user").get("id"), criteria.getUser().getId()));
            }
            query.orderBy(criteriaBuilder.desc(root.get("id")));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
