package com.example.messaging_version_01;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.messaging_version_01.RoomDbs.MessageItem;
import com.example.messaging_version_01.RoomDbs.MessageTxt;
import com.example.messaging_version_01.RoomDbs.MessagingDb;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class chat_activity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    private EditText txt_pNumber,txt_message;
    private AlertDialog.Builder dialogBuilder;
    public static HashMap<String,Contact> NUMBERS;
    public static final String LOGKEY="harisiqbal";

    RecyclerView recyclerView_SelectedContacts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_activity);
        NUMBERS=new HashMap<>();
        ImageView openMain_Activity = (ImageView) findViewById(R.id.openMain_Activity);
        openMain_Activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });

        txt_message = (EditText) findViewById(R.id.txt_message);
        txt_pNumber = (EditText) findViewById(R.id.txt_pNumber);
        recyclerView_SelectedContacts =  findViewById(R.id.selected_ContactRecycler);
//        recyclerView_SelectedContacts.setHasFixedSize(true);
    }

  @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == display_all_contacts.DISPLAY_ALL_CONTACT_CONST) {
            if (resultCode == RESULT_OK) {

                printContacts();

            }
        }
        super.onActivityResult(requestCode,resultCode,data);
    }

    private void printContacts(){
        //printing contact errors
       // NUMBER
        ArrayList<String> cont=new ArrayList<String>();
        ArrayList<String> no=new ArrayList<String>();
        for (Map.Entry<String,Contact> entry : NUMBERS.entrySet()) {
            no.add(entry.getKey());
            cont.add(entry.getValue().getName());
        }
        SelectedContact selectedContact=new SelectedContact(this,cont,no);
        recyclerView_SelectedContacts.setAdapter(selectedContact);
        recyclerView_SelectedContacts.setLayoutManager(new LinearLayoutManager(this));

    }



    public void openMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
//  -------------------------------------------------------------------------------------------------------------------
//    //==== Message Send Button ====
    public void btn_send(View view) {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);
        if(permissionCheck == PackageManager.PERMISSION_GRANTED){
            Log.e(LOGKEY,"send");
            putInDatabase();
        }
        //---------If user deny permission then it will again request------
        else
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS},0);
        }

    }

    private void putInDatabase(){

        Log.e(LOGKEY,"putin database");

        final String message=txt_message.getText().toString();
        Log.e(LOGKEY,message+" av");

        if(message==null||message.length()<1){
            return ;
        }


        final long key= Calendar.getInstance().getTimeInMillis();

        new Thread(new Runnable() {
            @Override
            public void run() {

                Log.e(LOGKEY,NUMBERS.size()+"");

        for (Map.Entry<String,Contact> entry : NUMBERS.entrySet()) {
            MessageItem messageItem=new MessageItem();
            messageItem.isSent=false;
            messageItem.messageKey= key;
            messageItem.contact=entry.getKey();
            messageItem.name=entry.getValue().getName();
            MessagingDb.getInstance(getApplicationContext()).mDao().insertMessageItem(messageItem);
        }//end of for loop


        MessageTxt messageTxt=new MessageTxt();
        messageTxt.messageKey=key;
        messageTxt.message=message;
        MessagingDb.getInstance(getApplicationContext()).mDao().insertMessageTxt(messageTxt);
        startService();

            }
        }).start();


    }

    public void startService() {

        Log.e(LOGKEY,"startsonservice");
        Intent serviceIntent = new Intent(chat_activity.this, SendMessageService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.startForegroundService(serviceIntent);
        }
        else
            this.startService(serviceIntent);


    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 0:
            if(grantResults.length >= 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                printContacts();
                putInDatabase();
            }
            else
            {
                Toast.makeText(this, "You don't have Required Permission!", Toast.LENGTH_SHORT).show();
            }

            break;
        }
    }
    //---------------------------------------------------------------------------------------------------------
    //----- For Showing Dropdown of group icon ----
    public void showDropdown(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.dropdown_menu);
        popup.show();

    }



    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btn_newGroup:
                Intent intent = new Intent(this, display_all_contacts.class);
                startActivityForResult(intent,display_all_contacts.DISPLAY_ALL_CONTACT_CONST);
                return true;
            case R.id.btn_joinGroup:
                return true;
            default:
                return false;
        }

    }



}
