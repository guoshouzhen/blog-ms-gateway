package top.guoshouzhen.blog.blogmsgateway.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * Jackson的封装
 *
 * @author guoshouzhen
 * @date 2021/12/8 22:46
 */
@Slf4j
public class JacksonUtil {
    /**
     * ObjectMapper
     */
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 将指定对象序列化为一个json字符串
     *
     * @param obj 对象
     * @return java.lang.String
     * @author guoshouzhen
     * @date 2021/12/8 22:49
     */
    public static String obj2Json(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj.getClass() == String.class) {
            return (String) obj;
        }
        try {
            return MAPPER.writeValueAsString(obj);
        } catch (Exception ex) {
            log.error("json序列化出错，对象信息：{}，异常信息：{}", obj, ex);
            return null;
        }
    }

    /**
     * 将指定json字符串序列化为对象
     *
     * @param strJson json字符串
     * @param clazz   对象class
     * @return T
     * @author guoshouzhen
     * @date 2021/12/8 22:54
     */
    public static <T> T json2Obj(String strJson, Class<T> clazz) {
        try {
            return MAPPER.readValue(strJson, clazz);
        } catch (Exception ex) {
            log.error("json解析出错，json：{}，异常信息：{}", strJson, ex);
            return null;
        }
    }

    /**
     * 将指定输入流解析为指定类型对象
     *
     * @param inputStream 输入流对象
     * @param clazz       指定类型
     * @return T
     * @author guoshouzhen
     * @date 2021/12/8 22:58
     */
    public static <T> T stream2Obj(InputStream inputStream, Class<T> clazz) {
        try {
            return MAPPER.readValue(inputStream, clazz);
        } catch (Exception ex) {
            log.error("Stream解析出错，stream：{}，异常信息：{}", inputStream, ex);
            return null;
        }
    }

    /**
     * 将指定json字符串解析为指定类型集合
     *
     * @param strJson json字符串
     * @param clazz   指定元素类型
     * @return java.util.List<E>
     * @author guoshouzhen
     * @date 2021/12/8 22:59
     */
    public static <E> List<E> json2ListObj(String strJson, Class<E> clazz) {
        try {
            return MAPPER.readValue(strJson, MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (Exception ex) {
            log.error("json解析出错，json：{}，异常信息：{}", strJson, ex);
            return null;
        }
    }

    /**
     * 将指定json字符串解析为指定键值对类型集合
     *
     * @param strJson json字符串
     * @param kClazz  指定键类型
     * @param vClazz  指定值类型
     * @return java.util.Map<K, V>
     * @author guoshouzhen
     * @date 2021/12/8 23:02
     */
    public static <K, V> Map<K, V> json2MapObj(String strJson, Class<K> kClazz, Class<V> vClazz) {
        try {
            return MAPPER.readValue(strJson, MAPPER.getTypeFactory().constructMapType(Map.class, kClazz, vClazz));
        } catch (Exception ex) {
            log.error("json解析出错，json：{}，异常信息：{}", strJson, ex);
            return null;
        }
    }

    /**
     * 将指定json字符串解析为一个复杂类型对象
     *
     * @param strJson json字符串
     * @param type    复杂类型
     * @return T
     * @author guoshouzhen
     * @date 2021/12/8 23:04
     */
    public static <T> T json2ComplexObj(String strJson, TypeReference<T> type) {
        try {
            return MAPPER.readValue(strJson, type);
        } catch (Exception ex) {
            log.error("json解析出错，json：{}，异常信息：{}", strJson, ex);
            return null;
        }
    }
}
