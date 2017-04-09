package com.sachin.common.mybatis.interceptor;

import com.sachin.common.mybatis.util.ReflectUtil;
import com.sachin.common.mybatis.dialect.MysqlDialect;
import com.sachin.common.mybatis.dialect.Dialect;
import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.RowBounds;

import java.sql.Connection;
import java.util.Properties;

/**
 * 物理分页支持类
 * <p>
 * 必须与{@link ResultSetHandlerInterceptor}共同配置使用
 * <p>
 * 在mybatis主配置文件进行如下配置
 * <pre>
 * &lt;plugins&gt;
 *      &lt;plugin interceptor="com.sachin.common.mybatis.interceptor.StatementHandlerInterceptor"&gt;&lt;/plugin&gt;
 * &lt;/plugins&gt;
 * </pre>
 * 这里默认使用{@link MysqlDialect}进行SQL处理，使用者可自行实现{@link Dialect}接口以应对不同的数据库的SQL规则，并在配置项中指定实现类。
 * <pre>
 * &lt;plugins&gt;
 *      &lt;plugin interceptor="com.sachin.common.mybatis.interceptor.StatementHandlerInterceptor"&gt;
 *          &lt;property name="dialectClass" value="com.sachin.common.mybatis.dialect.OracleDialect"/&gt;
 *      &lt;/plugin&gt;
 * &lt;/plugins&gt;
 * </pre>
 *
 * @author shicheng.zhang
 * @since 17-4-9 下午4:10
 *
 * @see ResultSetHandlerInterceptor
 */
@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
public class StatementHandlerInterceptor implements Interceptor {

    /** 分页处理程序 */
    private Dialect dialect;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 获取处理目标
        StatementHandler target = (StatementHandler) invocation.getTarget();
        if (target instanceof RoutingStatementHandler) {
            target = (BaseStatementHandler) ReflectUtil.getFieldValue(target, "delegate");
        }
        RowBounds rowBounds = (RowBounds) ReflectUtil.getFieldValue(target, "rowBounds");

        // 调整查询字符串
        if (rowBounds.getLimit() > RowBounds.NO_ROW_OFFSET && rowBounds.getLimit() < RowBounds.NO_ROW_LIMIT) {
            BoundSql boundSql = target.getBoundSql();
            String sql = boundSql.getSql();

            sql = dialect.getLimitString(sql, rowBounds.getOffset(), rowBounds.getLimit());
            ReflectUtil.setFieldValue(boundSql, "sql", sql);
        }

        // 执行查询处理
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        String dialectClass = properties.getProperty("dialectClass");

        // 初始化物理查询处理程序
        if (dialectClass == null || dialectClass.isEmpty()) {
            dialect = new MysqlDialect();
        } else {
            try {
                dialect = (Dialect) Class.forName(dialectClass).newInstance();
            } catch (Exception e) {
                throw new IllegalArgumentException("Invalid dialect class " + dialectClass, e);
            }
        }
    }
}
