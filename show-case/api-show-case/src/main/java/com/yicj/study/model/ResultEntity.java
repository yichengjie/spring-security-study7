package com.yicj.study.model;

import com.github.pagehelper.Page;
import lombok.Data;
import java.io.Serializable;
import java.util.HashMap;

/**
 * <p>
 * ResultEntity
 * </p>
 *
 * @author yicj
 * @since 2024年07月25日 15:22
 */
@Data
public class ResultEntity<T> implements Serializable {
    private Integer code;
    private String msg;
    private T data;
    public ResultEntity() {
    }

    public ResultEntity(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResultEntity(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }


    public static ResultEntity<Object> success(Object object) {
        ResultEntity<Object> resultEntity = new ResultEntity<>();
        resultEntity.setCode(TeamCreamEnum.GLOBAL_SUCCESS.getCode());
        resultEntity.setMsg(TeamCreamEnum.GLOBAL_SUCCESS.getMessage());
        if (object instanceof Page) {
            Page<Object> temp = (Page)object;
            HashMap<String, Object> data = new HashMap();
            data.put("curPageNo", temp.getPageNum());
            data.put("pageSize", temp.getPageSize());
            data.put("recordCount", temp.getTotal());
            data.put("pageCount", temp.getPages());
            data.put("recordList", object);
            resultEntity.setData(data);
        } else {
            resultEntity.setData(object);
        }
        return resultEntity;
    }

    public static ResultEntity<Void> success() {
        ResultEntity<Void> resultEntity = new ResultEntity<>();
        resultEntity.setCode(TeamCreamEnum.GLOBAL_SUCCESS.getCode());
        resultEntity.setMsg(TeamCreamEnum.GLOBAL_SUCCESS.getMessage());
        return resultEntity;
    }

    public static ResultEntity<?> error(Integer errorCode, String errorMsg, Object dataObject) {
        ResultEntity resultEntity = new ResultEntity();
        resultEntity.setCode(errorCode);
        resultEntity.setMsg(errorMsg);
        resultEntity.setData(dataObject);
        return resultEntity;
    }

    public static ResultEntity error(Integer errorCode, String errorMsg) {
        ResultEntity resultEntity = new ResultEntity();
        resultEntity.setCode(errorCode);
        resultEntity.setMsg(errorMsg);
        return resultEntity;
    }

}
