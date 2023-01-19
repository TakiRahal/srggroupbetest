package com.takirahal.srfgroup.modules.cgu.mapper;

import com.takirahal.srfgroup.mapper.EntityMapper;
import com.takirahal.srfgroup.modules.cgu.dto.CguDTO;
import com.takirahal.srfgroup.modules.cgu.entities.Cgu;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface CguMapper extends EntityMapper<CguDTO, Cgu> {
}
