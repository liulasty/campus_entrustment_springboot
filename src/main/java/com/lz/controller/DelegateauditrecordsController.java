package com.lz.controller;


import com.lz.service.IDelegateAuditRecordsService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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
public class DelegateauditrecordsController {

    @Autowired
    private IDelegateAuditRecordsService delegateAuditRecordService;

    /**
     * 查询存储委托信息审核记录列表
     */

    // @GetMapping("/list")
    // public TableDataInfo list(Delegateauditrecords delegateauditrecords)
    // {
    //     startPage();
    //     List<Delegateauditrecords> list = delegateauditrecordsService.selectDelegateauditrecordsList(delegateauditrecords);
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
    
   /* @Log(title = "存储委托信息审核记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{RecordIDs}")
    public AjaxResult remove(@PathVariable Long[] RecordIDs)
    {
        return toAjax(delegateauditrecordsService.deleteDelegateauditrecordsByRecordIDs(RecordIDs));
    }
    */
    
    
    
    
}