## Change Log ##
2.0.0 beta:
  * 以插件形式支持mybatis3实现分表功能，对mybatis代码无侵入
  * 全部重构shardbatis代码和0.9系列无关联
0.9.3 beta:
  * bugfix 解决paramExpr值为空字符串时，解决参数报错问题
  * 支持将sqlmap参数传递给ShardingStrategy 接口
```
shardingParams='[{"paramExpr":"#parameter#","tableName":"my_table"}]'
```
0.9.2 beta:
  * ibatis原生api支持sharding功能
  * 增加对jackson的依赖
0.9 beta:
  * 单数据库水平切分