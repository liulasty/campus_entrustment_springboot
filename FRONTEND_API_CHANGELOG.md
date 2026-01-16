# 前端对接升级文档

本文档详细描述了后端针对委托全流程（创建、审核、发布、接收、进度更新、完成）所进行的接口升级和数据模型变更。请前端同学根据本文档进行相应的适配和开发。

## 1. 变更概览

| 变更类型 | 描述 | 涉及模块 |
| :--- | :--- | :--- |
| **新增字段** | 委托任务增加 `money` (金额) 字段 | 创建委托, 委托详情 |
| **新增接口** | `PUT /task/auditTask/{id}` | 提交审核 |
| **新增接口** | `POST /taskUpdate/add` | 任务进度更新 |
| **枚举新增** | `TaskUpdateType` 新增 `PROGRESS_UPDATE` (进度更新) | 任务动态/日志 |

---

## 2. 数据模型变更

### 2.1 Task (委托任务)

在 `Task` 实体及相关 DTO 中增加了 `money` 字段。

*   **字段名**: `money`
*   **类型**: `BigDecimal` (数字)
*   **说明**: 委托金额/报酬
*   **适用范围**: 
    *   创建委托 (`TaskDTO`)
    *   委托详情展示 (`Task`, `TaskDraftVO`, `TaskDetails` 等返回体中已包含)

---

## 3. 接口变更详情

### 3.1 创建委托 (修改)

**接口**: `POST /task/addTaskDraft`

**变更**: 请求体 `TaskDTO` 增加 `money` 字段。

**请求体示例 (JSON)**:

```json
{
  "taskId": 0,          //如果是修改草稿则传ID，新增传0或不传
  "ownerId": 123,
  "location": "图书馆",
  "content": "帮我借本书",
  "type": "跑腿",
  "money": 15.50        // [新增] 委托金额
}
```

### 3.2 提交审核 (新增)

用户创建草稿后，需调用此接口将任务状态从 `DRAFT` (草稿) 变更为 `AUDITING` (审核中)。

*   **URL**: `/task/auditTask/{id}`
*   **Method**: `PUT`
*   **Path Parameters**:
    *   `id` (Long): 委托任务 ID
*   **Response**:
    *   `200 OK`: `{"code": 200, "msg": "操作成功", "data": null}`

**前端逻辑建议**:
在“我的草稿”列表或详情页，增加“提交审核”按钮。点击后调用此接口。

### 3.3 添加任务进度更新 (新增)

接收者（Receiver）在任务执行过程中（状态为 `ACCEPTED`），可以提交任务进度（如“已到达目的地”、“正在排队”等）。

*   **URL**: `/taskUpdate/add`
*   **Method**: `POST`
*   **Content-Type**: `application/json`
*   **Request Body**: `TaskUpdateDTO`

**请求体示例 (JSON)**:

```json
{
  "taskId": 10086,
  "description": "我已经到达取件点，正在排队。"
}
```

*   **Response**:
    *   `200 OK`: `{"code": 200, "msg": "操作成功", "data": null}`

**前端逻辑建议**:
在“我接收的任务”详情页，增加“更新进度”或“添加留言”功能。用户输入文字后调用此接口。

---

## 4. 枚举变更

### 4.1 TaskUpdateType (任务更新类型)

在 `TaskUpdateType` 枚举中新增了类型，用于标识任务进度的更新记录。

| 枚举名 | Code | Web Value | 说明 |
| :--- | :--- | :--- | :--- |
| `PROGRESS_UPDATE` | `5` | `"进度更新"` | 接收者提交的任务进度描述 |

**前端逻辑建议**:
在展示任务动态/时间轴（Task Timeline）时，如果遇到 `updateType` 为 `PROGRESS_UPDATE` (或 code 5) 的记录，应将其作为接收者的进度汇报进行展示。

---

## 5. 完整业务流程示例

1.  **发布者**创建任务 (`POST /task/addTaskDraft`)，填写金额 `money`。 -> 状态 `DRAFT`
2.  **发布者**提交审核 (`PUT /task/auditTask/{id}`)。 -> 状态 `AUDITING`
3.  **管理员**审核通过 (原有接口)。 -> 状态 `PENDING_RELEASE`
4.  **发布者**确认发布 (原有接口)。 -> 状态 `ONGOING`
5.  **接收者**申请接收 (原有接口)。
6.  **发布者**确认接收人 (原有接口)。 -> 状态 `ACCEPTED`
7.  **接收者**更新进度 (`POST /taskUpdate/add`)。 -> 插入一条 `TaskUpdates` 记录
8.  **接收者**完成任务/发布者确认完成 (原有接口)。 -> 状态 `COMPLETED`
