package com.takirahal.srfgroup.modules.notification.controllers;

import com.takirahal.srfgroup.modules.notification.dto.NotificationDTO;
import com.takirahal.srfgroup.modules.notification.dto.filter.NotificationFilter;
import com.takirahal.srfgroup.modules.notification.repositories.NotificationRepository;
import com.takirahal.srfgroup.modules.notification.services.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

/**
 * REST controller for managing {@link com.takirahal.srfgroup.modules.notification.entities.Notification}.
 */
@RestController
@RequestMapping("/api/notification/")
public class NotificationController {

    private final Logger log = LoggerFactory.getLogger(NotificationController.class);

    @Autowired
    NotificationService notificationService;


    /**
     * {@code GET  /notifications} : get all the notifications.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of notifications in body.
     */
    @GetMapping("current-user")
    public ResponseEntity<Page<NotificationDTO>> getAllNotificationsByCurrentUser(NotificationFilter criteria, Pageable pageable) {
        log.info("REST request to get Notifications by criteria: {}", criteria);
        Page<NotificationDTO> page = notificationService.findByCriteria(criteria, pageable);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }


    /**
     *
     * @param notificationDTOS
     */
    @PostMapping("set-is-read")
    public ResponseEntity<String> setIsReadNotifications(@RequestBody List<NotificationDTO> notificationDTOS) {
        log.info("REST request to set is read for list of notifications : {} ", notificationDTOS);
        notificationService.setIsReadNotifications(notificationDTOS);
        return new ResponseEntity<String>("true", HttpStatus.OK);
    }

}
