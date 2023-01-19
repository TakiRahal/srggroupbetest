package com.takirahal.srfgroup.modules.cgu.repositories;

import com.takirahal.srfgroup.modules.cgu.entities.Cgu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CguRepository extends JpaRepository<Cgu, Long> {
    Optional<Cgu> findTopByOrderByIdDesc();
}
