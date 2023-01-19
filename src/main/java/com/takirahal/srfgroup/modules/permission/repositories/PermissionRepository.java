package com.takirahal.srfgroup.modules.permission.repositories;

import com.takirahal.srfgroup.modules.permission.entities.Permission;
import com.takirahal.srfgroup.modules.permission.enums.EPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long>, JpaSpecificationExecutor<Permission> {

    Optional<Permission> findByName(EPermission name);
}
