package com.takirahal.srfgroup.modules.rentrequest.services;

import com.takirahal.srfgroup.modules.rentrequest.dto.RentRequestDTO;
import com.takirahal.srfgroup.modules.rentrequest.dto.filter.RentRequestFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RentRequestService {

    /**
     *
     * @param rentRequestDTO
     * @return
     */
    RentRequestDTO save(RentRequestDTO rentRequestDTO);


    /**
     *
     * @param rentRequestFilter
     * @param pageable
     * @return
     */
    Page<RentRequestDTO> getCartsByCurrentUserSent(RentRequestFilter rentRequestFilter, Pageable pageable);

    /**
     *
     * @param rentRequestFilter
     * @param pageable
     * @return
     */
    Page<RentRequestDTO> getCartsByCurrentUserReceived(RentRequestFilter rentRequestFilter, Pageable pageable);

    /**
     *
     * @param id
     */
    void delete(Long id);

    /**
     *
     * @param id
     */
    void refusedRentRequestReceived(Long id);

    /**
     * Accept RentRequest
     * @param id
     * @param rentRequestDTO
     */
    void acceptRentRequestReceived(Long id, RentRequestDTO rentRequestDTO);
}
