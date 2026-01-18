# 校园委托平台（后端 - Spring Boot）

简介
本��库是校园委托平台的后端实现，基于 Spring Boot 提供 RESTful API，用于用户、任务、订单等业务逻辑处理与数据存储。

主要特性
- 用户认证与授权（JWT / Session）
- 任务创建、查询、接取、完成流程
- 与前端对接的 REST API
- 数据库持久化（MySQL / PostgreSQL 等)

技术栈
- Java 版本：请参见 pom.xml / build.gradle
- Spring Boot
- Spring Security（如适用）
- JPA / MyBatis
- Maven 或 Gradle

运行（本地）
1. 配置数据库（例如 MySQL），并在 application.yml / application.properties 中设置连接信息
2. 使用 Maven 或 Gradle 构建并运行
```bash
# Maven
mvn spring-boot:run

# 或先打包再运行
mvn clean package
java -jar target/your-app.jar
```
3. 常用配置项（示例）
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/campus_entrust
    username: root
    password: password
  jpa:
    hibernate:
      ddl-auto: update
```

环境变量
- `SPRING_PROFILES_ACTIVE`（可选）用于指定运行环境（dev/prod）
- JWT 密钥、数据库密码等请通过环境变量或外部配置管理

部署
- 构建 jar 后在服务器运行
- 或者使用 Docker 构建镜像并部署到容器平台（如 Docker Compose / Kubernetes)

贡献
欢迎提交 issue 与 PR。请在 PR 描述中包含测试步骤与兼容性说明。

许可证
根据仓库需要添加许可证（例如 MIT）。如需我可以一并添加 LICENSE 文件.