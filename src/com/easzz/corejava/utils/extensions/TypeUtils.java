package com.easzz.corejava.utils.extensions;


import com.easzz.corejava.utils.annotation.Description;
import com.easzz.corejava.utils.annotation.Order;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * 类型工具类
 * Created by 李溪林 on 16-8-11.
 */
public class TypeUtils {

    private static final String currentClassName = "TypeUtils";

    private static final Map<Class<?>, Class<?>> primitiveWrapperTypeMap = new HashMap<Class<?>, Class<?>>(8);

    private static final Map<Class<?>, Class<?>> primitiveTypeToWrapperMap = new HashMap<Class<?>, Class<?>>(8);

    private static final Map<String, Class<?>> primitiveTypeNameMap = new HashMap<String, Class<?>>(32);

    private static final Map<String, Class<?>> commonClassCache = new HashMap<String, Class<?>>(32);

    private static final Map<Class<?>, Object> primitiveTypeDefaultValueMap = new HashMap<>(32);

    static {
        primitiveWrapperTypeMap.put(Boolean.class, boolean.class);
        primitiveWrapperTypeMap.put(Byte.class, byte.class);
        primitiveWrapperTypeMap.put(Character.class, char.class);
        primitiveWrapperTypeMap.put(Double.class, double.class);
        primitiveWrapperTypeMap.put(Float.class, float.class);
        primitiveWrapperTypeMap.put(Integer.class, int.class);
        primitiveWrapperTypeMap.put(Long.class, long.class);
        primitiveWrapperTypeMap.put(Short.class, short.class);

        for (Map.Entry<Class<?>, Class<?>> entry : primitiveWrapperTypeMap.entrySet()) {
            primitiveTypeToWrapperMap.put(entry.getValue(), entry.getKey());
            registerCommonClasses(entry.getKey());
        }

        Set<Class<?>> primitiveTypes = new HashSet<Class<?>>(32);
        primitiveTypes.addAll(primitiveWrapperTypeMap.values());
        primitiveTypes.addAll(Arrays.asList(new Class<?>[]{
                boolean[].class, byte[].class, char[].class, double[].class,
                float[].class, int[].class, long[].class, short[].class}));
        primitiveTypes.add(void.class);
        for (Class<?> primitiveType : primitiveTypes) {
            primitiveTypeNameMap.put(primitiveType.getName(), primitiveType);
        }

        registerCommonClasses(Boolean[].class, Byte[].class, Character[].class, Double[].class,
                Float[].class, Integer[].class, Long[].class, Short[].class);
        registerCommonClasses(Number.class, Number[].class, String.class, String[].class,
                Object.class, Object[].class, Class.class, Class[].class);
        registerCommonClasses(Throwable.class, Exception.class, RuntimeException.class,
                Error.class, StackTraceElement.class, StackTraceElement[].class);

        short tempShortDefault = 0;
        primitiveTypeDefaultValueMap.put(boolean.class, false);
        primitiveTypeDefaultValueMap.put(byte.class, 0);
        primitiveTypeDefaultValueMap.put(char.class, '\u0000');
        primitiveTypeDefaultValueMap.put(double.class, 0.0D);
        primitiveTypeDefaultValueMap.put(float.class, 0.0F);
        primitiveTypeDefaultValueMap.put(int.class, 0);
        primitiveTypeDefaultValueMap.put(long.class, 0L);
        primitiveTypeDefaultValueMap.put(short.class, tempShortDefault);
    }

    private static void registerCommonClasses(Class<?>... commonClasses) {
        for (Class<?> clazz : commonClasses) {
            commonClassCache.put(clazz.getName(), clazz);
        }
    }

    /**
     * 获取指定类型的字段信息集合
     *
     * @param tClass 指定类型
     * @param <T>    数据类型
     * @return 字段信息集合
     */
    public static <T> Field[] getFields(Class<T> tClass) {
        return getFields(tClass, true);
    }

    /**
     * 获取指定类型的字段信息集合
     *
     * @param tClass            指定类型
     * @param containSuperClass 是否包含父类的字段
     * @param <T>               数据类型
     * @return 字段信息集合
     */
    public static <T> Field[] getFields(Class<T> tClass, boolean containSuperClass) {
        checkClassIsNotNull(tClass, "getFields");
        Field[] fields = tClass.getDeclaredFields();
        if (!containSuperClass) {
            return fields;
        }
        List<Field> result = ArrayUtils.toList(fields);
        Class superClass = tClass.getSuperclass();
        while (superClass != Object.class) {
            Field[] superClassFields = superClass.getDeclaredFields();
            if (!ArrayUtils.isNullOrEmpty(superClassFields)) {
                for (Field item : superClassFields) {
                    result.add(item);
                }
            }
            superClass = superClass.getSuperclass();
        }

        return result.toArray(new Field[result.size()]);
    }

