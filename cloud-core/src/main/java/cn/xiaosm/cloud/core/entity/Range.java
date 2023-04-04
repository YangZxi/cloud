package cn.xiaosm.cloud.core.entity;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.xiaosm.cloud.common.exception.ResourceException;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.File;

@Data
@Accessors(chain = true)
public class Range {

    /**
     * 起始下标
     */
    private long start;
    /**
     * 结束下标
     */
    private long end;
    /**
     * 总大小
     */
    private long total;
    /**
     * 当前分段大小
     */
    private long contentLength;
    /**
     * 是否分段
     */
    private boolean part = true;

    private Range() { }

    public Range(long total) {
        this.start = 0;
        this.end = total - 1;
        this.total = total;
        this.contentLength = total;
    }

    public Range(long start, long total, int order) {
        this.start = start;
        this.end = total - 1;
        this.total = total;
        this.contentLength = total - start;
        // if (this.contentLength == this.total) this.part = false;
    }

    public Range(long start, long size, long total, int order) {
        this.start = start;
        this.end = start + size - 1;
        this.total = total;
        this.contentLength = size;
        // if (this.contentLength == this.total) this.part = false;
    }

    public static Range build(File file) {
        return new Range(file.length()).setPart(false);
    }

    public static Range build(String range) {
        if (StrUtil.isBlank(range)) throw new IllegalArgumentException("Range值不可以为空");
        if (range.startsWith("bytes ")) return buildByResponse(range);
        else return null;
    }

    /**
     * 根据响应头构建 Range
     * Content-Range: bytes 1-10/55755272
     * @param arg
     * @return
     */
    public static Range buildByResponse(String arg) {
        if (StrUtil.isBlank(arg)) throw new IllegalArgumentException("Range值不可以为空");
        if (!arg.startsWith("bytes ")) throw new IllegalArgumentException("Range不合法");
        String[] value = arg.substring(6).split("/");
        Assert.isTrue(value.length == 2, "Range不合法");
        String[] se = value[0].split("-");
        Assert.isTrue(se.length == 2, "Range不合法");
        Range range = new Range();
        range.setStart(Long.parseLong(se[0]));
        range.setEnd(Long.parseLong(se[1]));
        range.setTotal(Long.parseLong(value[1]));
        range.setContentLength(range.end - range.start + 1);
        if (range.contentLength == range.total) range.part = false;
        return range;
    }

    /**
     * 根据请求头构建 Range
     * Range: bytes=1-10
     * @param arg
     * @param file
     * @return
     * @throws ResourceException
     */
    public static Range build(String arg, File file) throws ResourceException {
        if (StrUtil.isBlank(arg)) return build(file);
        if (!arg.startsWith("bytes=")) return build(file);
        try {
            // 如果 arg 数据不存在，则默认使用 0-file.length() 的范围
            arg = arg.substring(arg.indexOf("=") + 1).trim();
            String[] arr = arg.split("-");
            Assert.isTrue(arr.length > 0 && arr.length <= 2, "Range不合法");
            // 如果 arg 存在，包装 arg 范围
            Range range = new Range();
            if (arr.length == 1) {
                if (arg.startsWith("-")) {  // 没有 start  -xxx
                    range.end = Long.valueOf(arr[0]);
                } else {                    // 没有 end    xxx-
                    range.start = Long.valueOf(arr[0]);
                    // 默认给 1MB 的传输数据
                    // range.setEnd(range.start + ((1 << 20) * 1 - 1));
                    range.end = file.length() - 1;
                }
            } else {
                range.start = Long.valueOf(arr[0]);
                range.end = Long.valueOf(arr[1]);
            }
            // 如果end大于文件大小
            if (range.end >= file.length()) {
                range.end = (file.length() - 1);
            }
            Assert.isTrue(range.start < range.end, "range请求头不规范，start < end is error");
            range.setTotal(file.length());
            /**
             * Range 的表示方式，如 length = 1000; 分两次下载
             * 则两次分段分别是是 [0, 499] [500, 999],其表示方式遵循数学的闭区间
             * 所以在计算长度的时候需要 +1
             */
            range.contentLength = range.end - range.start + 1;
            // if (range.contentLength == range.total) range.part = false;
            return range;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public String toString() {
        return start + "-" + end + "/" + total + ", contentLength=" + contentLength;
    }
}