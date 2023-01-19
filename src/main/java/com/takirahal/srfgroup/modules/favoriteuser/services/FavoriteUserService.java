package com.takirahal.srfgroup.modules.favoriteuser.services;

import com.takirahal.srfgroup.modules.favoriteuser.dto.FavoriteUserDTO;
import com.takirahal.srfgroup.modules.favoriteuser.dto.filter.FavoriteUserFilter;
import com.takirahal.srfgroup.modules.offer.dto.OfferDTO;
import com.takirahal.srfgroup.modules.offer.dto.OfferWithMyFavoriteUserDTO;
import com.takirahal.srfgroup.modules.user.dto.UserDTO;
import com.takirahal.srfgroup.security.UserPrincipal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface FavoriteUserService {

    /**
     * Save a favorite.
     *
     * @param favoriteDTO the entity to save.
     * @return the persisted entity.
     */
    FavoriteUserDTO save(FavoriteUserDTO favoriteDTO);

    OfferWithMyFavoriteUserDTO getOfferWithMyFavoriteUser(Optional<OfferDTO> offerDTO);

    Page<FavoriteUserDTO> findByCriteria(FavoriteUserFilter criteria, Pageable pageable);

    boolean isMyFavoriteUser(UserPrincipal currentUser, UserDTO favoriteUser);

    /**
     * Delete the "id" favorite.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
