package com.lz.pojo.Enum;


import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

/**
 * 公告状态枚举，代表公告的不同状态。
 * @author YourName
 */
@Getter
@ApiModel(value = "公告状态枚举")
public enum AnnouncementStatus {

    /**
     * 草稿状态
     */
    @ApiModelProperty(value = "草稿")
    DRAFT("DRAFT", "草稿"),

    /**
     * 已发布状态
     */
    @ApiModelProperty(value = "已发布")
    PUBLISHED("PUBLISHED", "已发布"),

    /**
     * 已撤回状态
     */
    @ApiModelProperty(value = "已撤回")
    WITHDRAWN("WITHDRAWN", "已撤回");

    /**
     * 数据库字符串表示。
     */
    @EnumValue
    private final String dbValue;

    /**
     * WEB字符串表示。
     */
    @JsonValue
    private final String webValue;

    /**
     * 构造函数，初始化公告状态及其对应的数据库值和Web应用值。
     * @param dbValue 状态在数据库中的字符串表示。
     * @param webValue 状态在Web应用中的字符串表示。
     */
    AnnouncementStatus(String dbValue, String webValue) {
        this.dbValue = dbValue;
        this.webValue = webValue;
    }

    /**
     * 将从数据库获取的字符串值转换为相应的{@link AnnouncementStatus}枚举实例。
     * 如果给定值与任何已知状态不匹配，则抛出{@link IllegalArgumentException}。
     *
     * @param dbValue 从数据库检索到的字符串值。
     * @return 对应的{@link AnnouncementStatus}实例。
     * @throws IllegalArgumentException 如果给定的数据库值无效，则抛出此异常。
     */
    public static AnnouncementStatus fromDbValue(String dbValue) {
        for (AnnouncementStatus status : values()) {
            if (status.getDbValue().equalsIgnoreCase(dbValue)) {
                return status;
            }
        }
        throw new IllegalArgumentException("无效的公告状态值: " + dbValue);
    }
}