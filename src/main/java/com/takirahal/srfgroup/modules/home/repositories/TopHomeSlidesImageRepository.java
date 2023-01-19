package com.takirahal.srfgroup.modules.home.repositories;

import com.takirahal.srfgroup.modules.home.entities.TopHomeSlidesImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopHomeSlidesImageRepository extends JpaRepository<TopHomeSlidesImage, Long> {
}
