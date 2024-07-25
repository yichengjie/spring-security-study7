package com.yicj.study.bodyadvice.model;

/**
 * <p>
 * TeamCreamEnum
 * </p>
 *
 * @author yicj
 * @since 2024年07月25日 15:25
 */
public enum TeamCreamEnum {

    GLOBAL_SUCCESS(200, "成功"),
    GLOBAL_ERROR(-1, "未知异常，请联系运维人员"),
    NO_SUCH_METHOD_EXCEPTION(101, "找不到对应的方法，请联系运维人员"),
    DB_OPERATE_ERROR(4001, "数据库操作失败"),
    DATA_LIST_ISEMPTY(4002, "导出数据为空！"),
    FILE_OPERATE_ERROR(4501, "文件操作失败"),
    FILE_UPLOAD_ERROR(4502, "文件上传失败"),
    REQUEST_PARAM_ERROR(400, "请求参数不合法"),
    INTERNAL_SERVER_ERROR(5000, "API调用错误");

    private final Integer code;
    private final String message;

    private TeamCreamEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}
