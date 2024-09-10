package com.yicj.study.jpa.repository;

import com.yicj.study.jpa.HelloJapApplication;
import com.yicj.study.jpa.entity.FeatureMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;

/**
 * <p>
 * FeatureMessageExampleTet
 * </p>
 *
 * @author yicj
 * @since 2024/9/10 21:14
 */
@SpringBootTest(classes = HelloJapApplication.class)
class FeatureMessageExampleTest {

    @Autowired
    private FeatureMessageRepository repository ;

    @Test
    void hello(){
        FeatureMessage message = new FeatureMessage() ;
        message.setMessageHeadline("head");
        message.setMessageContent("test");
        message.setMessageType("m1");
        message.setSummary("summary");
        //
        ExampleMatcher matcher = ExampleMatcher.matching()
            .withMatcher("messageHeadline", match -> match.contains().ignoreCase())
            .withMatcher("messageContent", match -> match.startsWith().ignoreCase())
            .withMatcher("messageType", match -> match.exact().ignoreCase())
            .withIgnoreNullValues()
            .withStringMatcher(ExampleMatcher.StringMatcher.DEFAULT)
            ;
        Example<FeatureMessage> example = Example.of(message, matcher) ;
        //
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate") ;
        //
        repository.findAll(example, sort).forEach(System.out::println) ;
    }

}
