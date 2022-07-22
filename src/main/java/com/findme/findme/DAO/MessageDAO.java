package com.findme.findme.DAO;

import com.findme.findme.entity.Message;
import com.findme.findme.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
public interface MessageDAO extends JpaRepository<Message, Long> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Message m WHERE (m.userFrom = :userTo AND m.userTo = :actionUser) OR (m.userFrom = :actionUser AND m.userTo = :userTo)")
    void deleteChat(User userTo, User actionUser);

    @Query("SELECT m FROM Message m WHERE (m.userFrom = :userTo AND m.userTo = :actionUser) OR (m.userFrom = :actionUser AND m.userTo = :userTo)")
    List<Message> getChat(User userTo, User actionUser);

    @Transactional
    @Modifying
    @Query("UPDATE Message m SET m.dateRead = :date WHERE (m.userFrom = :userTo AND m.userTo = :actionUser) OR (m.userFrom = :actionUser AND m.userTo = :userTo)")
    void readAllMessagesInChat(Date date, User userTo, User actionUser);
}
