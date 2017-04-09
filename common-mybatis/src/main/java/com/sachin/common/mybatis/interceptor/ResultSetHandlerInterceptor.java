package com.sachin.common.mybatis.interceptor;

import com.sachin.common.mybatis.util.ReflectUtil;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.RowBounds;

import java.sql.Statement;
import java.util.Properties;

/**
 * 物理分页支持类
 * <p>
 * 必须与{@link StatementHandlerInterceptor}共同配置使用
 * <p>
 *
 * 在mybatis主配置文件进行如下配置
 *
 * <pre>
 * &lt;plugins&gt;
 *      &lt;plugin interceptor="com.sachin.common.mybatis.interceptor.ResultSetHandlerInterceptor"&gt;&lt;/plugin&gt;
 * &lt;/plugins&gt;
 * </pre>
 *
 * @author shicheng.zhang
 * @since 17-4-9 下午4:29
 * @see StatementHandlerInterceptor
 */
@Intercepts({ @Signature(type = ResultSetHandler.class, method = "handleResultSets", args = { Statement.class }) })
public class ResultSetHandlerInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        ResultSetHandler target = (ResultSetHandler) invocation.getTarget();
        RowBounds rowBounds = (RowBounds) ReflectUtil.getFieldValue(target, "rowBounds");

        if (rowBounds.getLimit() > RowBounds.NO_ROW_OFFSET && rowBounds.getLimit() < RowBounds.NO_ROW_LIMIT) {
            // 清除翻页参数，禁止FastResultSetHandler#skipRows跳过结果集
            ReflectUtil.setFieldValue(target, "rowBounds", RowBounds.DEFAULT);
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
