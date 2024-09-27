package com.yicj.study.r2dbc.model.vo;


import com.yicj.study.r2dbc.enums.Code;
import lombok.Data;

/**
 * @author yicj
 * @since 2024/9/27
 */
@Data
public class ResultVO <T> {

    private T data ;

    private Integer code ;

    private String message ;

    public ResultVO(T data, Integer code, String message) {
        this.data = data;
        this.code = code;
        this.message = message;
    }

    // success no input data
    public static <T> ResultVO<T> success(){
        return new ResultVO<>(null, 200, "success") ;
    }

    public static <T> ResultVO<T> success(T data){
        return new ResultVO<>(data, 200, "success") ;
    }

    public static <T> ResultVO<T> error(Integer code, String message){
        return new ResultVO<>(null, code, message) ;
    }

    public static <T> ResultVO<T> error(Code code){
        return new ResultVO<>(null, code.getCode(), code.getMessage()) ;
    }

}
