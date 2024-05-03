package com.lz.pojo.Page;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/04/21/18:45
 * @Description:
 */

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lz.pojo.Enum.TaskPhase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * @author lz
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DraftConfig {
    @JsonProperty("CreatedAt")
    private LocalDate createdAt;

    @JsonProperty("Description")
    private String description;

    @JsonProperty("Location")
    private String location;

    @JsonProperty("pageNum")
    private int pageNum;

    @JsonProperty("pageSize")
    private int pageSize;

    @JsonProperty("taskType")
    private Integer taskType;

    
    @JsonProperty("TypePhase")
    private TaskPhase typePhase;
}