package com.lz.controller;


import com.lz.pojo.entity.AdminSettings;
import com.lz.pojo.result.Result;
import com.lz.service.IAdminsettingsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 存储系统管理员相关设置 前端控制器
 * </p>
 *
 * @author lz
 * @since 2024-04-04
 */
@RestController
@RequestMapping("/adminsettings")
@Api(tags = "存储系统管理员相关设置相关接口")
@Slf4j
public class AdminsettingsController {
    
    @Autowired
    private IAdminsettingsService adminsettingsService;


    /**
     * @return {@code Result}
     */
    @GetMapping("")
    
    public Result getAdminsettingsList() {
        List<AdminSettings> adminSettingsList = adminsettingsService.list();
        return Result.success(adminSettingsList);
    }
    @PostMapping
    @ApiOperation("新增分类")
    public Result<String> save(){
        log.info("新增分类：{}","adminSettings");
        
        return Result.success();
    }

}