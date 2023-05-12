# mod-war-keys
这是一个用Spring Boot + myBatis + javaFx 写的魔兽争霸3改键器

# 部署说明
1. 下载javaFx SDK, 下载地址：https://openjfx.cn/
2. 解压sdk到本地
3. 启动像目前, 添加VM参数：--module-path "E:\produce\javafx-sdk-20\lib" --add-modules javafx.controls,javafx.fxml
4. 启动项目
5. 导入魔兽争霸的配置文件，如果没有，可以到front模块的resource文件夹下找到测试文件

# 模块说明
1. front：前端模块，构建javaFx界面;
2. back：后端模块，提供改键、自动机翻及手动修改翻译等服务;
3. dal：持久化模块，提供基于mybatis + sqlite数据库的持久化服务，导入记录、快捷键记录等功能;
4. translation：翻译模块，提供基于微软、DeepL、百度等翻译引擎的翻译服务;
5. common：通用模块，提供各种通用工具;
