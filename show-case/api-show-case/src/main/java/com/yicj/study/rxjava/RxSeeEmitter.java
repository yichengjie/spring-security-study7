//package com.yicj.study.rxjava;
//
//import com.yicj.study.model.Temperature;
//import lombok.Getter;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
//import rx.Subscriber;
//
///**
// * <p>
// * RxSeeEmitter
// * </p>
// *
// * @author yicj
// * @since 2024年07月28日 19:37
// */
//@Slf4j
//@Getter
//public class RxSeeEmitter extends SseEmitter {
//
//    static final long SSE_SESSION_TIMEOUT = 30 * 60 * 1000L ;
//    private final Subscriber<Temperature> subscriber ;
//
//    public RxSeeEmitter(){
//        super(SSE_SESSION_TIMEOUT) ;
//        this.subscriber = new Subscriber<>() {
//            @Override
//            public void onNext(Temperature temperature) {
//                log.info("send temperature : {}", temperature);
//                try {
//                    RxSeeEmitter.this.send(temperature);
//                }catch (Exception ex){
//                    unsubscribe();
//                }
//            }
//            @Override
//            public void onCompleted() {
//                // ignore
//            }
//            @Override
//            public void onError(Throwable e) {
//                // ignore
//            }
//        } ;
//        onCompletion(subscriber::unsubscribe);
//        onTimeout(subscriber::unsubscribe);
//    }
//
//}
