package com.lz.utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MarkdownDirectoryGenerator {

    // 增加忽略路径列表
    // 使用 Arrays.asList 替代 List.of
    private static final List<String> IGNORE_PATHS = Arrays.asList("images", ".obsidian");

    /**
     * 递归生成目录结构
     *
     * @param directory 当前目录
     * @param indent    缩进（用于层级显示）
     * @return 目录结构列表
     */
    private static List<String> generateDirectoryStructure(File directory, String indent) {
        List<String> directoryStructure = new ArrayList<>();

        if (directory.isDirectory()) {
            // 添加当前目录名称
            directoryStructure.add(indent + "- " + directory.getName());

            // 遍历目录下的文件和子目录
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    // 检查是否在忽略路径列表中
                    if (IGNORE_PATHS.contains(file.getName())) {
                        continue;
                    }

                    if (file.isDirectory()) {
                        // 递归处理子目录
                        directoryStructure.addAll(generateDirectoryStructure(file, indent + "  "));
                    } else {
                        // 添加文件名称
                        String fileName = file.getName().substring(0, file.getName().lastIndexOf("."));
                        directoryStructure.add(indent + "  - [" + fileName + "](" + getRelativePath(file) + ")");
                    }
                }
            }
        }

        return directoryStructure;
    }

    /**
     * 获取相对路径
     *
     * @param file 文件
     * @return 相对路径
     */
    private static String getRelativePath(File file) {
        // 获取文件的绝对路径
        String absolutePath = file.getAbsolutePath();
        // 获取当前工作目录
        String basePath = System.getProperty("user.dir");
        // 从绝对路径中移除当前工作目录路径，得到相对路径
        String substring = absolutePath.substring(basePath.length() + 1);
        // 将路径中的反斜杠替换为斜杠，以统一路径格式
        substring = substring.replace("\\", "/");
        substring = substring.replace(" ", "%20");
        // 返回相对路径
        return substring;
    }

    /**
     * 生成目录结构
     *
     * @param directory 目录
     * @return 目录结构
     */
    public static String generateDirectoryStructure(File directory) {
        // 递归生成目录结构，初始调用时不带前缀
        List<String> directoryStructure = generateDirectoryStructure(directory, "");
        // 使用换行符连接目录结构列表中的所有字符串，形成完整的目录结构字符串
        return String.join("\n", directoryStructure);
    }

    public static void main(String[] args) {
        String targetDirectory = "C:\\Users\\Administrator\\Desktop\\studiousWaffle";
        String directoryStructure = generateDirectoryStructure(new File(targetDirectory));
        //生成md文件

        try {
            FileUtils.writeStringToFile(new File("C:\\Users\\Administrator\\Desktop\\studiousWaffle\\目录.md"), directoryStructure, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
