package com.takirahal.srfgroup.modules.home.repositories;

import com.takirahal.srfgroup.modules.home.entities.PostHomeFeature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostHomeFeatureRepository extends JpaRepository<PostHomeFeature, Long> {
    Optional<PostHomeFeature> findTopByOrderByIdDesc();

}
