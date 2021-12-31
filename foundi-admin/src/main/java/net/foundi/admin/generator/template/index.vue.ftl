<template>
  <div class="page-${lowerClassName} fd-page" :style="pageMinHeight" ref="moduleRoot">
    <fd-page-header v-show="showPageHeader"></fd-page-header>
    <div class="fd-page-form">
    <#if hasQuery>
      <el-form size="medium" :inline="true" :model="query" @keyup.enter="queryList()" ref="queryForm">
        <transition @after-enter="expandAfterEnter" @before-leave="expandBeforeLeave" @enter="expandEnter" name="expand">
          <div class="page-form-query" v-show="queryFormShow">
      <#list queryColumns as col>
        <#if col.htmlType == "input" || col.htmlType == "textarea">
            <el-form-item label="${col.columnBrief}" prop="${col.lowerFieldName}">
              <el-input v-model="query.${col.lowerFieldName}" clearable placeholder="请输入${col.columnBrief}" />
            </el-form-item>
        <#elseIf (col.htmlType == "select" || col.htmlType == "radio") && col.queryType != "IN">
            <el-form-item label="${col.columnBrief}" prop="${col.lowerFieldName}">
              <el-select v-model="query.${col.lowerFieldName}" size="medium">
                <el-option v-for="item in dicts.${col.dictType?uncapFirst}" :key="item.itemKey" :label="item.itemValue" :value="item.itemKey"></el-option>
              </el-select>
            </el-form-item>
        <#elseIf (col.htmlType == "select" || col.htmlType == "radio" || col.htmlType == "checkbox") && col.queryType == "IN">
            <el-form-item label="${col.columnBrief}" prop="${col.lowerFieldName}">
              <fd-tree-select :dataList="dicts.${col.dictType?uncapFirst}" v-model="query.${col.lowerFieldName}" :selectParams="{ multiple: true, placeholder:'请选择${col.columnBrief}' }" :treeFields="{ idField:'itemKey', labelField:'itemValue' }"></fd-tree-select>
            </el-form-item>
        <#elseIf col.htmlType == "checkbox" && col.fieldType == "Boolean">
            <el-form-item label="${col.columnBrief}" prop="${col.lowerFieldName}">
              <el-checkbox v-model="query.${col.lowerFieldName}"></el-checkbox>
            </el-form-item>
        <#elseIf col.htmlType == "datetime">
            <el-form-item label="${col.columnBrief}" prop="${col.lowerFieldName}">
              <el-date-picker clearable v-model="query.${col.lowerFieldName}" type="date" format="YYYY-MM-DD" placeholder="选择${col.columnBrief}"></el-date-picker>
            </el-form-item>
        <#elseIf col.htmlType == "daterange">
            <el-form-item label="${col.columnBrief}" prop="${col.lowerFieldName}">
              <el-date-picker v-model="query.${col.lowerFieldName}" type="daterange" format="YYYY-MM-DD" range-separator="-" :default-time="[new Date('0 0:0:0'), new Date('0 23:59:59')]" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
            </el-form-item>
        </#if>
      </#list>
            <el-form-item>
              <el-button type="primary" @click="queryList">
                <fd-svg-icon icon="search" class="in-button"></fd-svg-icon>
                查询
              </el-button>
              <el-button @click="resetQuery">
                <fd-svg-icon icon="refresh" class="in-button"></fd-svg-icon>
                清空
              </el-button>
            </el-form-item>
          </div>
        </transition>
      </el-form>
    </#if>
      <div class="page-form-action">
        <el-button type="danger" :disabled="selectedNodes.length <= 0" @click="del()" v-waves size="medium" v-show="hasAuth('${moduleNameCamel}:${lowerClassName}:delete')">
          <fd-svg-icon icon="delete" class="in-button"></fd-svg-icon>
          批量删除
        </el-button>
        <div class="right-action">
    <#if isFrontEdit>
          <el-button type="primary" v-waves size="medium" @click="showEdit()" v-show="hasAuth('${moduleNameCamel}:${lowerClassName}:add')">新增</el-button>
    </#if>
          <el-button v-waves size="medium" @click="exportData()" v-show="hasAuth('${moduleNameCamel}:${lowerClassName}:export')">导出数据</el-button>
    <#if hasQuery>
          <el-divider direction="vertical" class="action-divider"></el-divider>
          <el-tooltip :show-after="500" :content="queryFormShow ? '隐藏查询表单' : '显示查询表单'" effect="dark" placement="top">
            <fd-svg-button icon="double-down" class="action-toggle-btn" :class="queryFormShow ? 'expanded' : ''" @click="toggleQueryForm()"></fd-svg-button>
          </el-tooltip>
    </#if>
        </div>
      </div>
    </div>
    <div class="fd-page-table border">
    <#if isTree>
      <el-table
        :data="data"
        :indent="15"
        ref="table"
        :default-expand-all="true"
        row-key="id"
        v-loading="loading"
        @select="selectHandle"
        @select-all="selectAllHandle"
        style="width: 100%"
      >
    <#else>
      <el-table :data="data" @selection-change="selectionChangeHandle" row-key="${pkLowerFieldName}" v-loading="loading">
    </#if>
        <el-table-column align="center" header-align="center" type="selection" width="40"></el-table-column>
    <#list listColumns as col>
      <#if col.isDict>
        <el-table-column align="center" header-align="center" :show-overflow-tooltip="true" label="${col.columnBrief}" prop="${col.lowerFieldName}">
          <template #default="scope">
            <span>{{ dictVal(dicts.${col.dictType?uncapFirst}, scope.row.${col.lowerFieldName}) }}</span>
          </template>
        </el-table-column>
      <#else>
        <el-table-column align="center" header-align="center" :show-overflow-tooltip="true" label="${col.columnBrief}" prop="${col.lowerFieldName}"></el-table-column>
      </#if>
    </#list>
        <el-table-column align="center" fixed="right" header-align="center" label="操作" width="100">
          <template #default="scope">
    <#if isFrontDetail>
            <el-tooltip content="详细" placement="top" :show-after="500">
              <el-button @click="showDetail(scope.$index)" class="fd-tb-act tb-act-detail" plain type="primary" size="mini" v-show="hasAuth('system:operLog:delete')">
                <fd-svg-icon icon="view-grid-detail"></fd-svg-icon>
              </el-button>
            </el-tooltip>
    </#if>
    <#if isFrontEdit>
            <el-tooltip content="编辑" placement="top" :show-after="500">
              <el-button @click="showEdit(scope.row.id)" class="fd-tb-act tb-act-edit" plain size="mini" type="success" v-show="hasAuth('${moduleNameCamel}:${lowerClassName}:edit')">
                <fd-svg-icon icon="write"></fd-svg-icon>
              </el-button>
            </el-tooltip>
    </#if>
            <el-tooltip content="删除" placement="top" :show-after="500">
              <el-button @click="del(scope.row, scope.row.k)" class="fd-tb-act tb-act-delete" plain size="mini" type="danger" v-show="hasAuth('${moduleNameCamel}:${lowerClassName}:delete')">
                <fd-svg-icon icon="close"></fd-svg-icon>
              </el-button>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>
    <#if !isTree>
      <el-pagination
        :current-page="current"
        :page-size="size"
        :page-sizes="[10, 20, 50, 100, 200]"
        :page-count="total"
        :total="count"
        :background="true"
        @current-change="pageChange"
        @size-change="sizeChange"
        layout="total, sizes, prev, pager, next, jumper"
      >
      </el-pagination>
    </#if>
    </div>
    <el-backtop></el-backtop>
    <#if isFrontEdit>
    <edit @refresh-data-list="getList" ref="editDialog" v-if="editShow"></edit>
    </#if>
    <#if isFrontDetail>
    <detail @open-edit-dialog="showEdit" ref="detailDialog" v-if="detailShow"></detail>
    </#if>
  </div>
</template>

<script lang="ts">
import { defineComponent, toRefs } from 'vue'
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

export default defineComponent({
  name: '${moduleNameCamel?capFirst}${className}',
  components: { <#if isFrontEdit>Edit,</#if><#if isFrontDetail> Detail</#if> },
  setup() {
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
    const { mixRefs, mixState, mixComputed, mixMethods } = useTree(stateOption)
<#else>
    const { mixRefs, mixState, mixComputed, mixMethods } = useList(stateOption)
</#if>

    return {
      ...mixRefs,
      ...toRefs(mixState),
      ...mixComputed,
      ...mixMethods,
      ...useExpandTransition()
    }
  }
})
</script>
