package com.apache.lang3;

import org.apache.commons.lang3.*;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.apache.commons.lang3.reflect.TypeUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.*;

@SuppressWarnings("all")
@DisplayName("apache common lang3 常用功能")
public class LangTest {

    /**
     * 字符串处理
     */
    @Test
    public void test1() {
        // 将字符串缩短到某长度 => substring(str, 0, max-3) + "..."
        StringUtils.abbreviate("abcdefghijklmnopqrstuvwxuz", 6);

        // 如果不是以suffixs中的任意字符串结尾就添加上suffix
        StringUtils.appendIfMissing("abdefg", "efg");
        StringUtils.appendIfMissingIgnoreCase("abdefg", "efg");
        // TODO 为什么efg不会被添加上去
        StringUtils.appendIfMissing("abdefg", "efg", "abc", "efgg");
        // 首字母大小写转换
        StringUtils.capitalize("abc");
        StringUtils.uncapitalize("Abc");
        // 字符串大小写转换
        StringUtils.upperCase("aBc");
        StringUtils.lowerCase("aBc");
        StringUtils.swapCase("aBc");
        // 填充字符串到指定长度且原字符串居中
        StringUtils.center("abc", 7); // 默认填充空格
        StringUtils.center("abc", 9, "ab");
        // 去除 \n \r
        StringUtils.chomp("abc\n\r");
        // 删除所有空格
        StringUtils.deleteWhitespace("  ab c  ");
        // 包含
        StringUtils.contains("abc", "a");
        StringUtils.containsIgnoreCase("abc", "A");
        StringUtils.containsAny("abc", "e", "d");
        // 统计出现次数
        StringUtils.countMatches("abcabcdefe", "e");
        // 返回第二个参数中与第一个不同的字符串
        StringUtils.difference("abcdefg", "abcxyzg");
        // 以什么开头结尾
        StringUtils.endsWith("abcdefg", "g");
        StringUtils.endsWithIgnoreCase("ABC", "c");
        StringUtils.endsWithAny("abcdefg", "f", "g");
        // 获取共同前缀
        StringUtils.getCommonPrefix("abcdeefg", "abhijk");
        // 正向或反向查找出现位置
        StringUtils.indexOf("abcdefg", "c");
        StringUtils.lastIndexOf("abcdefg", "c");
        // 正向或泛型第n次出现的位置
        StringUtils.ordinalIndexOf("aabbccddcc", "cc", 2);
        // 是否全部为大/小写
        StringUtils.isAllUpperCase("abC");
        StringUtils.isAllLowerCase("abC");
        // 是否为空 只有 null 和 "" 返回false
        StringUtils.isEmpty("");
        // 先trim然后判断是否为空
        StringUtils.isBlank("");
        // 是否只包含数字(+-.都不识别)
        StringUtils.isNumeric("123");
        StringUtils.isNumericSpace("12 3"); // 识别空格
        // 替换字符串
        StringUtils.replace("abc", "a", "b");
        StringUtils.overlay("abcdefg", "zz", 2, 4);// 替换指定区域
        StringUtils.replaceEach("abcdefghijk", new String[]{"ab", "ef"}, new String[]{"cd", "gh"}); // ab -> cd, ef -> gh
        // 重复字符串
        StringUtils.repeat("ab", 3);
        // 反转
        StringUtils.reverse("abc");
        // 移除
        StringUtils.remove("abc", "b");
        // 去除空格(两侧，一侧，所有等)
        StringUtils.strip("  a b  ");
        // 从左/右截取n位字符
        StringUtils.left("abcd", 2);
        StringUtils.right("abcd", 2);
        // 从第pos位开始，截取len位字符
        StringUtils.mid("abcdef", 2, 3);
        // 截取字符串
        StringUtils.substringBefore("abcdefg", "c");
        StringUtils.substringBeforeLast("abcdcefg", "c");
        StringUtils.substringBetween("abcdcefg", "c");
    }

