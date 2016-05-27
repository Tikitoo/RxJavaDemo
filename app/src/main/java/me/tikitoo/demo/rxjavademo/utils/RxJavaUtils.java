package me.tikitoo.demo.rxjavademo.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import me.tikitoo.demo.rxjavademo.R;
import me.tikitoo.demo.rxjavademo.RetrofitService;
import me.tikitoo.demo.rxjavademo.api.GithubService;
import me.tikitoo.demo.rxjavademo.model.Course;
import me.tikitoo.demo.rxjavademo.model.Student;
import me.tikitoo.demo.rxjavademo.model.User;
import me.tikitoo.demo.rxjavademo.repo.Repo;
import rx.Observable;
import rx.Observer;
import rx.Producer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class RxJavaUtils {

    private static GithubService mGithubService;

    public static void main(String[] args) {
//        hello("dafa", "bvbvbvb", "unbngfhgh", "jkjkjk", "fdasfrehjhj");
//        queryTest();
//        zipTest();
//        liftObserverTest();
//        composeObserverrTest();
//        concatTest();
//        mergeTest();
        init();
        concatWithTest();
    }

    private RxJavaUtils instance = null;
    private static Context mContext = null;

    public void setContext(Context context) {
        mContext = context;
    }

    static void init() {
        mGithubService = RetrofitService.getInstance().create();
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

    public static void concatWithTest() {
        concatWith();
    }

    /**
     * 分页
     * reference http://stackoverflow.com/questions/29444297/stack-overflow-when-using-retrofit-rxjava-concatwith
     */
    public static void concatWith() {
        Observable.range(1, 4)
                .concatMap(new Func1<Integer, Observable<List<Repo>>>() {
                    @Override
                    public Observable<List<Repo>> call(Integer integer) {
                        return getStringByInteger(integer);
                    }
                })
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        System.out.println("RxJava concatWith: " + "doOnSubscribe");

                    }
                })
                .takeUntil(new Func1<List<Repo>, Boolean>() {
                    @Override
                    public Boolean call(List<Repo> repos) {
                        return repos.isEmpty();
                    }
                })
                .reduce(new Func2<List<Repo>, List<Repo>, List<Repo>>() {
                    @Override
                    public List<Repo> call(List<Repo> repos, List<Repo> repos2) {
                        List<Repo> repoList = new ArrayList<Repo>();
                        repoList.addAll(repos);
                        repoList.addAll(repos2);
                        return repoList;
                    }
                })
                .subscribe(new Subscriber<List<Repo>>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("RxJava concatWith: " + "onCompleted");

                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("RxJava concatWith: " + e);

                    }

                    @Override
                    public void onNext(List<Repo> repos) {
                        Observable.from(repos).subscribe(new Action1<Repo>() {
                            @Override
                            public void call(Repo repo) {
                                System.out.println("RxJava concatWith: " + repo.name);
                            }
                        });
                    }
                });

    }

    private static Observable<List<Repo>> getStringByInteger(Integer page) {
        return mGithubService.getReposRx(page, 5);
    }

    public static void mergeTest() {
        Observable<String> o1 = Observable.timer(0, 1000, TimeUnit.MILLISECONDS).map(new Func1<Long, String>() {
            @Override
            public String call(Long aLong) {
                System.out.println("RxJava timber o1 onNext: " + aLong);
                return String.valueOf(aLong * 5);
            }
        }).take(5);

        Observable<String> o2 = Observable.timer(500, 1000, TimeUnit.MILLISECONDS).map(new Func1<Long, String>() {
            @Override
            public String call(Long aLong) {
                System.out.println("RxJava timber o2 onNext: " + aLong);
                return String.valueOf(aLong * 10);
            }
        }).take(5);

        merge(o1, o2);

    }

    public static void merge(Observable o1, Observable o2) {
        Observable.merge(o1, o2).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                System.out.println("RxJava merge onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("RxJava merge onError: " + e);
            }

            @Override
            public void onNext(String o) {
                System.out.println("RxJava merge onNext: " + o);
            }
        });
    }
    public static void concatTest() {
        String[] strings = new String[] {
                "url1", null, "url3",
                "url4", "url5", "url6",
                "url7", "url8", "ur9",
        };

        String[] strings2 = new String[] {
                "data1", "data2", "data3",
                "data4", "data5", "data6",
        };

        concat(Observable.from(strings), Observable.from(strings2));
    }
    public static void concat(Observable<String> observable1, Observable<String> observable2) {
        Observable.concat(observable1, observable2).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                System.out.println("RxJava concat: " + s);
            }
        });
    }
    public static void composeObserverrTest() {
        composeObserver().subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                System.out.println("RxJava composeObserverr: " + s);
            }
        });
    }
    public static Observable<String> composeObserver() {
        Observable.Transformer<Integer, String> transformer = new Observable.Transformer<Integer, String>() {
            @Override
            public Observable<String> call(Observable<Integer> integerObservable) {
                return integerObservable.map(new Func1<Integer, String>() {
                    @Override
                    public String call(Integer integer) {
                        return "Transformer: " + integer;
                    }
                }).doOnNext(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        System.out.println("Transformer onNext: " + s);
                    }
                });
            }
        };
        return Observable.just(1,4, 7).compose(transformer);
    }

    public static void liftObserverTest() {
        liftObserver().subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                System.out.println("RxJava liftObserver: " + s);
            }
        });
    }

    public static Observable<String> liftObserver() {
        Observable.Operator<String, String> myOperator = new Observable.Operator<String, String>() {

            @Override
            public Subscriber<? super String> call(final Subscriber<? super String> subscriber) {
                return new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        if (!subscriber.isUnsubscribed()) {
                            subscriber.onCompleted();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (!subscriber.isUnsubscribed()) {
                            subscriber.onError(e);
                        }
                    }

                    @Override
                    public void onNext(String s) {
                        if (!subscriber.isUnsubscribed()) {
                            subscriber.onNext("myOperator: " + s);
                        }
                    }
                };
            }
        };

        return Observable.just(1, 3, 5)
                .map(new Func1<Integer, String>() {
                    @Override
                    public String call(Integer integer) {
                        return String.valueOf(integer);
                    }
                })
                .lift(myOperator).map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        return s;
                    }
                });
    }

    public static void zipTest() {
        String[] strings = new String[] {
                "url1", null, "url3",
                "url4", "url5", "url6",
                "url7", "url8", "ur9",
        };

        String[] strings2 = new String[] {
                "data1", null, "data3",
                "data", "data5", "data6",
        };
        zip(Arrays.asList(strings), Arrays.asList(strings2));
    }

    public static void zip(final List<String> stringList, List<String> stringList2) {
        Observable
                .zip(
                    Observable.just(stringList),
                    Observable.just(stringList2),
                    new Func2<List<String>, List<String>, List<String>>() {
                        @Override
                        public List<String> call(List<String> repos, List<String> strings) {
                            List<String> list = new ArrayList();
                            list.addAll(repos);
                            list.addAll(strings);
                            return list;
                        }
                    })

                .flatMap(new Func1<List<String>, Observable<String>>() {
                    @Override
                    public Observable<String> call(List<String> stringList) {
                        return Observable.from(stringList);
                    }
                })
                .filter(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String s) {
                        return s != null;
                    }
                }).subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("RxJava zip onCompleted");

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        System.out.println("RxJava zip onNext: " + s);

                    }
                });
    }

    public static void queryTest() {
        String[] strings = new String[] {
                "url1", "", "url3",
                "url4", "url5", "url6",
                "url7", "url8", "ur9",
                };
        query(Arrays.asList(strings));
    }

    public static void query(List<String> strings) {
        Observable.just(strings)
                .flatMap(new Func1<List<String>, Observable<String>>() {
                    @Override
                    public Observable<String> call(List<String> strings) {
                        return Observable.from(strings);
                    }
                })
                .flatMap(new Func1<String, Observable<String>>() {
                    @Override
                    public Observable<String> call(String s) {
                        return getTitle(s);
                    }
                })
                .filter(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String s) {
                        return s != null;
                    }
                })
                .take(4)
                .doOnNext(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        System.out.println("RxJava flatMap Query doOnNext " + s);

                    }
                })
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("RxJava flatMap Query onError: " + e.getMessage());
                    }

                    @Override
                    public void onNext(String s) {
                        System.out.println("RxJava flatMap Query " + s);

                    }
                });

    }

    private static Observable<String> getTitle(String s) {
        return Observable.just(s).map(new Func1<String, String>() {
            @Override
            public String call(String s) {
                String str = null;
                try {
                    str = s.substring(s.length() - 1, s.length());
                } catch (Exception e) {
                    Observable.error(new Throwable("message is not null"));
//                    throw new NullPointerException("message is not null");
//                    e.printStackTrace();
                }
                return str;
            }
        });
    }



    public static void hello(String... names) {
        CompositeSubscription subscription = new CompositeSubscription();
        subscription.add(subscription);
        subscription.remove(subscription);
        subscription.clear();

        Observable.from(new FutureTask<String>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return null;
            }
        })).filter(new Func1<String, Boolean>() {
            @Override
            public Boolean call(String s) {
                return null;
            }
        })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Action1<String>() {
            @Override
            public void call(String s) {

            }
        });

        Observable.from(new String[]{})
        .map(new Func1<String, Bitmap>() {
            @Override
            public Bitmap call(String s) {
                return null;
            }
        }).filter(new Func1<Bitmap, Boolean>() {
            @Override
            public Boolean call(Bitmap bitmap) {
                return bitmap != null;
            }
        }).subscribe(new Action1<Bitmap>() {
            @Override
            public void call(Bitmap bitmap) {

            }
        });

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
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
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



        mGithubService.getUserObservable("")
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

