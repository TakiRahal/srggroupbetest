package com.takirahal.srfgroup.modules.notification.repositories;

import com.takirahal.srfgroup.modules.notification.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long>, JpaSpecificationExecutor<Notification> {
    @Query("select notification from Notification notification where notification.user.username = ?#{principal.username}")
    List<Notification> findByUserIsCurrentUser();

    @Query(
            "SELECT COUNT(notification) FROM Notification notification WHERE notification.user.id = :userId  AND notification.isRead=false"
    )
    long getNotReadNotifications(@Param("userId") Long userId);


    @Modifying
    @Transactional
    @Query("UPDATE Notification n SET n.isRead=true WHERE n.id= :notifId AND n.user.id= :userId")
    public void updateSeeToNotification(@Param("notifId")Long notifId,@Param("userId") Long userId);
}
