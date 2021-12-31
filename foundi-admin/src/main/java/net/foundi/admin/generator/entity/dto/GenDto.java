/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.generator.entity.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 修改业务表Dto
 *
 * @author Afeng (afeng7882999@163.com)
 */
public class GenDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private GenTableDto table;

    private List<GenTableColumnDto> columns;

    public GenTableDto getTable() {
        return table;
    }

    public void setTable(GenTableDto table) {
        this.table = table;
    }

    public List<GenTableColumnDto> getColumns() {
        return columns;
    }

    public void setColumns(List<GenTableColumnDto> columns) {
        this.columns = columns;
    }

}
