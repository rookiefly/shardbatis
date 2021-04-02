[![Build Status](https://img.shields.io/badge/build-passing-brightgreen)](https://github.com/rookiefly/shardbatis)
[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)
[![Stars](https://img.shields.io/github/stars/rookiefly/shardbatis)](https://img.shields.io/github/stars/rookiefly/shardbatis)
[![Forks](https://img.shields.io/github/forks/rookiefly/shardbatis)](https://img.shields.io/github/forks/rookiefly/shardbatis)
![](https://img.shields.io/badge/build-release-brightgreen.svg)

# shardbatis
基于mybatis分表插件，优势：轻量、简单，插拔方便、兼容性比较好；可支持分库的二次开发。

兼容所有基于mybatis开发的程序，不影响原来的代码逻辑，即与开发人员的开发代码是分离的、独立的。

分表策略灵活，配置简单；可针对具体业务模块和单个表进行分表设置。

支持mybatis最新版本。

### 提醒
源码改动，jar没有提交到maven中央仓库，请自行打包。

### wiki page
[ProjectHome](https://github.com/rookiefly/shardbatis/wiki/ProjectHome)

### 其他分库分表中间件推荐列表
- [Sharding-JDBC](https://github.com/apache/incubator-shardingsphere) JDBC extension
- [Mycat-Server](https://github.com/MyCATApache/Mycat-Server) Mysql proxy
