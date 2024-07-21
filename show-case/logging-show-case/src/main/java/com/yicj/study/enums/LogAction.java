package com.yicj.study.enums;

public enum LogAction {

    USER_ACTION("用户日志", "user-action"),
    SYS_ACTION("系统日志", "sys-action"),
    CUSTON_ACTION("其他日志", "custom-action");

    private final String action;

    private final String actionName;

    LogAction(String action,String actionName) {
        this.action = action;
        this.actionName = actionName;
    }

    public String getAction() {
        return action;
    }

    public String getActionName() {
        return actionName;
    }

}
