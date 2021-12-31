/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.system.entity.dto;

import lombok.Data;
import net.foundi.admin.system.entity.domain.TaskDo;
import net.foundi.framework.entity.dto.Dto;
import net.foundi.framework.entity.jackson.JsonTimestamp;
import net.foundi.framework.entity.validation.EditGroup;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
* 系统任务DTO
*
* @author Afeng
*/
@Data
public class TaskDto implements Dto {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "ID不能为空", groups = {EditGroup.class})
    private Long id;

    /** 任务名 */
    private String jobName;

    /** 任务分组 */
    private String jobGroup;

    /** 任务状态 */
    private String jobStatus;

    /** 任务是否并发 */
    private Boolean isConcurrent;

    /** cron表达式 */
    private String cronExpression;

    /** 任务描述 */
    private String description;

    /** 任务执行时调用哪个类的方法 包名+类名 */
    private String beanClass;

    /** Spring bean */
    private String springBean;

    /** 任务调用的方法名 */
    private String methodName;

    /** 创建时间 */
    @JsonTimestamp
    private LocalDateTime createAt;

    /** 创建者 */
    private Long createBy;

    /** 更新时间 */
    @JsonTimestamp
    private LocalDateTime updateAt;

    /** 更新者 */
    private Long updateBy;


    public static TaskDo toDo(TaskDto dto) {
        if (dto == null) {
            return null;
        } else {
            TaskDo aDo = new TaskDo();
            aDo.setId(dto.getId());
            aDo.setJobName(dto.getJobName());
            aDo.setJobGroup(dto.getJobGroup());
            aDo.setJobStatus(dto.getJobStatus());
            aDo.setIsConcurrent(dto.getIsConcurrent());
            aDo.setCronExpression(dto.getCronExpression());
            aDo.setDescription(dto.getDescription());
            aDo.setBeanClass(dto.getBeanClass());
            aDo.setSpringBean(dto.getSpringBean());
            aDo.setMethodName(dto.getMethodName());
            aDo.setCreateAt(dto.getCreateAt());
            aDo.setCreateBy(dto.getCreateBy());
            aDo.setUpdateAt(dto.getUpdateAt());
            aDo.setUpdateBy(dto.getUpdateBy());
            return aDo;
        }
    }

    public static List<TaskDo> toDo(List<TaskDto> dtos) {
        if (dtos == null) {
            return null;
        } else {
            return dtos.stream().map(TaskDto::toDo).collect(Collectors.toList());
        }
    }

    public static TaskDto fromDo(TaskDo aDo) {
        if (aDo == null) {
            return null;
        } else {
            TaskDto dto = new TaskDto();
            dto.setId(aDo.getId());
            dto.setJobName(aDo.getJobName());
            dto.setJobGroup(aDo.getJobGroup());
            dto.setJobStatus(aDo.getJobStatus());
            dto.setIsConcurrent(aDo.getIsConcurrent());
            dto.setCronExpression(aDo.getCronExpression());
            dto.setDescription(aDo.getDescription());
            dto.setBeanClass(aDo.getBeanClass());
            dto.setSpringBean(aDo.getSpringBean());
            dto.setMethodName(aDo.getMethodName());
            dto.setCreateAt(aDo.getCreateAt());
            dto.setCreateBy(aDo.getCreateBy());
            dto.setUpdateAt(aDo.getUpdateAt());
            dto.setUpdateBy(aDo.getUpdateBy());
            return dto;
        }
    }

    public static List<TaskDto> fromDo(List<TaskDo> dos) {
        if (dos == null) {
            return null;
        } else {
            return dos.stream().map(TaskDto::fromDo).collect(Collectors.toList());
        }
    }

    public static List<Map<String, Object>> toMap(List<TaskDo> dos) {
        List<Map<String, Object>> list = new ArrayList<>();
        for (TaskDo task : dos) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("任务名", task.getJobName());
            map.put("任务分组", task.getJobGroup());
            map.put("任务状态", task.getJobStatus());
            map.put("任务是否并发", task.getIsConcurrent());
            map.put("cron表达式", task.getCronExpression());
            map.put("任务描述", task.getDescription());
            map.put("任务执行时调用哪个类的方法 包名+类名", task.getBeanClass());
            map.put("Spring bean", task.getSpringBean());
            map.put("任务调用的方法名", task.getMethodName());
            map.put("创建时间", task.getCreateAt());
            map.put("创建者", task.getCreateBy());
            map.put("更新时间", task.getUpdateAt());
            map.put("更新者", task.getUpdateBy());
            list.add(map);
        }
        return list;
    }

}