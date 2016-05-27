package me.tikitoo.demo.rxjavademo.utils;

import me.tikitoo.demo.rxjavademo.model.User;
import rx.Observable;
import rx.Subscriber;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

public class RxBus {
    private static volatile RxBus mInstance;

    public RxBus() {
    }

    public static RxBus getInstance() {
        if (mInstance == null) {
            synchronized (RxBus.class) {
                mInstance = new RxBus();
            }
        }
        return mInstance;
    }

    private final Subject _bus = new SerializedSubject(PublishSubject.create());

    public void send(Object obj) {
        _bus.onNext(obj);
    }

    public <T> Observable<T> toObserverable (final Class<T> eventType) {
        return _bus.ofType(eventType);
//        这里感谢小鄧子的提醒: ofType = filter + cast
        /*return _bus.filter(new Func1<Object, Boolean>() {
            @Override
            public Boolean call(Object o) {
                return eventType.isInstance(o);
            }
        }) .cast(eventType);*/
    }

    public static void main(String[] args) {
        RxBus.getInstance().send(new User("23", "Tikitoo"));

        RxBus.getInstance().toObserverable(User.class)
                .subscribe(new Subscriber<User>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(User user) {
                        System.out.println("user: "
                                + "id: " + user.id
                                + "login: " + user.login);
                    }
                });
    }
}
