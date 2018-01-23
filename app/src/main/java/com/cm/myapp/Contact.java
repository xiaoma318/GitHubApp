package com.cm.myapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cm on 1/16/18.
 */

public class Contact {
    String id;
    String name;
    String email;
    String address;
    String gender;
    Phone phone;

    public Contact(String id, String name, String email, String address, String gender) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.gender = gender;
    }

    static List<Contact> getContacts(JSONObject jsonObject) throws JSONException {
        List<Contact> res = new ArrayList<>();
        JSONArray contacts = jsonObject.getJSONArray("contacts");
        for(int i=0;i<contacts.length();i++){
            JSONObject contact = contacts.getJSONObject(i);
            String id = contact.getString("id");
            String name = contact.getString("name");
            String email = contact.getString("email");
            String address = contact.getString("address");
            String gender = contact.getString("gender");
            res.add(new Contact(id, name,email,address,gender));
        }
        return res;
    }

    static class Phone{
        String mobile;
        String home;
    }
    @Override
    public String toString() {
        return "Contact{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", gender='" + gender + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
