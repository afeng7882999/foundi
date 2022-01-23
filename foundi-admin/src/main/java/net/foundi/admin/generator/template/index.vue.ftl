<template>
  <div ref="moduleRoot" :style="docMinHeight" class="page-${moduleNameCamel}-${lowerClassName} fd-page">
    <!--  ${menuTitle}管理 -->
    <fd-page-header v-show="showPageHeader"></fd-page-header>
    <div class="fd-page__form">
    <#if hasQuery>
      <el-form ref="queryForm" :inline="true" :model="state.query" size="medium" @keyup.enter="queryList()">
        <transition
          name="expand"
          @enter="expandEnter"
          @after-enter="expandAfterEnter"
          @before-leave="expandBeforeLeave"
        >
          <div v-show="state.queryFormShow" class="fd-page__query">
      <#list queryColumns as col>
        <#if col.htmlType == "input" || col.htmlType == "textarea">
            <el-form-item label="${col.columnBrief}" prop="${col.lowerFieldName}">
              <el-input v-model="state.query.${col.lowerFieldName}" clearable placeholder="请输入${col.columnBrief}" style="width: 150px" />
            </el-form-item>
        <#elseIf (col.htmlType == "select" || col.htmlType == "radio") && col.queryType != "IN">
            <el-form-item label="${col.columnBrief}" prop="${col.lowerFieldName}">
              <el-select v-model="state.query.${col.lowerFieldName}" clearable placeholder="请选择${col.columnBrief}" style="width: 150px">
                <el-option
                  v-for="item in state.dicts.${col.dictType?uncapFirst}"
                  :key="item.itemKey"
                  :label="item.itemValue"
                  :value="item.itemKey"
                ></el-option>
              </el-select>
            </el-form-item>
        <#elseIf (col.htmlType == "select" || col.htmlType == "radio" || col.htmlType == "checkbox") && col.queryType == "IN">
            <el-form-item label="${col.columnBrief}" prop="${col.lowerFieldName}">
              <el-select v-model="state.query.${col.lowerFieldName}" multiple clearable placeholder="请选择${col.columnBrief}" style="width: 150px">
                <el-option
                  v-for="item in state.dicts.${col.dictType?uncapFirst}"
                  :key="item.itemKey"
                  :label="item.itemValue"
                  :value="item.itemKey"
                ></el-option>
              </el-select>
            </el-form-item>
        <#elseIf col.htmlType == "checkbox" && col.fieldType == "Boolean">
            <el-form-item label="${col.columnBrief}" prop="${col.lowerFieldName}">
              <el-checkbox v-model="state.query.${col.lowerFieldName}" style="width: 50px"></el-checkbox>
            </el-form-item>
        <#elseIf col.htmlType == "datetime" && col.queryType != "BETWEEN">
            <el-form-item label="${col.columnBrief}" prop="${col.lowerFieldName}">
              <el-date-picker
                v-model="state.query.${col.lowerFieldName}"
                clearable
                format="YYYY-MM-DD"
                value-format="x"
                placeholder="选择${col.columnBrief}"
                type="date"
                style="width: 150px"
              ></el-date-picker>
            </el-form-item>
        <#elseIf col.htmlType == "datetime" && col.queryType == "BETWEEN">
            <el-form-item label="${col.columnBrief}" prop="${col.lowerFieldName}">
              <el-date-picker
                v-model="state.query.${col.lowerFieldName}"
                :default-time="[new Date('0 0:0:0'), new Date('0 23:59:59')]"
                end-placeholder="结束日期"
                format="YYYY-MM-DD"
                value-format="x"
                range-separator="-"
                start-placeholder="开始日期"
                type="daterange"
                style="width: 280px"
              ></el-date-picker>
            </el-form-item>
        </#if>
      </#list>
            <el-form-item>
              <el-button plain type="primary" @click="queryList">
                <fd-icon icon="search" class="is-in-btn"></fd-icon>
                查询
              </el-button>
              <el-button @click="resetQuery">清空</el-button>
            </el-form-item>
          </div>
        </transition>
      </el-form>
    </#if>
      <div class="fd-page__action">
        <el-button
          v-show="hasAuth('${moduleNameCamel}:${lowerClassName}:delete')"
          v-waves
          :disabled="state.selectedNodes.length <= 0"
          plain
          size="medium"
          type="danger"
          @click="del()"
        >
          <fd-icon class="is-in-btn" icon="delete"></fd-icon>
          批量删除
        </el-button>
        <div class="action-right">
    <#if isFrontEdit>
          <el-button
            v-show="hasAuth('${moduleNameCamel}:${lowerClassName}:add')"
            v-waves
            plain
            size="medium"
            type="primary"
            @click="showEdit()"
          >
            <fd-icon class="is-in-btn" icon="plus"></fd-icon>
            新增
          </el-button>
    </#if>
          <el-button
            v-show="hasAuth('${moduleNameCamel}:${lowerClassName}:export')"
            v-waves
            plain
            size="medium"
            @click="exportData()"
          >
            导出数据
          </el-button>
    <#if hasQuery>
          <el-divider direction="vertical" class="action-divider"></el-divider>
          <el-tooltip
            :content="state.queryFormShow ? '隐藏查询表单' : '显示查询表单'"
            :show-after="500"
            effect="dark"
            placement="top"
          >
            <el-badge :hidden="state.queryFormShow || !state.queryLen" :value="state.queryLen" class="action-badge">
              <fd-icon-button class="action-query-toggle" icon="search" @click="toggleQueryForm()"></fd-icon-button>
            </el-badge>
          </el-tooltip>
    </#if>
        </div>
      </div>
    </div>
    <div class="fd-page__table is-bordered">
    <#if isTree>
      <el-table
        ref="table"
        v-loading="state.loading"
        :data="state.data"
        :default-expand-all="true"
        :indent="15"
        row-key="id"
        style="width: 100%"
        @select="onSelect"
        @select-all="onSelectAll"
      >
    <#else>
      <el-table v-loading="state.loading" :data="state.data" row-key="${pkLowerFieldName}" @selection-change="onSelectionChange">
    </#if>
        <el-table-column align="left" header-align="left" type="selection" width="40"></el-table-column>
    <#list listColumns as col>
      <#assign idx = ["LocalTime", "LocalDate", "LocalDateTime"]?seqIndexOf(col.fieldType)>
      <#if col.isDict>
        <el-table-column
          :show-overflow-tooltip="true"
          align="left"
          header-align="left"
          label="${col.columnBrief}"
          prop="${col.lowerFieldName}"
        >
          <template #default="scope">
            <span>{{ dictVal(state.dicts.${col.dictType?uncapFirst}, scope.row.${col.lowerFieldName}) }}</span>
          </template>
        </el-table-column>
      <#elseIf col.isOrder>
        <el-table-column
          :show-overflow-tooltip="true"
          align="left"
          header-align="left"
          label="${col.columnBrief}"
          prop="${col.lowerFieldName}"
          width="200"
        >
          <template #header="scope">
            <fd-table-sort-header :column="scope.column" @sort-changed="sortChanged"></fd-table-sort-header>
          </template>
        </el-table-column>
      <#elseIf idx != -1>
        <el-table-column
          :show-overflow-tooltip="true"
          align="left"
          header-align="left"
          label="${col.columnBrief}"
          prop="${col.lowerFieldName}"
          width="200"
        >
          <template #default="scope">
            <span>{{ dateTimeStr(scope.row.${col.lowerFieldName}<#if idx == 0>, 'time'<#elseIf idx == 1>, 'date'</#if>) }}</span>
          </template>
        </el-table-column>
      <#else>
        <el-table-column
          :show-overflow-tooltip="true"
          align="left"
          header-align="left"
          label="${col.columnBrief}"
          prop="${col.lowerFieldName}"
        ></el-table-column>
      </#if>
    </#list>
        <el-table-column align="left" fixed="right" header-align="left" label="操作" width="100">
          <template #default="scope">
    <#if isFrontDetail>
            <el-tooltip content="详细" placement="top" :show-after="500">
              <el-button
                v-show="hasAuth('${moduleNameCamel}:${lowerClassName}:delete')"
                class="fd-tb-act"
                plain
                size="mini"
                type="primary"
                @click="showDetail(scope.$index)"
              >
                <fd-icon icon="view-grid-detail"></fd-icon>
              </el-button>
            </el-tooltip>
    </#if>
    <#if isFrontEdit>
            <el-tooltip content="编辑" placement="top" :show-after="500">
              <el-button
                v-show="hasAuth('${moduleNameCamel}:${lowerClassName}:edit')"
                class="fd-tb-act"
                plain
                size="mini"
                type="success"
                @click="showEdit(scope.row.id)"
              >
                <fd-icon icon="write"></fd-icon>
              </el-button>
            </el-tooltip>
    </#if>
            <el-tooltip content="删除" placement="top" :show-after="500">
              <el-button
                v-show="hasAuth('${moduleNameCamel}:${lowerClassName}:delete')"
                class="fd-tb-act"
                plain
                size="mini"
                type="danger"
                @click="del(scope.row, scope.row.k)"
              >
                <fd-icon icon="close"></fd-icon>
              </el-button>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>
    <#if !isTree>
      <el-pagination
        :background="true"
        :current-page="state.current"
        :page-count="state.total"
        :page-size="state.size"
        :page-sizes="[10, 20, 50, 100, 200]"
        :total="state.count"
        layout="total, sizes, prev, pager, next, jumper"
        @current-change="pageChange"
        @size-change="sizeChange"
      >
      </el-pagination>
    </#if>
    </div>
    <el-backtop></el-backtop>
    <#if isFrontEdit>
    <edit v-if="state.editShow" ref="editDialog" @refresh-data-list="getList"></edit>
    </#if>
    <#if isFrontDetail>
    <detail v-if="state.detailShow" ref="detailDialog"<#if isFrontEdit> @open-edit-dialog="showEdit"</#if>></detail>
    </#if>
  </div>
</template>

<script lang="ts">
export default {
  name: '${moduleNameCamel?capFirst}${className}'
}
</script>

<script setup lang="ts">
<#if isTree>
import useTree from '@/components/crud/use-tree'
import { ${lowerClassName}Fields, ${lowerClassName}TreeFields,<#if hasDict> ${lowerClassName}Dicts,</#if><#if hasQuery> ${lowerClassName}Query,</#if> ${lowerClassName}List, ${lowerClassName}Del, ${lowerClassName}Export } from '@/api/${moduleNameDash}/${classNameDash}'
<#else>
import useList from '@/components/crud/use-list'
import { ${lowerClassName}Fields,<#if hasDict> ${lowerClassName}Dicts,</#if><#if hasQuery> ${lowerClassName}Query,</#if> ${lowerClassName}List, ${lowerClassName}Del, ${lowerClassName}Export } from '@/api/${moduleNameDash}/${classNameDash}'
</#if>
<#if isFrontEdit>
import Edit from './edit.vue'
</#if>
<#if isFrontDetail>
import Detail from './detail.vue'
</#if>
import useExpandTransition from '@/components/transition/use-expand-transition'

const stateOption = {
  idField: ${lowerClassName}Fields.idField,
<#if isTree>
  treeFields: ${lowerClassName}TreeFields,
</#if>
  listApi: ${lowerClassName}List,
  delApi: ${lowerClassName}Del,
  exportApi: ${lowerClassName}Export,
<#if hasDict>
  dicts: ${lowerClassName}Dicts,
</#if>
<#if hasQuery>
  query: ${lowerClassName}Query
</#if>
}

<#if isTree>
const { mixRefs, mixState: state, mixComputed, mixMethods } = useTree(stateOption)
const { <#if isFrontEdit>editDialog<#if isFrontDetail>, </#if></#if><#if isFrontDetail>detailDialog</#if> } = mixRefs
const { pageMinHeight, showPageHeader } = mixComputed
const {
  <#if hasQuery>
  queryList,
  resetQuery,
  toggleQueryForm,
  </#if>
  <#if hasDict>
  dictVal,
  </#if>
  <#if hasTime || hasDate || hasDateTime>
  dateTimeStr,
  </#if>
  <#if isFrontEdit>
  showEdit,
  getList,
  </#if>
  <#if isFrontDetail>
  showDetail,
  </#if>
  del,
  onSelect,
  onSelectAll,
  hasAuth,
  exportData
} = mixMethods
<#else>
const { mixRefs, mixState: state, mixComputed, mixMethods } = useList(stateOption)
const { queryForm<#if isFrontEdit>, editDialog</#if><#if isFrontDetail>, detailDialog</#if> } = mixRefs
const { pageMinHeight, showPageHeader } = mixComputed
const {
  <#if hasQuery>
  queryList,
  resetQuery,
  toggleQueryForm,
  </#if>
  <#if hasDict>
  dictVal,
  </#if>
  <#if hasTime || hasDate || hasDateTime>
  dateTimeStr,
  </#if>
  <#if isFrontEdit>
  showEdit,
  getList,
  </#if>
  <#if isFrontDetail>
  showDetail,
  </#if>
  <#if queryHasOrder>
  sortChanged,
  </#if>
  pageChange,
  sizeChange,
  del,
  onSelectionChange,
  hasAuth,
  exportData
} = mixMethods

const { expandEnter, expandAfterEnter, expandBeforeLeave } = useExpandTransition()
</#if>
</script>
