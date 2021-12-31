/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.system.entity.dto;

import lombok.Data;
import net.foundi.admin.system.entity.domain.DictDo;
import net.foundi.framework.entity.dto.Dto;
import net.foundi.framework.entity.jackson.JsonTimestamp;
import net.foundi.framework.entity.validation.EditGroup;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
* 系统字典DTO
*
* @author Afeng
*/
@Data
public class DictDto implements Dto {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "ID不能为空", groups = {EditGroup.class})
    private Long id;

    /** 字典名 */
    @NotBlank(message = "字典名不能为空")
    private String name;

    /** 字典中文名 */
    private String nameCn;

    /** 备注信息 */
    private String remarks;

    /** 创建者 */
    private Long createBy;

    /** 创建时间 */
    @JsonTimestamp
    private LocalDateTime createAt;

    /** 更新者 */
    private Long updateBy;

    /** 更新时间 */
    @JsonTimestamp
    private LocalDateTime updateAt;

    /** 是否删除  1：已删除  0：正常 */
    private Boolean delFlag;


    public static DictDo toDo(DictDto dto) {
        if (dto == null) {
            return null;
        } else {
            DictDo aDo = new DictDo();
            aDo.setId(dto.getId());
            aDo.setName(dto.getName());
            aDo.setNameCn(dto.getNameCn());
            aDo.setRemarks(dto.getRemarks());
            aDo.setCreateBy(dto.getCreateBy());
            aDo.setCreateAt(dto.getCreateAt());
            aDo.setUpdateBy(dto.getUpdateBy());
            aDo.setUpdateAt(dto.getUpdateAt());
            aDo.setDelFlag(dto.getDelFlag());
            return aDo;
        }
    }

    public static List<DictDo> toDo(List<DictDto> dtos) {
        if (dtos == null) {
            return null;
        } else {
            return dtos.stream().map(DictDto::toDo).collect(Collectors.toList());
        }
    }

    public static DictDto fromDo(DictDo aDo) {
        if (aDo == null) {
            return null;
        } else {
            DictDto dto = new DictDto();
            dto.setId(aDo.getId());
            dto.setName(aDo.getName());
            dto.setNameCn(aDo.getNameCn());
            dto.setRemarks(aDo.getRemarks());
            dto.setCreateBy(aDo.getCreateBy());
            dto.setCreateAt(aDo.getCreateAt());
            dto.setUpdateBy(aDo.getUpdateBy());
            dto.setUpdateAt(aDo.getUpdateAt());
            dto.setDelFlag(aDo.getDelFlag());
            return dto;
        }
    }

    public static List<DictDto> fromDo(List<DictDo> dos) {
        if (dos == null) {
            return null;
        } else {
            return dos.stream().map(DictDto::fromDo).collect(Collectors.toList());
        }
    }

    public static List<Map<String, Object>> toMap(List<DictDo> dos) {
        List<Map<String, Object>> list = new ArrayList<>();
        for (DictDo dict : dos) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("字典名", dict.getName());
            map.put("字典中文名", dict.getNameCn());
            map.put("备注信息", dict.getRemarks());
            map.put("创建者", dict.getCreateBy());
            map.put("创建时间", dict.getCreateAt());
            map.put("更新者", dict.getUpdateBy());
            map.put("更新时间", dict.getUpdateAt());
            map.put("是否删除  1：已删除  0：正常", dict.getDelFlag());
            list.add(map);
        }
        return list;
    }

}