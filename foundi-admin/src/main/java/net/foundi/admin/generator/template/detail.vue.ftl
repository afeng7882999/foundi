<template>
  <el-dialog :close-on-click-modal="false" :title="`${tableComment}详细 (<#noParse>$</#noParse>{idx + 1} / <#noParse>$</#noParse>{data.length})`" width="80%" v-model="visible">
    <el-descriptions :title="`ID: <#noParse>$</#noParse>{data[idx].${pkLowerFieldName}}`" :column="2" size="medium" border>
<#list listColumns as col>
  <#if col.isDict>
      <el-descriptions-item label="${col.columnBrief}" :span="2">{{ dictVal(dicts.${col.dictType?uncapFirst}, data[idx].${col.lowerFieldName}) }}</el-descriptions-item>
  <#else>
      <el-descriptions-item label="${col.columnBrief}" :span="2">{{ data[idx].${col.lowerFieldName} }}</el-descriptions-item>
  </#if>
</#list>
      <template #extra>
        <el-button size="medium" type="primary" @click="onEdit" v-show="ifEditable">编辑</el-button>
        <el-button size="medium" :disabled="prevDisabled" @click="onPrev" v-show="ifShowNavigation"> <fd-svg-icon icon="left-small" class="in-button"></fd-svg-icon>上一个 </el-button>
        <el-button size="medium" :disabled="nextDisabled" @click="onNext" v-show="ifShowNavigation"> 下一个<fd-svg-icon icon="right-small" class="in-button right"></fd-svg-icon> </el-button>
      </template>
    </el-descriptions>
  </el-dialog>
</template>

<script lang="ts">
import { defineComponent, toRefs } from 'vue'
import useDetail, { OPEN_EDIT_EVENT } from '@/components/crud/use-detail'
import { ${lowerClassName}Fields } from '@/api/${moduleNameDash}/${classNameDash}'

export default defineComponent({
  name: '${moduleNameCamel?capFirst}${className}Detail',
  emits: [OPEN_EDIT_EVENT],
  setup(props, { emit }) {
    const stateOption = {
      idField: ${lowerClassName}Fields.idField,
    <#if !isFrontEdit>
      ifEditable: false,
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
    }

    const { mixState, mixComputed, mixMethods } = useDetail(stateOption, emit)

    return {
      ...toRefs(mixState),
      ...mixComputed,
      ...mixMethods
    }
  }
})
</script>

<style lang="scss" scoped>
.el-descriptions {
  ::v-deep(.el-descriptions__label) {
    width: 80px;
  }
}
</style>
