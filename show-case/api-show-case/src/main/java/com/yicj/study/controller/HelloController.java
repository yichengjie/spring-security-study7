package com.yicj.study.controller;

import com.github.pagehelper.Page;
import com.yicj.study.bodyadvice.anno.ErasePasswordAnno;
import com.yicj.study.model.ResultEntity;
import com.yicj.study.model.BasicUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * <p>
 * HelloController
 * </p>
 *
 * @author yicj
 * @since 2024年07月25日 15:22
 */
@Slf4j
@RestController
@RequestMapping("/hello")
public class HelloController {

    @ErasePasswordAnno
    @GetMapping("/detail/{id}")
    public ResultEntity<BasicUser> findById(@PathVariable String id){
        BasicUser basicUser = new BasicUser() ;
        basicUser.setId(id);
        basicUser.setName("yicj");
        basicUser.setPassword("123456");
        ResultEntity value = ResultEntity.success(basicUser);
        log.info("value : {}", value);
        return value ;
    }

    @ErasePasswordAnno("['recordList']")
    @GetMapping("/listForPage")
    public ResultEntity findPage() {
        Page<BasicUser> basicUserPage = new Page<>(1, 10, true);
        basicUserPage.setPages(1) ;
        basicUserPage.setTotal(1);
        //
        BasicUser basicUser = new BasicUser() ;
        basicUser.setId("1");
        basicUser.setName("yicj");
        basicUser.setPassword("123456");
        //
        basicUserPage.add(basicUser);
        //
        ResultEntity value = ResultEntity.success(basicUserPage);
        log.info("value : {}", value);
        return value ;
    }

    @ErasePasswordAnno
    @GetMapping("/list")
    public ResultEntity findList() {
        BasicUser basicUser = new BasicUser() ;
        basicUser.setId("1");
        basicUser.setName("yicj");
        basicUser.setPassword("123456");
        //
        List<BasicUser> basicUserList = List.of(basicUser) ;
        ResultEntity value = ResultEntity.success(basicUserList);
        log.info("value : {}", value);
        return value ;
    }


    @ErasePasswordAnno
    @GetMapping("/null")
    public ResultEntity testNull() {
        return null ;
    }

    @ErasePasswordAnno
    @GetMapping("/void")
    public void testVoid() {

    }
}
