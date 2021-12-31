/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.framework.web.config;

/**
 * Web常量
 *
 * @author Afeng (afeng7882999@163.com)
 */
public class WebConst {

    //request

    //encrypt header key
    public static final String ENCRYPT_HEADER = "Encrypt";

    //encrypt request params key
    public static final String ENCRYPT_PARAMS_KEY = "enc";

    //current page
    public static final String CURRENT = "current";

    //page size
    public static final String SIZE = "size";

    //default record number of page
    public static final Long DEFAULT_SIZE = 20L;


    //response

    //code for business
    public static final String BUSINESS_CODE = "bz_code";
    public static final Integer BUSINESS_SUCCESS = 1;
    public static final Integer BUSINESS_FAIL = 0;

    //additional message
    public static final String MESSAGE = "msg";

    //exception
    public static final String EXCEPTION = "ex";

    //default key of data
    public static final String DEFAULT_DATA_KEY = "content";

    //default key for page
    public static final String PAGE = "page";

    //default key for token
    public static final String TOKEN= "token";

}
