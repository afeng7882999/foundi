<template>
  <el-dialog :close-on-click-modal="false" :title="isCreate ? '新增' : '修改'" v-model="visible" @closed="hideDialog">
    <el-form :model="formData" :rules="formRule" @keyup.enter="submit()" size="medium" label-width="80px" ref="form">
  <#list editColumns as col>
    <#if isTree && col.lowerFieldName == "${treeParentId}">
      <el-form-item label="${col.columnBrief}" prop="parentId">
        <fd-tree-select v-model="formData.${treeParentId}" :dataList="parentList" :treeFields="{ labelField:'${treeName}' }" style="width: 100%"></fd-tree-select>
      </el-form-item>
    <#elseIf col.htmlType == "input">
      <el-form-item label="${col.columnBrief}" prop="${col.lowerFieldName}">
        <el-input placeholder="请输入${col.columnBrief}" v-model="formData.${col.lowerFieldName}"></el-input>
      </el-form-item>
    <#elseIf col.htmlType == "imageUpload">
      <el-form-item label="${col.columnBrief}" prop="${col.lowerFieldName}">
        <imageUpload v-model="formData.${col.lowerFieldName}"/>
      </el-form-item>
    <#elseIf col.htmlType == "fileUpload">
      <el-form-item label="${col.columnBrief}" prop="${col.lowerFieldName}">
        <fileUpload v-model="formData.${col.lowerFieldName}"/>
      </el-form-item>
    <#elseIf col.htmlType = "editor">
      <el-form-item label="${col.columnBrief}" prop="${col.lowerFieldName}">
        <editor v-model="formData.${col.lowerFieldName}"/>
      </el-form-item>
    <#elseIf col.htmlType == "select">
      <el-form-item label="${col.columnBrief}" prop="${col.lowerFieldName}">
        <el-select v-model="formData.${col.lowerFieldName}" style="width: 100%">
          <el-option v-for="item in dicts.${col.dictType?uncapFirst}" :key="item.itemKey" :label="item.itemValue" :value="item.itemKey"></el-option>
        </el-select>
      </el-form-item>
    <#elseIf col.htmlType == "checkbox" && col.fieldType == "Boolean">
      <el-form-item label="${col.columnBrief}" prop="${col.lowerFieldName}">
        <el-switch v-model="formData.${col.lowerFieldName}" active-text="是" inactive-text="否"></el-switch>
      </el-form-item>
    <#elseIf col.htmlType == "checkbox" && col.fieldType != "Boolean">
      <el-form-item label="${col.columnBrief}" prop="${col.lowerFieldName}">
        <el-checkbox-group v-model="formData.${col.lowerFieldName}">
          <el-checkbox v-for="item in dicts.${col.dictType?uncapFirst}" :key="item.itemKey" :label="item.itemValue">{{ item.itemValue }}</el-radio>
        </el-checkbox-group>
      </el-form-item>
    <#elseIf col.htmlType == "radio">
      <el-form-item label="${col.columnBrief}" prop="${col.lowerFieldName}">
        <el-radio-group v-model="formData.${col.lowerFieldName}">
          <el-radio v-for="item in dicts.${col.dictType?uncapFirst}" :key="item.itemKey" :label="item.itemValue">{{ item.itemValue }}</el-radio>
        </el-radio-group>
      </el-form-item>
    <#elseIf col.htmlType == "datetime">
      <el-form-item label="${col.columnBrief}" prop="${col.lowerFieldName}">
        <el-date-picker clearable v-model="formData.${col.lowerFieldName}" type="date" value-format="YYYY-MM-DD" placeholder="选择${col.columnBrief}"></el-date-picker>
      </el-form-item>
    <#elseIf col.htmlType == "textarea">
      <el-form-item label="${col.columnBrief}" prop="${col.lowerFieldName}">
        <el-input v-model="formData.${col.lowerFieldName}" type="textarea" placeholder="请输入内容" />
      </el-form-item>
    </#if>
  </#list>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button size="medium" @click="visible = false">取消</el-button>
        <el-button size="medium" @click="submit" type="primary">确定</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script lang="ts">
import { defineComponent, toRefs } from 'vue'
<#if isTree>
import useTreeEdit, { REFRESH_DATA_EVENT } from '@/components/crud/use-tree-edit'
import { ${lowerClassName}Fields, ${lowerClassName}TreeFields,<#if hasDict> ${lowerClassName}Dicts,</#if> ${lowerClassName}List, ${lowerClassName}GetOne, ${lowerClassName}PostOne, ${lowerClassName}PutOne } from '@/api/${moduleNameDash}/${classNameDash}'
<#else>
import useListEdit, { REFRESH_DATA_EVENT } from '@/components/crud/use-list-edit'
import { ${lowerClassName}Fields,<#if hasDict> ${lowerClassName}Dicts,</#if> ${lowerClassName}GetOne, ${lowerClassName}PostOne, ${lowerClassName}PutOne } from '@/api/${moduleNameDash}/${classNameDash}'
</#if>

export default defineComponent({
  name: '${moduleNameCamel?capFirst}${className}Edit',
  emits: [REFRESH_DATA_EVENT],
  setup(props, { emit }) {
    const stateOption = {
      idField: ${lowerClassName}Fields.idField,
<#if isTree>
      treeFields: ${lowerClassName}TreeFields,
</#if>
      getApi: ${lowerClassName}GetOne,
<#if isTree>
      listApi: ${lowerClassName}List,
</#if>
      postApi: ${lowerClassName}PostOne,
      putApi: ${lowerClassName}PutOne,
<#if hasDict>
      dicts: ${lowerClassName}Dicts,
</#if>
<#if isTree>
      resetFormData: {
        ${pkLowerFieldName}: '',
        ${treeParentId}: '',
  <#list noAutoFillColumns as col>
    <#if col.lowerFieldName != "${pkLowerFieldName}" && col.lowerFieldName != "${treeParentId}">
        ${col.lowerFieldName}: <#if col.fieldType == "Long" || col.fieldType == "Integer">0<#else>''</#if><#if col?hasNext>,</#if>
    </#if>
  </#list>
      },
<#else>
      resetFormData: {
        ${pkLowerFieldName}: '',
  <#list editColumns as col>
    <#if col.lowerFieldName != "${pkLowerFieldName}">
        ${col.lowerFieldName}: <#if col.fieldType == "Long" || col.fieldType == "Integer">0<#else>''</#if><#if col?hasNext>,</#if>
    </#if>
  </#list>
      },
</#if>
      formRule: {
<#list editNotNullColumns as col>
        ${col.lowerFieldName}: [
          { required: true, message: '${col.columnBrief}不能为空', trigger: 'blur' }
        ]<#if col_has_next>,</#if>
</#list>
      }
    }

    const { mixRefs, mixState, mixMethods } = <#if isTree>useTreeEdit<#else>useListEdit</#if>(stateOption, emit)

    return {
      ...mixRefs,
      ...toRefs(mixState),
      ...mixMethods
    }
  }
})
</script>
