package com.takirahal.srfgroup.modules.favoriteuser.repositories;

import com.takirahal.srfgroup.modules.user.entities.User;
import com.takirahal.srfgroup.modules.favoriteuser.entities.FavoriteUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteUserRepository extends JpaRepository<FavoriteUser, Long> , JpaSpecificationExecutor<FavoriteUser> {
    Optional<FavoriteUser> findByCurrentUserAndFavoriteUser(User currentUserEntity, User entityFavoriteUser);

    @Query("SELECT fu from FavoriteUser fu where fu.favoriteUser.id = ?#{principal.id}")
    List<FavoriteUser> findYourFavoriteUsers();
}
