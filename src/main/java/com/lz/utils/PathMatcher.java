package com.lz.utils;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/04/06/16:42
 * @Description:
 */

/**
 * @author lz
 */
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * 路径匹配器
 *
 * @author lz
 * @date 2024/04/06
 */
@Slf4j
public class PathMatcher {
    private static final String PATH_SEPARATOR = "/campus_entrustment";
    private static final List<Pattern> WHITELIST_PATTERNS;

    static {
        WHITELIST_PATTERNS = Arrays.asList(
                Pattern.compile(PATH_SEPARATOR + "/user/login"),
                Pattern.compile(PATH_SEPARATOR + "/user/register"),
                Pattern.compile(PATH_SEPARATOR + "/user/active/.*"),
                // Convert "**" to ".*" for regex matching
                Pattern.compile(PATH_SEPARATOR + "/swagger-ui.html"),
                // Convert "**" to ".*" for regex matching
                Pattern.compile(PATH_SEPARATOR + "/swagger-resources/.*"), 
                //将“**”转换为“.*”以进行正则表达式匹配
                Pattern.compile(PATH_SEPARATOR + "/webjars/.*"), 
                Pattern.compile(PATH_SEPARATOR + "/swagger-resources"), 
                
                Pattern.compile(PATH_SEPARATOR + "/v2/api-docs"),
                // 将“**”转换为“.*”以进行正则表达式匹配
                Pattern.compile(PATH_SEPARATOR + "/druid/.*"), 
                Pattern.compile(PATH_SEPARATOR + "/doc.html"),
                Pattern.compile(PATH_SEPARATOR + "/favicon.ico"),
                Pattern.compile(PATH_SEPARATOR + "/user/logout"),
                Pattern.compile(PATH_SEPARATOR + "/img/upload"),
                Pattern.compile(PATH_SEPARATOR + "/common/.*")
        );
    }

    /**
     * 判断URL是否在白名单内
     *
     * @param url 待判断的URL字符串
     * @return 如果URL在白名单内，返回true；否则返回false。
     */
    public static boolean isUrlWhitelisted(String url) {
        if (url == null) {
            log.error("URL为空，无法判断是否在白名单内");
            return false;
        }

        // 标准化URL，移除查询参数和锚点（这里简化处理，实际可能需要更复杂的逻辑）
        String normalizedUrl = url.split("\\?")[0].split("#")[0];

        try {
            return WHITELIST_PATTERNS.stream()
                    .anyMatch(pattern -> pattern.matcher(normalizedUrl).matches());
        } catch (PatternSyntaxException e) {
            // 记录日志或执行其他错误处理
            e.printStackTrace();
            log.error("匹配 URL 时发生 PatternSyntaxException：{}", url, e);
            return false;
        }
    }
}