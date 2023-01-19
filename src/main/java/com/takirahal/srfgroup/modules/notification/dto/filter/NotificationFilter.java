package com.takirahal.srfgroup.modules.notification.dto.filter;

import com.takirahal.srfgroup.modules.user.dto.filter.UserOfferFilter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Lob;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationFilter {
    private Long id;

    private Instant dateCreated;

    private UserOfferFilter user;
}
