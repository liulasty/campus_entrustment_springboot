// package com.lz.utils;
//
// // import com.github.pagehelper.PageHelper;
// import com.lz.core.page.PageDomain;
// import com.lz.core.page.TableSupport;
// import com.lz.utils.sql.SqlUtil;
//
// /**
//  * 分页工具类
//  * 
//  * @author ruoyi
//  */
// public class PageUtils extends PageHelper
// {
//     /**
//      * 设置请求分页数据
//      */
//     public static void startPage()
//     {
//         // 初始化分页参数
//         PageDomain pageDomain = TableSupport.buildPageRequest();
//        
//         // 获取当前页码和每页大小
//         Integer pageNum = pageDomain.getPageNum();
//         Integer pageSize = pageDomain.getPageSize();
//        
//         // 对排序字段进行转义处理，防止SQL注入
//         String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
//        
//         // 获取是否启用合理分页的标志
//         Boolean reasonable = pageDomain.getReasonable();
//        
//         // 启动分页，设置当前页码、每页大小、排序方式，并指定是否启用合理分页
//         // PageHelper.startPage(pageNum, pageSize, orderBy).setReasonable(reasonable);
//
//     }
//
//     /**
//      * 清理分页的线程变量
//      */
//     public static void clearPage()
//     {
//         // PageHelper.clearPage();
//     }
// }