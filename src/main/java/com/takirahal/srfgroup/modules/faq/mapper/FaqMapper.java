package com.takirahal.srfgroup.modules.faq.mapper;

import com.takirahal.srfgroup.modules.faq.dto.FaqDTO;
import com.takirahal.srfgroup.modules.faq.entities.Faq;
import com.takirahal.srfgroup.mapper.EntityMapper;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Faq} and its DTO {@link FaqDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FaqMapper extends EntityMapper<FaqDTO, Faq> {
    FaqDTO toDto (Faq f);
}
