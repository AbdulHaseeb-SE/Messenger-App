package com.example.messaging_version_01.RoomDbs;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(primaryKeys = {"messageKey","contact"})
public class MessageItem {
    public long messageKey;

    @NonNull
    public String contact;
    public String name;
    public boolean isSent;
    public MessageItem(){

    }

    @Ignore
    public MessageItem(long messageKey, String contact, String name, boolean isSent) {
        this.messageKey = messageKey;
        this.contact = contact;
        this.name = name;
        this.isSent = isSent;
    }
}
