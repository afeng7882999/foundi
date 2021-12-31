/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.framework.entity.dto;

import net.foundi.common.utils.lang.CollectionUtils;
import net.foundi.framework.service.config.ServiceConst;

import java.util.*;

/**
 * 树状DTO基类
 *
 * @author Afeng (afeng7882999@163.com)
 */
public class TreeDto<T extends TreeDto<T>> implements Dto {

    private static final long serialVersionUID = -528850667799292187L;

    private Long id;
    private Long parentId;
    private String parentPath;
    private Integer sort;
    private Integer level;
    private List<T> children;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getParentPath() {
        return parentPath;
    }

    public void setParentPath(String parentPath) {
        this.parentPath = parentPath;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public List<T> getChildren() {
        return children;
    }

    public void setChildren(List<T> children) {
        this.children = children;
    }

    /**
     * 将DTO列表转化为前端需要的树状形式
     * 填充children、parentPath、level字段
     *
     * @param dtos DTO列表
     * @return 转化后的DTO
     */
    public static <T extends TreeDto<T>> List<T> buildTree(List<T> dtos) {
        if (CollectionUtils.isEmpty(dtos)) {
            return Collections.emptyList();
        }
        //list to tree
        List<T> roots = new ArrayList<>();
        for (T current : dtos) {
            //root node
            if (current.getId().equals(ServiceConst.TREE_ROOT_ID)) {
                roots.add(current);
                continue;
            }
            boolean hasParent = false;
            for (T test : dtos) {
                if (test.getId().equals(current.getParentId())) {
                    if (test.getChildren() == null) {
                        test.setChildren(new ArrayList<T>());
                    }
                    test.getChildren().add(current);
                    ((List<T>) test.getChildren()).sort(Comparator.comparing(d ->
                            d.getSort() != null ? d.getSort() : Integer.MAX_VALUE));
                    hasParent = true;
                    break;
                }
            }
            if (!hasParent) {
                roots.add(current);
            }
            roots.sort(Comparator.comparing(d ->
                    d.getSort() != null ? d.getSort() : Integer.MAX_VALUE));
        }
        //traverse tree to add parentPath and level
        Stack<T> stack = new Stack<>();
        for (T root : roots) {
            root.setParentPath(root.getParentId() + ",");
            root.setLevel(0);
            stack.push(root);
        }
        while (!stack.isEmpty()) {
            T current = stack.pop();
            Integer level = current.getLevel();
            String parentPath = current.getParentPath();
            List<T> children = current.getChildren();
            if (children != null) {
                for (T child : children) {
                    child.setParentPath(parentPath + child.getParentId() + ",");
                    child.setLevel(level + 1);
                    stack.push(child);
                }
            }
        }
        return roots;
    }
}
