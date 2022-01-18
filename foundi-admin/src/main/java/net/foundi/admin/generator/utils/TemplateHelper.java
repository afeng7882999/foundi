/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */
package net.foundi.admin.generator.utils;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import net.foundi.admin.generator.entity.domain.GenTableColumnDo;
import net.foundi.admin.generator.entity.domain.GenTableDo;
import net.foundi.common.constant.FoundiConst;
import net.foundi.common.exception.BusinessException;
import net.foundi.common.utils.base.IDUtils;
import net.foundi.common.utils.file.FileUtils;
import net.foundi.common.utils.lang.DateUtils;
import net.foundi.common.utils.lang.StringUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.util.ObjectUtils;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 代码生成
 *
 * @author Afeng (afeng7882999@163.com)
 */
@Slf4j
@SuppressWarnings("all")
public class TemplateHelper {

    private static final String TIME = "LocalTime";
    private static final String DATE = "LocalDate";
    private static final String DATETIME = "LocalDateTime";
    private static final String BIGDECIMAL = "BigDecimal";
    public static final String PK = "PRI";
    public static final String EXTRA = "auto_increment";
    public static final String TEMPLATE_PATH = "/net/foundi/admin/generator/template/";

    private static final String[][] SQL_JAVA_TYPE_MAPPING = {
            {"tinyint", "Integer"},
            {"smallint", "Integer"},
            {"mediumint", "Integer"},
            {"int", "Integer"},
            {"integer", "Integer"},
            {"bigint", "Long"},
            {"float", "Float"},
            {"double", "Double"},
            {"decimal", "BigDecimal"},
            {"bit", "Boolean"},
            {"char", "String"},
            {"varchar", "String"},
            {"tinytext", "String"},
            {"text", "String"},
            {"mediumtext", "String"},
            {"longtext", "String"},
            {"time", "LocalTime"},
            {"date", "LocalDate"},
            {"datetime", "LocalDateTime"},
            {"timestamp", "LocalDateTime"}
    };

    private static final String AUTO_INSERT_COLS = "create_by, create_at";
    private static final String AUTO_UPDATE_COLS = "update_by, update_at";
    private static final String VERSION_COL = "version";
    private static final String LOGIC_DELETE_COL = "del_flag";

    private static final String TREE_ID_COL = "id";
    private static final String TREE_NAME_COL = "name";
    private static final String PARENT_ID_COL = "parent_id";
    private static final String SORT_ORDER_COL = "sort";

    public static final String TREE_ID_FIELD = "id";
    public static final String TREE_NAME_FIELD = "name";
    public static final String PARENT_ID_FIELD = "parentId";
    public static final String SORT_ORDER_FIELD = "sort";

    /**
     * 代码生成为Map
     *
     * @param columns 多个GenTableColumnDo列表
     * @param table   多个GenTableDo对象
     * @return Map列表
     */
    public static List<Map<String, Object>> generateToMap(List<List<GenTableColumnDo>> columnsList, List<GenTableDo> tables) {
        List<Map<String, Object>> genList = new ArrayList<>();
        for (int i = 0; i < tables.size(); i++) {
            generateToMap(columnsList.get(i), tables.get(i), genList);
        }
        return genList;
    }

    /**
     * 代码生成为Map
     *
     * @param columns GenTableColumnDo列表
     * @param table   GenTableDo对象
     * @return Map列表
     */
    private static void generateToMap(List<GenTableColumnDo> columns, GenTableDo table,
                                      List<Map<String, Object>> genList) {
        Map<String, Object> params = getGenerateParams(columns, table);
        // 生成后端代码
        Configuration cfg = getFreemarkerCfg();
        for (String templateName : getAdminTemplateTypes(table)) {
            String filePath = getAdminFilePath(templateName, table, params.get("className").toString(), "");
            Map<String, Object> map = new HashMap<>(1);
            map.put("content", renderToString(cfg, templateName + ".ftl", params));
            map.put("path", filePath);
            map.put("name", FileUtils.getName(filePath));
            genList.add(map);
        }
        // 生成前端代码
        for (String templateName : getFrontTemplateTypes(table)) {
            String filePath = getFrontFilePath(templateName, params.get("moduleName").toString(),
                    params.get("lowerClassName").toString(), "");
            Map<String, Object> map = new HashMap<>(1);
            Template template = getTemplate(cfg, templateName + ".ftl");
            map.put("content", renderToString(template, params));
            map.put("path", filePath);
            map.put("name", FileUtils.getName(filePath));
            genList.add(map);
        }
    }

