package com.findme.findme.DAO;

import com.findme.findme.entity.Post;
import com.findme.findme.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostDAO extends JpaRepository<Post, Long> {
    List<Post> getPostsByUserPagePosted(User userPagePosted);
}
