本例实现了SAP -- JAVA的双向连接调用：

通过JCoServer，SAP可以在调用Java函数库中动态定义的function（或SAP中已定义的RFC）后，让Java端给出对应处理和反馈。

通过JCoFunction,在Java端可以调用SAP端的RFC/BAPI等。

通过JCoIDocServer，可以实现SAP与Java互通IDOC数据文件。

需要注意的是：

JCoServer（JCoIDocServer）和JCoFunction的连接，均建立在JCo destination的连接基础上，

JCoIDocServer继承于JCoServe，专门用来处理IDoc相关业务。

接口固定参数（表）约定：

1、import 参数

    E_MSGID	TYPE	ZZT_IF000_LOG-MSGID	    消息标识
    
    E_SENDR	TYPE	ZZT_IF000_LOG-SENDR	    发送方
    
2、export 参数

    I_MSGID	TYPE	ZZT_IF000_LOG-MSGID	    消息标识
    
3、table  参数
    
    T_RETURN	LIKE	ZZS_IF001_RETURN	接口平台--接口返回结构
    
    
4、JAVA端DTO字段名与SAP端字段名最好一致


关于JCO：

SAP的R/3系统与Java平台一样有着许多类似的技术理念，以及同样广泛的企业级用户，但是它们完全是两个不同的世界。当用户面临流程或者数据整合方面的需求的时候，就迫切需要一种高效的方式，在R/3系统和Java平台之间实时地交换数据。基于这样的需求，SAP提供了一套高效的基于RFC的ABAP和Java进程间通讯组件：SAP Java Connector(JCo)。
JCo库提供了可以直接在Java程序中使用的API。该API通过JNI调用部署在客户端的SAP的RFC库。该RFC库是用C语言实现的，并且与JCo库相互独立发布。但是，在下载的JCo库压缩包中也会提供。
该组件支持Inbound（在Java代码中调用ABAP函数）和Outbound（在ABAP代码中调用Java）两种模式。

关于sapjco3.jar，本地library的方式导入

version : 3.0.14 ，lib中对应的dll文件需要放在java jdk安装路径的bin目录内，否则会报错无法启动

关于sapidoc3.jar，本地library的方式导入

version : 3.0.12,需要sapjco3.jar至少也是3.0.12版本才能驱动

注：上述两个jar包在本地导入时只能用原名，切勿改名，否则会报错无法启动


端口号约定：

1、eurake-server

    8761-8800

2、eurake-client

    4000-4999
    
3、eureka-discovery

    5000-6000

4、zuul

    8080
    
5、springcloud config

    9000-9100

