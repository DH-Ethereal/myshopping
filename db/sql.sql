drop database if exists myshopping;
create database myshopping;

use myshopping;

drop table if exists tbl_car;

drop table if exists tbl_comment;

drop table if exists tbl_order;

drop table if exists tbl_order_desc;

drop table if exists tbl_book;

drop table if exists tbl_user;


create table if not exists tbl_user
(
    user_id       int auto_increment
        primary key,
    user_name     varchar(20)   not null,
    user_password varchar(100)  not null,
    user_email    varchar(50)   null,
    user_birthday date          null,
    user_hobbys   varchar(255)  null,
    user_sex      int           null,
    user_address  varchar(100)  null,
    user_status   int default 0 null,
    constraint userName
        unique (user_name)
);

create table if not exists tbl_book
(
    book_id    int auto_increment
        primary key,
    book_name  varchar(50)   not null,
    book_price decimal(5, 2) not null,
    book_image varchar(50)   not null,
    book_desc  text          not null
);

create table if not exists tbl_car
(
    car_id    int auto_increment
        primary key,
    book_id   int not null,
    car_count int not null,
    user_id   int null
);

create table if not exists tbl_comment
(
    comment_id      int auto_increment
        primary key,
    user_id         int      not null,
    book_id         int      not null,
    comment_message text     not null,
    comment_time    datetime not null,
    foreign key (user_id) references tbl_user (user_id),
    foreign key (book_id) references tbl_book (book_id)
);

create table if not exists tbl_order
(
    order_id          varchar(200)  not null
        primary key,
    user_id           int           not null,
    order_to_name     varchar(50)   not null,
    order_to_address  varchar(255)  not null,
    order_to_phone    varchar(20)   not null,
    order_time        datetime      not null,
    order_status      int default 0 not null comment '0表示未发货
1表示已发货
2表示交易成功
-1表示交易失败',
    order_total_price float         not null,
    foreign key (user_id) references tbl_user (user_id)
);

create index fk_user_id
    on tbl_order (user_id);

create table if not exists tbl_order_desc
(
    desc_id    int auto_increment
        primary key,
    order_id   varchar(200) not null,
    book_id    int          not null,
    desc_count int          not null,
    foreign key (book_id) references tbl_book (book_id),
    foreign key (order_id) references tbl_order (order_id)
);

create index fk_book_id
    on tbl_order_desc (book_id);

create index fk_order_id
    on tbl_order_desc (order_id);

create definer = root@localhost trigger trig_order_desc_insert
    after insert
    on tbl_order_desc
    for each row
begin
    declare user_id int(11);
    select user_id into user_id from tbl_order where order_id = new.order_id;
    delete from tbl_car where user_id = user_id and book_id = new.book_id;
end;


INSERT INTO myshopping.tbl_book (book_id, book_name, book_price, book_image, book_desc)
VALUES (1, '疯狂Java讲义', 69.30, '20376113-1_l.jpg',
        '从2000年至今，Java语言一直是应用最广的开发语言，并拥有最广泛的开发人群。如今，Java已经不再简单地是一门语言，它更像一个完整的体系，一个系统的开发平台。更甚至，它被延伸成一种开源精神。');
INSERT INTO myshopping.tbl_book (book_id, book_name, book_price, book_image, book_desc)
VALUES (2, 'JavaScript高级程序设计', 66.80, '20884225-1_l.jpg',
        '本书在上一版基础上进行了大幅度更新和修订，融入了近几年来JavaScript应用发展的最新成果，几乎涵盖了所有需要理解的重要概念和最新的JavaScript应用成果。');
INSERT INTO myshopping.tbl_book (book_id, book_name, book_price, book_image, book_desc)
VALUES (3, 'Head First Java (中文版)', 53.50, '9265169-1_l.jpg',
        '《Head First Java》是完整的面向对象程序设计和Java的学习指导。此书是根据学习理论所设计的，让你开源从学习程序语言的基础开始一直到包括线程、网络与分布式程序等项目。');
