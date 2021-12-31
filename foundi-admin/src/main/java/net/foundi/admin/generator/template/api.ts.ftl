import Api, { IResData } from '@/api'
import { AnyObject } from '@/utils'
import { IDictItem } from '@/api/system/dict-item'

export interface I${className} extends IResData {
  // 主键
  ${pkLowerFieldName}: string
<#list noPkColumns as col>
  // ${col.columnComment}
  ${col.lowerFieldName}: <#if col.fieldType == "Integer">number<#elseIf col.fieldType == "Boolean">boolean<#else>string</#if>
</#list>
}

export const ${lowerClassName}Fields = {
  idField: '${pkLowerFieldName}'
}
<#if isTree>

export const ${lowerClassName}TreeFields = {
  idField: '${treeId}',
  labelField: '${treeName}',
  parentIdField: '${treeParentId}',
  sortField: '${treeSort}',
  childrenField: 'children'
}
</#if>
<#if hasDict>

export const ${lowerClassName}Dicts = {
  <#list dictColumns as col>
  ${col.dictType?uncapFirst}: [] as IDictItem[]<#if col?hasNext>,</#if>
  </#list>
}
</#if>
<#if hasQuery>

export const ${lowerClassName}Query = {
  <#list queryColumns as col>
    <#if col.htmlType = "daterange">
  ${col.lowerFieldName}: [] as Date[]<#if col?hasNext>,<#elseIf hasOrder>,</#if>
    <#else>
  ${col.lowerFieldName}: undefined<#if col?hasNext>,<#elseIf hasOrder>,</#if>
    </#if>
  </#list>
  <#if hasOrder>
  orderByList: [] as string[]
  </#if>
}
</#if>

// api url
export const url = '/${moduleNameSlash}/${lowerClassName}'

// 获取单个${tableComment}
export const ${lowerClassName}GetOne = async (id: string) => Api.getOne<I${className}>(url, id)

// 获取${tableComment}列表
export const ${lowerClassName}List = async (query?: AnyObject) => Api.getList<I${className}>(url, query)

// 添加${tableComment}
export const ${lowerClassName}PostOne = async (data: AnyObject) => Api.postOne(url, data)

// 编辑${tableComment}
export const ${lowerClassName}PutOne = async (data: AnyObject) => Api.putOne(url, data)

// 删除${tableComment}
export const ${lowerClassName}Del = async (ids: string[]) => Api.del(url, ids)

// 导出${tableComment}列表
export const ${lowerClassName}Export = async (filename?: string, params?: AnyObject) => Api.exportData(url + '/export', filename, params)
