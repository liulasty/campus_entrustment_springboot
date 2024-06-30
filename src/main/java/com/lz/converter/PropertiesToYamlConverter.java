package com.lz.converter;

import java.io.*;
import java.util.*;
/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/06/29/下午1:29
 * @Description:
 */

/**
 * @author lz
 */
public class PropertiesToYamlConverter {
    private  static final String YAML_COMMENT = "src/main/java/com/lz/converter/";
    public static void main(String[] args) throws IOException {
        String propertiesFilePath = YAML_COMMENT+"application.properties";
        
        
        String replace = propertiesFilePath.replace("properties", "yml");
        System.out.println(replace);

        String yamlFilePath = YAML_COMMENT+replace;
        

        // 调用转换方法，将properties文件转换为yaml文件，并备份原properties文件  
        convert(new File(propertiesFilePath),new File(replace));
    }

   
    

    public static void convert(File propertiesFile, File yamlFile) throws IOException {
        List<String> lines = new ArrayList<>();
        List<String> comments = new ArrayList<>();
        LinkedHashMap<String, Object> yamlMap = new LinkedHashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(propertiesFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }
                if (line.startsWith("#")) {
                    comments.add(line);
                    continue;
                }

                String[] parts = line.split("=", 2);
                if (parts.length < 2) continue;

                String key = parts[0].trim();
                String value = parts[1].trim();

                String[] keys = key.split("\\.");
                LinkedHashMap<String, Object> currentMap = yamlMap;

                for (int i = 0; i < keys.length - 1; i++) {
                    currentMap = (LinkedHashMap<String, Object>) currentMap.computeIfAbsent(keys[i], k -> new LinkedHashMap<>());
                }

                currentMap.put(keys[keys.length - 1], value);
            }
        }
        System.out.println(yamlMap);
        System.out.println("comments:" + comments);

        String yamlContent = buildYamlString(yamlMap, 0);
        System.out.println("yamlContent:\n" + yamlContent);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(yamlFile))) {
            for (String line : lines) {
                if (line.trim().startsWith("#")) {
                    writer.write(line);
                    writer.newLine();
                }
            }
            writer.write(yamlContent);
        }
    }

    private static String buildYamlString(Map<String, Object> map, int indent) {
        StringBuilder yamlBuilder = new StringBuilder();
        // 使用StringBuilder来替代不存在的repeat方法
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < indent; i++) {
            sb.append(' ');
        }
        String indentString = sb.toString();


        for (Map.Entry<String, Object> entry : map.entrySet()) {
            yamlBuilder.append(indentString).append(entry.getKey()).append(": ");
            if (entry.getValue() instanceof Map) {
                yamlBuilder.append("\n").append(buildYamlString((Map<String, Object>) entry.getValue(), indent + 2));
            } else {
                yamlBuilder.append(entry.getValue()).append("\n");
            }
        }

        return yamlBuilder.toString();
    }
}