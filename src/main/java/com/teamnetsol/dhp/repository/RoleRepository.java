package com.teamnetsol.dhp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.teamnetsol.dhp.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

	Role findByName(String name);

}
