package com.yicj.study.retrofit;

import com.yicj.study.model.Blog;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.adapter.rxjava3.Result;
import retrofit2.http.POST;

import java.util.List;

public interface BlogService {

  @POST("/blog")
  Observable<Result<List<Blog>>> getBlogs();
}