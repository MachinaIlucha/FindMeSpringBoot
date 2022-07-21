package com.findme.findme.service.interfaces;

import com.findme.findme.entity.Relationship;
import com.findme.findme.entity.RelationshipType;

import java.util.List;

public interface RelationShipService {
    Relationship sendFriendRequest(Long user_to_id);

    Relationship addFriend(Long user_from_id, Long user_to_id);

    Relationship deniedFriend(Long user_from_id, Long user_to_id);

    void deleteFriend(Long friendId, Long userId);

    List<Relationship> getRelationshipsByUserToIdAndStatus(Long userFrom_id, RelationshipType relationshipType);

    List<Relationship> getRelationshipsByUserFromIdAndStatus(Long userFrom_id, RelationshipType relationshipType);
}
