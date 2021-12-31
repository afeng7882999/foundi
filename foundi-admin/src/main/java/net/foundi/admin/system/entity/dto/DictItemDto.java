/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.system.entity.dto;

import lombok.Data;
import net.foundi.admin.system.entity.domain.DictItemDo;
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
* 系统字典条目DTO
*
* @author Afeng
*/
@Data
public class DictItemDto implements Dto {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "ID不能为空", groups = {EditGroup.class})
    private Long id;

    /** 排序（升序） */
    private Integer sort;

    /** 字典项键值 */
    @NotBlank(message = "字典项键值不能为空")
    private String itemKey;

    /** 字典项值 */
    private String itemValue;

    /** 字典ID */
    private Long dictId;

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


    public static DictItemDo toDo(DictItemDto dto) {
        if (dto == null) {
            return null;
        } else {
            DictItemDo aDo = new DictItemDo();
            aDo.setId(dto.getId());
            aDo.setSort(dto.getSort());
            aDo.setItemKey(dto.getItemKey());
            aDo.setItemValue(dto.getItemValue());
            aDo.setDictId(dto.getDictId());
            aDo.setRemarks(dto.getRemarks());
            aDo.setCreateBy(dto.getCreateBy());
            aDo.setCreateAt(dto.getCreateAt());
            aDo.setUpdateBy(dto.getUpdateBy());
            aDo.setUpdateAt(dto.getUpdateAt());
            aDo.setDelFlag(dto.getDelFlag());
            return aDo;
        }
    }

    public static List<DictItemDo> toDo(List<DictItemDto> dtos) {
        if (dtos == null) {
            return null;
        } else {
            return dtos.stream().map(DictItemDto::toDo).collect(Collectors.toList());
        }
    }

    public static DictItemDto fromDo(DictItemDo aDo) {
        if (aDo == null) {
            return null;
        } else {
            DictItemDto dto = new DictItemDto();
            dto.setId(aDo.getId());
            dto.setSort(aDo.getSort());
            dto.setItemKey(aDo.getItemKey());
            dto.setItemValue(aDo.getItemValue());
            dto.setDictId(aDo.getDictId());
            dto.setRemarks(aDo.getRemarks());
            dto.setCreateBy(aDo.getCreateBy());
            dto.setCreateAt(aDo.getCreateAt());
            dto.setUpdateBy(aDo.getUpdateBy());
            dto.setUpdateAt(aDo.getUpdateAt());
            dto.setDelFlag(aDo.getDelFlag());
            return dto;
        }
    }

    public static List<DictItemDto> fromDo(List<DictItemDo> dos) {
        if (dos == null) {
            return null;
        } else {
            return dos.stream().map(DictItemDto::fromDo).collect(Collectors.toList());
        }
    }

    public static List<Map<String, Object>> toMap(List<DictItemDo> dos) {
        List<Map<String, Object>> list = new ArrayList<>();
        for (DictItemDo dictItem : dos) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("排序（升序）", dictItem.getSort());
            map.put("字典项键值", dictItem.getItemKey());
            map.put("字典项值", dictItem.getItemValue());
            map.put("字典ID", dictItem.getDictId());
            map.put("备注信息", dictItem.getRemarks());
            map.put("创建者", dictItem.getCreateBy());
            map.put("创建时间", dictItem.getCreateAt());
            map.put("更新者", dictItem.getUpdateBy());
            map.put("更新时间", dictItem.getUpdateAt());
            map.put("是否删除  1：已删除  0：正常", dictItem.getDelFlag());
            list.add(map);
        }
        return list;
    }

}