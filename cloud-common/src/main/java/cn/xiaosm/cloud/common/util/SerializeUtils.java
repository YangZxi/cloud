package cn.xiaosm.cloud.common.util;

import cn.hutool.core.lang.Assert;

import java.io.*;

/**
 * @author Young
 * @create 2021/5/8
 * @since 1.0.0
 */
public class SerializeUtils {

    /**
     * 序列化对象
     */
    public static byte[] serialize(Object o) {
        Assert.isTrue(o instanceof Serializable, "对象没有实现序列化");
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(bout);
            out.writeObject(o);
            return bout.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(bout, out);
        }
        return null;
    }

    /**
     * 反序列化对象
     */
    public static Object deserialize(byte[] bytes) {
        if (bytes == null) return null;
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        ObjectInputStream oin = null;
        try {
            oin = new ObjectInputStream(in);
            return oin.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            close(in, oin);
        }
        return null;
    }

    public static void close(Closeable... closeables) {
        for (Closeable closeable : closeables) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}