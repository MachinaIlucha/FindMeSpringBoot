package com.findme.findme.controller;

import com.findme.findme.DAO.RelationshipDAO;
import com.findme.findme.DAO.UserDAO;
import com.findme.findme.Exceptions.UserNotFoundException;
import com.findme.findme.entity.Relationship;
import com.findme.findme.entity.RelationshipType;
import com.findme.findme.entity.User;
import com.findme.findme.service.interfaces.RelationShipService;
import com.findme.findme.service.interfaces.UserService;
import com.findme.findme.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class FriendsRequestController {

    private final RelationShipService relationShipService;
    private final RelationshipDAO relationshipDAO;
    private final UserService userService;
    private final UserDAO userDAO;

    @Autowired
    public FriendsRequestController(RelationShipService relationShipService, UserService userService, UserDAO userDAO, RelationshipDAO relationshipDAO) {
        this.relationShipService = relationShipService;
        this.userService = userService;
        this.userDAO = userDAO;
        this.relationshipDAO = relationshipDAO;
    }

    @PostMapping(path = "/user/{userId}/sendFriendRequest")
    public String sendFriendRequest(@PathVariable Long userId) {
        relationShipService.sendFriendRequest(userId);
        return "redirect:/user/" + userId;
    }

    @PostMapping(path = "/user/{userId}/addFriend/{friendId}")
    public String addFriend(@PathVariable Long friendId) {
        User userFrom = userDAO.findById(SecurityUtil.getAuthorizedUserId()).orElseThrow(UserNotFoundException::new);;

        relationShipService.addFriend(userFrom.getId(), friendId);

        return "redirect:/user/" + userFrom.getId() + "/friends";
    }

    @PostMapping(path = "/user/{userId}/deniedFriend/{friendId}")
    public String deniedFriend(@PathVariable Long friendId) {
        User userFrom = userDAO.findById(SecurityUtil.getAuthorizedUserId()).orElseThrow(UserNotFoundException::new);;

        relationShipService.deniedFriend(userFrom.getId(), friendId);

        return "redirect:/user/" + userFrom.getId() + "/friends";
    }

    @GetMapping(path = "/user/{userId}/friends")
    public String userFriends(Model model, @PathVariable Long userId) {
        User user = userDAO.findById(userId).orElseThrow(UserNotFoundException::new);

        List<User> friends = userService.getFriendsOfUserById(user.getId());

        model.addAttribute("user", user)
                .addAttribute("friends", friends)
                .addAttribute("isAuthorized", userDAO.findById(SecurityUtil.getAuthorizedUserId()).orElseThrow(UserNotFoundException::new));

        return "friends";
    }

    @GetMapping(path = "/user/{userId}/incomeRequests")
    public String incomeRequests(Model model, @PathVariable Long userId) {
        User user = userDAO.findById(userId).orElseThrow(UserNotFoundException::new);

        List<Relationship> incomeRequests = relationshipDAO.getRelationshipsByUserToIdAndStatus(user.getId(), RelationshipType.WAITING);

        model.addAttribute("user", user)
                .addAttribute("incomeRequests", incomeRequests)
                .addAttribute("isAuthorized", userDAO.findById(SecurityUtil.getAuthorizedUserId()).orElseThrow(UserNotFoundException::new));

        return "incomeRequests";
    }

    @GetMapping(path = "/user/{userId}/outcomeRequests")
    public String outcomeRequests(Model model) {
        User user = userDAO.findById(SecurityUtil.getAuthorizedUserId()).orElseThrow(UserNotFoundException::new);

        List<Relationship> outcomeRequests = relationshipDAO.getRelationshipsByUserFromIdAndStatus(user.getId(), RelationshipType.WAITING);

        model.addAttribute("user", user)
                .addAttribute("outcomeRequests", outcomeRequests)
                .addAttribute("isAuthorized", user);

        return "outcomeRequests";
    }


}
