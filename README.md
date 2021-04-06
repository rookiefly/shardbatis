[![Build Status](https://img.shields.io/badge/build-passing-brightgreen)](https://github.com/rookiefly/shardbatis)
[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)
[![Stars](https://img.shields.io/github/stars/rookiefly/shardbatis)](https://img.shields.io/github/stars/rookiefly/shardbatis)
[![Forks](https://img.shields.io/github/forks/rookiefly/shardbatis)](https://img.shields.io/github/forks/rookiefly/shardbatis)
![](https://img.shields.io/badge/build-release-brightgreen.svg)

### Shardbatis

1. 基于mybatis分表插件，优势：轻量、简单，插拔方便、兼容性比较好；可支持分库的二次开发。

2. 兼容所有基于mybatis开发的程序，不影响原来的代码逻辑，即与开发人员的开发代码是分离的、独立的。

3. 分表策略灵活，配置简单；可针对具体业务模块和单个表进行分表设置。

### 添加maven依赖

```xml
<dependency>
    <groupId>com.rookiefly.open.shardbatis</groupId>
    <artifactId>shardbatis</artifactId>
    <version>2.2.0</version>
<dependency>
```

### WIKI

[ProjectHome](https://github.com/rookiefly/shardbatis/wiki/ProjectHome)

### 其他分库分表中间件推荐列表

- [Sharding-JDBC](https://github.com/apache/incubator-shardingsphere) JDBC extension
- [Mycat-Server](https://github.com/MyCATApache/Mycat-Server) Mysql proxy
