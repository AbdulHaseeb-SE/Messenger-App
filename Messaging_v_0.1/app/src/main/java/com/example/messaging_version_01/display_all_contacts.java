package com.example.messaging_version_01;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class display_all_contacts extends AppCompatActivity {
    public static final int DISPLAY_ALL_CONTACT_CONST=11;
     RecyclerView recyclerView;
     List<Contact> contactList;
     ContactAdapter adapter;
     boolean selectAll=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_all_contacts);

        recyclerView = findViewById(R.id.contact_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        contactList = new ArrayList<>();
        adapter = new ContactAdapter(this, contactList);
        recyclerView.setAdapter(adapter);

        //---------------------------------------------------------------------------------------------------------
        //=======For Checking Permission===========
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.READ_CONTACTS)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        if (response.getPermissionName().equals(Manifest.permission.READ_CONTACTS)){
                            getContacts();
                        }
                        else{
                            Toast.makeText(display_all_contacts.this, "Required Permission is not Enabled", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Toast.makeText(display_all_contacts.this, "Permission should be granted", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    //----------------------------------------------------------------------------------------------------------------------
    //===============Getting Contacts from PhoneBook===================
    private void getContacts() {
        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null
                ,null);
        while (phones.moveToNext())
        {
            String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            String photoUri = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));

            Contact contact = new Contact(name,phoneNumber,photoUri);
            contactList.add(contact);
            adapter.notifyDataSetChanged();
        }
    }
    public void btn_done (@Nullable ListView listView) {
    }


    public void go_back(View view) {
        Intent intent = new Intent(this, chat_activity.class);
        startActivity(intent);
    }


    public void doneClick(View view) {
        if(adapter!=null)
          chat_activity.NUMBERS= adapter.getSelectedNumbers();
        setResult(RESULT_OK);
        finish();
    }

    public void selectAll(View view){
        chat_activity.NUMBERS=new HashMap<>();
        if(selectAll){
            if(adapter!=null) {
                adapter.updateSelections();
            }
            selectAll=false;
            return;
        }
        if(contactList!=null&&contactList.size()>0){
            for (int i=0;i<contactList.size();i++){
                chat_activity.NUMBERS.put(contactList.get(i).getPhone(),contactList.get(i));
            }
        if(adapter!=null) {

            adapter.updateSelections();
        }
        selectAll=true;
        }
    }

}