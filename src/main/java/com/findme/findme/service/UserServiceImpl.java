package com.findme.findme.service;

import com.findme.findme.DAO.RelationshipDAO;
import com.findme.findme.DAO.RoleDAO;
import com.findme.findme.DAO.UserDAO;
import com.findme.findme.Exceptions.UserNotFoundException;
import com.findme.findme.entity.Relationship;
import com.findme.findme.entity.RoleName;
import com.findme.findme.entity.User;
import com.findme.findme.entity.UserPrincipal;
import com.findme.findme.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;
    private final RelationshipDAO relationshipDAO;
    private final PasswordEncoder passwordEncoder;
    private final RoleDAO roleDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO, RelationshipDAO relationshipDAO, PasswordEncoder passwordEncoder, RoleDAO roleDAO) {
        this.userDAO = userDAO;
        this.relationshipDAO = relationshipDAO;
        this.passwordEncoder = passwordEncoder;
        this.roleDAO = roleDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return new UserPrincipal(userDAO.findUserByEmail(email).orElseThrow(UserNotFoundException::new));
    }

    @Override
    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Set.of(roleDAO.findRoleByRoleName(RoleName.USER)));
        userDAO.save(user);
        return user;
    }

    @Override
    public List<User> getFriendsOfUserById(Long user_id) {
        User user = userDAO.findById(user_id).orElseThrow(UserNotFoundException::new);

        List<Relationship> relationships = relationshipDAO.getFriends(user_id);

        Set<User> friends = new HashSet<>();

        relationships.forEach(r -> friends.add(r.getUserTo().equals(user) ? r.getUserFrom() : r.getUserTo()));
        return new ArrayList<>(friends);
    }

    @Override
    public String changeAvatarOfUserByUserId(MultipartFile file, Long userId) {
        if (file.isEmpty())
            return "redirect:/myProfile";

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            Path path = Paths.get("D:\\Programming\\Projects\\FindMeSpringBoot\\src\\main\\resources\\static\\img\\" + fileName);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            Path oldPath = Paths.get("D:\\Programming\\Projects\\FindMeSpringBoot\\src\\main\\resources\\static\\img\\" + userDAO.findById(userId).orElseThrow(UserNotFoundException::new).getAvatar());
            Files.delete(oldPath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        userDAO.changeAvatarOfUserByUserId(fileName, userId);
        return "redirect:/myProfile";
    }
}