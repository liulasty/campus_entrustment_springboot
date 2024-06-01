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
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class AdminsettingsController {
    
    @Autowired
    private IAdminsettingsService adminsettingsService;


    /**
     * @return {@code Result}
     */
    @GetMapping("")
    @ApiOperation("获取系统管理员相关设置列表")
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
    
    @GetMapping("/enable")
    @ApiOperation("查询状态")
    public Result<AdminSettings> enable() {
        
        AdminSettings adminSettings = adminsettingsService.getById(1);
        return Result.success(adminSettings);
    }
    
    @PutMapping("/update")
    @ApiOperation("更新状态")
    public Result<String> update() {
        log.info("更新分类");
        AdminSettings adminSettings = adminsettingsService.getById(1);
        if (adminSettings.getSettingValue() == 1){
            adminSettings.setSettingValue(0L);
            adminsettingsService.updateById(adminSettings);
        }else {
            adminSettings.setSettingValue(1L);
            adminsettingsService.updateById(adminSettings);
        }
        return Result.success();
    }

}