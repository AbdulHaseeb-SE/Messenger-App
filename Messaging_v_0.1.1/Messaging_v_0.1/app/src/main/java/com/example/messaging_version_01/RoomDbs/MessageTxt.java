package com.example.messaging_version_01.RoomDbs;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


@Entity
public class MessageTxt {
    public String message;
    @PrimaryKey
    public long messageKey;
    public boolean sentAll;

    @Ignore
    public MessageTxt(String message, long messageKey, boolean sentAll) {
        this.message = message;
        this.messageKey = messageKey;
        this.sentAll = sentAll;
    }

    public MessageTxt(){}
}
