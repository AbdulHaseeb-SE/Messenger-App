package com.example.messaging_version_01;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SelectedContact extends RecyclerView.Adapter<SelectedContact.MyViewHolder> {

    ArrayList<String> cont;
    ArrayList<String> no;
    Context context;
    public SelectedContact(Context ct, ArrayList<String> contact, ArrayList<String> number){
        context=ct;
        cont=contact;
        no=number;
//        Log.e("Ha",cont.get(0));
//        Log.e("Ha",cont.get(1));
//        Log.e("Ha",cont.get(2));
//Log.e("Ha",no.get(0));
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.selected_contacts_items,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//        holder.contact.setText(cont.get(position));
//        holder.contact.setText(cont.get(position));
//        Log.e("Ha",cont.get(position));
//        Log.e("Jaa",cont.get(position));
        holder.number.setText(no.get(position));
        holder.contact.setText(cont.get(position));
    }


    @Override
    public int getItemCount() {
        return no.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView contact,number;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            contact=itemView.findViewById(R.id.selected_ContactName);
            number=itemView.findViewById(R.id.selected_PhoneNumber);
        }
    }
}
