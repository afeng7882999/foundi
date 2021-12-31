/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.framework.service;

import net.foundi.framework.entity.domain.Log;
import net.foundi.framework.entity.domain.Role;
import net.foundi.framework.security.enums.AuthcType;
import net.foundi.framework.security.model.UserContext;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户服务接口，需要下级包实现
 *
 * @author Afeng (afeng7882999@163.com)
 */
public interface AccountService {

    UserContext getUserContextByUsername(String username);

    List<Long> getSubGroupIdsByGroupId(Long groupId);

    List<Long> getGroupIdsByRoles(List<Role> roles);

    void logLogin(String username, String ip, String address, String browser, String os, String message,
                  AuthcType authcType, String type, String status, LocalDateTime time);

    void logOperation(Log log);

}
