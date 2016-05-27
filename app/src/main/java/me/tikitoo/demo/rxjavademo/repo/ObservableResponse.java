package me.tikitoo.demo.rxjavademo.repo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import rx.Observable;
import rx.functions.Func1;

public class ObservableResponse {
    public static Gson mGson;

    public ObservableResponse() {
        mGson = new GsonBuilder()
                .create();
    }

    public static <T> Observable<T> extractData2(final Observable<Response> observable, final Class<T> clazz) {
        return observable.flatMap(new Func1<Response, Observable<T>>() {
            @Override
            public Observable<T> call(Response response) {
                if (response == null) {
                    return Observable.error(new NetworkException());
                } else if (response.getStatusCode() == 200) {
                    return Observable.just(mGson.fromJson(mGson.toJson(response.mData), clazz));
                } else {
                    return Observable.error(new ResponseException(response));
                }
            }
        });
    }
}
