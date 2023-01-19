package com.takirahal.srfgroup.modules.cgu.services;

import com.takirahal.srfgroup.modules.cgu.dto.CguDTO;

public interface CguService {

    /**
     *
     * @param cguDTO
     * @return
     */
    CguDTO save(CguDTO cguDTO);

    /**
     *
     * @return
     */
    CguDTO getCgu();

    /**
     *
     * @param cguDTO
     * @return
     */
    CguDTO update(Long id, CguDTO cguDTO);
}
