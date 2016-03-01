package me.tikitoo.demo.rxjavademo.repo;

import com.google.gson.annotations.SerializedName;

public class Repo {
    public String name;
    @SerializedName("html_url")
    public String url;
    public String description;

    public Repo() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
