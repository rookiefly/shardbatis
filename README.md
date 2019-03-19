# shardbatis
Automatically exported from code.google.com/p/shardbatis

### 执行以下maven脚本install jsqlparser.jar依赖到本地仓库
```shell
mvn install:install-file -Dfile=./repository/net/sourceforge/jsqlparser/0.7.0/jsqlparser-0.7.0.jar -DgroupId=net.sourceforge \
    -DartifactId=jsqlparser -Dversion=0.7.0 -Dpackaging=jar
```

### 其他分库分表中间件推荐列表
- [sharding-jdbc](https://github.com/apache/incubator-shardingsphere) is a JDBC extension, provides distributed features such as sharding, read/write splitting and BASE transaction.
