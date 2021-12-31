/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.system.entity.dto;

import lombok.Data;
import net.foundi.admin.system.entity.domain.RoleDo;
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
* 系统角色DTO
*
* @author Afeng
*/
@Data
public class RoleDto implements Dto {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "ID不能为空", groups = {EditGroup.class})
    private Long id;

    /** 角色名称 */
    @NotBlank(message = "角色名称不能为空")
    private String name;

    /** 角色标识 */
    private String label;

    /** 备注 */
    private String remark;

    /** 数据范围（字典：SysRoleDataScope） */
    private String dataScopeDict;

    /** 创建用户id */
    private Long createBy;

    /** 创建时间 */
    @JsonTimestamp
    private LocalDateTime createAt;

    /** 修改用户id */
    private Long updateBy;

    /** 修改时间 */
    @JsonTimestamp
    private LocalDateTime updateAt;

    /** 是否删除  1：已删除  0：正常 */
    private Boolean delFlag;

    /** 菜单（业务）权限 */
    private List<Long> menuIdList;

    /** 用户组（数据）权限 */
    private List<Long> groupIdList;


    public static RoleDo toDo(RoleDto dto) {
        if (dto == null) {
            return null;
        } else {
            RoleDo aDo = new RoleDo();
            aDo.setId(dto.getId());
            aDo.setName(dto.getName());
            aDo.setLabel(dto.getLabel());
            aDo.setRemark(dto.getRemark());
            aDo.setDataScopeDict(dto.getDataScopeDict());
            aDo.setCreateBy(dto.getCreateBy());
            aDo.setCreateAt(dto.getCreateAt());
            aDo.setUpdateBy(dto.getUpdateBy());
            aDo.setUpdateAt(dto.getUpdateAt());
            aDo.setDelFlag(dto.getDelFlag());
            aDo.setMenuIdList(dto.getMenuIdList());
            aDo.setGroupIdList(dto.getGroupIdList());
            return aDo;
        }
    }

    public static List<RoleDo> toDo(List<RoleDto> dtos) {
        if (dtos == null) {
            return null;
        } else {
            return dtos.stream().map(RoleDto::toDo).collect(Collectors.toList());
        }
    }

    public static RoleDto fromDo(RoleDo aDo) {
        if (aDo == null) {
            return null;
        } else {
            RoleDto dto = new RoleDto();
            dto.setId(aDo.getId());
            dto.setName(aDo.getName());
            dto.setLabel(aDo.getLabel());
            dto.setRemark(aDo.getRemark());
            dto.setDataScopeDict(aDo.getDataScopeDict());
            dto.setCreateBy(aDo.getCreateBy());
            dto.setCreateAt(aDo.getCreateAt());
            dto.setUpdateBy(aDo.getUpdateBy());
            dto.setUpdateAt(aDo.getUpdateAt());
            dto.setDelFlag(aDo.getDelFlag());
            dto.setMenuIdList(aDo.getMenuIdList());
            dto.setGroupIdList(aDo.getGroupIdList());
            return dto;
        }
    }

    public static List<RoleDto> fromDo(List<RoleDo> dos) {
        if (dos == null) {
            return null;
        } else {
            return dos.stream().map(RoleDto::fromDo).collect(Collectors.toList());
        }
    }

    public static List<Map<String, Object>> toMap(List<RoleDo> dos) {
        List<Map<String, Object>> list = new ArrayList<>();
        for (RoleDo role : dos) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("角色名称", role.getName());
            map.put("角色标识", role.getLabel());
            map.put("备注", role.getRemark());
            map.put("数据范围（字典：SysRoleDataScope）", role.getDataScopeDict());
            map.put("创建用户id", role.getCreateBy());
            map.put("创建时间", role.getCreateAt());
            map.put("修改用户id", role.getUpdateBy());
            map.put("修改时间", role.getUpdateAt());
            map.put("是否删除  1：已删除  0：正常", role.getDelFlag());
            list.add(map);
        }
        return list;
    }

}