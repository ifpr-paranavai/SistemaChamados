package com.api.sistemachamados.repository;

import com.api.sistemachamados.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
