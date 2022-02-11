### log4j 源码解析



七个核心类

- Logger：对日志记录行为的抽象，提供不同级别日志的接口

  - RootLogger：根节点，不行设置 Level，避免所有 Logger 实例都没有设置 Level
  - NOPLogger：测试时使用，什么都不做

- LoggerRepository：Logger 实例的容器，相同名称的 Logger 只会创建一次

  Hierarchy 实现类

  - Configurator：解析配置文件，将解析后的信息添加到 LoggerRepository
  - LoggerManager：将 Configurator 和 LoggerRepository 整合到一起

- Level：日志级别的抽象

  all, fatal, error, warn, info, debug, trace, off

- LoggingEvent：日志记录过程中能用到的信息的抽象

  日志级别，线程名，位置信息(LocationInfo：源文件名，类名，方法名，行号)，...

- Appender：日志记录形式的抽象

  AppenderAttachable： 管理多个 Appender 实例

  - WriteAppender, ConsoleAppender
  - FileAppender, DailyRollingFileAppender(时间), RollingFileAppender(文件大小)
  - JDBCAppender

  

  - Filter 过滤日志

- Layout：日志行格式的抽象，将 LoggingEvent 中的信息格式化成一行日志信息

  - SimpleLayout
  - PatternLayout

- ObjectRender：日志实例的解析接口

调用流程

Logger  ->  LogRepository  ->  Level  ->  LoggingEvent  ->  AppenderAttachableImpl  ->  ConsoleAppender  ->  SimpleLayout



1. 获取Logger实例
2. 判断Logger实例对应的日志记录级别是否要比请求的级别低
   1. 是则调用forceLog记录日志
   2. 创建LoggingEvent实例
   3. 将LoggingEvent实例传递给Appender
   4. Appender调用Layout实例格式化日志消息
   5. Appender将格式化后的日志信息写入该Appender对应的日志输出中

