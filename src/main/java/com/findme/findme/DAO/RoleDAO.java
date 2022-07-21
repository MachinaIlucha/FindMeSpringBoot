package com.findme.findme.DAO;

import com.findme.findme.entity.Role;
import com.findme.findme.entity.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDAO extends JpaRepository<Role, Long> {
    Role findRoleByRoleName(RoleName roleName);
}
