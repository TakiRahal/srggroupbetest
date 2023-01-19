package com.takirahal.srfgroup.modules.address.services;

import com.takirahal.srfgroup.modules.address.dto.AddressDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AddressService {

    Page<AddressDTO> findByCriteria(AddressDTO addressDTO, Pageable pageable);
}
