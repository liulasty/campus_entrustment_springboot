package com.lz.exceptionHandling;

import com.lz.Exception.InvalidTokenException;
import com.lz.Exception.MyException;
import com.lz.Exception.NoAthleteException;
import com.lz.pojo.constants.MessageConstants;
import com.lz.pojo.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import com.lz.Exception.UsernameNotFoundException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author lz
 * <p>
 * 通过全局异常处理的方式统一处理异常。
 */
@RestControllerAdvice
@RestController
@Slf4j
public class GlobalControllerAdvice {
    private static final String BAD_REQUEST_MSG = "客户端请求参数错误";


    // <1> 处理 form data方式调用接口校验失败抛出的异常

    @ExceptionHandler(BindException.class)
    public Result<String> bindExceptionHandler(BindException e) {
        log.info("form data方式调用接口校验失败 " + e);
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();

        List<String> collect = fieldErrors.stream()

                .map(DefaultMessageSourceResolvable::getDefaultMessage)

                .collect(Collectors.toList());

        return Result.error(collect.toString());

    }

    // <2> 处理 json 请求体调用接口校验失败抛出的异常

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<String> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        log.info("json 请求体调用接口校验失败 " + e);
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();

        List<String> collect = fieldErrors.stream()

                .map(DefaultMessageSourceResolvable::getDefaultMessage)

                .collect(Collectors.toList());

        return Result.error(collect.toString());

    }

    // <3> 处理单个参数校验失败抛出的异常

    @ExceptionHandler(ConstraintViolationException.class)
    public Result<String> constraintViolationExceptionHandler(ConstraintViolationException e) {
        log.info("单个参数校验失败 " + e);
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();

        List<String> collect = constraintViolations.stream()

                .map(ConstraintViolation::getMessage)

                .collect(Collectors.toList());

        return Result.error(collect.toString());

    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public Result<String> sqlIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e) {
        log.info("SQLIntegrityConstraintViolationException = " + e);

        return Result.error("SQLIntegrityConstraintViolationException");
    }

    /**
     * 异常处理程序
     * 处理SQL异常
     *
     * @param e e
     *
     * @return {@code Result}
     */
    @ExceptionHandler(value = SQLException.class)
    public Result<String> exceptionHandler(SQLException e) {

        log.error("发生SQL异常！原因是{} {}:", e.getMessage(), e);
        return Result.error(e.getMessage());
    }

    @ExceptionHandler(value = NullPointerException.class)
    public Result<String> exceptionHandler(NullPointerException e) {
        log.error("发生系统异常！原因是:", e);
        return Result.error(e.getMessage());
    }

    @ExceptionHandler(value = MyException.class)
    public Result<String> myException(MyException e) {
        log.error("发生系统异常！原因是:", e);
        return Result.error(e.getMessage());
    }

    @ExceptionHandler(value = NoAthleteException.class)
    public Result<NoAthleteException> noAthleteException(NoAthleteException e) {
        log.error("发生系统异常！原因是NoAthleteException:", e);

        return Result.error(e);
    }

    @ExceptionHandler(value = UsernameNotFoundException.class)
    public Result<UsernameNotFoundException> usernameNotFoundException(UsernameNotFoundException e) {
        log.error("发生系统异常！原因是UsernameNotFoundException:", e);

        return Result.error(e);
    }


    @ExceptionHandler(value = AuthenticationException.class)
    public Result<AuthenticationException> authenticationExceptionResult(AuthenticationException e) {
        log.error("发生系统异常！原因是AuthenticationException:", e);
        if (e.getMessage().equals(MessageConstants.BAD_PASSWORD_ERROR)) {
            return Result.error(MessageConstants.DATABASE_PASSWORD_ERROR);
        }

        return Result.error(e.getMessage());
    }

    //拦截AccessDeniedException
    @ExceptionHandler(value = AccessDeniedException.class)
    public Result<AccessDeniedException> accessDeniedExceptionResult(AccessDeniedException e) {
        log.error("发生系统异常！原因是AccessDeniedException:", e);

        return Result.error(e);
    }

    //拦截RuntimeException
    @ExceptionHandler(value = InvalidTokenException.class)
    public Result<InvalidTokenException> runtimeExceptionResult(InvalidTokenException e) {
        log.error("发生系统异常！原因是RuntimeException:", e);

        return Result.error(e);
    }

}