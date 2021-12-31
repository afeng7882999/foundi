/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.generator.runner;

import net.foundi.admin.generator.entity.domain.GenTableColumnDo;
import net.foundi.admin.generator.entity.domain.GenTableDo;
import net.foundi.admin.generator.service.GenTableColumnService;
import net.foundi.admin.generator.service.GenTableService;
import net.foundi.admin.generator.utils.TemplateHelper;
import net.foundi.common.utils.lang.StringUtils;
import net.foundi.framework.service.config.ServiceConst;
import org.springframework.beans.factory.config.YamlMapFactoryBean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 命令行代码生成器
 *
 * @author Afeng (afeng7882999@163.com)
 */
@Component
public class GeneratorRunner implements CommandLineRunner {

    private final GenTableService genTableService;
    private final GenTableColumnService genTableColumnService;

    public GeneratorRunner(GenTableService genTableService, GenTableColumnService genTableColumnService) {
        this.genTableService = genTableService;
        this.genTableColumnService = genTableColumnService;
    }

    @Override
    public void run(String... args) {
        this.generate();
    }

    @SuppressWarnings("rawtypes")
    private void generate() {
        try {
            YamlMapFactoryBean yaml = new YamlMapFactoryBean();
            yaml.setResources(new ClassPathResource("net/foundi/admin/generator/runner/config.yml"));
            Map<String, Object> props = yaml.getObject();
            assert props != null;

            Boolean enabled = (Boolean) props.get("enabled");
            if (!enabled) {
                return;
            }

            System.out.println(StringUtils.withPrefix("运行代码生成器..."));
            System.out.println("-------------------- 开始生成 -------------------");

            for (Object table : (List) props.get("tables")) {
                Map map = (Map) table;
                GenTableDo tab = genTableService.getOneFromDb(map.get("tableName").toString());
                assert tab != null;
                tab.setPack(props.get("pack").toString());
                tab.setModule(map.get("module").toString());
                tab.setFrontPath(props.get("fontPath").toString());
                tab.setAuthor(props.get("author").toString());
                tab.setEntityName(Optional.ofNullable(map.get("entityName")).map(Object::toString).orElse(""));
                tab.setIsSub(map.get("isSub") != null && map.get("isSub").toString().equalsIgnoreCase("true"));
                List queryCols = (List) map.get("queryCols");
                List<GenTableColumnDo> cols = genTableColumnService.listFromDb(map.get("tableName").toString());
                initGenTableDo(tab, cols);
                int idx = 0;
                for (GenTableColumnDo col : cols) {
                    col.setIsEdit(true);
                    col.setIsList(true);
                    col.setSort(idx++);
                    if (queryCols != null) {
                        for (Object queryCol : queryCols) {
                            Map queryColMap = (Map) queryCol;
                            if (col.getColumnName().equals(queryColMap.get("name").toString())) {
                                col.setQueryType(queryColMap.get("type").toString());
                            }
                        }
                    }
                }
                System.out.println("生成表\"" + tab.getTableName() + "\"的代码...");
                TemplateHelper.generateFile(cols, tab, props.get("targetDir").toString(), true);

                System.out.println("-------------------- 生成完毕 -------------------");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initGenTableDo(GenTableDo table, List<GenTableColumnDo> columns) {
        if (TemplateHelper.isTree(columns)) {
            // 由是否包含特定列，确定是否是树表
            table.setIsTree(true);
            table.setTreeName(TemplateHelper.TREE_NAME_FIELD);
            table.setTreeId(TemplateHelper.TREE_ID_FIELD);
            table.setTreeParentId(TemplateHelper.PARENT_ID_FIELD);
            table.setTreeSort(TemplateHelper.SORT_ORDER_FIELD);
        } else {
            table.setIsTree(false);
        }
        table.setMenuTitle(table.getTableComment());
        table.setMenuParentId(ServiceConst.TREE_ROOT_ID);
    }

}
