package com.yicj.study.rxjava;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;


/**
 * <p>
 * RxJavaTest
 * </p>
 *
 * @author yicj
 * @since 2024/08/10 10:49
 */
@Slf4j
public class RxJavaTest {

    @Test
    void just(){
        Flowable.just("hello", "world")
            .subscribe(new BaseSubscriber<>() {
                @Override
                protected void hookOnSubscribe(Subscription subscription) {
                    request(1);
                }

                @Override
                protected void hookOnNext(String value) {
                    System.out.println(value);
                    request(1);
                }
            }) ;
    }

    @Test
    void create(){
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Throwable {
                emitter.onNext("hello");
                emitter.onNext("world");
                emitter.onComplete();
            }
        }).subscribe(value -> log.info("value : {}", value)) ;
    }
}
