package com.takirahal.srfgroup.modules.user.repositories;

import com.takirahal.srfgroup.modules.user.entities.Organigramme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrganigrammeRepository  extends JpaRepository<Organigramme, Long> {
    Optional<Organigramme> findTopByOrderByIdDesc();
}
