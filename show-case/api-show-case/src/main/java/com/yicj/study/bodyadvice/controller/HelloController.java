package com.yicj.study.bodyadvice.controller;

import com.github.pagehelper.Page;
import com.yicj.study.bodyadvice.anno.ErasePasswordAnno;
import com.yicj.study.bodyadvice.model.ResultEntity;
import com.yicj.study.bodyadvice.model.BasicUser;
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
        return ResultEntity.success(basicUser);
    }

    @ErasePasswordAnno
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
        return ResultEntity.success(basicUserPage);
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
        return ResultEntity.success(basicUserList);
    }

}
