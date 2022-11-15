package com.icesi.edu.users.repository;

import com.icesi.edu.users.model.Permission;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface PermissionRepository extends CrudRepository<Permission, UUID> {
}
