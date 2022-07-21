package com.findme.findme.DAO;

import com.findme.findme.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface UserDAO extends JpaRepository<User, Long> {
    Optional<User> findUserByEmailAndPassword(String email, String password);
    Optional<User> findUserByEmail(String email);

    @Transactional
    @Modifying
    @Query(value = "UPDATE User user set user.avatar = :avatar where user.id = :userId")
    void changeAvatarOfUserByUserId(String avatar, Long userId);
}
