package com.takirahal.srfgroup.modules.home.mapper;


import com.takirahal.srfgroup.mapper.EntityMapper;
import com.takirahal.srfgroup.modules.home.dto.TopHomeSlidesImageDTO;
import com.takirahal.srfgroup.modules.home.entities.TopHomeSlidesImage;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = { })
public interface TopHomeSlidesImageMapper extends EntityMapper<TopHomeSlidesImageDTO, TopHomeSlidesImage> {
}
