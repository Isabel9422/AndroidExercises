package com.example.serna_agudo_isabel_pmdm_tarea04;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.serna_agudo_isabel_pmdm_tarea04.databinding.ContactsItemBinding;
import com.example.serna_agudo_isabel_pmdm_tarea04.model.Contact;

import java.util.List;


public class BirthdayHelperRecyclerViewAdapter extends RecyclerView.Adapter<BirthdayHelperRecyclerViewAdapter.ViewHolder> {

    private final List<Contact> contacts;
    ContactsItemBinding binding;
    Context context;
    AdapterClickListener clickListener;


    public BirthdayHelperRecyclerViewAdapter(Context context, List<Contact> contacts, AdapterClickListener clickListener) {
        this.context = context;
        this.contacts = contacts;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ContactsItemBinding.inflate(LayoutInflater.from(context));
        return new BirthdayHelperRecyclerViewAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(contacts.get(position));
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    interface AdapterClickListener {
        void onClickListener(Contact contact);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ContactsItemBinding binding;

        public ViewHolder(@NonNull ContactsItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bindData(final Contact contact) {
            binding.contactItemName.setText(contact.getName());
            // https://stackoverflow.com/questions/7738192/how-to-get-contact-photo-uri
//          binding.contactItemImage.setImageURI(contact.getImage());
            binding.contactItemPhone.setText(contact.getPhoneNumber() + "");
            binding.contactItemDescription.setText(contact.getMsg());
            binding.getRoot().setOnClickListener(v -> clickListener.onClickListener(contact));

//            binding.getRoot().setOnLongClickListener(v -> {
//                        clickListener.onClickListener(contact);
//                        return false;
//                    }
//            );
        }
    }
}
