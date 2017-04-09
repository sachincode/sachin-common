package com.sachin.common.mybatis.dialect;

/**
 * Dialect接口的MySQL实现，用于提供物理分页。
 *
 * @author shicheng.zhang
 * @since 17-4-9 下午4:16
 */
public class MysqlDialect implements Dialect {

    /**
     * SQL结束标记.
     */
    protected static final String SQL_END_DELIMITER = ";";

    @Override
    public String getLimitString(String sql, int offset, int limit) {
        if (sql.endsWith(SQL_END_DELIMITER)) {
            CharSequence _sql = sql.subSequence(0, sql.length() - SQL_END_DELIMITER.length());
            return append(new StringBuilder(_sql), offset, limit).append(SQL_END_DELIMITER).toString();
        }
        return append(new StringBuilder(sql), offset, limit).toString();
    }

    private StringBuilder append(StringBuilder builder, int offset, int limit) {
        builder.append(" limit ").append(limit);
        if (offset > 0) {
            builder.append(" offset ").append(offset);
        }
        return builder;
    }
}
