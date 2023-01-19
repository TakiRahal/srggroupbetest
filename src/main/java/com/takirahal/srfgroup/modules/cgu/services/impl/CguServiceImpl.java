package com.takirahal.srfgroup.modules.cgu.services.impl;

import com.takirahal.srfgroup.exceptions.ResouorceNotFoundException;
import com.takirahal.srfgroup.modules.cgu.dto.CguDTO;
import com.takirahal.srfgroup.modules.cgu.entities.Cgu;
import com.takirahal.srfgroup.modules.cgu.mapper.CguMapper;
import com.takirahal.srfgroup.modules.cgu.repositories.CguRepository;
import com.takirahal.srfgroup.modules.cgu.services.CguService;
import com.takirahal.srfgroup.modules.permission.enums.EPermission;
import com.takirahal.srfgroup.modules.user.services.AuthorityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CguServiceImpl implements CguService {

    private final Logger log = LoggerFactory.getLogger(CguServiceImpl.class);

    @Autowired
    CguMapper cguMapper;

    @Autowired
    CguRepository cguRepository;

    @Autowired
    AuthorityService authorityService;

    @Override
    public CguDTO save(CguDTO cguDTO) {
        log.debug("Request to save Cgu : {}", cguDTO);

        // Check Permission
        authorityService.checkForPermissions(EPermission.CRUD_CGU);

        Cgu cgu = cguMapper.toEntity(cguDTO);
        cgu = cguRepository.save(cgu);
        return cguMapper.toDto(cgu);
    }

    @Override
    public CguDTO getCgu() {
        log.debug("Request to get Cgu : {}");
        Optional<Cgu> cgu = cguRepository.findTopByOrderByIdDesc();
        if( !cgu.isPresent() ){
            throw new ResouorceNotFoundException("Cgu not found");
        }
        return cguMapper.toDto(cgu.get());
    }

    @Override
    public CguDTO update(Long id, CguDTO cguDTO) {
        log.debug("Request to update Cgu : {}", cguDTO);
        Cgu cgu = cguRepository.findById(id)
                .orElseThrow(() -> new ResouorceNotFoundException("Entity not found with id"));
        cgu.setContentAr(cguDTO.getContentAr());
        cgu.setContentFr(cguDTO.getContentFr());
        cgu.setContentEn(cguDTO.getContentEn());
        cgu = cguRepository.save(cgu);
        return cguMapper.toDto(cgu);
    }
}
