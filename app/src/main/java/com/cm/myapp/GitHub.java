package com.cm.myapp;

import com.google.gson.annotations.SerializedName;

/**
 * Created by cm on 1/22/18.
 */

public class GitHub {
    static class User{
        String login;
        String avatar_url;
        String name;
        String created_at;

        @SerializedName("html_url")
        String htmlUrl;

        @Override
        public String toString() {
            return "User{" +
                    "login='" + login + '\'' +
                    ", avatar_url='" + avatar_url + '\'' +
                    ", name='" + name + '\'' +
                    ", created_at='" + created_at + '\'' +
                    '}';
        }
    }
}
