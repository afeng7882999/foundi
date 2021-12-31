/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.common.utils.file;

import net.foundi.common.utils.base.IDUtils;
import net.foundi.common.utils.crypto.BASE64;
import net.foundi.common.utils.lang.CollectionUtils;
import net.foundi.common.utils.lang.DateUtils;
import net.foundi.common.utils.lang.StringUtils;
import org.apache.tika.Tika;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 文件操作工具类
 *
 * @author Afeng (afeng7882999@163.com)
 */
public class FileUtils {

    public static String FOLDER_TYPE = "directory" ;

    private static final Tika tika = new Tika();

    /**
     * 创建一个空文件
     *
     * @param path 文件路径
     */
    public static void createFile(Path path) throws IOException {
        Files.createFile(path);
    }

    /**
     * 创建一个空文件夹，以及不存在的上级文件夹
     *
     * @param path 文件夹路径
     */
    public static void createFolder(Path path) throws IOException {
        Files.createDirectories(path);
    }

    /**
     * 删除一个存在的文件
     *
     * @param path 文件路径
     */
    public static void deleteFile(Path path) throws IOException {
        if (!isFolder(path)) {
            Files.deleteIfExists(path);
        }
    }

    /**
     * 删除一个存在的文件夹
     *
     * @param path 文件夹路径
     */
    public static void deleteFolder(Path path) throws IOException {
        deleteFolder(path, true);
    }

    /**
     * 删除一个存在的文件夹，首先判断是否为空
     *
     * @param path          文件夹路径
     * @param deleteNoEmpty true：删除非空文件夹、空文件夹，false：仅删除空文件夹
     */
    public static void deleteFolder(Path path, boolean deleteNoEmpty) throws IOException {
        if (isFolder(path)) {
            if (deleteNoEmpty) {
                List<Path> listChildren = listChildren(path);
                for (Path children : listChildren) {
                    if (isFolder(children)) {
                        deleteFolder(children, true);
                    } else {
                        deleteFile(children);
                    }
                }
            }
            Files.deleteIfExists(path);
        }
    }

    /**
     * 判断一个文件是否存在
     *
     * @param path 文件路径
     */
    public static boolean exists(Path path) {
        return Files.exists(path);
    }

    /**
     * 获取文件修改时间
     *
     * @param path 文件路径
     * @return 文件修改时间，单位毫秒
     */
    public static long getLastModified(Path path) throws IOException {
        return Files.getLastModifiedTime(path).toMillis();
    }

