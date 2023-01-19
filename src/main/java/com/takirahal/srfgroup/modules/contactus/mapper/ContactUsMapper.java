package com.takirahal.srfgroup.modules.contactus.mapper;

import com.takirahal.srfgroup.modules.contactus.dto.ContactUsDTO;
import com.takirahal.srfgroup.modules.contactus.entities.ContactUs;
import com.takirahal.srfgroup.mapper.EntityMapper;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link ContactUs} and its DTO {@link ContactUsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ContactUsMapper extends EntityMapper<ContactUsDTO, ContactUs> {}
