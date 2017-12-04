package com.test.rxjava;

import com.sun.jndi.toolkit.url.Uri;

import java.util.Collections;
import java.util.List;

/**
 * Created by hzshangjiaxiong on 16/11/21.
 */
public class AsyncCatsHelper {

    AsyncApi api;

    public Observable<List<Cat>> queryCats(String query){
        return new Observable<List<Cat>>() {
            @Override
            public void subscribe(AsyncApi.Subscriber<List<Cat>> subscriber) {
                api.queryCats(query , subscriber);
            }
        };
    }

    public Observable<Uri> storeCat(Cat cat){
        return new Observable<Uri>() {
            @Override
            public void subscribe(AsyncApi.Subscriber<Uri> subscriber) {
                api.store( cat , subscriber);
            }
        };
    }


    public Observable<Uri> saveTheCutestCat(String query){
        Observable<List<Cat>> catsJob = queryCats(query);

        Observable<Cat> cutestCatJob = catsJob.map(f -> Collections.max(f));

        Observable<Uri> storeUriJob = cutestCatJob.flatmap(f -> storeCat(f));

        return storeUriJob;
    }

    public void saveTheCutestCat(String query , AsyncApi.Subscriber<Uri> cutestCatSubscriber){

        Observable<List<Cat>> queryCatsJob = queryCats(query);

        queryCatsJob.subscribe(new AsyncApi.Subscriber<List<Cat>>() {
            @Override
            public void onNext(List<Cat> data) {
                Observable<Uri> storeCat = storeCat( Collections.max(data) );
                storeCat.subscribe(cutestCatSubscriber);
            }

            @Override
            public void onError(Exception e) {
                cutestCatSubscriber.onError(e);
            }
        });

    }
}

abstract class Observable<T>{

    interface Func<T,F>{
        F call(T f);
    }

    public abstract void subscribe(AsyncApi.Subscriber<T> subscriber);

    public <F> Observable<F> map(Func<T,F> func){
        final Observable<T> source = this;
        return new Observable<F>() {
            @Override
            public void subscribe(AsyncApi.Subscriber<F> subscriber) {
                source.subscribe(new AsyncApi.Subscriber<T>() {
                    @Override
                    public void onNext(T data) {
                        subscriber.onNext( func.call(data) );
                    }

                    @Override
                    public void onError(Exception e) {
                        subscriber.onError(e);
                    }
                });
            }

        };
    }

    public <F> Observable<F> flatmap(Func<T,Observable<F>> func){
        final Observable<T> source = this;
        return new Observable<F>() {
            @Override
            public void subscribe(AsyncApi.Subscriber<F> subscriber) {
                source.subscribe(new AsyncApi.Subscriber<T>() {
                    @Override
                    public void onNext(T data) {
                        Observable<F> mapped = func.call(data);
                        mapped.subscribe(subscriber);
                    }

                    @Override
                    public void onError(Exception e) {
                        subscriber.onError(e);
                    }
                });
            }

        };
    }
}

interface AsyncApi{

    interface Subscriber<T>{
        void onNext(T data);
        void onError(Exception e);
    }

    void queryCats(String query, Subscriber<List<Cat>> catsQuerySubscriber);

    void store(Cat cat, Subscriber<Uri> storeSubscriber);
}