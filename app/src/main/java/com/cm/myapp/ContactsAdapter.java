package com.cm.myapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by cm on 1/16/18.
 */

public class ContactsAdapter extends ArrayAdapter<Contact> {
    Context mContext;

    public ContactsAdapter(Context context, List<Contact> objects) {
        super(context, -1, objects);
        mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.contact_list, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.name = convertView.findViewById(R.id.tvName);
            viewHolder.gender = convertView.findViewById(R.id.tvGender);
            viewHolder.address = convertView.findViewById(R.id.tvAddress);
            viewHolder.email = convertView.findViewById(R.id.tvEmail);
            viewHolder.phone = convertView.findViewById(R.id.tvPhone);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Contact contact = getItem(position);
        viewHolder.name.setText(contact.name);
        viewHolder.gender.setText(contact.gender);
        viewHolder.email.setText(contact.email);
        viewHolder.address.setText(contact.address);
        viewHolder.phone.setText(contact.phone.mobile);
        return convertView;
    }

    static class ViewHolder {
        TextView name, gender, address, email, phone;
    }
}