    /**
     * 在指定类型中获取包含指定属性的字段信息集合
     *
     * @param tClass              指定类型
     * @param propertyDescriptors 属性描述信息集合
     * @param <T>                 数据类型
     * @return 字段信息集合
     */
    public static <T> Field[] getFields(Class<T> tClass, PropertyDescriptor[] propertyDescriptors) {
        checkClassIsNotNull(tClass, "getFields");
        if (ArrayUtils.isNullOrEmpty(propertyDescriptors)) {
            return null;
        }
        List<Field> result = new ArrayList<>();
        Field[] fields = getFields(tClass, true);
        Map<String, PropertyDescriptor> map = new HashMap<>();
        for (PropertyDescriptor item : propertyDescriptors) {
            map.put(item.getName(), item);
        }
        for (Field item : fields) {
            if (map.containsKey(item.getName())) {
                result.add(item);
            }
        }

        return result.toArray(new Field[result.size()]);
    }

    /**
     * 在指定类型中获取指定属性对应的字段信息
     *
     * @param tClass             指定类型
     * @param propertyDescriptor 属性描述信息
     * @param <T>                数据类型
     * @return 字段信息
     */
    public static <T> Field getField(Class<T> tClass, PropertyDescriptor propertyDescriptor) {
        checkClassIsNotNull(tClass, "getField");
        if (propertyDescriptor == null) {
            return null;
        }
        Field[] fields = getFields(tClass, true);
        return ArrayUtils.firstOrDefault(fields, f -> f.getName().equals(propertyDescriptor.getName()));
    }

