package me.tikitoo.demo.rxjavademo.model;

public class User {
    public String login;
    public String id;
    public String avatar_url;
    public String gravatar_id;
    public String url;
    public String html_url;
    public String followers_url;
    public String following_url;
    public String gists_url;
    public String starred_url; // https://api.github.com/users/Tikitoo/starred{/owner}{/repo}",
    public String subscriptions_url;
    public String organizations_url; // https://api.github.com/users/Tikitoo/orgs",
    public String repos_url; // https://api.github.com/users/Tikitoo/repos",
    public String events_url; // https://api.github.com/users/Tikitoo/events{/privacy}",
    public String received_events_url; // https://api.github.com/users/Tikitoo/received_events",
    public String type;
    public String site_admin;
    public String name; // Tikitoo"
    public String company;
    public String blog; // http://tikitoo.me",
    public String location; // Beijing",
    public String email; // codes0306@gmail.com",
    public String hireable;
    public String bio;
    public String public_repos;
    public String public_gists;
    public String followers;
    public String following;
    public String created_at;
    public String updated_at;

    public User(String id, String login) {
        this.id = id;
        this.login = login;
    }

    public User() {
    }

    public User(String login) {
        this.login = login;
    }
}
