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
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 路径匹配器
 *
 * @author lz
 * @date 2024/04/06
 */
public class PathMatcher {
    private static final String PATH_SEPARATOR = "/campus_entrustment";
    private static final List<Pattern> WHITELIST_PATTERNS = Arrays.asList(
            Pattern.compile(PATH_SEPARATOR+"/user/login"),
            Pattern.compile(PATH_SEPARATOR+"/user/register"),
            Pattern.compile(PATH_SEPARATOR+"/user/active/.*"), // Convert "**" to ".*" for regex matching
            Pattern.compile(PATH_SEPARATOR+"/swagger-ui.html"),
            Pattern.compile(PATH_SEPARATOR+"/swagger-resources/.*"), // Convert "**" to ".*" for regex matching
            Pattern.compile(PATH_SEPARATOR+"/webjars/.*"), // Convert "**" to ".*" for regex matching
            Pattern.compile(PATH_SEPARATOR+"/swagger-resources"), // No need to convert, as it's a literal match
            Pattern.compile(PATH_SEPARATOR+"/v2/api-docs"),
            Pattern.compile(PATH_SEPARATOR+"/druid/.*"), // Convert "**" to ".*" for regex matching
            Pattern.compile(PATH_SEPARATOR+"/doc.html"),
            Pattern.compile(PATH_SEPARATOR+"/favicon.ico")
    );

    public static boolean isUrlWhitelisted(String url) {
        if (url == null) {
            return false;
        }
        return WHITELIST_PATTERNS.stream()
                .anyMatch(pattern -> pattern.matcher(url).matches());
    }
}