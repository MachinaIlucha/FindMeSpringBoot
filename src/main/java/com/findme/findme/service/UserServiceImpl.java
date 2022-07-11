package com.findme.findme.service;

import com.findme.findme.DAO.RelationshipDAO;
import com.findme.findme.DAO.UserDAO;
import com.findme.findme.Exceptions.UserNotFoundException;
import com.findme.findme.entity.Relationship;
import com.findme.findme.entity.User;
import com.findme.findme.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;
    private final RelationshipDAO relationshipDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO, RelationshipDAO relationshipDAO) {
        this.userDAO = userDAO;
        this.relationshipDAO = relationshipDAO;
    }

    @Override
    public List<User> getFriendsOfUserById(Long user_id) {
        User user = userDAO.findById(user_id).orElseThrow(UserNotFoundException::new);

        List<Relationship> relationships = relationshipDAO.getFriends(user_id);

        Set<User> friends = new HashSet<>();

        relationships.forEach(r -> friends.add(r.getUserTo().equals(user) ? r.getUserFrom() : r.getUserTo()));
        return new ArrayList<>(friends);
    }
}