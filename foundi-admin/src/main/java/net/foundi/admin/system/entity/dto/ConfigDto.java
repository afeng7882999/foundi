/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.system.entity.dto;

import lombok.Data;
import net.foundi.admin.system.entity.domain.ConfigDo;
import net.foundi.framework.entity.dto.Dto;
import net.foundi.framework.entity.jackson.JsonTimestamp;
import net.foundi.framework.entity.validation.EditGroup;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
* 系统配置DTO
*
* @author Afeng
*/
@Data
public class ConfigDto implements Dto {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "ID不能为空", groups = {EditGroup.class})
    private Long id;

    /** 配置分类（字典：SysConfigType) */
    @NotBlank(message = "配置分类（字典：SysConfigType)不能为空")
    private String configTypeDict;

    /** 键 */
    @NotBlank(message = "键不能为空")
    private String configKey;

    /** 值 */
    @NotBlank(message = "值不能为空")
    private String configValue;

    /** 是否启用 */
    private Boolean enabled;

    /** 备注 */
    private String remark;

    /** 创建时间 */
    @JsonTimestamp
    private LocalDateTime createAt;

    public static ConfigDo toDo(ConfigDto dto) {
        if (dto == null) {
            return null;
        } else {
            ConfigDo aDo = new ConfigDo();
            aDo.setId(dto.getId());
            aDo.setConfigTypeDict(dto.getConfigTypeDict());
            aDo.setConfigKey(dto.getConfigKey());
            aDo.setConfigValue(dto.getConfigValue());
            aDo.setEnabled(dto.getEnabled());
            aDo.setRemark(dto.getRemark());
            aDo.setCreateAt(dto.getCreateAt());
            return aDo;
        }
    }

    public static List<ConfigDo> toDo(List<ConfigDto> dtos) {
        if (dtos == null) {
            return null;
        } else {
            return dtos.stream().map(ConfigDto::toDo).collect(Collectors.toList());
        }
    }

    public static ConfigDto fromDo(ConfigDo aDo) {
        if (aDo == null) {
            return null;
        } else {
            ConfigDto dto = new ConfigDto();
            dto.setId(aDo.getId());
            dto.setConfigTypeDict(aDo.getConfigTypeDict());
            dto.setConfigKey(aDo.getConfigKey());
            dto.setConfigValue(aDo.getConfigValue());
            dto.setEnabled(aDo.getEnabled());
            dto.setRemark(aDo.getRemark());
            dto.setCreateAt(aDo.getCreateAt());
            return dto;
        }
    }

    public static List<ConfigDto> fromDo(List<ConfigDo> dos) {
        if (dos == null) {
            return null;
        } else {
            return dos.stream().map(ConfigDto::fromDo).collect(Collectors.toList());
        }
    }

    public static List<Map<String, Object>> toMap(List<ConfigDo> dos) {
        List<Map<String, Object>> list = new ArrayList<>();
        for (ConfigDo config : dos) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("配置分类（字典：SysConfigType)", config.getConfigTypeDict());
            map.put("键", config.getConfigKey());
            map.put("值", config.getConfigValue());
            map.put("是否启用", config.getEnabled());
            map.put("备注", config.getRemark());
            map.put("创建时间", config.getCreateAt());
            list.add(map);
        }
        return list;
    }

}