package com.findme.findme.DAO;

import com.findme.findme.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostDAO extends JpaRepository<Post, Long> {

    List<Post> getPostsByUserPagePostedId(Long userPagePosted_id);

    @Query(value = """
            SELECT p FROM Post p
                WHERE p.userPosted in (SELECT r.userFrom FROM Relationship r WHERE r.userFrom.id = :user_id OR r.userTo.id = :user_id AND r.status = 'FRIENDS')
                OR p.userPosted in (SELECT r.userTo FROM Relationship r WHERE r.userFrom.id = :user_id OR r.userTo.id = :user_id AND r.status = 'FRIENDS')
                ORDER BY p.datePosted DESC""")
    List<Post> getPostsByUserFriends(Long user_id);
}
