package me.tikitoo.demo.rxjavademo.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import me.tikitoo.demo.rxjavademo.R;
import me.tikitoo.demo.rxjavademo.model.Course;
import me.tikitoo.demo.rxjavademo.model.Student;
import me.tikitoo.demo.rxjavademo.model.User;
import me.tikitoo.demo.rxjavademo.api.GithubService;
import retrofit2.Retrofit;
import rx.Observable;
import rx.Observer;
import rx.Producer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class RxJavaUtils {
    public static void main(String[] args) {
        hello("dafa", "bvbvbvb", "unbngfhgh", "jkjkjk", "fdasfrehjhj");
    }


    private RxJavaUtils instance = null;
    private static Context mContext = null;


    public void setContext(Context context) {
        mContext = context;
    }

    public RxJavaUtils getInstance() {
        if (instance != null) {
            synchronized (this) {
                if (instance != null) {
                    instance = new RxJavaUtils();
                }
            }
        }
        return instance;
    }

    public static void hello(String... names) {
        Observable.from(names).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                System.out.println("Hello " + s + "!");
            }
        });

        Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber<? super Object> subscriber) {
                subscriber.onNext("");
                subscriber.onCompleted();
            }
        }).subscribe(new Subscriber<Object>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object o) {

            }
        });
    }

    public static void onEvent(String... msg) {
        Observable.from(msg)
                .doOnNext(new Action1<String>() {
                    @Override
                    public void call(String s) {

                    }
                })
                .doOnCompleted(new Action0() {
                    @Override
                    public void call() {

                    }
                })
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.getStackTrace();
                    }
                })
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {

                    }
                });

        Observer<String> observerStr = new Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {

            }
        };

        final Subscriber<String> subscriberStr = new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (isUnsubscribed()) {
                    unsubscribe();
                }

            }

            @Override
            public void onNext(String s) {

            }

            @Override
            public void onStart() {
                super.onStart();
            }


            @Override
            public void setProducer(Producer p) {
                super.setProducer(p);
            }
        };

        Observable observableCreate = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("");
                subscriber.onNext("");
                subscriber.onNext("");
                subscriber.onCompleted();
            }

        });

        Observable observableJust = Observable.just("dfaf", "oewpopew", "ieuuei", "cvumdue");

        String[] words = {"hello", "World", "Alpha"};
        Observable observableFrom = Observable.from(words);

        observableCreate.subscribe(observerStr);
        observableCreate.subscribe(subscriberStr);

        Action1<String> onNextAction = new Action1<String>() {
            @Override
            public void call(String s) {

            }
        };

        Action1<Throwable> onErrorAction = new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        };

        Action0 onCompletedAction = new Action0() {
            @Override
            public void call() {

            }
        };

        observableCreate.subscribe(onNextAction);
        observableCreate.subscribe(onNextAction, onErrorAction);
        observableCreate.subscribe(onNextAction, onErrorAction, onCompletedAction);

        // print string array
        String[] names = {"", "", ""};
        Observable.from(names).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                System.out.println("onNext: " + s);
            }
        });

        // get photo show by id
        final int drawableRes = R.drawable.ic_logo;
        final ImageView imageView = null;
        Observable.create(new Observable.OnSubscribe<Drawable>() {
            @Override
            public void call(Subscriber<? super Drawable> subscriber) {
                Drawable drawable = mContext.getTheme().getDrawable(drawableRes);
                subscriber.onNext(drawable);
                subscriber.onCompleted();
            }
        }).subscribe(new Observer<Drawable>() {
            @Override
            public void onNext(Drawable drawable) {
                imageView.setImageDrawable(drawable);
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                System.out.println("Error: " + e.getStackTrace());
            }
        });

        // Scheduler: Thread Control
        Observable.just(1, 23, 4, 4, 4543, 565)
                .subscribeOn(Schedulers.io()) // subscribe() thread
                .observeOn(AndroidSchedulers.mainThread()) // Observer/Subscriber thread
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        System.out.println("Number: " + integer);
                    }
                });

        // Switch map()/ flatMap()
        Observable.just("image/logo.phg")
                .map(new Func1<String, Bitmap>() {
                    @Override
                    public Bitmap call(String filePath) {
                        return getBitmapFromPath(filePath);
                    }
                })
                .subscribe(new Action1<Bitmap>() {
                    @Override
                    public void call(Bitmap bitmap) {
                        showBitmap(bitmap);
                    }
                });

        Student[] students = new Student[] {};
        Subscriber<Course> courseSubscriber = new Subscriber<Course>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Course course) {
                System.out.println("tag: " + course.name);
            }
        };

        Observable.from(students)
//                .lift()
                .flatMap(new Func1<Student, Observable<Course>>() {
                    @Override
                    public Observable<Course> call(Student student) {
                        return Observable.from(student.course);
                    }
                })
                .subscribe(courseSubscriber);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("")
                .build();

        GithubService githubService = retrofit.create(GithubService.class);
        githubService.getUserObservable("")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<User>() {
                    @Override
                    public void onNext(User user) {
                        setUser(user);
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });


    }

    private static void setUser(User user) {

    }

    private static void showBitmap(Bitmap bitmap) {
    }

    private static Bitmap getBitmapFromPath(String filePath) {
        Bitmap bitmap = null;
        return bitmap;
    }


}

