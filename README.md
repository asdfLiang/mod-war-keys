# mod-war-keys

这是一个用Spring Boot + MyBatis + JavaFx 写的魔兽争霸3改键器

# 功能说明

本修改器基于CustomKeys.txt文件开发，主要功能是解析CustomKeys.txt文件并提供快捷键修改功能。由于会修改CustomKeys.txt文件，建议使用前先进行备份。

# 使用说明

1. 将CustomKeys.txt文件(注意备份)放到游戏安装目录"\\Warcraft III Frozen Throne\\"下
2. 打开本修改器，对CustomKeys进行修改
3. 打开游戏魔兽争霸3，在游戏内进入"选项 -> 游戏性"菜单, 勾选"自定义快捷键"，改键即可生效

# 环境说明

1. JDK17
2. JavaFx SDK 20
3. sqlite3

# 部署说明

1. 下载并解压 JavaFx SDK 到本地，下载地址：https://openjfx.cn/
2. Idea添加VM参数：--module-path "E:\produce\javafx-sdk-20\lib" --add-modules javafx.controls,javafx.fxml，启动项目
3. 导入魔兽争霸的配置文件，如果没有，可以到front模块的resource文件夹下找到测试文件

# 模块说明

1. front：前端模块，构建JavaFx界面
2. back：后端模块，提供前端功能所需要的接口，包含快捷键、指令翻译修改等
3. dal：持久化模块，提供本地存储能力，主要用来保存文件导入记录、快捷键记录
4. translation：翻译模块，提供基于微软、DeepL、百度等翻译引擎的翻译服务，除了百度是看文档接的，其他都是直接看网页接口的~居然能使用
5. common：通用模块，提供各种通用工具

# 项目打包

目前可以打包成jar包使用了，启动时需要使用如下命令：

java [vm启动参数] -jar 文件名.jar

(vm启动参数：--module-path "JavaFx SDK的lib目录" --add-modules javafx.controls,javafx.fxml)

```shell
java --module-path "E:\produce\javafx-sdk-20\lib" --add-modules javafx.controls,javafx.fxml -jar front.jar
```

后面试试打成exe包，今天就到这

# 待开发内容

1. 支持功能键的输入
2. 支持按照"种族"过滤快捷键
3. 配置文件备份功能，本地备份
4. 冲突检测升级，现在是根据单位类型检测冲突，但实际上单独操作一个单位时，它的和另一个单位的快捷键不会冲突
5. 日志配置
6. 样式单独写到css文件