    @Test
    public void test2() {
        // 随机生成指定长度的数字字符串
        RandomStringUtils.randomNumeric(3);
        RandomStringUtils.randomAlphabetic(5);
        // 从指定的字符串中生成固定长度的随机字符串
        RandomStringUtils.random(4, "abcdefg");
        RandomStringUtils.random(4, false, true);
    }

    @Test
    public void test3() {
        NumberUtils.max(1, 3, 4, 9, 2, 6);
        // 是否只包含数字
        NumberUtils.isDigits("12.3");
        // 是否是有效数字
        NumberUtils.isCreatable("0213.3");
        NumberUtils.isCreatable("0.3");
    }

    @Test
    public void test4() {
        // 创建数组
        String[] strings1 = ArrayUtils.toArray("a", "b", "c");
        String[] strings2 = ArrayUtils.toArray("a", "b", "c");
        // 判断数组是否相等
        Arrays.deepEquals(strings1, strings1);
        // 是否包含
        ArrayUtils.contains(strings1, "a");
        // 数组转map
        ArrayUtils.toMap(new String[][]{{"k1", "v1"}, {"k2", "v2"}});
    }

    @Test
    public void test5() throws ParseException {
        DateUtils.addDays(new Date(), 3);
        DateUtils.parseDate("20210821", "yyyyMMdd");
    }

    /**
     * ArchUtils 提供运行环境的系统信息工具类
     */
    @Test
    public void archTest() {
        ArchUtils.getProcessor().getArch();// 获取电脑处理器体系结构 32 bit、64 bit、unknown
        ArchUtils.getProcessor().getType();// 返回处理器类型 x86、ia64、ppc、unknown
        ArchUtils.getProcessor().is32Bit();// 检查处理器是否为32位
        ArchUtils.getProcessor().is64Bit();// 检查处理器是否为64位
        ArchUtils.getProcessor().isIA64();// 检查是否是英特尔安腾处理器类型
        ArchUtils.getProcessor().isPPC();// 检查处理器是否是电源PC类型
        ArchUtils.getProcessor().isX86();// 检查处理器是否是x86类型
    }

    /**
     * ArrayUtils 用于对数组的操作，如添加、查找、删除、子数组、倒序、元素类型转换等；
     */
    @Test
    public void arrayTest() {
        // 添加元素，返回一个新数组
        String[] array = {"a", "b", "c", "d"};
        String[] tmp = {"x", "y", "z"};
        ArrayUtils.add(array, "z");
        ArrayUtils.add(array, 2, "b");
        ArrayUtils.addAll(array, tmp);
        // 浅度复制数组对象
        ArrayUtils.clone(array);
        ArrayUtils.contains(array, "z");
        ArrayUtils.getLength(array);
        System.out.println(ArrayUtils.hashCode(array));
        System.out.println(Objects.hashCode(array));

        // 从头/尾查询指定元素的位置
        ArrayUtils.indexOf(array, "c");
        ArrayUtils.indexOf(array, "c", 2); // 限制开始查询的位置
        // 从指定位置插入多个元素
        ArrayUtils.insert(1, array, "a", "b");

        ArrayUtils.isEmpty(array);
        ArrayUtils.isSameLength(array, tmp);
        ArrayUtils.isSorted(array);
        // 是否按照指定比较器排好序
        ArrayUtils.isSorted(array, Comparator.naturalOrder());
        ArrayUtils.nullToEmpty(array);
        // 移除所有出现的元素
        ArrayUtils.removeAllOccurrences(array, "a");
        // 移除第一个元素
        ArrayUtils.removeElement(array, "a");
        ArrayUtils.removeElements(array, "a");
        // 翻转 截取 交换
        ArrayUtils.reverse(array);
        ArrayUtils.reverse(array, 2, 4);
        ArrayUtils.subarray(array, 2, 4);
        ArrayUtils.swap(array, 2, 4);
    }

    /**
     * boolean
     */
    @Test
    public void booleanTest() {
        // 与 或 非 异或 操作
        // boolean -> 多种字符串
        BooleanUtils.toBoolean(1); // true
        BooleanUtils.toBoolean("true");
        BooleanUtils.toBoolean("on");
    }

