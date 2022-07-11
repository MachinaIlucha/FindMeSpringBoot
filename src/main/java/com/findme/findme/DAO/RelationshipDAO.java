package com.findme.findme.DAO;

import com.findme.findme.entity.Relationship;
import com.findme.findme.entity.RelationshipType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RelationshipDAO extends JpaRepository<Relationship, Long> {

    @Query(value = "SELECT r FROM Relationship r WHERE (r.userFrom.id = ?1 and r.userTo.id = ?2) or (r.userFrom.id = ?2 and r.userTo.id = ?1)")
    Optional<Relationship> getRelationshipByUserFromIdAndUserToId(Long userFrom_id, Long userTo_id);

    List<Relationship> getRelationshipsByUserFromIdAndStatus(Long userFrom_id, RelationshipType relationshipType);

    @Query(value = "SELECT r FROM Relationship r WHERE r.userFrom.id = ?1 or r.userTo.id = ?1 and r.status = 'FRIENDS'")
    List<Relationship> getFriends(Long userFrom_id);

    List<Relationship> getRelationshipsByUserToIdAndStatus(Long userFrom_id, RelationshipType relationshipType);
}
