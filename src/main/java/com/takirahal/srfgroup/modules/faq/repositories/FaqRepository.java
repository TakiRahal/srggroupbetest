package com.takirahal.srfgroup.modules.faq.repositories;

import com.takirahal.srfgroup.modules.faq.entities.Faq;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FaqRepository extends JpaRepository<Faq, Long>, JpaSpecificationExecutor<Faq> {

    Optional<Faq> findByResponseAr(String responseAr);
}
