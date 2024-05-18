package com.lz.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lz.pojo.constants.MessageConstants;
import com.lz.pojo.result.Result;
import com.lz.service.IDelegateAuditRecordsService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 存储委托信息审核记录 前端控制器
 * </p>
 *
 * @author lz
 * @since 2024-04-05
 */
@RestController
@RequestMapping("/delegateauditrecords")
public class DelegateAuditRecordsController {

    @Autowired
    private IDelegateAuditRecordsService delegateAuditRecordService;

    /**
     * 查询存储委托信息审核记录列表
     */
    // @GetMapping("/list")
    // public Result list(Delegateauditrecords delegateauditrecords)
    // {
    //     // 在查询方法中使用分页
    //     Page<Delegateauditrecords> page = new Page<>(1, 10); // 查询第一页，每页10条记录
    //     PageHelper.setPagination(page); // 设置分页对象，自动注入分页数据
    //
    //     List<Delegateauditrecords> list = delegateAuditRecordService.selectDelegateauditrecordsList(delegateauditrecords);
    //     // 获取总记录数
    //     long total = page.getTotal();
    //     // 获取查询结果列表
    //     List<Delegateauditrecords> records = page.getRecords(); 
    //    
    //     return getDataTable(list);
    // }

    /**
     * 导出存储委托信息审核记录列表
     */

    // @Log(title = "存储委托信息审核记录", businessType = BusinessType.EXPORT)
    // @PostMapping("/export")
    // public void export(HttpServletResponse response, Delegateauditrecords delegateauditrecords)
    // {
    //     List<Delegateauditrecords> list = delegateauditrecordsService.selectDelegateauditrecordsList(delegateauditrecords);
    //     ExcelUtil<Delegateauditrecords> util = new ExcelUtil<Delegateauditrecords>(Delegateauditrecords.class);
    //     util.exportExcel(response, list, "存储委托信息审核记录数据");
    // }

    /**
     * 获取存储委托信息审核记录详细信息
     */

    // @GetMapping(value = "/{RecordID}")
    // public AjaxResult getInfo(@PathVariable("RecordID") Long RecordID)
    // {
    //     return success(delegateauditrecordsService.selectDelegateauditrecordsByRecordID(RecordID));
    // }

    /**
     * 新增存储委托信息审核记录
     */

    // @Log(title = "存储委托信息审核记录", businessType = BusinessType.INSERT)
    // @PostMapping
    // public AjaxResult add(@RequestBody Delegateauditrecords delegateauditrecords)
    // {
    //     return toAjax(delegateauditrecordsService.insertDelegateauditrecords(delegateauditrecords));
    // }

    /**
     * 修改存储委托信息审核记录
     */

    // @Log(title = "存储委托信息审核记录", businessType = BusinessType.UPDATE)
    // @PutMapping
    // public AjaxResult edit(@RequestBody Delegateauditrecords delegateauditrecords)
    // {
    //     return toAjax(delegateauditrecordsService.updateDelegateauditrecords(delegateauditrecords));
    // }

    /**
     * 删除存储委托信息审核记录
     */


    // @DeleteMapping("/{RecordIDs}")
    // public Result remove(@PathVariable Long[] RecordIDs) {
    //    
    //    
    //     return Result.success(MessageConstants.TASK_RECORDS_DELETE_SUCCESS);
    // }


}