    /**
     * 代码生成，输出byte[]
     *
     * @param columns 多个GenTableColumnDo列表
     * @param table   多个GenTableDo对象
     * @return byte数组
     */
    public static byte[] generateToZip(List<List<GenTableColumnDo>> columnsList, List<GenTableDo> tables) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        for (int i = 0; i < tables.size(); i++) {
            generateToZip(columnsList.get(i), tables.get(i), zip);
        }
        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();
    }

    /**
     * 代码生成，输出至ZipOutputStream
     *
     * @param columns GenTableColumnDo列表
     * @param table   GenTableDo对象
     * @return Map列表
     */
    private static void generateToZip(List<GenTableColumnDo> columns, GenTableDo table, ZipOutputStream zip) {
        Map<String, Object> params = getGenerateParams(columns, table);
        // 生成后端代码
        Configuration cfg = getFreemarkerCfg();
        for (String templateName : getAdminTemplateTypes(table)) {
            String filePath = getAdminFilePath(templateName, table, params.get("className").toString(), "");
            try {
                zip.putNextEntry(new ZipEntry(filePath));
                IOUtils.write(renderToString(cfg, templateName + ".ftl", params), zip,
                        FoundiConst.DEFAULT_CHARSET);
                zip.flush();
                zip.closeEntry();
            } catch (IOException e) {
                throw new BusinessException("代码生成：后端代码生成失败，表名：", table.getTableName(), e);
            }
        }
        // 生成前端代码
        for (String templateName : getFrontTemplateTypes(table)) {
            String filePath = getFrontFilePath(templateName, params.get("moduleName").toString(),
                    params.get("lowerClassName").toString(), "");
            try {
                zip.putNextEntry(new ZipEntry(filePath));
                IOUtils.write(renderToString(cfg, templateName + ".ftl", params), zip,
                        FoundiConst.DEFAULT_CHARSET);
                zip.flush();
                zip.closeEntry();
            } catch (IOException e) {
                throw new BusinessException("代码生成：前端代码生成失败，表名：", table.getTableName(), e);
            }
        }
    }

    /**
     * 代码生成为文件，存放于系统temp目录，用于下载
     *
     * @param columns   多个GenTableColumnDo列表
     * @param table     多个GenTableDo对象
     * @param overwrite 覆盖旧代码
     * @return 根目录
     */
    public static String generateFile(List<List<GenTableColumnDo>> columnsList, List<GenTableDo> tables, boolean overwrite) {
        String tempPath = FileUtils.getTempDir("foundi-generator-tmp/" + IDUtils.genMillisId("", "/"));
        generateFile(columnsList, tables, tempPath, overwrite);
        return tempPath;
    }

    /**
     * 代码生成为文件
     *
     * @param columns   多个GenTableColumnDo列表
     * @param table     多个GenTableDo对象
     * @param rootPath  根目录
     * @param overwrite 覆盖旧代码
     * @return 根目录
     */
    public static void generateFile(List<List<GenTableColumnDo>> columnsList, List<GenTableDo> tables, String rootPath,
                                    boolean overwrite) {
        for (int i = 0; i < tables.size(); i++) {
            generateFile(columnsList.get(i), tables.get(i), rootPath, overwrite);
        }
    }

    /**
     * 代码生成为文件，存放于系统temp目录，用于下载
     *
     * @param columns   GenTableColumnDo列表
     * @param table     GenTableDo对象
     * @param overwrite 覆盖旧代码
     * @return 根目录
     */
    public static String generateFile(List<GenTableColumnDo> columns, GenTableDo table, boolean overwrite) {
        String tempPath = FileUtils.getTempDir("foundi-generator-tmp/" + IDUtils.genMillisId("", "/"));
        generateFile(columns, table, tempPath, overwrite);
        return tempPath;
    }

    /**
     * 代码生成为文件
     *
     * @param columns   GenTableColumnDo列表
     * @param table     GenTableDo对象
     * @param rootPath  根目录
     * @param overwirte 覆盖旧代码
     */
    public static void generateFile(List<GenTableColumnDo> columns, GenTableDo table, String rootPath, boolean overwirte) {
        Map<String, Object> params = getGenerateParams(columns, table);
        Configuration cfg = getFreemarkerCfg();

        try {
            // 生成后端代码
            for (String templateName : getAdminTemplateTypes(table)) {
                String filePath = getAdminFilePath(templateName, table, params.get("className").toString(), rootPath);
                renderToFile(filePath, getTemplate(cfg, templateName + ".ftl"), params, overwirte);
            }

            // 生成前端代码
            for (String templateName : getFrontTemplateTypes(table)) {
                String filePath = getFrontFilePath(templateName, params.get("moduleName").toString(),
                        params.get("lowerClassName").toString(), rootPath);
                renderToFile(filePath, getTemplate(cfg, templateName + ".ftl"), params, overwirte);
            }

        } catch (IOException | TemplateException e) {
            throw new BusinessException("代码生成：代码生成失败", e);
        }
    }

    /**
     * 获取模板参数
     *
     * @param columns GenTableColumnDo列表
     * @param table   GenTableDo对象
     * @return 参数Map
     */
    private static Map<String, Object> getGenerateParams(List<GenTableColumnDo> columns, GenTableDo table) {

        String className = StringUtils.upperFirst(table.getEntityName());
        String lowerClassName = StringUtils.lowerFirst(table.getEntityName());

        // 存储模版数据
        Map<String, Object> params = new HashMap<>(16);
        // 数据表别名
        params.put("tableComment", table.getTableComment());
        // 包名称
        params.put("package", table.getPack());
        // 模块名称，“.”分隔
        params.put("moduleName", table.getModule());
        // 模块名称，“/”分隔
        params.put("moduleNameSlash", table.getModule().replace(".", "/"));
        // 模块名称，“-”分隔
        params.put("moduleNameDash", StringUtils.camelToSnake(table.getModule().replace(".", "/"), "-"));
        // 模块名称，驼峰
        params.put("moduleNameCamel", StringUtils.splitToCamel(table.getModule(), '.' , false));
        // 作者
        params.put("author", table.getAuthor());
        // 创建日期
        params.put("date", LocalDate.now().toString());
        // 表名
        params.put("tableName", table.getTableName());
        // 大写开头的类名
        params.put("className", className);
        // 小写开头的类名
        params.put("lowerClassName", lowerClassName);
        // “-”分隔的类名
        params.put("classNameDash", StringUtils.camelToSnake(lowerClassName, "-"));
        // 存在 Images 字段
        params.put("hasImages", false);
        // 存在 LocalTime 字段
        params.put("hasTime", false);
        // 存在 LocalDate字段
        params.put("hasDate", false);
        // 存在 LocalDateTime 字段
        params.put("hasDateTime", false);
        // 查询类中存在 LocalTime 字段
        params.put("queryHasTime", false);
        // 查询类中存在 LocalDate 字段
        params.put("queryHasDate", false);
        // 查询类中存在 Datetime 字段
        params.put("queryHasDateTime", false);
        // 存在 BigDecimal 字段
        params.put("hasBigDecimal", false);
        // 查询类中存在 BigDecimal 字段
        params.put("queryHasBigDecimal", false);
        // 是否需要创建查询
        params.put("hasQuery", false);
        // 是否需要排序
        params.put("hasOrder", false);
        // 自增主键
        params.put("auto", false);
        // 存在字典
        params.put("hasDict", false);
        // 是否是子表
        params.put("isSub", table.getIsSub());
        // 是否是树表
        params.put("isTree", table.getIsTree());
        if (table.getIsTree()) {
            params.put("treeId", table.getTreeId());
            params.put("treeName", table.getTreeName());
            params.put("treeParentId", table.getTreeParentId());
            params.put("treeSort", table.getTreeSort());
        }
        // 前端是否生成编辑、详细页面
        params.put("isFrontEdit", table.getIsFrontEdit());
        params.put("isFrontDetail", table.getIsFrontDetail());
        // 前端菜单
        params.put("menuTitle", table.getMenuTitle());
        params.put("menuParentId", table.getMenuParentId());

        // 字段信息
        List<Map<String, Object>> cols = new ArrayList<>();
        // 非主键字段信息
        List<Map<String, Object>> noPkCols = new ArrayList<>();
        // 非自动生成字段
        List<Map<String, Object>> noAutoFillCols = new ArrayList<>();
        // 保存查询字段的信息
        List<Map<String, Object>> queryCols = new ArrayList<>();
        // 保存字典查询字段的信息
        List<Map<String, Object>> queryDictCols = new ArrayList<>();
        // 存储字典字段信息
        List<Map<String, Object>> dictCols = new ArrayList<>();
        // 存储 between 信息
        List<Map<String, Object>> queryBetweenCols = new ArrayList<>();
        // 存储不为空的字段信息
        List<Map<String, Object>> notNullColumns = new ArrayList<>();
        // 存储列表显示的字段
        List<Map<String, Object>> listColumns = new ArrayList<>();
        // 存储添加、修改表单显示的字段
        List<Map<String, Object>> editColumns = new ArrayList<>();
        // 存储添加、修改表单显示的非空字段
        List<Map<String, Object>> editNotNullColumns = new ArrayList<>();

        columns = columns.stream()
                .sorted(Comparator.comparing(GenTableColumnDo::getSort))
                .collect(Collectors.toList());

        for (GenTableColumnDo col : columns) {
            Map<String, Object> listMap = new HashMap<>(16);
            // 数据列描述
            listMap.put("columnComment", col.getColumnComment());
            // 简要数据列描述
            listMap.put("columnBrief", beforePunc(col.getColumnComment()));
            // 主键类型
            listMap.put("columnKey", col.getColumnKey());
            // 字段类型
            String fieldType = col.getFieldType();
            // 小写开头的字段名
            String lowerFieldName = col.getFieldName();
            // 大写开头的字段名
            String upperFieldName = StringUtils.upperFirst(lowerFieldName);

            // 是否存在 Images 字段
            if ("image".equals(col.getHtmlType())) {
                params.put("hasImages", true);
            }
            // 是否存在 LocalTime 类型的字段
            if (TIME.equals(fieldType)) {
                params.put("hasTime", true);
            }
            // 是否存在 LocalDate 类型的字段
            if (DATE.equals(fieldType)) {
                params.put("hasDate", true);
            }
            // 是否存在 LocalDateTime 类型的字段
            if (DATETIME.equals(fieldType)) {
                params.put("hasDateTime", true);
            }
            // 是否存在 BigDecimal 类型的字段
            if (BIGDECIMAL.equals(fieldType)) {
                params.put("hasBigDecimal", true);
            }
            // 主键是否自增
            if (EXTRA.equals(col.getColumnExtra())) {
                params.put("auto", true);
            }
            // 存在字典
            if (StringUtils.hasValue(col.getDictType())) {
                params.put("hasDict", true);
                listMap.put("isDict", true);
            } else {
                listMap.put("isDict", false);
            }

            // 存储数据列名
            listMap.put("columnName", col.getColumnName());
            // 字段名是否是数据列名转换（下划线转驼峰）
            listMap.put("isSpecialName", !StringUtils.snakeToCamel(col.getColumnName(), false).equals(col.getFieldName()));
            // 存储字段类型
            listMap.put("fieldType", fieldType);
            // 不为空
            listMap.put("isRequired", col.getIsRequired());
            // 字段列表显示
            listMap.put("isList", col.getIsList());
            // 表单显示
            listMap.put("isEdit", col.getIsEdit());
            // 需要查询
            listMap.put("isQuery", col.getIsQuery());
            // 需要排序
            listMap.put("isOrder", col.getIsOrder());
            // 表单组件类型
            listMap.put("htmlType", StringUtils.hasValue(col.getHtmlType()) ? col.getHtmlType() : "");
            // 小写开头的字段名称
            listMap.put("lowerFieldName", lowerFieldName);
            //大写开头的字段名称
            listMap.put("upperFieldName", upperFieldName);
            // 字典名称
            listMap.put("dictType", col.getDictType());

            // 是否是主键
            boolean isPk = PK.equals(col.getColumnKey());
            listMap.put("isPk", isPk);
            if (isPk) {
                // 存储主键类型
                params.put("pkFieldType", fieldType);
                // 存储小写开头的字段名
                params.put("pkLowerFieldName", lowerFieldName);
                // 存储大写开头的字段名
                params.put("pkUpperFieldName", upperFieldName);
            }

            // 是否是 MybatisPlus 自动注入字段
            boolean isAutoInsertField = StringUtils.contain(AUTO_INSERT_COLS, col.getColumnName());
            listMap.put("isAutoInsertField", isAutoInsertField);
            boolean isAutoUpdateField = StringUtils.contain(AUTO_UPDATE_COLS, col.getColumnName());
            listMap.put("isAutoUpdateField", isAutoUpdateField);
            boolean isVersionField = col.getColumnName().equals(VERSION_COL);
            listMap.put("isVersionField", isVersionField);
            boolean isLogicDeleteField = col.getColumnName().equals(LOGIC_DELETE_COL);
            listMap.put("isLogicDeleteField", isLogicDeleteField);

            // 添加非空字段信息
            if (col.getIsRequired()) {
                notNullColumns.add(listMap);
            }

            // 判断是否有查询，如有则把查询的字段set进columnQuery
            if (col.getIsQuery() && StringUtils.hasValue(col.getQueryType())) {
                // 查询类型
                listMap.put("queryType", col.getQueryType());
                // 是否存在查询
                params.put("hasQuery", true);
                // 添加到查询列表中
                queryCols.add(listMap);

                if (TIME.equals(fieldType)) {
                    // 查询 LocalTime 类型
                    params.put("queryHasTime", true);
                }
                if (DATE.equals(fieldType)) {
                    // 查询 LocalDate 类型
                    params.put("queryHasDate", true);
                }
                if (DATETIME.equals(fieldType)) {
                    // 查询 Datetime 类型
                    params.put("queryHasDateTime", true);
                }
                if (BIGDECIMAL.equals(fieldType)) {
                    // 查询 BigDecimal 类型
                    params.put("queryHasBigDecimal", true);
                }
                if ("between".equalsIgnoreCase(col.getQueryType())) {
                    // 添加到Between列表中
                    queryBetweenCols.add(listMap);
                }
                if (StringUtils.hasValue(col.getDictType())) {
                    // 添加到字典查询列表中
                    queryDictCols.add(listMap);
                }
            }

            // 判断是否有排序
            if (col.getIsOrder()) {
                params.put("hasOrder", true);
            }

            // 添加到字段列表中
            cols.add(listMap);
            // 添加非主键到字段列表中
            if (!isPk) {
                noPkCols.add(listMap);
            }
            // 添加非自动添加字段
            if (!isPk && !isAutoInsertField && !isAutoUpdateField && !isVersionField && !isLogicDeleteField) {
                noAutoFillCols.add(listMap);
            }
            // 添加列表显示字段
            if (col.getIsList()) {
                listColumns.add(listMap);
            }
            // 添加添加、编辑表单显示字段
            if (col.getIsEdit()) {
                editColumns.add(listMap);
            }
            // 添加添加、编辑表单显示非空字段
            if (col.getIsEdit() && col.getIsRequired()) {
                editNotNullColumns.add(listMap);
            }
            // 添加字典字段
            if (StringUtils.hasValue(col.getDictType())) {
                dictCols.add(listMap);
            }
        }

        // 保存字段列表
        params.put("columns", cols);
        // 保存非主键字段列表
        params.put("noPkColumns", noPkCols);
        // 保存非自动添加字段列表
        params.put("noAutoFillColumns", noAutoFillCols);
        // 保存查询列表
        params.put("queryColumns", queryCols);
        // 保存字典列表
        params.put("dictColumns", dictCols);
        // 保存字典查询列表中
        params.put("queryDictColumns", queryDictCols);
        // 保存查询Between列表
        params.put("queryBetweenColumns", queryBetweenCols);
        // 保存非空字段信息
        params.put("notNullColumns", notNullColumns);
        // 保存列表显示字段信息
        params.put("listColumns", listColumns);
        // 保存添加、编辑表单显示字段信息
        params.put("editColumns", editColumns);
        // 保存添加、编辑表单显示非空字段信息
        params.put("editNotNullColumns", editNotNullColumns);

        // 保存sql的id列表
        List<String> ids = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ids.add(String.valueOf(IdWorker.getId()));
        }
        params.put("ids", ids);
        params.put("version", DateUtils.currentDateTimeStr());

        return params;
    }

    /**
     * SQL字段类型映射为Java类型
     *
     * @param sqlType SQL字段类型
     * @return Java类型
     */
    public static String sqlToJavaType(String sqlType) {
        for (String[] pair : SQL_JAVA_TYPE_MAPPING) {
            if (pair[0].equals(sqlType)) {
                return pair[1];
            }
        }
        return "unknownType";
    }

    /**
     * 判断是否是树表
     *
     * @param columns 字段列表
     * @return true: 是树表
     */
    public static boolean isTree(List<GenTableColumnDo> columns) {
        return Arrays.asList(TREE_ID_COL, TREE_NAME_COL, PARENT_ID_COL, SORT_ORDER_COL).stream()
                .allMatch(i -> columns.stream()
                        .map(GenTableColumnDo::getColumnName)
                        .anyMatch(n -> n.equals(i))
                );
    }

    /**
     * 判断是否是自动填充字段
     *
     * @param column 字段
     * @return true: 是自动填充字段
     */
    public static boolean isAutoFilledCol(GenTableColumnDo column) {
        boolean isAutoInsertField = StringUtils.contain(AUTO_INSERT_COLS, column.getColumnName());
        boolean isAutoUpdateField = StringUtils.contain(AUTO_UPDATE_COLS, column.getColumnName());
        boolean isVersionField = column.getColumnName().equals(VERSION_COL);
        boolean isLogicDeleteField = column.getColumnName().equals(LOGIC_DELETE_COL);
        return isAutoInsertField || isAutoUpdateField || isVersionField || isLogicDeleteField;
    }

    /**
     * 获取后端代码模板类型
     *
     * @return 名称列表
     */
    private static List<String> getAdminTemplateTypes(GenTableDo table) {
        List<String> types = new ArrayList<>();
        types.add("do");
        types.add("sqlMapper");
        types.add("dao");
        if (!table.getIsSub()) {
            // 子表仅生成DAO层
            types.add("dto");
            types.add("controller");
            types.add("query");
            types.add("service");
            types.add("serviceImpl");
            types.add("sql");
        }
        return types;
    }

    /**
     * 定义后端文件路径以及名称
     *
     * @param templateType 模板类型
     * @param table        GenTableDo对象
     * @param className    代码生成的类名
     * @param rootPath     根目录
     * @return 生成代码的文件路径
     */
    private static String getAdminFilePath(String templateType, GenTableDo table, String className, String rootPath) {
        String packagePath = rootPath + "/src/main/java/";
        if (!ObjectUtils.isEmpty(table.getPack())) {
            packagePath += table.getPack().replace(".", "/") + "/";
        }
        String projectPath = packagePath + table.getModule().replace(".", "/") + "/";

        if ("do".equals(templateType)) {
            return projectPath + "entity/domain/" + className + "Do.java";
        }
        if ("controller".equals(templateType)) {
            return projectPath + "controller/" + className + "Controller.java";
        }
        if ("service".equals(templateType)) {
            return projectPath + "service/" + className + "Service.java";
        }
        if ("serviceImpl".equals(templateType)) {
            return projectPath + "service/impl/" + className + "ServiceImpl.java";
        }
        if ("dto".equals(templateType)) {
            return projectPath + "entity/dto/" + className + "Dto.java";
        }
        if ("query".equals(templateType)) {
            return projectPath + "entity/query/" + className + "Query.java";
        }
        if ("sqlMapper".equals(templateType)) {
            return projectPath + "dao/mapper/" + className + "Mapper.xml";
        }
        if ("dao".equals(templateType)) {
            return projectPath + "dao/" + className + "Dao.java";
        }
        if ("sql".equals(templateType)) {
            return packagePath + className + ".sql";
        }

        throw new BusinessException(StringUtils.format("代码生成：不支持的模板类型：{}", templateType));
    }

    /**
     * 获取前端代码模板名称
     *
     * @return 名称列表
     */
    private static List<String> getFrontTemplateTypes(GenTableDo table) {
        List<String> types = new ArrayList<>();
        if (table.getIsSub()) {
            // 子表不生成前端
            return types;
        }
        types.add("api.ts");
        types.add("index.vue");
        if (table.getIsFrontEdit()) {
            types.add("edit.vue");
        }
        if (table.getIsFrontDetail()) {
            types.add("detail.vue");
        }
        return types;
    }

    /**
     * 定义文件路径以及名称
     *
     * @param templateType 模板类型
     * @param table        GenTableDo对象
     * @param className    代码生成的类名
     * @param rootPath     根目录
     * @return 生成代码的文件路径
     */
    private static String getFrontFilePath(String templateType, String moduleName, String className, String rootPath) {
        String path = rootPath + "/web/";
        String pagePath = path + "src/views/modules/" + StringUtils.camelToSnake(moduleName, "-")  + "/"
                + StringUtils.camelToSnake(className, "-") + "/";
        String apiPath = path + "src/api/" + StringUtils.camelToSnake(moduleName, "-") + "/";
        if ("edit.vue".equals(templateType)) {
            return pagePath + "edit.vue";
        }
        if ("index.vue".equals(templateType)) {
            return pagePath + "index.vue";
        }
        if ("detail.vue".equals(templateType)) {
            return pagePath + "detail.vue";
        }
        if ("api.ts".equals(templateType)) {
            return apiPath + StringUtils.camelToSnake(className, "-") + ".ts";
        }

        throw new BusinessException(StringUtils.format("代码生成：不支持的模板类型：{}", templateType));
    }

    /**
     * 获取Freemarker配置
     *
     * @param resource 模板文件
     * @return Configuration
     */
    private static Configuration getFreemarkerCfg() {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_30);
        cfg.setLocalizedLookup(false);
        cfg.setDefaultEncoding(FoundiConst.DEFAULT_CHARSET);
        cfg.setTemplateLoader(new ClassTemplateLoader(TemplateHelper.class, TEMPLATE_PATH));
        return cfg;
    }

    /**
     * 获取Freemarker模板
     *
     * @param resource 模板文件
     * @return Template
     */
    private static Template getTemplate(Configuration cfg, String resource) {
        try {
            return cfg.getTemplate(resource);
        } catch (IOException e) {
            throw new BusinessException(StringUtils.format("代码生成：无法找到代码模板：{}", resource), e);
        }
    }

    /**
     * 代码生成为字符串
     *
     * @param template   Freemarker Template
     * @param bindingMap 模板参数
     * @return 字符串
     */
    private static String renderToString(Template template, Map<?, ?> bindingMap) {
        StringWriter writer = new StringWriter();
        try {
            template.process(bindingMap, writer);
        } catch (TemplateException | IOException e) {
            throw new BusinessException("代码生成：代码生成失败", e);
        }
        return writer.toString();
    }

    /**
     * 生成代码字符串
     *
     * @param cfg        Freemarker配置
     * @param resource   模板文件
     * @param bindingMap 模板参数
     * @return 字符串
     */
    private static String renderToString(Configuration cfg, String resource, Map<?, ?> bindingMap) {
        StringWriter writer = new StringWriter();
        try {
            getTemplate(cfg, resource).process(bindingMap, writer);
        } catch (TemplateException | IOException e) {
            throw new BusinessException("代码生成：代码生成失败", e);
        }
        return writer.toString();
    }

    /**
     * 生成代码文件
     *
     * @param fileName  文件路径
     * @param template  Freemarker Template
     * @param params    模板参数
     * @param overwrite 是否覆盖旧文件
     */
    private static void renderToFile(String filePath, Template template, Map<String, Object> params, boolean overwrite)
            throws IOException, TemplateException {
        Path file = Paths.get(filePath);
        Path dir = FileUtils.getParent(file);
        String pathName = dir.toString();
        String fileName = FileUtils.getName(file).toString();
        String backupName = pathName + "/bak." + fileName;


        Files.createDirectories(dir);
        if (Files.exists(file) && !overwrite) {
            //backup old files
            Path backup = Paths.get(backupName);
            Files.move(file, backup, StandardCopyOption.REPLACE_EXISTING);
        }

        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath),
                Charset.forName(FoundiConst.DEFAULT_CHARSET))) {
            template.process(params, writer);
            writer.flush();
        }
    }

    /**
     * 获取标点之前的子字符串
     *
     * @param strWithPunc 原字符串
     * @return 子字符串
     */
    private static String beforePunc(String strWithPunc) {
        List<Character> puncList = Arrays.asList(' ' , '(' , ',' , '-' , '_' , ':' , '（' , '，' , '：' , ' ' );
        int length = strWithPunc.length();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            Character c = strWithPunc.charAt(i);
            if (puncList.contains(c)) {
                return sb.toString();
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

}
