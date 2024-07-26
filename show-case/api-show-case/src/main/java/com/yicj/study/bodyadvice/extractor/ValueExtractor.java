package com.yicj.study.bodyadvice.extractor;

/**
 * <p>
 * ValueExtractor
 * </p>
 *
 * @author yicj
 * @since 2024年07月26日 10:22
 */
public interface ValueExtractor<T>{

    Object extract(T data) ;

}
