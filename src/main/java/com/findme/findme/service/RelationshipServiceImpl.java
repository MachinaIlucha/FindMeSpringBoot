package com.findme.findme.service;

import com.findme.findme.DAO.RelationshipDAO;
import com.findme.findme.DAO.UserDAO;
import com.findme.findme.Exceptions.RelationshipNotFoundException;
import com.findme.findme.entity.Relationship;
import com.findme.findme.entity.RelationshipType;
import com.findme.findme.entity.User;
import com.findme.findme.Exceptions.RelationshipAlreadyExistException;
import com.findme.findme.Exceptions.UserNotFoundException;
import com.findme.findme.service.interfaces.RelationShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class RelationshipServiceImpl implements RelationShipService {

    private final RelationshipDAO relationshipDAO;
    private final UserDAO userDAO;

    @Autowired
    public RelationshipServiceImpl(RelationshipDAO relationshipDAO, UserDAO userDAO) {
        this.relationshipDAO = relationshipDAO;
        this.userDAO = userDAO;
    }

    @Override
    public Relationship addFriend(Long user_from_id, Long user_to_id) {
        Relationship relationship = relationshipDAO.getRelationshipByUserFromIdAndUserToId(user_to_id, user_from_id)
                .orElseThrow(RelationshipNotFoundException::new);

        relationship.setStatus(RelationshipType.FRIENDS);
        return relationshipDAO.save(relationship);
    }

    @Override
    public Relationship deniedFriend(Long user_from_id, Long user_to_id) {
        Relationship relationship = relationshipDAO.getRelationshipByUserFromIdAndUserToId(user_from_id, user_to_id)
                .orElseThrow(RelationshipNotFoundException::new);

        relationship.setStatus(RelationshipType.CANCELLED);
        return relationshipDAO.save(relationship);
    }

    @Override
    public Relationship sendFriendRequest(HttpSession session, Long user_to_id) {
        User userFrom = (User) session.getAttribute("user");
        User userTo = userDAO.findById(user_to_id)
                .orElseThrow(UserNotFoundException::new);

        /* if relationship already exist throw bad request else add relationship */
        relationshipDAO.getRelationshipByUserFromIdAndUserToId(userFrom.getId(), user_to_id)
                .ifPresent(s -> { throw new RelationshipAlreadyExistException(); });

        Relationship relationship = new Relationship();
        relationship.setUserTo(userTo);
        relationship.setUserFrom(userFrom);

        return relationshipDAO.save(relationship);
    }
}
