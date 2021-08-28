
  package com.example.messaging_version_01;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {
    Context mcontext;
    List<Contact> contactList;
    HashMap<String,Contact> numbers;

    public ContactAdapter(Context mcontext, List<Contact> contactList) {
        this.mcontext = mcontext;
        this.contactList = contactList;
        numbers=new HashMap<>();
    }
    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.item_contact,   parent ,false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {

        Contact contact = contactList.get(position);
        if(numbers.containsKey(contact.getPhone()))
            holder.checkBox.setChecked(true);
        else
            holder.checkBox.setChecked(false);

        holder.name_contact.setText(contact.getName());
        holder.phone_contact.setText(contact.getPhone());

        if (contact.getPhoto() != null){
            Picasso.get().load(contact.getPhoto()).into(holder.img_contact);
        }
        else{
            holder.img_contact.setImageResource(R.drawable.ic_baseline_account_circle_24);
        }

    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView  name_contact, phone_contact;
        CircleImageView img_contact;
        CheckBox checkBox;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            name_contact = itemView.findViewById(R.id.name_contact);
            phone_contact = itemView.findViewById(R.id.phone_contact);
            img_contact = itemView.findViewById(R.id.img_contact);
            checkBox=itemView.findViewById(R.id.lv_checkbox);
            checkBox.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            CheckBox c= (CheckBox) view;
            if(c.isChecked())
                numbers.put(contactList.get(getAdapterPosition()).getPhone(),contactList.get(getAdapterPosition()));
            else
                numbers.remove(contactList.get(getAdapterPosition()).getPhone());


        }
    }

    public HashMap<String,Contact> getSelectedNumbers(){
        return numbers;

    }
    public void updateSelections(){
        numbers=chat_activity.NUMBERS;
        notifyDataSetChanged();
    }
}