    /**
     * 在指定类型中获取指定字段名称对应的字段信息
     *
     * @param tClass    指定类型
     * @param fieldName 字段名称
     * @param <T>       数据类型
     * @return 字段信息
     */
    public static <T> Field getField(Class<T> tClass, String fieldName) {
        checkClassIsNotNull(tClass, "getField");
        if (StringUtils.isNullOrWhiteSpace(fieldName)) {
            return null;
        }
        try {
            return tClass.getDeclaredField(fieldName);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取指定类型的属性描述信息集合
     *
     * @param tClass 指定类型
     * @param <T>    数据类型
     * @return 属性描述信息集合
     */
    public static <T> PropertyDescriptor[] getPropertyDescriptors(Class<T> tClass) {
        return getPropertyDescriptors(tClass, false);
    }

    /**
     * 获取指定类型的属性描述信息集合
     *
     * @param tClass 指定类型
     * @param order  是否根据注解 Order 进行排序
     * @param <T>    数据类型
     * @return 属性描述信息集合
     */
    public static <T> PropertyDescriptor[] getPropertyDescriptors(Class<T> tClass, boolean order) {
        checkClassIsNotNull(tClass, "getPropertyDescriptors");
        PropertyDescriptor[] ori = getOriginalPropertyDescriptors(tClass);
        if (!order) {
            return ori;
        }
        ArrayList<PropertyDescriptor> result = new ArrayList<>();

        HashMap<String, PropertyDescriptor> maps = new HashMap<>();
        for (PropertyDescriptor item : ori) {
            maps.put(item.getName(), item);
        }
        Field[] fields = getFields(tClass, true);
        for (Field item : fields) {
            PropertyDescriptor pro = maps.get(item.getName());
            if (maps.containsKey(item.getName()) && item.getName().equals(pro.getName())) {
                Order annotation = item.getAnnotation(Order.class);
                int temp = annotation == null ? 0 : annotation.value();
                int len = result.size();
                result.add(temp > len ? len : temp, pro);
            }
        }

        return result.toArray(new PropertyDescriptor[result.size()]);
    }

    /**
     * 获取指定类型中指定属性名的属性描述信息
     *
     * @param tClass       指定类型
     * @param propertyName 指定属性名
     * @param <T>          数据类型
     * @return 属性描述信息
     */
    public static <T> PropertyDescriptor getPropertyDescriptor(Class<T> tClass, String propertyName) {
        checkClassIsNotNull(tClass, "getPropertyDescriptor");
        if (StringUtils.isNullOrWhiteSpace(propertyName)) {
            return null;
        }
        PropertyDescriptor[] props = getPropertyDescriptors(tClass);
        return ArrayUtils.firstOrDefault(props, f -> f.getName().equals(propertyName));
    }

    /**
     * 获取指定类型中指定属性名的属性描述信息集合
     *
     * @param tClass        指定类型
     * @param propertyNames 指定属性名集合
     * @param <T>           数据类型
     * @return 属性描述信息集合
     */
    public static <T> PropertyDescriptor[] getPropertyDescriptors(Class<T> tClass, String[] propertyNames) {
        return getPropertyDescriptors(tClass, propertyNames, false);
    }

    /**
     * 获取指定类型中指定属性名的属性描述信息集合
     *
     * @param tClass        指定类型
     * @param propertyNames 指定属性名集合
     * @param order         是否根据注解 Order 进行排序
     * @param <T>           数据类型
     * @return 属性描述信息集合
     */
    public static <T> PropertyDescriptor[] getPropertyDescriptors(Class<T> tClass, String[] propertyNames, boolean order) {
        checkClassIsNotNull(tClass, "getPropertyDescriptors");
        if (ArrayUtils.isNullOrEmpty(propertyNames)) {
            return null;
        }
        PropertyDescriptor[] props = getPropertyDescriptors(tClass, order);
        Map<String, PropertyDescriptor> map = new HashMap<>();
        for (PropertyDescriptor item : props) {
            map.put(item.getName(), item);
        }
        List<PropertyDescriptor> list = new ArrayList<>();
        for (String item : propertyNames) {
            if (map.containsKey(item)) {
                list.add(map.get(item));
            }
        }
        return list.toArray(new PropertyDescriptor[list.size()]);
    }

    /**
     * 获取指定类型的可公开get的属性描述信息集合
     *
     * @param tClass 指定类型
     * @param <T>    数据类型
     * @return 属性描述信息集合
     */
    public static <T> PropertyDescriptor[] getAllowGetPropertyDescriptors(Class<T> tClass) {
        return getAllowGetPropertyDescriptors(tClass, false);
    }

    /**
     * 获取指定类型的可公开get的属性描述信息集合
     *
     * @param tClass 指定类型
     * @param order  是否根据注解 Order 进行排序
     * @param <T>    数据类型
     * @return 属性描述信息集合
     */
    public static <T> PropertyDescriptor[] getAllowGetPropertyDescriptors(Class<T> tClass, boolean order) {
        checkClassIsNotNull(tClass, "getAllowGetPropertyDescriptors");
        PropertyDescriptor[] all = getPropertyDescriptors(tClass, order);
        List<PropertyDescriptor> result = new ArrayList<>();
        for (PropertyDescriptor prop : all) {
            if (prop.getName().equals("class")) {
                continue;
            }
            Method getter = prop.getReadMethod();
            if (getter != null && isPublic(getter)) {
                result.add(prop);
            }
        }

        return result.toArray(new PropertyDescriptor[result.size()]);
    }

    /**
     * 获取指定类型的可公开set的属性描述信息集合
     *
     * @param tClass 指定类型
     * @param <T>    数据类型
     * @return 属性描述信息集合
     */
    public static <T> PropertyDescriptor[] getAllowSetPropertyDescriptors(Class<T> tClass) {
        return getAllowSetPropertyDescriptors(tClass, false);
    }

    /**
     * 获取指定类型的可公开set的属性描述信息集合
     *
     * @param tClass 指定类型
     * @param order  是否根据注解 Order 进行排序
     * @param <T>    数据类型
     * @return 属性描述信息集合
     */
    public static <T> PropertyDescriptor[] getAllowSetPropertyDescriptors(Class<T> tClass, boolean order) {
        checkClassIsNotNull(tClass, "getAllowSetPropertyDescriptors");
        PropertyDescriptor[] all = getPropertyDescriptors(tClass, order);
        List<PropertyDescriptor> result = new ArrayList<>();
        for (PropertyDescriptor prop : all) {
            if (prop.getName().equals("class")) {
                continue;
            }
            Method setter = prop.getWriteMethod();
            if (setter != null && isPublic(setter)) {
                result.add(prop);
            }
        }

        return result.toArray(new PropertyDescriptor[result.size()]);
    }

    /**
     * 获取指定类型的可公开 get 且 set 的属性描述信息集合
     *
     * @param tClass 指定类型
     * @param <T>    数据类型
     * @return 属性描述信息集合
     */
    public static <T> PropertyDescriptor[] getAllowGetSetPropertyDescriptors(Class<T> tClass) {
        return getAllowGetSetPropertyDescriptors(tClass, false);
    }

    /**
     * 获取指定类型的可公开 get 且 set 的属性描述信息集合
     *
     * @param tClass 指定类型
     * @param order  是否根据注解 Order 进行排序
     * @param <T>    数据类型
     * @return 属性描述信息集合
     */
    public static <T> PropertyDescriptor[] getAllowGetSetPropertyDescriptors(Class<T> tClass, boolean order) {
        checkClassIsNotNull(tClass, "getAllowGetSetPropertyDescriptors");
        PropertyDescriptor[] all = getPropertyDescriptors(tClass, order);
        List<PropertyDescriptor> result = new ArrayList<>();
        for (PropertyDescriptor prop : all) {
            if (prop.getName().equals("class")) {
                continue;
            }
            Method getter = prop.getReadMethod();
            Method setter = prop.getWriteMethod();
            if (getter != null && setter != null && isPublic(getter) && isPublic(setter)) {
                result.add(prop);
            }
        }

        return result.toArray(new PropertyDescriptor[result.size()]);
    }

    /**
     * 判定第1个类型是否可以从第2个类型中指派
     * 例: ArrayList 可以指派给 List,基本类型和包装类型之间可以相互指派
     *
     * @param lhsType 第二个类型
     * @param rhsType 第一个类型
     * @return 可以指派则返回true, 否则返回false
     */
    public static boolean isAssignable(Class<?> lhsType, Class<?> rhsType) {
        checkClassIsNotNull(lhsType, "isAssignable");
        checkClassIsNotNull(rhsType, "isAssignable");
        if (lhsType.isAssignableFrom(rhsType)) {
            return true;
        }
        if (lhsType.isPrimitive()) {
            //如果是基础类型,尝试将第二个类型转化成基础类型,如果相等则true
            Class<?> resolvedPrimitive = primitiveWrapperTypeMap.get(rhsType);
            if (resolvedPrimitive != null && lhsType.equals(resolvedPrimitive)) {
                return true;
            }
        } else {
            //如果不是基础类型,尝试将第二个类型转化成包装类型,如果符合isAssignableFrom则true
            Class<?> resolvedWrapper = primitiveTypeToWrapperMap.get(rhsType);
            if (resolvedWrapper != null && lhsType.isAssignableFrom(resolvedWrapper)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取指定类型的包装类型
     *
     * @param theClass 指定类型
     * @return 指定类型的包装类型
     */
    public static Class<?> getWrapperClass(Class<?> theClass) {
        checkClassIsNotNull(theClass, "getPackageClass");
        if (!theClass.isPrimitive()) {
            return theClass;
        }
        return primitiveTypeToWrapperMap.get(theClass);
    }

    /**
     * 判定方法的访问修饰符是否是public
     *
     * @param method 方法对象
     * @return 若是public则返回true, 否则返回false
     */
    public static boolean isPublic(Method method) {
        checkMethodIsNotNull(method, "isPublic");
        return Modifier.isPublic(method.getModifiers());
    }

    /**
     * 获取指定类型中指定属性的 Description 注解值集合
     * 若该注解不存在或value为空,则以属性名称返回
     *
     * @param tClass              指定类型
     * @param propertyDescriptors 指定属性描述集合
     * @param <T>                 数据类型
     * @return 以字典格式返回, 其中key是属性名, value是注解值
     */
    public static <T> Map<String, String> getDescriptions(Class<T> tClass, PropertyDescriptor[] propertyDescriptors) {
        checkClassIsNotNull(tClass, "getDescriptions");
        if (ArrayUtils.isNullOrEmpty(propertyDescriptors)) {
            return null;
        }
        Map<String, String> result = new HashMap<>();
        Field[] fields = getFields(tClass, true);
        Map<String, PropertyDescriptor> map = new HashMap<>();
        for (PropertyDescriptor item : propertyDescriptors) {
            map.put(item.getName(), item);
        }
        for (Field item : fields) {
            if (map.containsKey(item.getName())) {
                Description description = item.getAnnotation(Description.class);
                result.put(item.getName(), description == null ? item.getName() : description.value());
            }
        }

        return result;
    }

    /**
     * 获取指定类型中指定属性的 Description 注解值
     * 若该注解不存在或value为空,则以属性名称返回
     *
     * @param tClass             指定类型
     * @param propertyDescriptor 指定属性描述集合
     * @param <T>                数据类型
     * @return 若该注解不存在或value为空, 则以属性名称返回
     */
    public static <T> String getDescription(Class<T> tClass, PropertyDescriptor propertyDescriptor) {
        checkClassIsNotNull(tClass, "getDescription");
        if (propertyDescriptor == null) {
            return null;
        }
        Field[] fields = getFields(tClass, true);
        for (Field item : fields) {
            if (item.getName().equals(propertyDescriptor.getName())) {
                Description description = item.getAnnotation(Description.class);
                return description == null ? item.getName() : description.value();
            }
        }
        return null;
    }

    /**
     * 获取指定类型中指定字段名称的指定类型的注解信息
     *
     * @param tClass          指定类型
     * @param fieldName       指定字段名称
     * @param annotationClass 指定类型的注解
     * @param <T>             数据类型
     * @return 若该注解不存在或则返回null
     */
    public static <T extends Annotation> T getAnnotation(Class<?> tClass, String fieldName, Class<T> annotationClass) {
        checkClassIsNotNull(tClass, "getAnnotation");
        if (StringUtils.isNullOrWhiteSpace(fieldName) || annotationClass == null) {
            return null;
        }
        Field field = getField(tClass, fieldName);
        if (field == null) {
            throw new UtilsException(currentClassName + "中的方法[getAnnotation]发生异常:Class类型中不存在名为" + fieldName + "的字段,无法获取其注解.");
        }
        T annotation = field.getAnnotation(annotationClass);

        return annotation;
    }

    /**
     * 判断指定类型是否存在公共无参构造
     *
     * @param tClass 要判断的类型对象
     * @param <T>    数据类型
     * @return 存在无参构造则返回true, 否则返回false
     */
    public static <T> boolean hasConstructorWithoutParams(Class<T> tClass) {
        checkClassIsNotNull(tClass, "hasConstructorWithoutParams");
        Constructor[] cons = tClass.getConstructors();
        return ArrayUtils.any(cons, f -> f.getParameterCount() == 0);
    }

    /**
     * 获取指定类型的公共无参构造
     *
     * @param tClass 指定类型
     * @param <T>    数据类型
     * @return 无参构造, 若不存在则返回null
     */
    public static <T> Constructor getConstructorWithoutParams(Class<T> tClass) {
        checkClassIsNotNull(tClass, "getConstructorWithoutParams");
        Constructor[] cons = tClass.getConstructors();
        return ArrayUtils.firstOrDefault(cons, f -> f.getParameterCount() == 0);
    }

    /**
     * 获取指定类型的新实例
     *
     * @param tClass 指定类型
     * @param <T>    数据类型
     * @return 指定类型的新实例
     */
    public static <T> T getNewInstance(Class<T> tClass) {
        checkClassIsNotNull(tClass, "getNewInstance");
        if (hasConstructorWithoutParams(tClass)) {
            try {
                return tClass.newInstance();
            } catch (Exception e) {
                throw new UtilsException(currentClassName + "中的方法[getNewInstance]发生异常:为" + tClass.getName() + "类型初始化对象失败.", e);
            }

        } else {
            if (tClass.isPrimitive()) {
                T value = (T) primitiveTypeDefaultValueMap.get(tClass);
                return value;
            } else {
                return null;
            }
        }
    }

    /**
     * 检查Class类型是否为null,为null则抛出异常
     *
     * @param tClass     Class类型
     * @param methodName 调用方法名
     * @param <T>        数据类型
     */
    private static <T> void checkClassIsNotNull(Class<T> tClass, String methodName) {
        if (tClass == null) {
            throw new UtilsException(currentClassName + "中的方法[" + methodName + "]发生异常:Class类型不能为null.");
        }
    }

    /**
     * 检查方法是否为null,为null则抛出异常
     *
     * @param method     方法
     * @param methodName 调用方法名
     */
    private static void checkMethodIsNotNull(Method method, String methodName) {
        if (method == null) {
            throw new UtilsException(currentClassName + "中的方法[" + methodName + "]发生异常:方法对象不能为null.");
        }
    }

    /**
     * 获取指定类型的属性描述信息原始集合
     *
     * @param tClass 指定类型
     * @param <T>    数据类型
     * @return
     */
    private static <T> PropertyDescriptor[] getOriginalPropertyDescriptors(Class<T> tClass) {
        try {
            BeanInfo bi = Introspector.getBeanInfo(tClass);
            return bi.getPropertyDescriptors();
        } catch (Exception e) {
            throw new UtilsException(currentClassName + "中的私有方法[getOriginalPropertyDescriptors]发生异常.", e);
        }
    }
}
