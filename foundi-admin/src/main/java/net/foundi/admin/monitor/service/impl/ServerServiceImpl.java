/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.monitor.service.impl;

import net.foundi.admin.monitor.entity.vo.ServerVo;
import net.foundi.admin.monitor.service.ServerService;
import net.foundi.common.utils.lang.ByteUtils;
import net.foundi.common.utils.lang.DateUtils;
import net.foundi.common.utils.lang.MathUtils;
import net.foundi.common.utils.web.IPUtils;
import org.springframework.stereotype.Service;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;
import oshi.software.os.OperatingSystem;
import oshi.util.Util;

import java.lang.management.ManagementFactory;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import static net.foundi.common.constant.FoundiConst.GB;
import static net.foundi.common.constant.FoundiConst.MB;

/**
 * 服务器信息Service实现
 *
 * @author Afeng (afeng7882999@163.com)
 */
@Service
public class ServerServiceImpl implements ServerService {

    private static final long OSHI_WAIT_SECOND = 1000L;

    @Override
    public ServerVo getServerInfo() {
        ServerVo result = new ServerVo();

        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();

        this.setCpu(result, hal.getProcessor());
        this.setMemory(result, hal.getMemory());
        this.setSystem(result);
        this.setJvm(result);
        this.setDisks(result, si.getOperatingSystem());

        return result;
    }

    /**
     * 设置CPU信息
     *
     * @param serverVo  ServerVo对象
     * @param processor CentralProcessor对象
     */
    private void setCpu(ServerVo serverVo, CentralProcessor processor) {
        long[] prevTicks = processor.getSystemCpuLoadTicks();
        Util.sleep(OSHI_WAIT_SECOND);
        long[] ticks = processor.getSystemCpuLoadTicks();
        long nice = ticks[CentralProcessor.TickType.NICE.getIndex()] - prevTicks[CentralProcessor.TickType.NICE.getIndex()];
        long irq = ticks[CentralProcessor.TickType.IRQ.getIndex()] - prevTicks[CentralProcessor.TickType.IRQ.getIndex()];
        long softIrq = ticks[CentralProcessor.TickType.SOFTIRQ.getIndex()] - prevTicks[CentralProcessor.TickType.SOFTIRQ.getIndex()];
        long steal = ticks[CentralProcessor.TickType.STEAL.getIndex()] - prevTicks[CentralProcessor.TickType.STEAL.getIndex()];
        long system = ticks[CentralProcessor.TickType.SYSTEM.getIndex()] - prevTicks[CentralProcessor.TickType.SYSTEM.getIndex()];
        long user = ticks[CentralProcessor.TickType.USER.getIndex()] - prevTicks[CentralProcessor.TickType.USER.getIndex()];
        long ioWait = ticks[CentralProcessor.TickType.IOWAIT.getIndex()] - prevTicks[CentralProcessor.TickType.IOWAIT.getIndex()];
        long idle = ticks[CentralProcessor.TickType.IDLE.getIndex()] - prevTicks[CentralProcessor.TickType.IDLE.getIndex()];
        long total = user + nice + system + idle + ioWait + irq + softIrq + steal;
        serverVo.getCpu().setLogicalCount(processor.getLogicalProcessorCount());
        serverVo.getCpu().setSystemUsage(MathUtils.round((double) system / total * 100, 2));
        serverVo.getCpu().setUserUsage(MathUtils.round((double) user / total * 100, 2));
        serverVo.getCpu().setIoWait(MathUtils.round((double) ioWait / total * 100, 2));
        double cpuIdle = MathUtils.round((double) idle / total * 100, 2);
        serverVo.getCpu().setIdle(cpuIdle);
        serverVo.getCpu().setTotalUsage(100D - cpuIdle);
    }

    /**
     * 设置内存信息
     *
     * @param serverVo ServerVo对象
     * @param memory   GlobalMemory对象
     */
    private void setMemory(ServerVo serverVo, GlobalMemory memory) {
        serverVo.getMemory().setTotal(MathUtils.round((double) memory.getTotal() / GB, 2));
        long used = memory.getTotal() - memory.getAvailable();
        serverVo.getMemory().setUsed(MathUtils.round((double) used / GB, 2));
        serverVo.getMemory().setAvailable(MathUtils.round((double) memory.getAvailable() / GB, 2));
    }

    /**
     * 设置服务器系统信息
     *
     * @param serverVo ServerVo对象
     */
    private void setSystem(ServerVo serverVo) {
        Properties props = System.getProperties();
        serverVo.getSystem().setComputerName(IPUtils.getHostName());
        serverVo.getSystem().setComputerIp(IPUtils.getHostIp());
        serverVo.getSystem().setOsName(props.getProperty("os.name"));
        serverVo.getSystem().setOsArchitecture(props.getProperty("os.arch"));
        serverVo.getSystem().setUserDir(props.getProperty("user.dir"));
    }

    /**
     * 设置Java虚拟机
     *
     * @param serverVo ServerVo对象
     */
    private void setJvm(ServerVo serverVo) {
        Properties props = System.getProperties();
        serverVo.getJvm().setTotalMemory(MathUtils.div(Runtime.getRuntime().totalMemory(), MB, 2));
        serverVo.getJvm().setMaxMemory(MathUtils.div(Runtime.getRuntime().maxMemory(), MB, 2));
        serverVo.getJvm().setFreeMemory(MathUtils.div(Runtime.getRuntime().freeMemory(), MB, 2));
        serverVo.getJvm().setVersion(props.getProperty("java.version"));
        serverVo.getJvm().setHome(props.getProperty("java.home"));
        long start = ManagementFactory.getRuntimeMXBean().getStartTime();
        serverVo.getJvm().setStartTime(DateUtils.dateTimeStr(DateUtils.of(start)));
        serverVo.getJvm().setRunningTime(DateUtils.millisFormatStr(System.currentTimeMillis() - start));
    }

    /**
     * 设置磁盘信息
     *
     * @param serverVo ServerVo对象
     * @param os       OperatingSystem对象
     */
    private void setDisks(ServerVo serverVo, OperatingSystem os) {
        FileSystem fileSystem = os.getFileSystem();
        List<OSFileStore> fsArray = fileSystem.getFileStores();
        for (OSFileStore fs : fsArray) {
            long free = fs.getUsableSpace();
            long total = fs.getTotalSpace();
            long used = total - free;
            ServerVo.Disk disk = new ServerVo.Disk();
            disk.setMount(fs.getMount());
            disk.setType(fs.getType());
            disk.setName(fs.getName());
            disk.setTotal(ByteUtils.sizeToStr(total));
            disk.setFree(ByteUtils.sizeToStr(free));
            disk.setUsed(ByteUtils.sizeToStr(used));
            disk.setUsage(MathUtils.round((double) used / total * 100, 2));
            serverVo.getDisks().add(disk);
        }
    }

    public static void main(String... args) {
        ServerService serverService = new ServerServiceImpl();
        ServerVo serverVo = serverService.getServerInfo();
        System.out.println(serverVo);
    }
}
