package com.findme.findme.service.interfaces;

import com.findme.findme.entity.Relationship;

public interface RelationShipService {
    Relationship sendFriendRequest(Long user_to_id);

    Relationship addFriend(Long user_from_id, Long user_to_id);

    Relationship deniedFriend(Long user_from_id, Long user_to_id);
}
