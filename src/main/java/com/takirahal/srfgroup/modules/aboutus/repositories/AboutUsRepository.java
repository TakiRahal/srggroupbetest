package com.takirahal.srfgroup.modules.aboutus.repositories;

import com.takirahal.srfgroup.modules.aboutus.entities.AboutUs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AboutUsRepository extends JpaRepository<AboutUs, Long>, JpaSpecificationExecutor<AboutUs> {

    Optional<AboutUs> findTopByOrderByIdDesc();
}
