package com.takirahal.srfgroup.modules.user.repositories;

import com.takirahal.srfgroup.modules.chat.entities.Conversation;
import com.takirahal.srfgroup.modules.user.entities.ReportUser;
import com.takirahal.srfgroup.modules.user.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReportUserRepository  extends JpaRepository<ReportUser, Long>, JpaSpecificationExecutor<ReportUser> {

    Optional<ReportUser> findByReportedUser(User reportedUser);
}
