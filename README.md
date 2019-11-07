# IaaS

本文档对云海IaaS系统的系统架构设计和各个模块分别加以说明

## 总体架构

![架构图](https://github.com/ShangfengDing/IaaS/blob/master/frame.jpg)

在上图中，使用了三种不同颜色的连线，代表了模块间通信的不同方式。为了使上图更加简洁，忽略了Resource-Scheduler、Vm-Scheduler、Volume-Scheduler、Network-Provider、Image-Server模块与DB-Proxy模块的连线，实际上，这五个模块都涉及数据库读写。同样出于简洁的考虑，简略了LOL（日志）模块与其他模块的连线，在IaaS系统中，所有的模块都需要将日志通过RPC接口写到LOL。

1. IaaS Front
IaaS Front是用户门户，使用户通过浏览器就可以申请、操作、管理云资源，并记录用户的操作事件。IaaS Front的登录依赖于Account。
IaaS Front的主要功能包括：

    - 申请云主机，对云主机进行开机、关机、重启、删除、修改配置等基本操作，以及VNC远程访问、创建备份、系统重置等高级功能；
    - 申请云硬盘，对云硬盘进行挂载、删除等操作；
    - 创建和管理防火墙；
    - 记录操作日志，显示用户账户信息等；
    - 创建企业用户，企业用户管理；
    
2. IaaS Admin
IaaS Admin是管理员门户，使用管理员通过浏览器对IaaS平台进行全面的管理，包括：
    -监控IaaS平台主要功能模块的运行状态；
    -管理IaaS平台的资源（集群、节点、网络、模板等）；
    -管理IaaS平台的云产品（云主机、云硬盘）；
    -管理IaaS平台的用户和群组；
    -管理IaaS平台的计费，包括资源费用设置、套餐设置、交易管理等；
    -IaaS平台的运行管理，包括记录运行日志，设置告警；
    -IaaS平台的操作管理，包括管理员的设置，管理员操作日志的记录和查询等。
IaaS Admin的登录不依赖于Account。

   
