//package com.yicj.study;
//
//import org.junit.jupiter.api.Test;
//
//import static java.lang.StringTemplate.STR;
//
///**
// * <p>
// * HelloTest
// * </p>
// *
// * @author yicj
// * @since 2024年07月13日 14:47
// */
//public class StringFeatureTest {
//
//    @Test
//    void hello(){
//        String orderId = "10011" ;
//        String orderDate = "2021-10-01" ;
//        String product = "pens" ;
//        int quantity = 10 ;
//        //
//        String content = STR."""
//               Order processed successfully:
//               Order ID: \{orderId}, placed on \{orderDate}
//               \{product}, \{quantity} (\{product.contains("pens") ? "dozens" : "units"})
//               Total Price: \{calculatePrice(product, quantity)}""" ;
//        System.out.println(content);
//    }
//
//    private static Integer calculatePrice(String product, int quantity) {
//        return 1 ;
//    }
//}
