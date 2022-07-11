package com.findme.findme.DAO;

import com.findme.findme.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostDAO extends JpaRepository<Post, Long> {

    List<Post> getPostsByUserPagePostedId(Long userPagePosted_id);
}
