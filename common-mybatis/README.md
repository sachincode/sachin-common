# common-mybatis

## 支持数据库物理查询

mybatis默认在翻页查询时，并不将limit/offset发送到数据库。而是先查到满足条件的所有数据，之后通过ResultSet.next()方式跳过offset个数据，然后返回。

这里支持物理翻页，直接将limit/offset发送到数据库执行，mybatis-config.xml中增加插件

```
    <plugins>
        <plugin interceptor="com.sachin.common.mybatis.interceptor.StatementHandlerInterceptor" />
        <plugin interceptor="com.sachin.common.mybatis.interceptor.ResultSetHandlerInterceptor" />
    </plugins>
```

默认时将limit/offset拼接到原SQL的末尾发送，使用者可以通过实现'com.qunar.base.meerkat.orm.mybatis.support.dialect.Dialect'接口定制该行为，并调整配置，如：

```
    <plugins>
        <plugin interceptor="com.sachin.common.mybatis.interceptor.StatementHandlerInterceptor">
          <property name="dialectClass" value="your.package.name.dialect.OracleDialect"/>
        </plugin>
    </plugins>
```