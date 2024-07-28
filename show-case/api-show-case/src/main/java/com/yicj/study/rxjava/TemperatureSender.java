package com.yicj.study.rxjava;

import com.yicj.study.model.Temperature;
import org.springframework.stereotype.Component;
import rx.Observable;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * TemperatureSender
 * </p>
 *
 * @author yicj
 * @since 2024年07月28日 18:17
 */
@Component
public class TemperatureSender {

    private final Random rnd = new Random() ;

    private final Observable<Temperature> dataStream =
            Observable.range(0, Integer.MAX_VALUE)
                    .concatMap(tick -> Observable
                            .just(tick)
                            .delay(rnd.nextInt(5000), TimeUnit.MILLISECONDS)
                            .map(tick2 -> probe()))
                    .publish()
                    .refCount();
    private Temperature probe(){
        return new Temperature(16 + rnd.nextGaussian() * 10) ;
    }

    public Observable<Temperature> temperatureStream(){
        return dataStream ;
    }
}
