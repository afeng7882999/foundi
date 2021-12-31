/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.system.entity.dto;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.foundi.admin.system.entity.domain.GroupDo;
import net.foundi.framework.entity.dto.TreeDto;
import net.foundi.framework.entity.validation.EditGroup;

/**
* 系统用户组DTO
*
* @author Afeng
*/
@Data
@EqualsAndHashCode(callSuper = true)
public class GroupDto extends TreeDto<GroupDto> {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "ID不能为空", groups = {EditGroup.class})
    private Long id;

    /** 上级用户组ID，一级用户组为0 */
    private Long parentId;

    /** 排序 */
    private Integer sort;

    /** 用户组名称 */
    @NotBlank(message = "用户组名称不能为空")
    private String name;

    /** 是否删除  1：已删除  0：正常 */
    private Boolean delFlag;


    public static GroupDo toDo(GroupDto dto) {
        if (dto == null) {
            return null;
        } else {
            GroupDo aDo = new GroupDo();
            aDo.setId(dto.getId());
            aDo.setParentId(dto.getParentId());
            aDo.setSort(dto.getSort());
            aDo.setName(dto.getName());
            aDo.setDelFlag(dto.getDelFlag());
            return aDo;
        }
    }

    public static List<GroupDo> toDo(List<GroupDto> dtos) {
        if (dtos == null) {
            return null;
        } else {
            return dtos.stream().map(GroupDto::toDo).collect(Collectors.toList());
        }
    }

    public static GroupDto fromDo(GroupDo aDo) {
        if (aDo == null) {
            return null;
        } else {
            GroupDto dto = new GroupDto();
            dto.setId(aDo.getId());
            dto.setParentId(aDo.getParentId());
            dto.setSort(aDo.getSort());
            dto.setName(aDo.getName());
            dto.setDelFlag(aDo.getDelFlag());
            return dto;
        }
    }

    public static List<GroupDto> fromDo(List<GroupDo> dos) {
        if (dos == null) {
            return null;
        } else {
            return dos.stream().map(GroupDto::fromDo).collect(Collectors.toList());
        }
    }

    public static List<Map<String, Object>> toMap(List<GroupDo> dos) {
        List<Map<String, Object>> list = new ArrayList<>();
        for (GroupDo group : dos) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("上级用户组ID，一级用户组为0", group.getParentId());
            map.put("排序", group.getSort());
            map.put("用户组名称", group.getName());
            map.put("是否删除  1：已删除  0：正常", group.getDelFlag());
            list.add(map);
        }
        return list;
    }

}