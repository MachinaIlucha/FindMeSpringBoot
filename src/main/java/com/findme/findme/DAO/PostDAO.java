package com.findme.findme.DAO;

import com.findme.findme.entity.Post;
import com.findme.findme.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostDAO extends JpaRepository<Post, Long> {
    List<Post> getPostsByUserPagePostedOrderByDatePostedDesc(User userPagePosted);

    List<Post> getPostsByUserPagePostedOrderByDatePostedAsc(User userPagePosted);

    @Query(value = "SELECT p FROM Post p " +
            "WHERE p.userPagePosted = :userPagePosted AND p.userPosted IN " +
            "(SELECT r FROM Relationship r WHERE (r.userFrom.id = :userPagePosted OR r.userTo.id = :userPagePosted) AND r.status = 'FRIENDS')")
    List<Post> getOnlyFriendsPosts(User userPagePosted);

    @Query(value = "SELECT p FROM Post p WHERE p.userPagePosted = :userPagePosted AND p.userPosted = :userPagePosted")
    List<Post> getOnlyUserPosts(User userPagePosted);
}
