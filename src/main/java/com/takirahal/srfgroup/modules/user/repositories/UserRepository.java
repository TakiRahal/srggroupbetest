package com.takirahal.srfgroup.modules.user.repositories;

import com.takirahal.srfgroup.modules.user.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    Optional<User> findOneByEmailIgnoreCase(String email);

    Optional<User> findOneByActivationKey(String activationKey);

    Optional<User> findOneWithAuthoritiesByEmailIgnoreCase(String usernameOrEmail);

    Optional<User> findOneByResetKey(String resetKey);
}