    /**
     * 获取文件访问时间
     *
     * @param path 文件路径
     * @return 文件访问时间，单位毫秒
     */
    public static long getLastAccessTime(Path path) throws IOException {
        BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);
        return attr.lastAccessTime().toMillis();
    }

    /**
     * 分析文件内容，获取文件的MIME信息
     *
     * @param path 文件路径
     * @return MIME信息
     */
    public static String getMimeType(Path path) throws IOException {
        if (FileUtils.isFolder(path)) {
            return FOLDER_TYPE;
        }
        return tika.detect(path);
    }

    /**
     * 分析文件开始部分内容、文件名，获取MIME信息
     *
     * @param someBytes 文件开始部分的字节数组
     * @param filename  文件名
     * @return MIME信息
     */
    public static String getMimeType(byte[] someBytes, String filename) throws IOException {
        return tika.detect(someBytes, filename);
    }

    /**
     * 分析文件文件名，获取MIME信息
     *
     * @param filename 文件名
     * @return MIME信息
     */
    public static String getMimeType(String filename) throws IOException {
        return tika.detect(filename);
    }

    /**
     * 获取Path对象中的文件名、文件夹名
     *
     * @param path Path对象
     * @return 文件名、文件夹名
     */
    public static String getName(Path path) {
        return path.getFileName().toString();
    }

    /**
     * 获取完整路径中的文件名、文件夹名
     *
     * @param pathName 完整路径
     * @return 文件名、文件夹名
     */
    public static String getName(String pathName) {
        if (StringUtils.isEmpty(pathName)) {
            return "" ;
        } else {
            return getName(Paths.get(pathName));
        }
    }

    /**
     * 获取Path对象的父目录，没有父目录返回null
     *
     * @param path Path对象
     * @return 父目录的Path对象
     */
    public static Path getParent(Path path) {
        return path.getParent();
    }

    /**
     * 对于根目录，获取Path的相对路径名称
     *
     * @param rootPath 根目录
     * @param path     检测目录
     * @return 相对路径名称
     */
    public static String getRelativePath(Path rootPath, Path path) throws IOException {
        String relativePath = "" ;
        // to avoid java.nio.file.NoSuchFileException lets use the absolute path string
        // to check if the given paths are the same.
        String r = rootPath.toString().trim();
        String p = path.toString().trim();
        if (!p.equalsIgnoreCase(r) && p.startsWith(r)) {
            relativePath = path.subpath(rootPath.getNameCount(), path.getNameCount()).toString();
        }
        return relativePath;
    }

    /**
     * 判断一个文件夹是否是另一个的子文件夹
     *
     * @param testFolder   检测文件夹
     * @param targetFolder 目标文件夹
     * @return true：是子文件夹
     */
    public static boolean isDescendantFolder(Path testFolder, Path targetFolder) {
        return isFolder(testFolder) && isFolder(targetFolder) && testFolder.startsWith(targetFolder);
    }

    /**
     * 获取指定文件夹的大小
     *
     * @param path      文件夹
     * @param recursive true：包含子文件夹，false：不包含子文件夹
     * @return 文件夹大小，返回long类型
     */
    public static long getTotalSizeInBytes(Path path, boolean recursive) throws IOException {
        if (isFolder(path) && recursive) {
            FileTreeSize fileTreeSize = new FileTreeSize();
            Files.walkFileTree(path, fileTreeSize);
            return fileTreeSize.getTotalSize();
        }
        return Files.size(path);
    }

    /**
     * 判断一个路径是否是文件夹
     *
     * @param path 路径
     * @return true： 路径是文件夹，false：不是文件夹或不存在
     */
    public static boolean isFolder(Path path) {
        return Files.isDirectory(path);
    }

    /**
     * 判断两个路径是否指向一个文件
     *
     * @param path1 路径1
     * @param path2 路径2
     * @return true：指向同一个文件
     */
    public static boolean isSame(Path path1, Path path2) throws IOException {
        return Files.isSameFile(path1, path2);
    }

    /**
     * 判断文件夹下是否有文件，包括下级文件夹
     *
     * @param dir 文件夹
     * @return true：文件夹、下级文件夹包含文件
     */
    public static boolean hasChildFolder(Path dir) throws IOException {
        if (isFolder(dir)) {

            // directory filter
            DirectoryStream.Filter<Path> filter = new DirectoryStream.Filter<Path>() {
                @Override
                public boolean accept(Path path) {
                    return Files.isDirectory(path);
                }
            };

            try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(dir, filter)) {
                return directoryStream.iterator().hasNext();
            }
        }
        return false;
    }

    /**
     * 获取子文件夹与文件的列表
     *
     * @param dir 文件夹
     * @return 子文件夹与文件的列表
     */
    public static List<Path> listChildren(Path dir) throws IOException {
        return listChildren(dir, null);
    }

    /**
     * 获取子文件夹与文件的列表，不包括隐藏项目
     *
     * @param dir 文件夹
     * @return 子文件夹与文件的列表
     */
    public static List<Path> listChildrenNotHidden(Path dir) throws IOException {
        // not hidden file filter
        DirectoryStream.Filter<Path> notHiddenFilter = path -> !Files.isHidden(path);
        return listChildren(dir, notHiddenFilter);
    }

    /**
     * 获取子文件夹与文件的列表，使用过滤器filter
     *
     * @param dir    文件夹
     * @param filter 过滤器
     * @return 子文件夹与文件的列表
     */
    public static List<Path> listChildren(Path dir, DirectoryStream.Filter<? super Path> filter) throws IOException {
        if (isFolder(dir)) {
            List<Path> list = new ArrayList<>();
            try (DirectoryStream<Path> directoryStream = (filter != null ? Files.newDirectoryStream(dir, filter) : Files.newDirectoryStream(dir))) {
                for (Path p : directoryStream) {
                    list.add(p);
                }
            }
            return Collections.unmodifiableList(list);
        }
        return Collections.emptyList();
    }

    /**
     * 打开文件的InputStream
     *
     * @param path 文件
     * @return InputStream
     */
    public static InputStream openInputStream(Path path) throws IOException {
        return Files.newInputStream(path);
    }

    /**
     * 打开或创建一个文件，返回OutputStream
     *
     * @param path 文件
     * @return OutputStream
     */
    public static OutputStream openOutputStream(Path path) throws IOException {
        return Files.newOutputStream(path);
    }

    /**
     * 重命名文件、文件夹
     *
     * @param origin  源文件、文件夹
     * @param newName 新名称
     */
    public static void rename(Path origin, String newName) throws IOException {
        Path parent = origin.getParent();
        Path destination = Paths.get(parent.toString() + "/" + newName);
        Files.move(origin, destination, StandardCopyOption.REPLACE_EXISTING);
    }

    /**
     * 移动文件、文件夹
     * 如：d:/download/file.zip -> d:/files/001.zip，d:/download/file/ -> d:/download/file01/
     *
     * @param origin      源文件、文件夹
     * @param destination 目标文件、文件夹
     */
    public static void move(Path origin, Path destination) throws IOException {
        Files.move(origin, destination, StandardCopyOption.REPLACE_EXISTING);
    }

    /**
     * 复制文件、文件夹，不包括文件夹下的内容
     *
     * @param origin      源文件、文件夹
     * @param destination 目标文件、文件夹
     */
    public static void copy(Path origin, Path destination) throws IOException {
        Files.copy(origin, destination, StandardCopyOption.REPLACE_EXISTING);
    }

    /**
     * 复制文件、文件夹，包括文件夹下的所有内容
     *
     * @param origin      源文件、文件夹
     * @param destination 目标文件、文件夹
     */
    public static void copyDirectory(Path origin, Path destination) throws IOException {
        Files.walkFileTree(origin, new DirectoryCopy(destination));
    }

    /**
     * 搜索文件夹
     *
     * @param path       文件夹
     * @param target     目标文件名
     * @param mode       匹配模式，EXACT：文件名完全相等，ANYWHERE：文件名部分匹配
     * @param ignoreCase 是否考虑大小写
     * @return 找到的文件列表
     */
    public static List<Path> search(Path path, String target, FileTreeSearch.MatchMode mode, boolean ignoreCase) throws IOException {
        if (isFolder(path)) {
            FileTreeSearch fileTreeSearch = new FileTreeSearch(target, mode, ignoreCase);
            Files.walkFileTree(path, fileTreeSearch);

            List<Path> paths = fileTreeSearch.getFoundPaths();
            return Collections.unmodifiableList(paths);
        }
        throw new IllegalArgumentException("参数path不是文件夹");
    }

    /**
     * 搜索文件夹，文件名模糊匹配
     *
     * @param path   文件夹
     * @param target 目标文件名
     * @return 找到的文件列表
     */
    public static List<Path> search(Path path, String target) throws IOException {
        return search(path, target, FileTreeSearch.MatchMode.ANYWHERE, true);
    }

    /**
     * 随机生成文件名
     *
     * @param filename 原文件名
     * @return 生成的文件名
     */
    public static String randomFilename(String filename) {
        if (StringUtils.isEmpty(filename)) {
            return "" ;
        }
        String name = getName(Paths.get(filename));
        String ext = "" ;
        int idx = name.lastIndexOf('.' );
        if (idx > 0) {
            ext = "." + name.substring(idx + 1);
            name = name.substring(0, idx);
        }
        return IDUtils.genMillisId(name + "-", "") + ext;
    }

    /**
     * 判断路径名是否合法
     *
     * @param pathName 路径名
     * @return true：合法
     */
    public static Boolean isInvalidPath(String pathName) {
        return pathName.chars()
                .mapToObj(c -> (char) c)
                .anyMatch(c -> (c < ' ' ) || ("<>:\"|?*".indexOf(c) != -1));
    }

    /**
     * 获取扩展名
     *
     * @param filename 文件名
     * @return 扩展名
     */
    public static String getExtName(String filename) {
        if (StringUtils.isEmpty(filename)) {
            return "" ;
        }
        String ext = "" ;
        int idx = filename.lastIndexOf('.' );
        if (idx > 0) {
            ext = filename.substring(idx + 1);
        }
        return ext.toLowerCase();
    }

    /**
     * 去掉扩展名
     *
     * @param filename 文件名
     * @return 不包括扩展名的文件名
     */
    public static String getBaseName(String filename) {
        if (StringUtils.isEmpty(filename)) {
            return "" ;
        }
        String name = getName(Paths.get(filename));
        int idx = name.lastIndexOf('.' );
        if (idx > 0) {
            name = name.substring(0, idx);
        }
        return name;
    }

    /**
     * 获取上级目录列表
     * 如："123/345/wx.jpg" -> "123/", "123/345/"
     *
     * @param pathName 路径名
     * @return 上级文件夹列表
     */
    public static List<String> getParentPaths(String pathName) {
        ArrayList<String> result = new ArrayList<>();
        if (StringUtils.isEmpty(pathName)) {
            return result;
        }
        String[] parts = pathName.split("/");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < parts.length - 1; i++) {
            if (StringUtils.hasValue(parts[i])) {
                sb.append(parts[i]).append("/");
                result.add(sb.toString());
            }
        }
        return result;
    }

    /**
     * 判断是否是文件夹
     *
     * @param pathName 路径名
     * @return true：是文件夹
     */
    public static boolean isFolderByName(String pathName) {
        return pathName.endsWith("/");
    }

    /**
     * 根据文件名生成文件夹名
     *
     * @param fileName 文件名
     * @return 文件夹名
     */
    public static String generatePathNameByFileName(String fileName) {
        String format = DateUtils.currentDateStr();
        int hashCode = fileName.hashCode();
        String dir1 = Integer.toHexString(hashCode & 0XF);
        String dir2 = Integer.toHexString((hashCode >> 4) & 0XF);
        return format + "/" + dir1 + "/" + dir2;
    }

    /**
     * 根据时间生成文件夹名
     *
     * @return 文件夹名
     */
    public static String generatePathNameByTime() {
        return DateUtils.currentDateStr().replaceAll("-", "");
    }

    /**
     * 格式化路径名，'\' 替换为 '/', 结尾添加 '/'
     *
     * @param pathName 路径名
     * @return 处理后的路径名
     */
    public static String formatPathName(String pathName) {
        if (pathName.endsWith("/") || pathName.endsWith("\\")) {
            return pathName.replaceAll("\\\\", "/");
        }
        // 没有分隔符结尾，确定是文件夹还是文件
        Path path = Paths.get(pathName);
        String pathStr = path.toString().replaceAll("\\\\", "/");
        if (isFolder(path) && !pathStr.endsWith("/")) {
            pathStr = pathStr + "/" ;
        }
        return pathStr;
    }

    /**
     * 获取temp文件夹
     *
     * @param subDir 子文件夹
     * @return 完整temp文件夹路径
     */
    public static String getTempDir(String subDir) {
        if (subDir.startsWith("/") || subDir.startsWith("\\")) {
            subDir = subDir.substring(1);
        }
        if (subDir.endsWith("/") || subDir.endsWith("\\")) {
            subDir = subDir.substring(0, subDir.length() - 1);
        }
        return formatPathName(System.getProperty("java.io.tmpdir") + subDir + "/");
    }

    /**
     * 文件内容转换为BASE64字符串
     *
     * @param file File对象
     * @return BASE64字符串
     */
    public static String toBase64(File file) throws IOException {
        FileInputStream inputFile = new FileInputStream(file);
        String base64;
        byte[] buffer = new byte[(int) file.length()];
        inputFile.read(buffer);
        inputFile.close();
        base64 = BASE64.encode(buffer);
        return base64.replaceAll("[\\s*\t\n\r]", "");
    }

    /**
     * 合并多个文件
     *
     * @param sourceFileNames 源文件
     * @param targetFileName  目标文件
     * @param ifDeleteOld     是否删除源文件
     */
    public static void mergeFiles(List<String> sourceFileNames, String targetFileName, boolean ifDeleteOld) throws IOException {
        if (StringUtils.isEmpty(targetFileName) || CollectionUtils.isEmpty(sourceFileNames)) {
            return;
        }
        if (sourceFileNames.size() == 1) {
            rename(Paths.get(sourceFileNames.get(0)), getName(Paths.get(targetFileName)));
        }

        List<File> files = sourceFileNames.stream()
                .filter(StringUtils::hasValue)
                .map(File::new)
                .filter(File::exists)
                .filter(File::isFile)
                .collect(Collectors.toList());
        File targetFile = new File(targetFileName);

        FileChannel targetFileChannel = new FileOutputStream(targetFile, true).getChannel();
        for (File file : files) {
            FileChannel blk = new FileInputStream(file).getChannel();
            targetFileChannel.transferFrom(blk, targetFileChannel.size(), blk.size());
            blk.close();
        }
        targetFileChannel.close();

        if (ifDeleteOld) {
            for (String sourceFileName : sourceFileNames) {
                Files.deleteIfExists(Paths.get(sourceFileName));
            }
        }
    }

    /**
     * FileTreeSize类，递归计算目录大小
     */
    private static class FileTreeSize extends SimpleFileVisitor<Path> {

        private long totalSize = 0L;

        @Override
        public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) {
            totalSize += attrs.size();
            return FileVisitResult.CONTINUE;
        }

        public long getTotalSize() {
            return totalSize;
        }
    }

    /**
     * FileTreeSearch类，目录树递归查找
     */
    private static class FileTreeSearch extends SimpleFileVisitor<Path> {

        enum MatchMode {
            EXACT, ANYWHERE
        }

        private final String query;
        private final MatchMode mode;
        private final boolean ignoreCase;
        private final List<Path> foundPaths;

        public FileTreeSearch(String query, MatchMode mode, boolean ignoreCase) {
            this.query = query;
            this.mode = mode;
            this.ignoreCase = ignoreCase;
            this.foundPaths = new ArrayList<>();
        }

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) {
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
            Objects.requireNonNull(dir);
            if (exc == null) {
                search(dir);
            }

            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            super.visitFile(file, attrs);
            search(file);
            return FileVisitResult.CONTINUE;
        }

        private void search(Path path) {
            if (path != null && path.getFileName() != null) {
                String fileName = path.getFileName().toString();
                boolean found;
                switch (mode) {
                    case EXACT:
                        if (ignoreCase) {
                            found = fileName.equalsIgnoreCase(query);
                        } else {
                            found = fileName.equals(query);
                        }
                        break;
                    case ANYWHERE:
                        if (ignoreCase) {
                            found = fileName.toLowerCase().contains(query.toLowerCase());
                        } else {
                            found = fileName.contains(query);
                        }
                        break;
                    default:
                        // NOP - This Should Never Happen
                        throw new AssertionError();
                }
                if (found) {
                    foundPaths.add(path);
                }
            }
        }

        public List<Path> getFoundPaths() {
            return Collections.unmodifiableList(foundPaths);
        }

    }

    /**
     * DirectoryCopy类，目录树递归复制
     */
    public static class DirectoryCopy extends SimpleFileVisitor<Path> {

        private final Path targetPath;
        private Path sourcePath = null;

        public DirectoryCopy(Path targetPath) {
            this.targetPath = targetPath;
        }

        @Override
        public FileVisitResult preVisitDirectory(final Path dir, final BasicFileAttributes attrs) throws IOException {
            if (sourcePath == null) {
                sourcePath = dir;
            }
            Files.createDirectories(targetPath.resolve(sourcePath.relativize(dir)));
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
            Files.copy(file, targetPath.resolve(sourcePath.relativize(file)), StandardCopyOption.REPLACE_EXISTING);
            return FileVisitResult.CONTINUE;
        }
    }

}
