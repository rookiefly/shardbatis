# shardbatis
基于mybatis分表插件，优势：轻量、简单，插拔方便、兼容性比较好；可支持分库的二次开发。
兼容所有基于mybatis开发的程序，不影响原来的代码逻辑，即与开发人员的开发代码是分离的、独立的。
分表策略灵活，配置简单；可针对具体业务模块和单个表进行分表设置。
支持mybatis最新版本。

### remind[提醒]
源码改动，jar没有提交到maven中央仓库，请自行打包。

### 执行以下maven脚本install jsqlparser.jar依赖到本地仓库
```shell
mvn install:install-file -Dfile=./repository/net/sourceforge/jsqlparser/0.7.0/jsqlparser-0.7.0.jar -DgroupId=net.sourceforge \
    -DartifactId=jsqlparser -Dversion=0.7.0 -Dpackaging=jar
```

### 其他分库分表中间件推荐列表
- [sharding-jdbc](https://github.com/apache/incubator-shardingsphere) is a JDBC extension, provides distributed features such as sharding, read/write splitting and BASE transaction.
