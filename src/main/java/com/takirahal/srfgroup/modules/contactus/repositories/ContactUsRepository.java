package com.takirahal.srfgroup.modules.contactus.repositories;

import com.takirahal.srfgroup.modules.contactus.entities.ContactUs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactUsRepository extends JpaRepository<ContactUs, Long> {
}
