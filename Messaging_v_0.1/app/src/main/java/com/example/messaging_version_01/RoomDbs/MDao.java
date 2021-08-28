package com.example.messaging_version_01.RoomDbs;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;
@Dao
public interface MDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertMessageItem(MessageItem... mainData);

    @Query("SELECT * FROM MessageItem")
    public List<MessageItem> loadAllMessageItems();

    @Delete
    public void deleteMessage(MessageItem... mainData);

    @Query("Delete FROM MessageItem")
    public void deleteAllMessagesItem();

    @Query("SELECT * FROM MessageItem where isSent=0")
    public List<MessageItem> getUnsentMessageItem();



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertMessageTxt(MessageTxt... mainData);

    @Query("SELECT * FROM MessageTxt")
    public List<MessageTxt> loadAllMessageTxt();

    @Delete
    public void deleteMessageText(MessageTxt... mainData);

    @Query("Delete FROM MessageTxt")
    public void deleteAllMessagesText();

    @Query("SELECT * FROM MessageTxt where messageKey=:key")
    public List<MessageTxt> getUnsentMessageTxt(long key);
}
