package me.tikitoo.demo.rxjavademo.test;

import java.util.ArrayList;
import java.util.List;

/**
 * 被观察者
 *
 * 观察者
 *
 *
 */
public class Subject {
    private List<Observer> mObserverList = new ArrayList<>();

    public void register(Observer observer) {
        mObserverList.add(observer);
        System.out.println("register a observer.");
    }
    
    public void unRegister(Observer observer) {
        mObserverList.remove(observer);
    }
    
    public void notifyObservers(String newState) {
        for (Observer observer : mObserverList) {
            observer.update(newState);
        }
    }

}

class StuSubject extends Subject {
    private String state;

    public String getState() {
        return state;
    }

    public void change(String newState) {
        state = newState;
        System.out.println("Subject change: " + state);
        notifyObservers(state);
    }
}

interface Observer {
    void update(String state);
}

class StuObserver implements Observer {
    private String observerState;

    @Override
    public void update(String state) {
        observerState = state;
        System.out.println("Observer change: " + state);
    }
}



class Test {
    public static void main(String[] args) {
        StuSubject subject = new StuSubject();
        for (int i = 0; i < 10; i++) {
            Observer observer = new StuObserver();
            subject.register(observer);
            subject.change("new state" + i);
        }
    }
}
