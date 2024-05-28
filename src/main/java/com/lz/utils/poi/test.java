package com.lz.utils.poi;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/05/29/0:54
 * @Description:
 */

import com.lz.pojo.entity.Reviews;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

/**
 * @author lz
 */
public class test {
    public static void main(String[] args) {
        InputStream is = null;
        try {
            is = new FileInputStream("C:\\Users\\lz\\OneDrive\\桌面\\reviews" +
                                             ".xlsx");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ExcelUtil<Reviews> util = new ExcelUtil<Reviews>(Reviews.class);
        List<Reviews> userList = util.importExcel(is);
        
        System.out.println(userList);
    }
    
}