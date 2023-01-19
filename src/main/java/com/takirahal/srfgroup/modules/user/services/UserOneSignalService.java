package com.takirahal.srfgroup.modules.user.services;

import com.takirahal.srfgroup.modules.user.dto.UserOneSignalDTO;
import com.takirahal.srfgroup.modules.user.entities.User;
import com.takirahal.srfgroup.modules.user.entities.UserOneSignal;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface UserOneSignalService {

    /**
     *
     * @param userOneSignalDTO
     * @return
     */
    UserOneSignalDTO save(UserOneSignalDTO userOneSignalDTO);


    /**
     *
     * @param idOneSignal
     * @return
     */
    Optional<UserOneSignal> findByIdOneSignalAndUser(String idOneSignal, User user);

    /**
     * Find all onesignal by user
     * @param user
     * @return
     */
    List<UserOneSignal> findOneSignalByUser(User user);

    /**
     * Find all onesignal by user
     * @param id
     * @return
     */
    List<UserOneSignal> findOneSignalByUserId(Long id);

    /**
     * Sent push noti for user for all devices
     * @param user
     */
    void sendPushNotifForUser(User user, String messageCommentOffer);


}
