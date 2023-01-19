package com.takirahal.srfgroup.modules.user.repositories;

import com.takirahal.srfgroup.constants.AuthoritiesConstants;
import com.takirahal.srfgroup.modules.user.entities.Authority;
import com.takirahal.srfgroup.modules.user.entities.User;
import com.takirahal.srfgroup.modules.user.enums.EAuthority;
import com.takirahal.srfgroup.utils.RandomUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.Instant;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    // Delete all data table after each test (clean state)
    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void findOneByEmailIgnoreCase() {

        // Given
        String email = "taki@rahal.tn";
        User user = new User();
        user.setEmail(email);
        user.setUsername(email);
        user.setPassword("123");
        user.setFirstName("");
        user.setLastName("");
        user.setActivatedAccount(false);
        user.setActivationKey(RandomUtil.generateActivationKey(20));
        user.setSourceConnectedDevice("Web");
        user.setRegisterDate(Instant.now());
        user.setLangKey("fr");

        Set<Authority> authorities = new HashSet<>();
        Authority authority = new Authority();
        authority.setName(EAuthority.ROLE_USER);
        authorities.add(authority);
        user.setAuthorities(authorities);
        userRepository.save(user);

        // When
        Optional<User> expected = userRepository.findOneByEmailIgnoreCase(email);

        // Then
        assertTrue(expected.isPresent());

    }

    @Test
    void findOneByActivationKey() {

        // Given
        String activationKey = "123456789";
        User user = new User();
        user.setEmail("test@test.com");
        user.setUsername("test@test.com");
        user.setPassword("123");
        user.setFirstName("");
        user.setLastName("");
        user.setActivatedAccount(false);
        user.setActivationKey(activationKey);
        user.setSourceConnectedDevice("Web");
        user.setRegisterDate(Instant.now());
        user.setLangKey("fr");

        Set<Authority> authorities = new HashSet<>();
        Authority authority = new Authority();
        authority.setName(EAuthority.ROLE_USER);
        authorities.add(authority);
        user.setAuthorities(authorities);
        userRepository.save(user);

        // When
        Optional<User> expected = userRepository.findOneByActivationKey(activationKey);

        // Then
        assertTrue(expected.isPresent());
    }
}
