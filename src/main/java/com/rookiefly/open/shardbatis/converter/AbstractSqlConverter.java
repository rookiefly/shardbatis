package com.rookiefly.open.shardbatis.converter;

import com.rookiefly.open.shardbatis.builder.ShardConfigHolder;
import com.rookiefly.open.shardbatis.strategy.ShardStrategy;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.util.deparser.StatementDeParser;

/**
 * @author sean.he
 */
public abstract class AbstractSqlConverter implements SqlConverter {

    @Override
    public String convert(Statement statement, Object params, String mapperId) {
        return doDeParse(doConvert(statement, params, mapperId));
    }

    /**
     * 将Statement反解析为sql
     *
     * @param statement
     * @return String
     */
    protected String doDeParse(Statement statement) {
        StatementDeParser deParser = new StatementDeParser(new StringBuffer());
        statement.accept(deParser);
        return deParser.getBuffer().toString();
    }

    /**
     * 从ShardConfigFactory中查找ShardStrategy并对表名进行修改<br>
     * 如果没有相应的ShardStrategy则对表名不做修改
     *
     * @param tableName
     * @param params
     * @param mapperId
     * @return String
     */
    protected String convertTableName(String tableName, Object params,
                                      String mapperId) {
        ShardConfigHolder configFactory = ShardConfigHolder.getInstance();
        ShardStrategy strategy = configFactory.getStrategy(tableName);
        if (strategy == null) {
            return tableName;
        }
        return strategy.getTargetTableName(tableName, params, mapperId);
    }

    /**
     * 修改statement代表的sql语句
     *
     * @param statement
     * @param params
     * @param mapperId
     * @return Statement
     */
    protected abstract Statement doConvert(Statement statement, Object params,
                                           String mapperId);
}
