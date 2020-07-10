package com.chris.blue;

import java.util.Vector;

public class Observable {

    private static Observable mSearchObservable;

    private Observable(){}

    public static Observable getInstance() {
        if(null == mSearchObservable){
            synchronized (Observable.class){
                if(null == mSearchObservable){
                    mSearchObservable = new Observable();
                }
            }
        }
        return mSearchObservable;
    }

    public interface Observer {
        void onSearch(String word);
    }

    private Vector<Observer> observerVector = new Vector<>();

    public void addObserver(Observer observer) {
        if (!observerVector.contains(observer)) {
            observerVector.addElement(observer);
        }
    }

    public void deleteObserver(Observer observer) {
        if (observerVector.contains(observer)) {
            observerVector.removeElement(observer);
        }
    }

    public synchronized void notifyObservers(String word){
        for (Observer observer : observerVector){
            observer.onSearch(word);
        }
    }
}
