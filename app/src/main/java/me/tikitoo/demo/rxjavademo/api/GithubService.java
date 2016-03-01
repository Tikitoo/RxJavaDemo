package me.tikitoo.demo.rxjavademo.api;

import java.util.List;

import me.tikitoo.demo.rxjavademo.repo.Repo;
import me.tikitoo.demo.rxjavademo.model.Contributor;
import me.tikitoo.demo.rxjavademo.model.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 *
 * reference: https://github.com/basil2style
 */
public interface GithubService {

    /**
     * See https://developer.github.com/v3/users/
     */
    @GET("/users/{username}")
    Call<User> getUser(@Path("username") String username);

    @GET("/users/{username}")
    Observable<User> getUserObservable(@Path("username") String username);


    /**
     * See https://developer.github.com/v3/repos/#list-contributors
     */
    @GET("/repos/{owner}/{repo}/contributors")
    Observable<List<Contributor>> getContributorsObservable(@Path("owner") String owner, @Path("repo") String repo);

    @GET("/repos/{owner}/{repo}/contributors")
    Call<List<Contributor>> getContributors(@Path("owner") String owner, @Path("repo") String repo);


    @GET("/users/google/repos")
    Observable<List<Repo>> getReposRx();
}
