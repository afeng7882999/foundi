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
 * ไปฃ็ ็ๆ
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
     * ไปฃ็ ็ๆไธบMap
     *
     * @param columns ๅคไธชGenTableColumnDoๅ่กจ
     * @param table   ๅคไธชGenTableDoๅฏน่ฑก
     * @return Mapๅ่กจ
     */
    public static List<Map<String, Object>> generateToMap(List<List<GenTableColumnDo>> columnsList, List<GenTableDo> tables) {
        List<Map<String, Object>> genList = new ArrayList<>();
        for (int i = 0; i < tables.size(); i++) {
            generateToMap(columnsList.get(i), tables.get(i), genList);
        }
        return genList;
    }

    /**
     * ไปฃ็ ็ๆไธบMap
     *
     * @param columns GenTableColumnDoๅ่กจ
     * @param table   GenTableDoๅฏน่ฑก
     * @return Mapๅ่กจ
     */
    private static void generateToMap(List<GenTableColumnDo> columns, GenTableDo table,
                                      List<Map<String, Object>> genList) {
        Map<String, Object> params = getGenerateParams(columns, table);
        // ็ๆๅ็ซฏไปฃ็ 
        Configuration cfg = getFreemarkerCfg();
        for (String templateName : getAdminTemplateTypes(table)) {
            String filePath = getAdminFilePath(templateName, table, params.get("className").toString(), "");
            Map<String, Object> map = new HashMap<>(1);
            map.put("content", renderToString(cfg, templateName + ".ftl", params));
            map.put("path", filePath);
            map.put("name", FileUtils.getName(filePath));
            genList.add(map);
        }
        // ็ๆๅ็ซฏไปฃ็ 
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
     * ไปฃ็ ็ๆ๏ผ่พๅบbyte[]
     *
     * @param columns ๅคไธชGenTableColumnDoๅ่กจ
     * @param table   ๅคไธชGenTableDoๅฏน่ฑก
     * @return byteๆฐ็ป
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
     * ไปฃ็ ็ๆ๏ผ่พๅบ่ณZipOutputStream
     *
     * @param columns GenTableColumnDoๅ่กจ
     * @param table   GenTableDoๅฏน่ฑก
     * @return Mapๅ่กจ
     */
    private static void generateToZip(List<GenTableColumnDo> columns, GenTableDo table, ZipOutputStream zip) {
        Map<String, Object> params = getGenerateParams(columns, table);
        // ็ๆๅ็ซฏไปฃ็ 
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
                throw new BusinessException("ไปฃ็ ็ๆ๏ผๅ็ซฏไปฃ็ ็ๆๅคฑ่ดฅ๏ผ่กจๅ๏ผ", table.getTableName(), e);
            }
        }
        // ็ๆๅ็ซฏไปฃ็ 
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
                throw new BusinessException("ไปฃ็ ็ๆ๏ผๅ็ซฏไปฃ็ ็ๆๅคฑ่ดฅ๏ผ่กจๅ๏ผ", table.getTableName(), e);
            }
        }
    }

    /**
     * ไปฃ็ ็ๆไธบๆไปถ๏ผๅญๆพไบ็ณป็ปtemp็ฎๅฝ๏ผ็จไบไธ่ฝฝ
     *
     * @param columns   ๅคไธชGenTableColumnDoๅ่กจ
     * @param table     ๅคไธชGenTableDoๅฏน่ฑก
     * @param overwrite ่ฆ็ๆงไปฃ็ 
     * @return ๆ น็ฎๅฝ
     */
    public static String generateFile(List<List<GenTableColumnDo>> columnsList, List<GenTableDo> tables, boolean overwrite) {
        String tempPath = FileUtils.getTempDir("foundi-generator-tmp/" + IDUtils.genMillisId("", "/"));
        generateFile(columnsList, tables, tempPath, overwrite);
        return tempPath;
    }

    /**
     * ไปฃ็ ็ๆไธบๆไปถ
     *
     * @param columns   ๅคไธชGenTableColumnDoๅ่กจ
     * @param table     ๅคไธชGenTableDoๅฏน่ฑก
     * @param rootPath  ๆ น็ฎๅฝ
     * @param overwrite ่ฆ็ๆงไปฃ็ 
     * @return ๆ น็ฎๅฝ
     */
    public static void generateFile(List<List<GenTableColumnDo>> columnsList, List<GenTableDo> tables, String rootPath,
                                    boolean overwrite) {
        for (int i = 0; i < tables.size(); i++) {
            generateFile(columnsList.get(i), tables.get(i), rootPath, overwrite);
        }
    }

    /**
     * ไปฃ็ ็ๆไธบๆไปถ๏ผๅญๆพไบ็ณป็ปtemp็ฎๅฝ๏ผ็จไบไธ่ฝฝ
     *
     * @param columns   GenTableColumnDoๅ่กจ
     * @param table     GenTableDoๅฏน่ฑก
     * @param overwrite ่ฆ็ๆงไปฃ็ 
     * @return ๆ น็ฎๅฝ
     */
    public static String generateFile(List<GenTableColumnDo> columns, GenTableDo table, boolean overwrite) {
        String tempPath = FileUtils.getTempDir("foundi-generator-tmp/" + IDUtils.genMillisId("", "/"));
        generateFile(columns, table, tempPath, overwrite);
        return tempPath;
    }

    /**
     * ไปฃ็ ็ๆไธบๆไปถ
     *
     * @param columns   GenTableColumnDoๅ่กจ
     * @param table     GenTableDoๅฏน่ฑก
     * @param rootPath  ๆ น็ฎๅฝ
     * @param overwirte ่ฆ็ๆงไปฃ็ 
     */
    public static void generateFile(List<GenTableColumnDo> columns, GenTableDo table, String rootPath, boolean overwirte) {
        Map<String, Object> params = getGenerateParams(columns, table);
        Configuration cfg = getFreemarkerCfg();

        try {
            // ็ๆๅ็ซฏไปฃ็ 
            for (String templateName : getAdminTemplateTypes(table)) {
                String filePath = getAdminFilePath(templateName, table, params.get("className").toString(), rootPath);
                renderToFile(filePath, getTemplate(cfg, templateName + ".ftl"), params, overwirte);
            }

            // ็ๆๅ็ซฏไปฃ็ 
            for (String templateName : getFrontTemplateTypes(table)) {
                String filePath = getFrontFilePath(templateName, params.get("moduleName").toString(),
                        params.get("lowerClassName").toString(), rootPath);
                renderToFile(filePath, getTemplate(cfg, templateName + ".ftl"), params, overwirte);
            }

        } catch (IOException | TemplateException e) {
            throw new BusinessException("ไปฃ็ ็ๆ๏ผไปฃ็ ็ๆๅคฑ่ดฅ", e);
        }
    }

    /**
     * ่ทๅๆจกๆฟๅๆฐ
     *
     * @param columns GenTableColumnDoๅ่กจ
     * @param table   GenTableDoๅฏน่ฑก
     * @return ๅๆฐMap
     */
    private static Map<String, Object> getGenerateParams(List<GenTableColumnDo> columns, GenTableDo table) {

        String className = StringUtils.upperFirst(table.getEntityName());
        String lowerClassName = StringUtils.lowerFirst(table.getEntityName());

        // ๅญๅจๆจก็ๆฐๆฎ
        Map<String, Object> params = new HashMap<>(16);
        // ๆฐๆฎ่กจๅซๅ
        params.put("tableComment", table.getTableComment());
        // ๅๅ็งฐ
        params.put("package", table.getPack());
        // ๆจกๅๅ็งฐ๏ผโ.โๅ้
        params.put("moduleName", table.getModule());
        // ๆจกๅๅ็งฐ๏ผโ/โๅ้
        params.put("moduleNameSlash", table.getModule().replace(".", "/"));
        // ๆจกๅๅ็งฐ๏ผโ-โๅ้
        params.put("moduleNameDash", StringUtils.camelToSnake(table.getModule().replace(".", "/"), "-"));
        // ๆจกๅๅ็งฐ๏ผ้ฉผๅณฐ
        params.put("moduleNameCamel", StringUtils.splitToCamel(table.getModule(), '.' , false));
        // ไฝ่
        params.put("author", table.getAuthor());
        // ๅๅปบๆฅๆ
        params.put("date", LocalDate.now().toString());
        // ่กจๅ
        params.put("tableName", table.getTableName());
        // ๅคงๅๅผๅคด็็ฑปๅ
        params.put("className", className);
        // ๅฐๅๅผๅคด็็ฑปๅ
        params.put("lowerClassName", lowerClassName);
        // โ-โๅ้็็ฑปๅ
        params.put("classNameDash", StringUtils.camelToSnake(lowerClassName, "-"));
        // ๅญๅจ Images ๅญๆฎต
        params.put("hasImages", false);
        // ๅญๅจ LocalTime ๅญๆฎต
        params.put("hasTime", false);
        // ๅญๅจ LocalDateๅญๆฎต
        params.put("hasDate", false);
        // ๅญๅจ LocalDateTime ๅญๆฎต
        params.put("hasDateTime", false);
        // ๆฅ่ฏข็ฑปไธญๅญๅจ LocalTime ๅญๆฎต
        params.put("queryHasTime", false);
        // ๆฅ่ฏข็ฑปไธญๅญๅจ LocalDate ๅญๆฎต
        params.put("queryHasDate", false);
        // ๆฅ่ฏข็ฑปไธญๅญๅจ Datetime ๅญๆฎต
        params.put("queryHasDateTime", false);
        // ๅญๅจ BigDecimal ๅญๆฎต
        params.put("hasBigDecimal", false);
        // ๆฅ่ฏข็ฑปไธญๅญๅจ BigDecimal ๅญๆฎต
        params.put("queryHasBigDecimal", false);
        // ๆฏๅฆ้่ฆๅๅปบๆฅ่ฏข
        params.put("hasQuery", false);
        // ๆฏๅฆ้่ฆๆๅบ
        params.put("queryHasOrder", false);
        // ่ชๅขไธป้ฎ
        params.put("auto", false);
        // ๅญๅจๅญๅธ
        params.put("hasDict", false);
        // ๆฏๅฆๆฏๅญ่กจ
        params.put("isSub", table.getIsSub());
        // ๆฏๅฆๆฏๆ ่กจ
        params.put("isTree", table.getIsTree());
        if (table.getIsTree()) {
            params.put("treeId", table.getTreeId());
            params.put("treeName", table.getTreeName());
            params.put("treeParentId", table.getTreeParentId());
            params.put("treeSort", table.getTreeSort());
        }
        // ๅ็ซฏๆฏๅฆ็ๆ็ผ่พใ่ฏฆ็ป้กต้ข
        params.put("isFrontEdit", table.getIsFrontEdit());
        params.put("isFrontDetail", table.getIsFrontDetail());
        // ๅ็ซฏ่ๅ
        params.put("menuTitle", table.getMenuTitle());
        params.put("menuParentId", table.getMenuParentId());

        // ๅญๆฎตไฟกๆฏ
        List<Map<String, Object>> cols = new ArrayList<>();
        // ้ไธป้ฎๅญๆฎตไฟกๆฏ
        List<Map<String, Object>> noPkCols = new ArrayList<>();
        // ้่ชๅจ็ๆๅญๆฎต
        List<Map<String, Object>> noAutoFillCols = new ArrayList<>();
        // ไฟๅญๆฅ่ฏขๅญๆฎต็ไฟกๆฏ
        List<Map<String, Object>> queryCols = new ArrayList<>();
        // ไฟๅญๅญๅธๆฅ่ฏขๅญๆฎต็ไฟกๆฏ
        List<Map<String, Object>> queryDictCols = new ArrayList<>();
        // ไฟๅญๅญๅธๆฅ่ฏขๆๅบๅญๆฎต็ไฟกๆฏ
        List<Map<String, Object>> queryOrderCols = new ArrayList<>();
        // ๅญๅจๅญๅธๅญๆฎตไฟกๆฏ
        List<Map<String, Object>> dictCols = new ArrayList<>();
        // ๅญๅจ between ไฟกๆฏ
        List<Map<String, Object>> queryBetweenCols = new ArrayList<>();
        // ๅญๅจไธไธบ็ฉบ็ๅญๆฎตไฟกๆฏ
        List<Map<String, Object>> notNullColumns = new ArrayList<>();
        // ๅญๅจๅ่กจๆพ็คบ็ๅญๆฎต
        List<Map<String, Object>> listColumns = new ArrayList<>();
        // ๅญๅจๆทปๅ ใไฟฎๆน่กจๅๆพ็คบ็ๅญๆฎต
        List<Map<String, Object>> editColumns = new ArrayList<>();
        // ๅญๅจๆทปๅ ใไฟฎๆน่กจๅๆพ็คบ็้็ฉบๅญๆฎต
        List<Map<String, Object>> editNotNullColumns = new ArrayList<>();

        columns = columns.stream()
                .sorted(Comparator.comparing(GenTableColumnDo::getSort))
                .collect(Collectors.toList());

        for (GenTableColumnDo col : columns) {
            Map<String, Object> listMap = new HashMap<>(16);
            // ๆฐๆฎๅๆ่ฟฐ
            listMap.put("columnComment", col.getColumnComment());
            // ็ฎ่ฆๆฐๆฎๅๆ่ฟฐ
            listMap.put("columnBrief", beforePunc(col.getColumnComment()));
            // ไธป้ฎ็ฑปๅ
            listMap.put("columnKey", col.getColumnKey());
            // ๅญๆฎต็ฑปๅ
            String fieldType = col.getFieldType();
            // ๅฐๅๅผๅคด็ๅญๆฎตๅ
            String lowerFieldName = col.getFieldName();
            // ๅคงๅๅผๅคด็ๅญๆฎตๅ
            String upperFieldName = StringUtils.upperFirst(lowerFieldName);

            // ๆฏๅฆๅญๅจ Images ๅญๆฎต
            if ("image".equals(col.getHtmlType())) {
                params.put("hasImages", true);
            }
            // ๆฏๅฆๅญๅจ LocalTime ็ฑปๅ็ๅญๆฎต
            if (TIME.equals(fieldType)) {
                params.put("hasTime", true);
            }
            // ๆฏๅฆๅญๅจ LocalDate ็ฑปๅ็ๅญๆฎต
            if (DATE.equals(fieldType)) {
                params.put("hasDate", true);
            }
            // ๆฏๅฆๅญๅจ LocalDateTime ็ฑปๅ็ๅญๆฎต
            if (DATETIME.equals(fieldType)) {
                params.put("hasDateTime", true);
            }
            // ๆฏๅฆๅญๅจ BigDecimal ็ฑปๅ็ๅญๆฎต
            if (BIGDECIMAL.equals(fieldType)) {
                params.put("hasBigDecimal", true);
            }
            // ไธป้ฎๆฏๅฆ่ชๅข
            if (EXTRA.equals(col.getColumnExtra())) {
                params.put("auto", true);
            }
            // ๅญๅจๅญๅธ
            if (StringUtils.hasValue(col.getDictType())) {
                params.put("hasDict", true);
                listMap.put("isDict", true);
            } else {
                listMap.put("isDict", false);
            }

            // ๅญๅจๆฐๆฎๅๅ
            listMap.put("columnName", col.getColumnName());
            // ๅญๆฎตๅๆฏๅฆๆฏๆฐๆฎๅๅ่ฝฌๆข๏ผไธๅ็บฟ่ฝฌ้ฉผๅณฐ๏ผ
            listMap.put("isSpecialName", !StringUtils.snakeToCamel(col.getColumnName(), false).equals(col.getFieldName()));
            // ๅญๅจๅญๆฎต็ฑปๅ
            listMap.put("fieldType", fieldType);
            // ไธไธบ็ฉบ
            listMap.put("isRequired", col.getIsRequired());
            // ๅญๆฎตๅ่กจๆพ็คบ
            listMap.put("isList", col.getIsList());
            // ่กจๅๆพ็คบ
            listMap.put("isEdit", col.getIsEdit());
            // ้่ฆๆฅ่ฏข
            listMap.put("isQuery", col.getIsQuery());
            // ้่ฆๆๅบ
            listMap.put("isOrder", col.getIsOrder());
            // ่กจๅ็ปไปถ็ฑปๅ
            listMap.put("htmlType", StringUtils.hasValue(col.getHtmlType()) ? col.getHtmlType() : "");
            // ๅฐๅๅผๅคด็ๅญๆฎตๅ็งฐ
            listMap.put("lowerFieldName", lowerFieldName);
            //ๅคงๅๅผๅคด็ๅญๆฎตๅ็งฐ
            listMap.put("upperFieldName", upperFieldName);
            // ๅญๅธๅ็งฐ
            listMap.put("dictType", col.getDictType());

            // ๆฏๅฆๆฏไธป้ฎ
            boolean isPk = PK.equals(col.getColumnKey());
            listMap.put("isPk", isPk);
            if (isPk) {
                // ๅญๅจไธป้ฎ็ฑปๅ
                params.put("pkFieldType", fieldType);
                // ๅญๅจๅฐๅๅผๅคด็ๅญๆฎตๅ
                params.put("pkLowerFieldName", lowerFieldName);
                // ๅญๅจๅคงๅๅผๅคด็ๅญๆฎตๅ
                params.put("pkUpperFieldName", upperFieldName);
            }

            // ๆฏๅฆๆฏ MybatisPlus ่ชๅจๆณจๅฅๅญๆฎต
            boolean isAutoInsertField = StringUtils.contain(AUTO_INSERT_COLS, col.getColumnName());
            listMap.put("isAutoInsertField", isAutoInsertField);
            boolean isAutoUpdateField = StringUtils.contain(AUTO_UPDATE_COLS, col.getColumnName());
            listMap.put("isAutoUpdateField", isAutoUpdateField);
            boolean isVersionField = col.getColumnName().equals(VERSION_COL);
            listMap.put("isVersionField", isVersionField);
            boolean isLogicDeleteField = col.getColumnName().equals(LOGIC_DELETE_COL);
            listMap.put("isLogicDeleteField", isLogicDeleteField);

            // ๆทปๅ ้็ฉบๅญๆฎตไฟกๆฏ
            if (col.getIsRequired()) {
                notNullColumns.add(listMap);
            }

            // ๅคๆญๆฏๅฆๆๆฅ่ฏข๏ผๅฆๆๅๆๆฅ่ฏข็ๅญๆฎตset่ฟcolumnQuery
            if (col.getIsQuery() && StringUtils.hasValue(col.getQueryType())) {
                // ๆฅ่ฏข็ฑปๅ
                listMap.put("queryType", col.getQueryType());
                // ๆฏๅฆๅญๅจๆฅ่ฏข
                params.put("hasQuery", true);
                // ๆทปๅ ๅฐๆฅ่ฏขๅ่กจไธญ
                queryCols.add(listMap);

                if (TIME.equals(fieldType)) {
                    // ๆฅ่ฏข LocalTime ็ฑปๅ
                    params.put("queryHasTime", true);
                }
                if (DATE.equals(fieldType)) {
                    // ๆฅ่ฏข LocalDate ็ฑปๅ
                    params.put("queryHasDate", true);
                }
                if (DATETIME.equals(fieldType)) {
                    // ๆฅ่ฏข Datetime ็ฑปๅ
                    params.put("queryHasDateTime", true);
                }
                if (BIGDECIMAL.equals(fieldType)) {
                    // ๆฅ่ฏข BigDecimal ็ฑปๅ
                    params.put("queryHasBigDecimal", true);
                }
                if ("between".equalsIgnoreCase(col.getQueryType())) {
                    // ๆทปๅ ๅฐBetweenๅ่กจไธญ
                    queryBetweenCols.add(listMap);
                }
                if (StringUtils.hasValue(col.getDictType())) {
                    // ๆทปๅ ๅฐๅญๅธๆฅ่ฏขๅ่กจไธญ
                    queryDictCols.add(listMap);
                }
                if (col.getIsOrder()) {
                    // ๅคๆญๆฏๅฆๆๆๅบ
                    params.put("queryHasOrder", true);
                    queryOrderCols.add(listMap);
                }
            }

            // ๆทปๅ ๅฐๅญๆฎตๅ่กจไธญ
            cols.add(listMap);
            // ๆทปๅ ้ไธป้ฎๅฐๅญๆฎตๅ่กจไธญ
            if (!isPk) {
                noPkCols.add(listMap);
            }
            // ๆทปๅ ้่ชๅจๆทปๅ ๅญๆฎต
            if (!isPk && !isAutoInsertField && !isAutoUpdateField && !isVersionField && !isLogicDeleteField) {
                noAutoFillCols.add(listMap);
            }
            // ๆทปๅ ๅ่กจๆพ็คบๅญๆฎต
            if (col.getIsList()) {
                listColumns.add(listMap);
            }
            // ๆทปๅ ๆทปๅ ใ็ผ่พ่กจๅๆพ็คบๅญๆฎต
            if (col.getIsEdit()) {
                editColumns.add(listMap);
            }
            // ๆทปๅ ๆทปๅ ใ็ผ่พ่กจๅๆพ็คบ้็ฉบๅญๆฎต
            if (col.getIsEdit() && col.getIsRequired()) {
                editNotNullColumns.add(listMap);
            }
            // ๆทปๅ ๅญๅธๅญๆฎต
            if (StringUtils.hasValue(col.getDictType())) {
                dictCols.add(listMap);
            }
        }

        // ไฟๅญๅญๆฎตๅ่กจ
        params.put("columns", cols);
        // ไฟๅญ้ไธป้ฎๅญๆฎตๅ่กจ
        params.put("noPkColumns", noPkCols);
        // ไฟๅญ้่ชๅจๆทปๅ ๅญๆฎตๅ่กจ
        params.put("noAutoFillColumns", noAutoFillCols);
        // ไฟๅญๆฅ่ฏขๅ่กจ
        params.put("queryColumns", queryCols);
        // ไฟๅญๅญๅธๅ่กจ
        params.put("dictColumns", dictCols);
        // ไฟๅญๅญๅธๆฅ่ฏขๅ่กจ
        params.put("queryDictColumns", queryDictCols);
        // ไฟๅญๆฅ่ฏขๆๅบๅ่กจ
        params.put("queryOrderColumns", queryOrderCols);
        // ไฟๅญๆฅ่ฏขBetweenๅ่กจ
        params.put("queryBetweenColumns", queryBetweenCols);
        // ไฟๅญ้็ฉบๅญๆฎตไฟกๆฏ
        params.put("notNullColumns", notNullColumns);
        // ไฟๅญๅ่กจๆพ็คบๅญๆฎตไฟกๆฏ
        params.put("listColumns", listColumns);
        // ไฟๅญๆทปๅ ใ็ผ่พ่กจๅๆพ็คบๅญๆฎตไฟกๆฏ
        params.put("editColumns", editColumns);
        // ไฟๅญๆทปๅ ใ็ผ่พ่กจๅๆพ็คบ้็ฉบๅญๆฎตไฟกๆฏ
        params.put("editNotNullColumns", editNotNullColumns);

        // ไฟๅญsql็idๅ่กจ
        List<String> ids = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ids.add(String.valueOf(IdWorker.getId()));
        }
        params.put("ids", ids);
        params.put("version", DateUtils.currentDateTimeStr());

        return params;
    }

    /**
     * SQLๅญๆฎต็ฑปๅๆ ๅฐไธบJava็ฑปๅ
     *
     * @param sqlType SQLๅญๆฎต็ฑปๅ
     * @return Java็ฑปๅ
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
     * ๅคๆญๆฏๅฆๆฏๆ ่กจ
     *
     * @param columns ๅญๆฎตๅ่กจ
     * @return true: ๆฏๆ ่กจ
     */
    public static boolean isTree(List<GenTableColumnDo> columns) {
        return Arrays.asList(TREE_ID_COL, TREE_NAME_COL, PARENT_ID_COL, SORT_ORDER_COL).stream()
                .allMatch(i -> columns.stream()
                        .map(GenTableColumnDo::getColumnName)
                        .anyMatch(n -> n.equals(i))
                );
    }

    /**
     * ๅคๆญๆฏๅฆๆฏ่ชๅจๅกซๅๅญๆฎต
     *
     * @param column ๅญๆฎต
     * @return true: ๆฏ่ชๅจๅกซๅๅญๆฎต
     */
    public static boolean isAutoFilledCol(GenTableColumnDo column) {
        boolean isAutoInsertField = StringUtils.contain(AUTO_INSERT_COLS, column.getColumnName());
        boolean isAutoUpdateField = StringUtils.contain(AUTO_UPDATE_COLS, column.getColumnName());
        boolean isVersionField = column.getColumnName().equals(VERSION_COL);
        boolean isLogicDeleteField = column.getColumnName().equals(LOGIC_DELETE_COL);
        return isAutoInsertField || isAutoUpdateField || isVersionField || isLogicDeleteField;
    }

    /**
     * ่ทๅๅ็ซฏไปฃ็ ๆจกๆฟ็ฑปๅ
     *
     * @return ๅ็งฐๅ่กจ
     */
    private static List<String> getAdminTemplateTypes(GenTableDo table) {
        List<String> types = new ArrayList<>();
        types.add("do");
        types.add("sqlMapper");
        types.add("dao");
        if (!table.getIsSub()) {
            // ๅญ่กจไป็ๆDAOๅฑ
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
     * ๅฎไนๅ็ซฏๆไปถ่ทฏๅพไปฅๅๅ็งฐ
     *
     * @param templateType ๆจกๆฟ็ฑปๅ
     * @param table        GenTableDoๅฏน่ฑก
     * @param className    ไปฃ็ ็ๆ็็ฑปๅ
     * @param rootPath     ๆ น็ฎๅฝ
     * @return ็ๆไปฃ็ ็ๆไปถ่ทฏๅพ
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

        throw new BusinessException(StringUtils.format("ไปฃ็ ็ๆ๏ผไธๆฏๆ็ๆจกๆฟ็ฑปๅ๏ผ{}", templateType));
    }

    /**
     * ่ทๅๅ็ซฏไปฃ็ ๆจกๆฟๅ็งฐ
     *
     * @return ๅ็งฐๅ่กจ
     */
    private static List<String> getFrontTemplateTypes(GenTableDo table) {
        List<String> types = new ArrayList<>();
        if (table.getIsSub()) {
            // ๅญ่กจไธ็ๆๅ็ซฏ
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
     * ๅฎไนๆไปถ่ทฏๅพไปฅๅๅ็งฐ
     *
     * @param templateType ๆจกๆฟ็ฑปๅ
     * @param table        GenTableDoๅฏน่ฑก
     * @param className    ไปฃ็ ็ๆ็็ฑปๅ
     * @param rootPath     ๆ น็ฎๅฝ
     * @return ็ๆไปฃ็ ็ๆไปถ่ทฏๅพ
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

        throw new BusinessException(StringUtils.format("ไปฃ็ ็ๆ๏ผไธๆฏๆ็ๆจกๆฟ็ฑปๅ๏ผ{}", templateType));
    }

    /**
     * ่ทๅFreemarker้็ฝฎ
     *
     * @param resource ๆจกๆฟๆไปถ
     * @return Configuration
     */
    private static Configuration getFreemarkerCfg() {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_31);
        cfg.setLocalizedLookup(false);
        cfg.setDefaultEncoding(FoundiConst.DEFAULT_CHARSET);
        cfg.setTemplateLoader(new ClassTemplateLoader(TemplateHelper.class, TEMPLATE_PATH));
        return cfg;
    }

    /**
     * ่ทๅFreemarkerๆจกๆฟ
     *
     * @param resource ๆจกๆฟๆไปถ
     * @return Template
     */
    private static Template getTemplate(Configuration cfg, String resource) {
        try {
            return cfg.getTemplate(resource);
        } catch (IOException e) {
            throw new BusinessException(StringUtils.format("ไปฃ็ ็ๆ๏ผๆ ๆณๆพๅฐไปฃ็ ๆจกๆฟ๏ผๆๆจกๆฟๆ ผๅผๆ่ฏฏ๏ผ{}", resource), e);
        }
    }

    /**
     * ไปฃ็ ็ๆไธบๅญ็ฌฆไธฒ
     *
     * @param template   Freemarker Template
     * @param bindingMap ๆจกๆฟๅๆฐ
     * @return ๅญ็ฌฆไธฒ
     */
    private static String renderToString(Template template, Map<?, ?> bindingMap) {
        StringWriter writer = new StringWriter();
        try {
            template.process(bindingMap, writer);
        } catch (TemplateException | IOException e) {
            throw new BusinessException("ไปฃ็ ็ๆ๏ผไปฃ็ ็ๆๅคฑ่ดฅ", e);
        }
        return writer.toString();
    }

    /**
     * ็ๆไปฃ็ ๅญ็ฌฆไธฒ
     *
     * @param cfg        Freemarker้็ฝฎ
     * @param resource   ๆจกๆฟๆไปถ
     * @param bindingMap ๆจกๆฟๅๆฐ
     * @return ๅญ็ฌฆไธฒ
     */
    private static String renderToString(Configuration cfg, String resource, Map<?, ?> bindingMap) {
        StringWriter writer = new StringWriter();
        try {
            getTemplate(cfg, resource).process(bindingMap, writer);
        } catch (TemplateException | IOException e) {
            throw new BusinessException("ไปฃ็ ็ๆ๏ผไปฃ็ ็ๆๅคฑ่ดฅ", e);
        }
        return writer.toString();
    }

    /**
     * ็ๆไปฃ็ ๆไปถ
     *
     * @param fileName  ๆไปถ่ทฏๅพ
     * @param template  Freemarker Template
     * @param params    ๆจกๆฟๅๆฐ
     * @param overwrite ๆฏๅฆ่ฆ็ๆงๆไปถ
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
     * ่ทๅๆ ็นไนๅ็ๅญๅญ็ฌฆไธฒ
     *
     * @param strWithPunc ๅๅญ็ฌฆไธฒ
     * @return ๅญๅญ็ฌฆไธฒ
     */
    private static String beforePunc(String strWithPunc) {
        List<Character> puncList = Arrays.asList(' ' , '(' , ',' , '-' , '_' , ':' , '๏ผ' , '๏ผ' , '๏ผ' , 'โ' );
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
