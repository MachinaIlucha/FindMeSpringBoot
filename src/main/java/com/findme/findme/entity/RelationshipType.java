package com.findme.findme.entity;

public enum RelationshipType {
    NEVERFRIENDS, //не друзья(и никогда не были)
    NOFRIENDS, //не друзья (когда-то были)
    WAITING, //запрос отправлен
    CANCELLED, //запрос отклонен
    FRIENDS // друзья
}
