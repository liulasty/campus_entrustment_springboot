package com.lz.exceptionHandling;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.lz.Exception.InvalidTokenException;
import com.lz.Exception.MyException;
import com.lz.Exception.NoAthleteException;
import com.lz.Exception.UsernameNotFoundException;
import com.lz.pojo.constants.MessageConstants;
import com.lz.pojo.result.ErrorCode;
import com.lz.pojo.result.Result;

import lombok.extern.slf4j.Slf4j;

/**
 * @author lz
 *         <p>
 *         通过全局异常处理的方式统一处理异常。
 */
@RestControllerAdvice
@RestController
@Slf4j
public class GlobalControllerAdvice {

    /**
     * 处理 form data方式调用接口校验失败抛出的异常
     */
    @ExceptionHandler(BindException.class)
    public Result<?> bindExceptionHandler(BindException e) {
        log.error("form data方式调用接口校验失败: {}", e.getMessage());
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        String msg = fieldErrors.stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));
        return Result.error(ErrorCode.PARAM_ERROR, msg);
    }

    /**
     * 处理 json 请求体调用接口校验失败抛出的异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<?> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        log.error("json 请求体调用接口校验失败: {}", e.getMessage());
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        String msg = fieldErrors.stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));
        return Result.error(ErrorCode.PARAM_ERROR, msg);
    }

    /**
     * 处理单个参数校验失败抛出的异常
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Result<?> constraintViolationExceptionHandler(ConstraintViolationException e) {
        log.error("单个参数校验失败: {}", e.getMessage());
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        String msg = constraintViolations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", "));
        return Result.error(ErrorCode.PARAM_ERROR, msg);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public Result<?> sqlIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e) {
        log.error("数据完整性违规异常: {}", e.getMessage());
        return Result.error(ErrorCode.DUPLICATE_KEY);
    }

    /**
     * 处理SQL异常
     */
    @ExceptionHandler(SQLException.class)
    public Result<?> exceptionHandler(SQLException e) {
        log.error("发生SQL异常: {}", e.getMessage());
        return Result.error(ErrorCode.DATABASE_ERROR);
    }

    @ExceptionHandler(NullPointerException.class)
    public Result<?> exceptionHandler(NullPointerException e) {
        log.error("发生空指针异常:", e);
        return Result.error(ErrorCode.SYSTEM_ERROR, "系统内部错误(NPE)");
    }

    @ExceptionHandler(MyException.class)
    public Result<?> myException(MyException e) {
        log.error("发生业务异常: {}", e.getMessage());
        return Result.error(ErrorCode.BUSINESS_ERROR, e.getMessage());
    }

    @ExceptionHandler(NoAthleteException.class)
    public Result<?> noAthleteException(NoAthleteException e) {
        log.error("未找到相关人员异常: {}", e.getMessage());
        return Result.error(ErrorCode.USER_NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public Result<?> usernameNotFoundException(UsernameNotFoundException e) {
        log.error("用户名不存在异常: {}", e.getMessage());
        return Result.error(ErrorCode.USER_NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(AuthenticationException.class)
    public Result<?> authenticationExceptionResult(AuthenticationException e) {
        log.error("认证失败异常: {}", e.getMessage());
        if (MessageConstants.BAD_PASSWORD_ERROR.equals(e.getMessage())) {
            return Result.error(ErrorCode.PASSWORD_ERROR, MessageConstants.DATABASE_PASSWORD_ERROR);
        }
        return Result.error(ErrorCode.UNAUTHORIZED, e.getMessage());
    }

    // 拦截AccessDeniedException
    @ExceptionHandler(AccessDeniedException.class)
    public Result<?> accessDeniedExceptionResult(AccessDeniedException e) {
        log.error("权限不足异常: {}", e.getMessage());
        return Result.error(ErrorCode.FORBIDDEN);
    }

    // 拦截InvalidTokenException
    @ExceptionHandler(InvalidTokenException.class)
    public Result<?> invalidTokenExceptionResult(InvalidTokenException e) {
        log.error("无效令牌异常: {}", e.getMessage());
        return Result.error(ErrorCode.TOKEN_INVALID, e.getMessage());
    }

    // 兜底异常处理
    @ExceptionHandler(Exception.class)
    public Result<?> globalExceptionHandler(Exception e) {
        log.error("发生未知系统异常:", e);
        return Result.error(ErrorCode.SYSTEM_ERROR, "系统繁忙，请稍后重试");
    }
}
