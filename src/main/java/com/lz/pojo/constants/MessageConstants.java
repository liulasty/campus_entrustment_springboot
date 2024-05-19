package com.lz.pojo.constants;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/04/04/21:29
 * @Description:
 */

/**
 * 消息常量
 *
 * @author lz
 * @date 2024/04/10
 */
public class MessageConstants {
    
    
    public static final String USER_NOT_EXIST = "用户名不存在";
    public static final String USER_EXIST = "用户名已存在";
    public static final String DELETE_USER_FAIL =  "删除用户失败";
    //用户名或密码错误
    public static final String USER_PASSWORD_ERROR = "用户名或密码错误";
    public static final String USER_REGISTER_SUCCESS = "注册成功,请在你的邮箱点击激活链接";
    public static final String USER_REGISTER_FAIL = "注册失败,用户名已存在";
    public static final String USER_LOGIN_SUCCESS = "登录成功";
    public static final String USER_LOGIN_FAIL = "登录失败";
    public static final String USER_LOGOUT_SUCCESS = "登出成功";
    
    public static final String USER_NOT_ACTIVE = "用户未激活,请前往邮箱激活";
    public static final String USER_ACTIVE_SUCCESS = "您的账户已成功激活！现在您可以登录并开始使用我们的服务了。";

    public static final String ADMIN_ACTIVE_USER_SUCCESS = "管理员激活用户成功";
    public static final String USER_ACTIVE_FAIL = "激活失败";
    public static final String USER_ACTIVE_CODE_ERROR = "激活码错误";
    public static final String USER_UPDATE_SUCCESS = "更新用户信息成功";
    public static final String DATA_VALIDATION_SUCCESS = "委托任务已提交请等待审核";

    

    /**
     前后端交互异常
     */
    public static final String NETWORK_ERROR = "网络连接不稳定，请稍候重试或检查您的网络设置";
    public static final String REQUEST_TIMEOUT = "服务器响应超时，请稍后重试";
    public static final String REQUEST_FORMAT_ERROR = "请求格式错误，请检查并按照规定格式发送数据";
    public static final String PERMISSION_DENIED = "您没有权限执行此操作";

    /**
     后端处理信息异常
     */
    public static final String DATABASE_ERROR = "系统繁忙，请稍后再试";
    public static final String DATA_VALIDATION_ERROR = "提交的数据不符合要求，请检查并修正";
    public static final String RESOURCE_NOT_FOUND = "您请求的资源未找到";
    public static final String BUSINESS_LOGIC_VIOLATION = "当前操作无法执行，原因：{}";
    public static final String CONCURRENCY_ISSUE = "由于并发冲突，操作未能成功完成，请稍后重试";
    public static final String THIRD_PARTY_SERVICE_UNAVAILABLE = "依赖的外部服务暂时不可用，请稍后重试或联系客服";

    /**
     * 其他通用异常信息
     */
    public static final String INTERNAL_SERVER_ERROR = "服务器内部错误，技术人员已收到通知并将尽快处理";
    public static final String UNEXPECTED_EXCEPTION = "发生意外错误，请稍后重试或联系客服";

    public static final String PASSWORD_CANNOT_BE_EMPTY = "密码不能为空";
    public static final String PASSWORD_WRONG = "密码错误，请重新输入";

    public static final String USER_DISABLE_SUCCESS = "管理员禁用用户成功";
    public static final String USER_DELETE_SUCCESS = "删除用户信息成功";
    public static final String USER_ABLE_SUCCESS = "取消禁用用户成功";
    public static final String USER_LOGOUT_FAILURE = "退出登录失败";
    public static final String SEND_EMAIL_FAIL = "发送邮件失败";
    public static final String TASK_UPDATE_SUCCESS =  "更新委托成功";
    public static final String TASK_PUBLISH_SUCCESS = "发布委托成功";
    public static final String TASK_PUBLISH_FAIL = "发布委托失败";
    public static final String TASK_AUDIT_REASON = "审核不通过，原因";
    public static final String DATA_AUDIT_SUCCESS =  "委托审核成功";
    public static final String DATA_AUDIT_FAIL = "委托审核失败";
    public static final String TASK_CANCEL_SUCCESS = "委托取消发布成功";

    
    public static final String USER_STATUS_ERROR = "用户状态异常,无法认证请重新查看";
    
    public static final String USER_EXIST_TASK = "用户存在进行中的委托任务，无法删除认证信息";   

    public static final String USER_AUTHENTICATION_INFO_NOT_EXIST = "用户认证信息不存在";

    public static final String USER_CANCEL_SUCCESS =  "取消认证成功";
    public static final String USER_CANCEL_FAIL =  "取消认证失败";
    public static final String USER_INFO_DELETE_SUCCESS =  "删除用户认证信息成功";
    //正在认证中，请耐心等待
    public static final String USER_AUTHENTICATION_INFO_EXISTING = "正在认证中，请耐心等待";
    public static final String USER_AUTHENTICATION_INFO_EXIST = "用户认证信息已存在";

    public static final String TASK_DRAFT_DELETE_SUCCESS = "委托草稿删除成功";
    public static final String TASK_NOT_EXIST = "当前委托信息异常，系统异常，请刷新后重试";
    public static final String TASK_INFO_SUCCESS = "获取委托信息成功" ;
    public static final String DATA_ACCEPT_SUCCESS = "接受委托信息已提交成功，等待处理";
    public static final String ACCEPT_CANCEL_SUCCESS = "取消委托接收成功";

    public static final String USER_INFO_ERROR = "用户信息异常";
    public static final String TASK_ACCEPT_SUCCESS = "委托接收成功";
    public static final String ADD_MESSAGE_FAILURE = "添加通知消息失败";
    public static final String ADD_MESSAGE_SUCCESS = "添加通知消息成功";
    public static final String DELETE_MESSAGE_SUCCESS = "删除通知消息成功";
    public static final String SYSTEM_ANNOUNCEMENTS_DELETE_SUCCESS = "删除系统公告成功";
    
    //委托类别更新成功
    public static final String TASK_CATEGORY_UPDATE_SUCCESS = "委托类别更新成功";
    public static final String TASK_CATEGORY_DELETE_SUCCESS = "委托类别删除成功";
    public static final String TASK_CATEGORY_EXIST = "委托类别已存在";
    public static final String TASK_CATEGORY_UPDATE_FAIL = "委托类别更新失败";
    public static final String TASK_CATEGORY_DELETE_FAIL = "委托类别删除失败";
    public static final String TASK_CATEGORY_NOT_EXIST_ERROR = "委托类别不存在";
    public static final String TASK_CATEGORY_ADD_SUCCESS = "委托类别添加成功";
    public static final String TASK_CANCEL_PUBLISH_SUCCESS = "取消委托发布成功";
    public static final String TASK_ACCEPTANCE_PROCESSED_SUCCESS = "您的委托接收成功";
    public static final String TASK_ACCEPTANCE_PROCESSED_FAILED = 
            "您的委托接收未被选中";
    public static final String TASK_COMPLETED_SUCCESS = "委托已完成";
    public static final String TASK_RECORDS_DELETE_SUCCESS = "委托记录删除成功";
    public static final String TASK_WITHDRAW_SUCCESS = "委托撤回成功";
}