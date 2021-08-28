package com.example.messaging_version_01;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;


import com.example.messaging_version_01.RoomDbs.MessageItem;
import com.example.messaging_version_01.RoomDbs.MessagingDb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SendMessageService extends Service {
    public static final int MAX_NUMBER_SENT=5;//GROUP OF MESSAGE SEND
    public static final long SLEEP_TIME=5000;//SLEEP AFTER
    public static final String LOGKEY="harisiqbal";
    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(LOGKEY,"open new activity");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(LOGKEY,"on start command");

        Intent notificationIntent = new Intent(this, MainActivity.class);
       // PendingIntent pendingIntent = PendingIntent.getActivity(this,
         //       0, notificationIntent, 0);
        Notification notification = new NotificationCompat.Builder(this, App.CHANNEL_ID)
                .setContentTitle("Message Service")
                .setContentText("Messages are sending")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
               // .setContentIntent(pendingIntent)
                .build();
        startForeground(1, notification);
       //here we will check permission if message are going to sent
        startSendingMessages();

        //do heavy work on a background thread
        //stopSelf();
        return START_STICKY;
    }

    private void startSendingMessages() {

        Log.e(LOGKEY,"start sending ");
        new Thread(new Runnable() {


            @Override
            public void run() {

                int maxNumbers=0;
                //do sending of messages here

                //getting all contacts and names to sent messages
                List<MessageItem> messageItems= MessagingDb.getInstance(getApplicationContext()).mDao().getUnsentMessageItem();

                //key value pair of message key to sent and message value
                HashMap<Long,String> messageKeys=new HashMap<>();
                Log.e(LOGKEY,messageItems.size()+"");
                for(MessageItem messageItem:messageItems){
                    try{
                //if contact key message is present else extract that from database
                        Log.e(LOGKEY,messageKeys.size()+"");
                        if(!messageKeys.containsKey(messageItem.messageKey))
                           messageKeys.put(messageItem.messageKey,MessagingDb.getInstance(getApplicationContext()).mDao().getUnsentMessageTxt(messageItem.messageKey).get(0).message);

                        //sending message
                        if(messageKeys.get(messageItem.messageKey).length()>159){
                            SmsManager smsManager = SmsManager.getDefault();

                            ArrayList<String> parts = smsManager.divideMessage(messageKeys.get(messageItem.messageKey));
                            //smsManager.sendTextMessage(phoneNumber, null, message, null, null);
                            smsManager.sendMultipartTextMessage(messageItem.contact, null, parts,
                                    null, null);
                        }else

                        SmsManager.getDefault().sendTextMessage(messageItem.contact, null, messageKeys.get(messageItem.messageKey), null,null);
                        //setting sending of messages to true
                        messageItem.isSent=true;
                       //updating sending of messages
                        MessagingDb.getInstance(getApplicationContext()).mDao().insertMessageItem(messageItem);

                        //thread sleeping logic
                        maxNumbers++;
                        if(maxNumbers>MAX_NUMBER_SENT){
                            maxNumbers=0;
                            Thread.sleep(SLEEP_TIME);
                        }
                    }catch (Exception e){
                        Log.e("harisKhan",e.getLocalizedMessage());
                    }

                }//end of for loop


                //stopping the service
                SendMessageService.this.stopSelf();
            }
        }).start();

    }



    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
