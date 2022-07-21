package com.findme.findme.DAO;

import com.findme.findme.entity.Relationship;
import com.findme.findme.entity.RelationshipType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface RelationshipDAO extends JpaRepository<Relationship, Long> {

    @Query(value = "SELECT r FROM Relationship r WHERE (r.userFrom.id = :userFrom_id AND r.userTo.id = :userTo_id) OR (r.userFrom.id = :userTo_id AND r.userTo.id = :userFrom_id)")
    Optional<Relationship> getRelationshipByUserFromIdAndUserToId(Long userFrom_id, Long userTo_id);

    List<Relationship> getRelationshipsByUserFromIdAndStatus(Long userFrom_id, RelationshipType relationshipType);

    @Query(value = "SELECT r FROM Relationship r WHERE (r.userFrom.id = :userFrom_id OR r.userTo.id = :userFrom_id) AND r.status = 'FRIENDS'")
    List<Relationship> getFriends(Long userFrom_id);

    List<Relationship> getRelationshipsByUserToIdAndStatus(Long userFrom_id, RelationshipType relationshipType);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM Relationship r WHERE " +
            "((r.userFrom.id = :friendId AND r.userTo.id = :userId) OR (r.userFrom.id = :userId AND r.userTo.id = :friendId))" +
            " AND r.status = 'FRIENDS'")
    void deleteFriend(Long friendId, Long userId);
}
