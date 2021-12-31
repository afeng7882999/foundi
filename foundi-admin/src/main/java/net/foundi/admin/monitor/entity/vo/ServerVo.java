/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.monitor.entity.vo;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;

/**
 * 服务器信息VO
 *
 * @author Afeng (afeng7882999@163.com)
 */
@Data
public class ServerVo {

    /** CPU相关信息 */
    private Cpu cpu = new Cpu();

    /** JVM相关信息 */
    private Jvm jvm = new Jvm();

    /** 內存相关信息 */
    private Memory memory = new Memory();

    /** 系统相关信息 */
    private System system = new System();

    /** 磁盘相关信息 */
    private List<Disk> disks = new LinkedList<>();

    /**
     * CPU实体类
     */
    @Data
    public static class Cpu {

        /** 核心数 */
        private int logicalCount;

        /** CPU总的使用率 */
        private double totalUsage;

        /** CPU系统使用率 */
        private double systemUsage;

        /** CPU用户使用率 */
        private double userUsage;

        /** CPU当前等待率 */
        private double ioWait;

        /** CPU当前空闲率 */
        private double idle;
    }

    /**
     * JVM实体类
     */
    @Data
    public static class Jvm {

        /** 当前JVM占用的内存总数(M) */
        private double totalMemory;

        /** JVM最大可用内存总数(M) */
        private double maxMemory;

        /** JVM空闲内存(M) */
        private double freeMemory;

        /** JDK版本 */
        private String version;

        /** JDK路径 */
        private String home;

        /** JVM启动时间 */
        private String startTime;

        /** JVM运行时间 */
        private String runningTime;
    }

    /**
     * 内存实体类
     */
    @Data
    public static class Memory {

        /** 内存总量 */
        private double total;

        /** 已用内存 */
        private double used;

        /** 剩余内存 */
        private double available;
    }

    /**
     * 系统实体类
     */
    @Data
    public static class System {

        /** 服务器名称 */
        private String computerName;

        /** 服务器IP */
        private String computerIp;

        /** 项目路径 */
        private String userDir;

        /** 操作系统 */
        private String osName;

        /** 系统架构 */
        private String osArchitecture;
    }

    /**
     * 磁盘实体类
     */
    @Data
    public static class Disk {

        /** 盘符路径 */
        private String mount;

        /** 文件系统类型 */
        private String type;

        /** 文件系统名称 */
        private String name;

        /** 总大小 */
        private String total;

        /** 剩余大小 */
        private String free;

        /** 已经使用量 */
        private String used;

        /** 使用率 */
        private double usage;
    }

}
