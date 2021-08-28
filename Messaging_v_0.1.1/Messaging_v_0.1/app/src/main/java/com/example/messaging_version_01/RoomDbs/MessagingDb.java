package com.example.messaging_version_01.RoomDbs;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;



@Database(entities = {MessageItem.class,MessageTxt.class}, version = 1)
public abstract class MessagingDb extends RoomDatabase {

    private static final String DATA_BASE="research_db";




    private static MessagingDb INSTANCE;





    public static MessagingDb getInstance(Context context) {

        if(INSTANCE==null)
            INSTANCE=Room.databaseBuilder(context.getApplicationContext(), MessagingDb.class, DATA_BASE)
                    .build();
        return INSTANCE;
    }








    public abstract MDao mDao();
}
