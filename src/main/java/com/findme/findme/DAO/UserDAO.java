package com.findme.findme.DAO;

import com.findme.findme.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDAO extends JpaRepository<User, Long> {
    Optional<User> findUserByEmailAndPassword(String email, String password);
}