    /**
     * class路径工具
     */
    @Test
    public void classPathTest() {
        System.out.println(ClassPathUtils.toFullyQualifiedName(ClassPathUtils.class, "StringUtils.properties"));
        System.out.println(ClassPathUtils.toFullyQualifiedPath(ClassPathUtils.class, "StringUtils.properties"));
    }

    /**
     * 枚举
     */
    @Test
    public void enumTest() {
        EnumUtils.getEnum(EnumDemo.class, "AA").getValue();
        EnumUtils.getEnumList(EnumDemo.class);
        EnumUtils.getEnumMap(EnumDemo.class); // key字符串 value枚举值
    }

    private static enum EnumDemo {
        AA("1"), BB("2");
        private String value;

        EnumDemo(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    @Test
    public void objectTest() {
        ObjectUtils.allNotNull("", null, "a");
        ObjectUtils.anyNotNull("", null, "a");
        // 如果为null返回默认值
        ObjectUtils.defaultIfNull("s", "a");
        // 返回第一个不为null的值
        System.out.println(ObjectUtils.firstNonNull(null, "a", null));
    }

    @Test
    public void randomTest() {
        RandomUtils.nextInt();
        RandomUtils.nextInt(1, 100);
    }

    /**
     * 操作系统信息
     */
    @Test
    public void systemTest() {
        SystemUtils.getUserName();
        SystemUtils.getJavaHome();
        SystemUtils.getHostName();
        SystemUtils.getUserDir();
        String fileEncoding = SystemUtils.FILE_ENCODING;
        String javaClassPath = SystemUtils.JAVA_CLASS_PATH;
        boolean isOsWindows10 = SystemUtils.IS_OS_WINDOWS_10;
    }

    @Test
    public void reflectTest() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        // 获取属性的值
        ReflectDemo demo = new ReflectDemo();
        Object name = FieldUtils.readDeclaredField(demo, "name", true);
        Object ss = FieldUtils.readDeclaredStaticField(ReflectDemo.class, "ss", true);
        System.out.println(name);
        System.out.println(ss);
        // 设置属性的值
        FieldUtils.writeDeclaredField(demo, "name", "eee", true);
        System.out.println(demo);

        // 获取被注解修饰的方法
        Method[] withAnnotation = MethodUtils.getMethodsWithAnnotation(ReflectDemo.class, Test.class);
        System.out.println(Arrays.toString(withAnnotation));
        // 调用方法
        // 调用当前类的static方法
        Object getSs = MethodUtils.invokeExactStaticMethod(ReflectDemo.class, "getSs");
        // 调用当前类的实例方法
        MethodUtils.invokeExactMethod(demo, "setName", "name");

        // 调用当前类和父类中的实例方法和静态方法
        Object getName = MethodUtils.invokeMethod(demo, "getName");
        MethodUtils.invokeStaticMethod(ReflectDemo.class, "setSs", "sss");

        ClassUtils.getAllInterfaces(ArrayList.class).forEach(System.out::println);
        ClassUtils.getAllSuperclasses(ArrayList.class).forEach(System.out::println);
        System.out.println(ClassUtils.getPackageName(ArrayList.class));
        System.out.println(ClassUtils.getSimpleName(ArrayList.class));
        // 获取规范的类名
        System.out.println(ClassUtils.getCanonicalName(ArrayList.class));
        System.out.println(ClassUtils.getShortCanonicalName(ArrayList.class));

        // 多个工具类...
        ClassUtils classUtils;
        ConstructorUtils constructorUtils;
        TypeUtils typeUtils;

    }

    public static class ReflectDemo {
        private String name = "qqq";
        private static String ss = "www";

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public static String getSs() {
            return ss;
        }

        public static void setSs(String ss) {
            ReflectDemo.ss = ss;
        }

        @Override
        @Test
        public String toString() {
            return "ReflectDemo{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }
}






