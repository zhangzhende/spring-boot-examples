package com.zzd.redis.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


/**
 * 〈序列化就是将一个对象转换为二进制的数据流。这样就可以进行传输，或者保存到文件中。如果一个类的对象要想实现序列化，就必须实现serializable接口。
 * 在此接口中没有任何的方法，此接口只是作为一个标识，表示本类的对象具备了序列化的能力而已。〉 〈反序列化:将二进制数据流转换成相应的对象。〉
 * 
 * @see SerializationUtil
 * @since
 */
public class SerializationUtil {
    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SerializationUtil.class);

    /**
     * 序列化
     * 
     * @param object
     * @return 返回字节码
     */
    public static byte[] serialize(Object object) {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            byte[] bytes = baos.toByteArray();
            return bytes;
        }
        catch (Exception e) {
            LOGGER.warn("an exception occured when serialize object for redis.", e);
        }
        return null;
    }

    /**
     * 反序列化
     * 
     * @param bytes 如果为空则返回空对象
     * @return 返回object
     */
    public static Object deserialize(byte[] bytes) {
        ByteArrayInputStream bais = null;
        if(bytes==null) {
            return new Object();
        }
        try {
            bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();
        }
        catch (Exception e) {
            LOGGER.warn("an exception occured when deserialize object for redis.", e);
        }
        return null;
    }

}