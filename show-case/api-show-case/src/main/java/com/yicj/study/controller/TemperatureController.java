//package com.yicj.study.controller;
//
//import com.yicj.study.rxjava.RxSeeEmitter;
//import com.yicj.study.rxjava.TemperatureSender;
//import io.reactivex.rxjava3.core.Single;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
//import reactor.core.publisher.Mono;
//
//
///**
// * <p>
// * TemperatureController
// * </p>
// *
// * @author yicj
// * @since 2024年07月28日 19:42
// */
//@RestController
//public class TemperatureController {
//
//    private final TemperatureSender temperatureSender ;
//
//    public TemperatureController(TemperatureSender temperatureSender) {
//        this.temperatureSender = temperatureSender;
//    }
//
//    @GetMapping("/temperature/stream")
//    public SseEmitter events(){
//        RxSeeEmitter emitter = new RxSeeEmitter() ;
//        temperatureSender.temperatureStream().subscribe(emitter.getSubscriber());
//        return emitter ;
//    }
//
//    @GetMapping(value = "/temperature/rxjava")
//    public Single<String> rxjava(){
//        return Single.just("hello") ;
//    }
//
//
//    @GetMapping("/temperature/reactor")
//    public Mono<String> reactor(){
//        return Mono.just("hello") ;
//    }
//}
