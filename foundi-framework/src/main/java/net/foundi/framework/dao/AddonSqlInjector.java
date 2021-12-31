package net.foundi.framework.dao;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.extension.injector.methods.AlwaysUpdateSomeColumnById;
import com.baomidou.mybatisplus.extension.injector.methods.InsertBatchSomeColumn;
import com.baomidou.mybatisplus.extension.injector.methods.LogicDeleteByIdWithFill;

import java.util.List;

public class AddonSqlInjector extends DefaultSqlInjector {

    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass, TableInfo tableInfo) {
        List<AbstractMethod> methodList = super.getMethodList(mapperClass, tableInfo);
        // 批量新增数据，MySQL
        methodList.add(new InsertBatchSomeColumn());
        // 固定更新某些字段(不包含逻辑删除字段)
        methodList.add(new AlwaysUpdateSomeColumnById());
        // 逻辑删除并填充某些字段
        methodList.add(new LogicDeleteByIdWithFill());
        return methodList;
    }
}
