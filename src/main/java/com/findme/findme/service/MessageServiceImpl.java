package com.findme.findme.service;

import com.findme.findme.DAO.MessageDAO;
import com.findme.findme.DAO.UserDAO;
import com.findme.findme.Exceptions.UserNotFoundException;
import com.findme.findme.entity.Message;
import com.findme.findme.entity.User;
import com.findme.findme.service.interfaces.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    private final UserDAO userDAO;
    private final MessageDAO messageDAO;

    @Autowired
    public MessageServiceImpl(UserDAO userDAO, MessageDAO messageDAO) {
        this.userDAO = userDAO;
        this.messageDAO = messageDAO;
    }

    @Override
    public Message save(String text, Long userToId, Long actionUserId) {
        Message message = new Message();
        message.setText(text);
        message.setUserTo(userDAO.findById(userToId).orElseThrow(UserNotFoundException::new));
        message.setUserFrom(userDAO.findById(actionUserId).orElseThrow(UserNotFoundException::new));
        return messageDAO.save(message);
    }

    @Override
    public void delete(Long[] ids, long actionUserId) {
        messageDAO.deleteAllByIdInBatch(Arrays.asList(ids));
    }

    @Override
    public void deleteChat(Long userToId, Long actionUserId) {
        User userTo = userDAO.findById(userToId).orElseThrow(UserNotFoundException::new);
        User actionUser = userDAO.findById(actionUserId).orElseThrow(UserNotFoundException::new);
        messageDAO.deleteChat(userTo, actionUser);
    }

    @Override
    public List<Message> getChat(Long userOneId, Long actionUserId) {
        User userTo = userDAO.findById(userOneId).orElseThrow(UserNotFoundException::new);
        User actionUser = userDAO.findById(actionUserId).orElseThrow(UserNotFoundException::new);
        messageDAO.readAllMessagesInChat(new Date(), userTo, actionUser);

        return messageDAO.getChat(userTo, actionUser);
    }
}
