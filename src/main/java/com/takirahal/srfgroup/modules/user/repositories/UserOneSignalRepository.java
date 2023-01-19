package com.takirahal.srfgroup.modules.user.repositories;

import com.takirahal.srfgroup.modules.user.entities.User;
import com.takirahal.srfgroup.modules.user.entities.UserOneSignal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserOneSignalRepository extends JpaRepository<UserOneSignal, Long> {
    Optional<UserOneSignal> findByIdOneSignalAndUser(String idOneSignal, User user);

    List<UserOneSignal> findByUser(User user);
}
