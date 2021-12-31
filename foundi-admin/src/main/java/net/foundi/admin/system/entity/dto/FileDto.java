/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.system.entity.dto;

import lombok.Data;
import net.foundi.admin.system.entity.domain.FileDo;
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
* 文件上传DTO
*
* @author Afeng
*/
@Data
public class FileDto implements Dto {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "ID不能为空", groups = {EditGroup.class})
    private Long id;

    /** 文件名 */
    private String name;

    /** 文件上传的OSS配置键值 */
    private String oss;

    /** 文件类型（字典：SysFileType） */
    private String typeDict;

    /** URL地址 */
    private String url;

    /** 创建人 */
    private Long createBy;

    /** 创建时间 */
    @JsonTimestamp
    private LocalDateTime createAt;


    public static FileDo toDo(FileDto dto) {
        if (dto == null) {
            return null;
        } else {
            FileDo aDo = new FileDo();
            aDo.setId(dto.getId());
            aDo.setName(dto.getName());
            aDo.setOss(dto.getOss());
            aDo.setTypeDict(dto.getTypeDict());
            aDo.setUrl(dto.getUrl());
            aDo.setCreateBy(dto.getCreateBy());
            aDo.setCreateAt(dto.getCreateAt());
            return aDo;
        }
    }

    public static List<FileDo> toDo(List<FileDto> dtos) {
        if (dtos == null) {
            return null;
        } else {
            return dtos.stream().map(FileDto::toDo).collect(Collectors.toList());
        }
    }

    public static FileDto fromDo(FileDo aDo) {
        if (aDo == null) {
            return null;
        } else {
            FileDto dto = new FileDto();
            dto.setId(aDo.getId());
            dto.setName(aDo.getName());
            dto.setOss(aDo.getOss());
            dto.setTypeDict(aDo.getTypeDict());
            dto.setUrl(aDo.getUrl());
            dto.setCreateBy(aDo.getCreateBy());
            dto.setCreateAt(aDo.getCreateAt());
            return dto;
        }
    }

    public static List<FileDto> fromDo(List<FileDo> dos) {
        if (dos == null) {
            return null;
        } else {
            return dos.stream().map(FileDto::fromDo).collect(Collectors.toList());
        }
    }

    public static List<Map<String, Object>> toMap(List<FileDo> dos) {
        List<Map<String, Object>> list = new ArrayList<>();
        for (FileDo file : dos) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("文件名", file.getName());
            map.put("文件上传的OSS配置键值", file.getOss());
            map.put("文件类型（字典：SysFileType）", file.getTypeDict());
            map.put("URL地址", file.getUrl());
            map.put("创建人", file.getCreateBy());
            map.put("创建时间", file.getCreateAt());
            list.add(map);
        }
        return list;
    }

}