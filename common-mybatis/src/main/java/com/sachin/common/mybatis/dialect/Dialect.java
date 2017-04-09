package com.sachin.common.mybatis.dialect;

/**
 * 数据库翻页参数生成接口。
 *
 * @author shicheng.zhang
 * @since 17-4-9 下午4:13
 */
public interface Dialect {

    /**
     * 拼接SQL的 limit 及 offset 数据
     *
     * @param sql 原始SQL
     * @param offset 偏移量
     * @param limit 获取记录数
     * @return 拼接完成的SQL
     */
    String getLimitString(String sql, int offset, int limit);
}
