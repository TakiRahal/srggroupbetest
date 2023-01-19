package com.takirahal.srfgroup.modules.address.services.impl;

import com.takirahal.srfgroup.modules.address.services.AddressService;
import com.takirahal.srfgroup.modules.address.dto.AddressDTO;
import com.takirahal.srfgroup.modules.address.entities.Address;
import com.takirahal.srfgroup.modules.address.mapper.AddressMapper;
import com.takirahal.srfgroup.modules.address.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
class AddressServiceImpl implements AddressService {

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    AddressMapper addressMapper;

    @Override
    public Page<AddressDTO> findByCriteria(AddressDTO addressDTO, Pageable page) {
        return addressRepository.findAll(createSpecification(addressDTO), page).map(address -> addressMapper.toDto(address));
    }

    protected Specification<Address> createSpecification(AddressDTO address) {
        Specification<Address> specification = Specification.where(null);
        return specification;
    }
}