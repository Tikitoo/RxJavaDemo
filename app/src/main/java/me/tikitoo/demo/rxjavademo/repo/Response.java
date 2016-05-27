package me.tikitoo.demo.rxjavademo.repo;

import com.google.gson.annotations.SerializedName;

public class Response<T> {

    int status;

    @SerializedName("data")
    T mData;

    public int getStatusCode() {
        return status;
    }

}
