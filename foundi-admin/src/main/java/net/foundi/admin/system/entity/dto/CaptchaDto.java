/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.system.entity.dto;

import lombok.Data;
import net.foundi.framework.entity.dto.Dto;

/**
 * 验证码VO
 *
 * @author Afeng (afeng7882999@163.com)
 */
@Data
public class CaptchaDto implements Dto {

    private static final long serialVersionUID = -187994405955350202L;

    /** 验证码ID */
    private String uuid;

    /** 验证码图片BASE64编码 */
    private String img;

    /** 附加信息 */
    private String extra;
}
