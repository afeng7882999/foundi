/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.monitor.service;

import net.foundi.admin.monitor.entity.vo.ServerVo;

/**
 * 服务器监控信息Service
 *
 * @author Afeng (afeng7882999@163.com)
 */
public interface ServerService {

    /**
     * 获取服务器信息
     *
     * @return ServerVo对象
     */
    ServerVo getServerInfo();
}