INSERT INTO myshopping.tbl_book (book_id, book_name, book_price, book_image, book_desc)
VALUES (4, 'Java从入门到精通', 44.60, '20810282-1_l.jpg',
        '本书以零基础讲解为宗旨，用实例引导读者深入学习，采取【基础知识→核心技术→高级应用→项目实战】的讲解模式，深入浅出地讲解Java的各种技术及实战技能。');
INSERT INTO myshopping.tbl_book (book_id, book_name, book_price, book_image, book_desc)
VALUES (5, 'Java核心技术:卷1基础知识', 65.70, '20285763-1_l.jpg',
        '《Java核心技术》出版以来一直畅销不衰，深受读者青睐，每个新版本都尽可能快地跟上Java开发工具箱发展的步伐，而且每一版都重新改写了部分内容，以便应用Java的最新特性。');
INSERT INTO myshopping.tbl_book (book_id, book_name, book_price, book_image, book_desc)
VALUES (6, '疯狂Ajax讲义', 51.80, '20549981-1_l.jpg', '本书是《基于J2EE的Ajax宝典》的第二版，《基于J2EE的Ajax宝典》面市近2年，作为Ajax领域最全面、实用的图书，一直深受读者的好评。　　全书主要分为三个部分。
');
INSERT INTO myshopping.tbl_book (book_id, book_name, book_price, book_image, book_desc)
VALUES (7, 'JavaScript权威指南(第五版)', 81.70, '20019046-1_l.jpg', '《JavaScript权威指南第4版》中文版出版至今已有三年多的时间。这本《JaVaScript权威指南》连续印刷5次，销售数十万册，成为很多JavaScript学习者的必备宝典。由于其封面上是一只“爪哇犀牛”（封面上...
');
INSERT INTO myshopping.tbl_book (book_id, book_name, book_price, book_image, book_desc)
VALUES (8, 'jQuery权威指南', 40.50, '21006995-1_l.jpg', '本书由国内资深web技术专家亲自执笔，4大web开发社区一致鼎力推荐，权威性毋庸置疑。　　内容新颖，基于jquery的最新版本撰写，所有新功能和新特性一览无余；内容全面，不仅讲解了jquery技术本身的方方...
');
INSERT INTO myshopping.tbl_book (book_id, book_name, book_price, book_image, book_desc)
VALUES (9, 'Java程序设计(第二版)', 21.40, '9336332-1_l.jpg', '本书是《Java程序设计》的第二版。它从Java语言的基本特点入手，全面介绍了Java语言的基本概念和编程方法，并深入介绍了Java的高级特性。本书共分为12章，涉及Java中的基本语法、数据类型、类、异常、...
');
INSERT INTO myshopping.tbl_book (book_id, book_name, book_price, book_image, book_desc)
VALUES (10, 'JAVA核心技术卷II：高级特性（原书第8版）', 80.60, '20446562-1_l.jpg', '本书是Java技术权威指南，全面覆盖Java技术的高级主题，包括流与文件、XML、网络、数据库编程、高级Swing、高级 AWT、JavaBean构件、安全、分布式对象、脚本、编译与注解处理等，同时涉及本地化、国际...
');
INSERT INTO myshopping.tbl_book (book_id, book_name, book_price, book_image, book_desc)
VALUES (11, 'Spring in Action（第二版）中文版', 44.60, '20345173-1_l.jpg', '本书分成三大部分并附有两个附录，每一部分分别对应Spring框架的一个领域：核心框架、业务和数据层、表示层。每一部分既是建立在前一部分的基础上，同时也可以独立存在。因此，读者不必从头开始，完全...
');
INSERT INTO myshopping.tbl_book (book_id, book_name, book_price, book_image, book_desc)
VALUES (12, 'Tomcat与Java Web开发技术详解（第2版）(含光盘1张) ', 61.00, '20420983-1_l.jpg', '本书结合最新的Tomcat 6版本，详细介绍了开发Java Web应用的各种技术。主要内容包括：Tomcat和Java Web开发的基础知识、Java Web开发的高级技术、在Java Web应用中运用第三方提供的实用软件（如Struts...
');
INSERT INTO myshopping.tbl_book (book_id, book_name, book_price, book_image, book_desc)
VALUES (13, 'Java程序员面试宝典（第2版）', 31.30, '21127179-1_l.jpg', '本书是《Java程序员面试宝典》的第2版。第2版在保留第1版数据结构、字符串处理、Java程序设计等主干内容的基础上，大量更新了程序面试题目，内容主要取材于2009年到2011年各大公司的面试题，以反...
');
INSERT INTO myshopping.tbl_book (book_id, book_name, book_price, book_image, book_desc)
VALUES (14, '高性能JavaScript', 36.80, '20967330-1_l.jpg', '如果你使用JavaScript构建交互丰富的Web应用，那么JavaScript代码可能是造成你的Web应用速度变慢的主要原因。本书揭示的技术和策略能帮助你在开发过程中消除性能瓶颈。你将会了解如何提升各方面的性能...
');
INSERT INTO myshopping.tbl_book (book_id, book_name, book_price, book_image, book_desc)
VALUES (15, 'Java项目开发案例全程实录（第2版）（配光盘）（软件项目开发全 ', 52.30, '20991549-1_l.jpg', '本书以进销存管理系统、企业内部通信系统、企业人事管理系统、酒店管理系统、图书馆管理系统、企业快信、欣想电子商城、医药管理系统、企业门户网站、BBS系统、手机网络游戏和棋牌游戏系统之网络...
');
INSERT INTO myshopping.tbl_book (book_id, book_name, book_price, book_image, book_desc)
VALUES (16, '深入浅出JavaScript（中文版）', 73.30, '20958893-1_l.jpg', '你将从本书学到什么?这么说你准备从写html和css的静态网页跃进到编写动态网络应用程序脚本了?这里就是起点。《深入浅出java script》带你游历令人兴奋的交互式网页创建过程。为了启发你的思考，本书覆...
');
INSERT INTO myshopping.tbl_book (book_id, book_name, book_price, book_image, book_desc)
VALUES (17, 'Java语言程序设计：基础篇（原书第8版）', 56.20, '21117631-1_l.jpg', '《java语言程序设计.基础篇(原书第8版)》是java语言的经典教材，中文版分为《java语言程序设计基础篇》和《java语言程序设计进阶篇》，主要介绍程序设计基础、面向对象程序设计、gui程序设计、...
');
INSERT INTO myshopping.tbl_book (book_id, book_name, book_price, book_image, book_desc)
VALUES (18, '经典Java EE企业应用实战——基于WebLogic/JBoss的JSF+EJB 3+JPA ', 60.60, '20928547-1_l.jpg',
        '本书是《轻量级Java EE企业应用实战》的姊妹篇，《轻量级Java EE企业应用实战》主要介绍以Spring+Hibernate为基础的Java EE应用；本书则主要介绍以EJB 3+JPA为基础的Java EE应用。EJB 3、JPA规');
INSERT INTO myshopping.tbl_book (book_id, book_name, book_price, book_image, book_desc)
VALUES (19, 'Java编程思想（英文版·第4版）', 56.10, '9288920-1_l.jpg', '本书特色：　　·适合初学者与专业人员的经典的面向对象的叙述方式；为更新的Java SE5/6的相关内容增加了新的示例和章节。　　·测验框架显示程序输出。　　·设计模式贯穿于众多示例中：适配器、桥...
');
INSERT INTO myshopping.tbl_book (book_id, book_name, book_price, book_image, book_desc)
VALUES (20, 'JAVA面向对象编程', 49.50, '9186890-1_l.jpg', '本书内容由浅入深，紧密结合实际，利用大量典型实例，详细讲解Java面向对象的编程思想、编程语法和设计模式，介绍常见Java类库的用法，总结优化Java编程的各种宝贵经验，深入阐述Java虚拟机执行Java程...
');
INSERT INTO myshopping.tbl_book (book_id, book_name, book_price, book_image, book_desc)
VALUES (21, 'Java程序设计实用教程（第3版）', 26.20, '20791263-1_l.jpg', '本书为普通高等教育“十一五”国家级规划教材。　　本书全面、系统地介绍Java语言程序设计的基本知识、运行机制和各种常用编程方法和技术，将面向对象的程序设计思想贯穿其中，建立起牢固扎实的理...
');
INSERT INTO myshopping.tbl_book (book_id, book_name, book_price, book_image, book_desc)
VALUES (22, '解密搜索引擎技术实战：Lucene&Java精华版(含DVD光盘1张) ', 52.40, '21117052-1_l.jpg',
        '本书是猎兔搜索开发团队的软件研发和教学实践的经验汇总。　　本书总结搜索引擎相关理论与实际解决方案，并给出了Java实现，其中利用了流行的开源项目Lucene和Solr，而且还包括原创的实现。');
INSERT INTO myshopping.tbl_book (book_id, book_name, book_price, book_image, book_desc)
VALUES (23, '犀利开发——jQuery内核详解与实践（配光盘）（网站开发路线图） ', 36.80, '20938710-1_l.jpg', '本书循序渐进地讲解了jQuery高效开发的方法和技巧，内容包括jQuery框架的设计模式和思路、Sizzle选择器的构成和工作机制、DOM文档操作、事件处理、动画设计、Ajax异步通信、插件扩展和辅助工具等。 ...
');
INSERT INTO myshopping.tbl_book (book_id, book_name, book_price, book_image, book_desc)
VALUES (24, 'Java加密与解密的艺术', 51.80, '20813335-1_l.jpg', '本书是Java安全领域的百科全书，密码学领域的权威经典，4大社区一致鼎力推荐。 全书包含3个部分，基础篇对Java企业级应用的安全知识、密码学核心知识、与Java加密相关的API和通过权限文件加强系统安...
');
INSERT INTO myshopping.tbl_book (book_id, book_name, book_price, book_image, book_desc)
VALUES (25, 'jQuery基础教程(第2版)', 37.00, '20719847-1_l.jpg',
        '本书作为《jQuery基础教程》的升级版，涵盖了jQuery 1.3的全部新特性，特别是新增了介绍jQuery UI(jQuery官方用户界面插件库)的内容。本书前6章以通俗易懂的方式介绍了jQuery的基本概念，主要包括');
INSERT INTO myshopping.tbl_book (book_id, book_name, book_price, book_image, book_desc)
VALUES (26, '数据结构与算法分析Java语言描述 第2版', 38.50, '20417467-1_l.jpg', '本书是国外数据结构与算法分析方面的经典教材，使用卓越的Java编程语言作为实现工具讨论了数据结构（组织大量数据的方法）和算法分析（对算法运行时间的估计）。　　随着计算机速度的不断增加和功...
');
INSERT INTO myshopping.tbl_book (book_id, book_name, book_price, book_image, book_desc)
VALUES (27, '21天学通Java 6(第5版)', 33.80, '20443876-1_l.jpg', '本书包括3周的课程，通过学习，读者将具备使用Java开发应用程序、servlet和JSP的知识和技能。第1周介绍Java语言的基本知识，包括数据类型、变量、表达式、对象、数组、条件语句、循环、类、接口、包、...
');
INSERT INTO myshopping.tbl_book (book_id, book_name, book_price, book_image, book_desc)
VALUES (28, 'Java面向对象程序设计（21世纪高等学校计算机专业实用规划教材） ', 25.50, '20773224-1_l.jpg', 'Java语言具有面向对象、与平台无关、安全、稳定和多线程等优良特性，是目前软件设计中极为强大的编程语言。Java已成为网络时代最重要的语言之一。本书注重结合实例，以及重要的设计模式，循序渐进地向...
');
INSERT INTO myshopping.tbl_book (book_id, book_name, book_price, book_image, book_desc)
VALUES (29, 'jQuery全面提速', 39.00, '20856539-1_l.jpg', 'jQuery是一款轻量级的JavaScript库，它不仅功能强大，而且简洁优雅，能使用最少的代码来实现最多的功能，深受Web开发人员的青睐。本书通过大量实例循序渐进地讲解了jQuery的基本概念和使用技巧，前...
');
INSERT INTO myshopping.tbl_book (book_id, book_name, book_price, book_image, book_desc)
VALUES (30, 'Java语言程序设计：进阶篇（原书第8版）', 59.20, '21117631-1_l.jpg',
        '本书由Y.Daniel Liang所著，是 Java语言的经典教材，中文版分为《Java语言程序设计基础篇》和《Java语言程序设计进阶篇》，主要介绍程序设计基础、面向对象程序设计、GUI程序设计、算法和数据结');
INSERT INTO myshopping.tbl_book (book_id, book_name, book_price, book_image, book_desc)
VALUES (31, 'Java SOA Cookbook中文版', 59.20, '22469090-1_l.jpg', 'Java SOA Cookbook中文版》为需要在企业中实现面向服务架构 (SOA)的程序员提供了实用的解决方案和建议。《Java SOA Cookbook中文版》不只是给出一些概念性、宏观性的SO...
');
INSERT INTO myshopping.tbl_book (book_id, book_name, book_price, book_image, book_desc)
VALUES (32, '实战突击：Java Web项目整合开发(含DVD光盘1张) ', 67.30, '22477555-1_l.jpg', '本书以都市供求信息网、物流配货网、编程爱好者博客地带、明日知道、天下淘网络商城、成长在线考试网、企业物资管理系统、办公自动化管理系统、校园管理系统、高校学生选课系...
');
INSERT INTO myshopping.tbl_book (book_id, book_name, book_price, book_image, book_desc)
VALUES (33, 'JavaScript详解(第2版)', 74.20, '22541821-1_l.jpg', 'JavaScript是目前最流行和最重要的Web编程语言。《JavaScript详解(第2版)》从JavaScript的编程结构开始，循序渐进地讲述了JavaScript及相关的CSS、W3C DOM、cookie、正则表达式和匹配模式，以及功...
');
INSERT INTO myshopping.tbl_book (book_id, book_name, book_price, book_image, book_desc)
VALUES (34, ' JAVASCRIPT语言精髓与编程实践', 51.00, '20185383-1_l.jpg', '本书详细讲述JavaScript作为一种混合式语言的各方面特性，包括过程式、面向对象、函数式和动态语言特性等，在动态函数式语言特性方面有着尤为细致的讲述。本书的主要努力之一，就是分解出这些语言...
');
INSERT INTO myshopping.tbl_book (book_id, book_name, book_price, book_image, book_desc)
VALUES (35, 'Java大学实用教程（第2版）', 21.80, '20228875-1_l.jpg', '本书是《Java大学实用教程》的第2版，继续保留原教材的特点——注重教材的可读性和实用性。本书加大了面向对象的知识容量，对部分例题的内容做了调整，增加了一些新的知识内容和例题。考虑到Java与数据...
');

INSERT INTO myshopping.tbl_user (user_id, user_name, user_password, user_email, user_birthday, user_hobbys, user_sex,
                                 user_address, user_status)
VALUES (default, 'admin', '$2a$10$9pu9DPjnDwlG.OeXSZPZZe9raybcPH22xWRht1.tZzUbp8h.fjf5q', 'jm110@163.comn',
        '2021-04-01', '吃_逛街_游戏_睡觉_学习', 0, '西安市', 0);