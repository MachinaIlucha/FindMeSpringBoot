package com.findme.findme.service.interfaces;

import com.findme.findme.entity.Message;

import java.util.List;

public interface MessageService {
    Message save(String text, Long userToId, Long actionUserId);

    void delete(Long[] ids, long actionUserId);

    void deleteChat(Long userToId, Long actionUserId);

    List<Message> getChat(Long userOneId, Long actionUserId) ;
}
