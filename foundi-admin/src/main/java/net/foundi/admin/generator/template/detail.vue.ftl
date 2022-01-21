<template>
  <el-dialog
    v-model="state.visible"
    :close-on-click-modal="false"
    :title="`${tableComment}详细 (<#noParse>$</#noParse>{state.idx + 1} / <#noParse>$</#noParse>{state.data.length})`"
    width="80%"
  >
    <el-descriptions :column="2" :title="`ID: <#noParse>$</#noParse>{state.data[state.idx].${pkLowerFieldName}}`" border size="medium">
<#list listColumns as col>
  <#assign idx = ["LocalTime", "LocalDate", "LocalDateTime"]?seqIndexOf(col.fieldType)>
  <#if col.isDict>
      <el-descriptions-item label="${col.columnBrief}" :span="2">
        {{ dictVal(state.dicts.${col.dictType?uncapFirst}, state.data[state.idx].${col.lowerFieldName}) }}
      </el-descriptions-item>
  <#elseIf idx != -1>
      <el-descriptions-item label="${col.columnBrief}" :span="2">
        {{ dateTimeStr(state.data[state.idx].${col.lowerFieldName}<#if idx == 0>, 'time'<#elseIf idx == 1>, 'date'</#if>) }}
      </el-descriptions-item>
  <#else>
      <el-descriptions-item label="${col.columnBrief}" :span="2">{{ state.data[state.idx].${col.lowerFieldName} }}</el-descriptions-item>
  </#if>
</#list>
      <template #extra>
        <el-button v-show="state.ifEditable" size="medium" type="primary" @click="onEdit">编辑</el-button>
        <el-button v-show="state.ifShowNavigation" size="medium" :disabled="prevDisabled" @click="onPrev">
          <fd-icon icon="left-small" class="is-in-btn"></fd-icon>
          上一个
        </el-button>
        <el-button v-show="state.ifShowNavigation" size="medium" :disabled="nextDisabled" @click="onNext">
          下一个
          <fd-icon icon="right-small" class="is-in-btn right"></fd-icon>
        </el-button>
      </template>
    </el-descriptions>
  </el-dialog>
</template>

<script lang="ts">
export default {
  name: '${moduleNameCamel?capFirst}${className}Detail'
}
</script>

<script setup lang="ts">
import useDetail, { OPEN_EDIT_EVENT } from '@/components/crud/use-detail'
import { ${lowerClassName}Fields } from '@/api/${moduleNameDash}/${classNameDash}'

const emit = defineEmits([OPEN_EDIT_EVENT])

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
  }
    <#else>
  resetFormData: {
    ${pkLowerFieldName}: '',
    <#list editColumns as col>
      <#if col.lowerFieldName != "${pkLowerFieldName}">
    ${col.lowerFieldName}: <#if col.fieldType == "Long" || col.fieldType == "Integer">0<#else>''</#if><#if col?hasNext>,</#if>
      </#if>
    </#list>
  }
    </#if>
}

const { mixState: state, mixComputed, mixMethods } = useDetail(stateOption, emit)
const { prevDisabled, nextDisabled } = mixComputed
const { open,<#if hasDict> dictVal,</#if><#if hasTime || hasDate || hasDateTime> dateTimeStr,</#if> onEdit, onPrev, onNext, close } = mixMethods

defineExpose({
  open,
  close
})
</script>

<style lang="scss" scoped>
.el-descriptions {
  ::v-deep(.el-descriptions__label) {
    width: 80px;
  }
}
</style>
