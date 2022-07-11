package com.findme.findme.service.interfaces;

import com.findme.findme.entity.Relationship;

import javax.servlet.http.HttpSession;

public interface RelationShipService {
    Relationship sendFriendRequest(HttpSession session, Long user_to_id);

    Relationship addFriend(Long user_from_id, Long user_to_id);
}
