/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.system.entity.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.foundi.admin.system.entity.domain.MenuDo;
import net.foundi.framework.entity.dto.TreeDto;
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
* 系统菜单DTO
*
* @author Afeng
*/
@Data
@EqualsAndHashCode(callSuper = true)
public class MenuDto extends TreeDto<MenuDto> {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "ID不能为空", groups = {EditGroup.class})
    private Long id;

    /** 父菜单ID，一级菜单为0 */
    private Long parentId;

    /** 菜单名称 */
    @NotBlank(message = "菜单名称不能为空")
    private String name;

    /** 菜单URL */
    private String url;

    /** 菜单跳转 */
    private String redirect;

    /** 授权（多个用逗号分隔，如：user:list,user:create） */
    private String perms;

    /** 类型（字典：SysMenuType，0：目录，1：菜单，2：按钮） */
    private String typeDict;

    /** 页面文件路径 */
    private String pagePath;

    /** 菜单图标 */
    private String icon;

    /** 菜单缩写 */
    private String abbr;

    /** 排序 */
    private Integer sort;

    /** 注释 */
    private String remark;

    /** 是否显示 */
    @NotNull(message = "是否显示不能为空")
    private Boolean visible;

    /** 创建时间 */
    @JsonTimestamp
    private LocalDateTime createAt;

    /** 修改时间 */
    @JsonTimestamp
    private LocalDateTime updateAt;


    public static MenuDo toDo(MenuDto dto) {
        if (dto == null) {
            return null;
        } else {
            MenuDo aDo = new MenuDo();
            aDo.setId(dto.getId());
            aDo.setParentId(dto.getParentId());
            aDo.setName(dto.getName());
            aDo.setUrl(dto.getUrl());
            aDo.setRedirect(dto.getRedirect());
            aDo.setPerms(dto.getPerms());
            aDo.setTypeDict(dto.getTypeDict());
            aDo.setPagePath(dto.getPagePath());
            aDo.setIcon(dto.getIcon());
            aDo.setAbbr(dto.getAbbr());
            aDo.setSort(dto.getSort());
            aDo.setRemark(dto.getRemark());
            aDo.setVisible(dto.getVisible());
            aDo.setCreateAt(dto.getCreateAt());
            aDo.setUpdateAt(dto.getUpdateAt());
            return aDo;
        }
    }

    public static List<MenuDo> toDo(List<MenuDto> dtos) {
        if (dtos == null) {
            return null;
        } else {
            return dtos.stream().map(MenuDto::toDo).collect(Collectors.toList());
        }
    }

    public static MenuDto fromDo(MenuDo aDo) {
        if (aDo == null) {
            return null;
        } else {
            MenuDto dto = new MenuDto();
            dto.setId(aDo.getId());
            dto.setParentId(aDo.getParentId());
            dto.setName(aDo.getName());
            dto.setUrl(aDo.getUrl());
            dto.setRedirect(aDo.getRedirect());
            dto.setPerms(aDo.getPerms());
            dto.setTypeDict(aDo.getTypeDict());
            dto.setPagePath(aDo.getPagePath());
            dto.setIcon(aDo.getIcon());
            dto.setAbbr(aDo.getAbbr());
            dto.setSort(aDo.getSort());
            dto.setRemark(aDo.getRemark());
            dto.setVisible(aDo.getVisible());
            dto.setCreateAt(aDo.getCreateAt());
            dto.setUpdateAt(aDo.getUpdateAt());
            return dto;
        }
    }

    public static List<MenuDto> fromDo(List<MenuDo> dos) {
        if (dos == null) {
            return null;
        } else {
            return dos.stream().map(MenuDto::fromDo).collect(Collectors.toList());
        }
    }

    public static List<Map<String, Object>> toMap(List<MenuDo> dos) {
        List<Map<String, Object>> list = new ArrayList<>();
        for (MenuDo menu : dos) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("父菜单ID，一级菜单为0", menu.getParentId());
            map.put("菜单名称", menu.getName());
            map.put("菜单URL", menu.getUrl());
            map.put("菜单跳转", menu.getRedirect());
            map.put("授权（多个用逗号分隔，如：user:list,user:create）", menu.getPerms());
            map.put("类型（字典：SysMenuType，0：目录，1：菜单，2：按钮）", menu.getTypeDict());
            map.put("页面文件路径", menu.getPagePath());
            map.put("菜单图标", menu.getIcon());
            map.put("菜单缩写", menu.getAbbr());
            map.put("排序", menu.getSort());
            map.put("注释", menu.getRemark());
            map.put("是否显示", menu.getVisible());
            map.put("创建时间", menu.getCreateAt());
            map.put("修改时间", menu.getUpdateAt());
            list.add(map);
        }
        return list;
    }

}