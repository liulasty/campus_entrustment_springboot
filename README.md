# 校园委托服务平台 (Campus Entrustment Platform)

## 📖 项目简介

这是一个基于 Spring Boot 开发的校园委托服务平台（跑腿系统）。旨在为校园内的师生提供一个便捷、高效的互助平台，解决代买午餐、代取快递、闲置物品交易等校园生活中的实际需求。通过该平台，用户可以发布委托任务，也可以接受任务赚取报酬，促进校园内的资源共享和互助。

## ✨ 核心功能

*   **用户模块**:
    *   用户注册与登录（JWT 认证）
    *   个人信息管理
    *   学生身份认证（实名认证）
*   **委托任务模块**:
    *   **发布委托**: 支持多种委托类型，支持保存草稿。
    *   **接受委托**: 用户可以浏览并接受待处理的委托。
    *   **任务管理**: 任务状态流转（待接受、进行中、待确认、已完成）。
    *   **任务审核**: 后台管理员对发布的任务进行审核。
*   **评价系统**: 任务完成后，发布者和接受者可以互评。
*   **消息通知**:
    *   系统公告发布与查看。
    *   任务状态变更通知。
*   **后台管理**:
    *   用户管理
    *   任务分类管理
    *   系统设置

## 🛠 技术栈

*   **核心框架**: Spring Boot 2.7.3
*   **ORM 框架**: MyBatis Plus 3.4.3
*   **数据库**: MySQL 5.7+
*   **缓存**: Redis
*   **消息队列**: RabbitMQ
*   **权限安全**: Spring Security + JWT
*   **对象存储**: Aliyun OSS
*   **API 文档**: Knife4j
*   **工具库**: Lombok, EasyExcel, FastJSON


## 🚀 快速开始

### 1. 环境准备

确保你的开发环境满足以下要求：

*   JDK 8+
*   Maven 3.6+
*   MySQL 5.7+
*   Redis
*   RabbitMQ

### 2. 克隆项目

```bash
git clone https://gitee.com/Maybe_I_wrong/campus_entrustment_springboot.git
cd campus_entrustment_springboot
```

### 3. 数据库初始化

1.  创建数据库 `campus_entrustment`。
2.  执行 SQL 脚本：`src/main/resources/sql/校园委托0.99.sql`。

### 4. 配置修改

修改 `src/main/resources/application.yml` 文件，配置你的环境信息：

*   **MySQL**: 修改数据库连接 URL、用户名和密码。
*   **Redis**: 配置 Redis 地址和密码。
*   **RabbitMQ**: 配置 RabbitMQ 地址、用户名和密码。
*   **Aliyun OSS**: 配置你的 AccessKey 和 Bucket 信息。

### 5. 运行项目

```bash
mvn spring-boot:run
```

项目启动后，访问 API 文档：`http://localhost:8080/doc.html`。

## 📂 目录结构

```
src/main/java/com/lz
├── common          # 通用模块
├── config          # 配置类
├── controller      # 控制器层
├── entity          # 实体类
├── mapper          # DAO 层
├── service         # 业务逻辑层
├── utils           # 工具类
└── Application.java # 启动类
```

## 🤝 贡献

欢迎提交 Issue 和 Pull Request 来改进本项目。

## 📄 许可证

[MIT License](LICENSE)

