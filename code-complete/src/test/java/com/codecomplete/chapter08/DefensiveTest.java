package com.codecomplete.chapter08;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Objects;

@DisplayName("08 防范式编程")
public class DefensiveTest {

    /**
     * 断言
     */
    @Test
    public void assertTest(String s) {
        // 用错误代码处理预期会发生的状况
        // 用断言处理绝不应该发生的状况，将断言看作可执行的注释，不能依赖它让代码正常工作
        // 不要把要执行的代码放到断言中
        // 用断言来注解并验证前置条件和后置条件
        // 对于高健壮性的代码，先使用断言再处理错误
        Objects.requireNonNull(s, "字符串不能为null");
    }

    /**
     * 错误处理技术
     */
    @Test
    public void handleError() {
        // 返回中立值 0, "", null
        // 换用下一个正确的数据
        // 返回与上一次相同的数据
        // 欢颜最接近的合法值 (将一些不会为负数的值改成0(速度))
        // 把警告信息记录到日志文件
        // 返回错误码 (让上游程序处理)
        // 调用错误处理子程序或对象 (把错误集中到一个对象中(代码耦合))
        // 发生错误时显示出错信息
        // 用最妥当的方式在局部处理错误
        // 关闭程序

        // 健壮性 robustness 正确性 correctness
        // 安全相关的系统更倾向于正确性而非健壮性(不返回结果比返回错误结果好)
        // 消费类系统更倾向于健壮性(有的小错误不希望系统重启，刷新页面就好)
    }

    /**
     * 异常
     */
    @Test
    public void exceptionTest() {
        // 用异常通知程序的其他部分，发生了不可忽略的错误
        // 只在真正例外的情况下才抛出异常
        // 不能用异常推卸责任(不要抛出应该在局部被处理的异常)
        // 不要再构造函数 或 析构函数中抛出异常
        // 再恰当的抽象层次抛出异常(抛出的异常也是接口的一部分, service 层不应该抛出 DaoException )
        // 在异常消息中加入关于导致异常发生的全部信息
        // 避免使用空的 catch 语句
        // 了解所用函数库可能抛出的异常
        // 创建一个集中的异常报告机制
    }

    /**
     * 隔离程序，使之包容由错误造成的损害
     */
    public void barricade() {
        // 把某些接口选定为安全区域的边界(service)，对穿越安全区域边界的数据进行合法性校验，数据非法时做出反应(controller)
        // 在输入数据时将其转换为恰当的类型

        // 不要把产品版的限制强加于开发版上(在开发期间牺牲一些速度和对资源的使用，换取一些可以让开发更顺畅的内置工具)
        // 尽早引入辅助调试代码
        // 采用进攻式编程(对于异常的处理，开发节点让其显示出来，在产品代码中让它自我恢复)
        // 计划移除调试辅助的代码

        // 保留检查重要错误的代码，去掉检查细微错误的代码
        // 去掉可以导致系统崩溃的代码，保留可以让程序稳妥崩溃的代码
        // 为技术任意记录错误信息
        // 确认代码中的错误消息是友好的

    }
}
