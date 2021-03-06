# Head First 设计模式

## 设计模式

- **策略模式** 定义了算法族，分别封装起来，让它们之间可以互相替换，此模式让算法的变化独立于使用算法的客户。

- **观察者模式** 定义了对象之间的一对多依赖，这样一来，当一个对象改变状态时，它的所有依赖者都会收到通知并自动更新。
![observer](http://note.youdao.com/yws/public/resource/d5272cb82a3d24975b3a1798e08f0c43/xmlnote/WEBRESOURCE6093b84e2a557e5f6a7392513782d08e/1723)

- **装饰者模式** 动态地将责任附加到对象上。若要扩展功能，装饰者提供了比继承更有弹性的替代方案。

    - 不改变接口,但加入责任

    ![decorator](http://note.youdao.com/yws/public/resource/d5272cb82a3d24975b3a1798e08f0c43/xmlnote/WEBRESOURCEe18537b3d0a3a8f9a4152bd941d5e496/1725)

- **工厂方法模式** 定义了一个创建对象的接口,但由子类决定要实例化的类是哪一个。工厂方法让类把实例化推迟到子类。
![factoryMethod](http://note.youdao.com/yws/public/resource/d5272cb82a3d24975b3a1798e08f0c43/xmlnote/WEBRESOURCEdc90c73693c9d6c4eddad2bf67ad2188/1717)

- **抽象工厂模式** 提供一个接口,用于创建相关或依赖对象的家族,而不需要明确指定具体类
![absFactory](http://note.youdao.com/yws/public/resource/d5272cb82a3d24975b3a1798e08f0c43/xmlnote/WEBRESOURCE9772fc9950a73f76aa20456de90bbf1c/1720)

    - 所有的工厂都是用来封装对象的创建,以便将代码从具体类解耦

- **单例模式** 确保一个类只有一个实例,并提供一个全局访问点
![singleton](http://note.youdao.com/yws/public/resource/d5272cb82a3d24975b3a1798e08f0c43/xmlnote/WEBRESOURCE193d5f5faa6f3dba521899b39f804a70/1728)

- **命令模式** 将"请求"封装成对象,以便使用不同的请求、队列或者日志来参数化其他对象。命令模式也支持可撤销的操作。

    - 命令模式将发出请求的对象和执行请求的对象解耦
    - 被解耦的两者之间通过命令对象进行沟通,命令对象封装了接受者和一个或一组动作

    ![command](http://note.youdao.com/yws/public/resource/d5272cb82a3d24975b3a1798e08f0c43/xmlnote/WEBRESOURCEeba73f358486584a40d43099d3db9443/1730)

- **适配器模式** 将一个类的接口,转换成客户期望的另一个接口。适配器让原本接口不兼容的类可以合作无间。

    - 将一个接口转成另一个接口

    ![adapter](http://note.youdao.com/yws/public/resource/d5272cb82a3d24975b3a1798e08f0c43/xmlnote/WEBRESOURCE97feb03a8bc50b24c0c97a691cc48d68/1733)

- **外观模式** 提供了一个统一的接口,用来访问子系统中的一群接口。外观定义了一个高层接口,让子系统更容易使用。

    - 外观不只是简化了接口,也将客户从组件的子系统中解耦
    - 外观和适配器可以包装许多类,但是外观的意图是简化接口,而适配器的意图是将接口转换成不同接口

    ![facade](http://note.youdao.com/yws/public/resource/d5272cb82a3d24975b3a1798e08f0c43/xmlnote/WEBRESOURCEab4a45cf1753c2b1e47960f7685f7859/1734)

- **模板方法模式** 在一个方法中定义一个算法的骨架,而将一些步骤延迟到子类中。模板方法使得子类可以在不改变算法结构的情况下,重新定义算法中的某些步骤。

    ![templateMethod](http://note.youdao.com/yws/public/resource/d5272cb82a3d24975b3a1798e08f0c43/xmlnote/WEBRESOURCEcad1527160fb6f53c04943a5500c6402/1736)

- **迭代器模式** 提供一种方法顺序访问一个聚合对象中的各个元素,而又不暴露其内部的表示。

    ![iterator](http://note.youdao.com/yws/public/resource/d5272cb82a3d24975b3a1798e08f0c43/xmlnote/WEBRESOURCE889bae7dee730a718a0cda75a540fbf7/1740)

- **组合模式** 允许你将对象组合成树形结构来表现 "整体/部分" 层次结构。组合能让客户以一致的方式处理个别对象以及对象组合。

    ![composite](http://note.youdao.com/yws/public/resource/d5272cb82a3d24975b3a1798e08f0c43/xmlnote/WEBRESOURCEd542f114f1dc45a834230cf3fdeeae41/1739)

- **状态模式** 允许对象在内部状态改变时改变它的行为,对象看起来好像修改了它的类。

    ![state](http://note.youdao.com/yws/public/resource/d5272cb82a3d24975b3a1798e08f0c43/xmlnote/WEBRESOURCE947ba275f82dc77884e86f5497d4cefa/1791)

- **代理模式** 为另一个对象提供一个替身或占位符以控制对这个对象的访问。

    - 远程代理: 控制访问远程资源。远程代理可以作为另一个JVM上对象的本地代表。调用代理的方法,会被代理利用网络转发到远程执行,并且结果会通过网络返回给代理,再由代理将结果转给客户。
    - 虚拟代理: 控制访问创建开销大的资源。虚拟代理作为创建开销大的对象的代表。虚拟代理经常直到我们真正需要一个对象的时候才创建它。当对象在创建前和创建中时,由虚拟代理来扮演对象的替身。
    对象创建后,代理就会将请求直接委托给对象。
    - 保护代理: 基于权限控制对资源的访问。

    ![proxy](http://note.youdao.com/yws/public/resource/d5272cb82a3d24975b3a1798e08f0c43/xmlnote/WEBRESOURCE05a2c4a152043d227adc54a4b9bb3069/1794)

- **复合模式** 由多种模式复合而成的模式(Compound Pattern)

- **MVC** (模型-视图-控制器) 典型的复合模式

    - 策略模式: 视图和控制器实现了经典的策略模式,视图是一个对象,可以被调整使用不同的策略,而控制器提供了策略。
    - 组合模式: 每个视图组件如果不是组合节点(例如窗口),就是叶节点(例如按钮)。当控制器告诉视图更新时,只需告诉视图最顶层的组件即可。
    - 观察者模式: 模型实现了,当状态改变时,相关对象将持续更新。使用观察者模式,可以让模型完全独立于视图和控制器。

## 设计原则

- 找出应用中可能需要变化之处，把他们独立出来，不需要和那些不需要变化的代码混在一起

- 针对接口编程，而不是针对实现编程

- 多用组合，少用继承

- 为了交互对象之间的松耦合设计而努力

- 类应该对扩展开放，对修改关闭

- 要依赖抽象，不要依赖具体类 (不能让高层组件依赖低层组件,而且不管高层或低层组件,都应该依赖于抽象)

- 最少知识(Least Knowledge)原则: 我们要减少对象之间的交互,只和你的密友说话(不要让太多的类耦合在一起)
    - 不要调用从另一个调用中返回的对象的方法(减少你认识的对象)

- 好莱坞原则: 别调用(打电话给)我们,我们会调用(打电话给)你
    - 允许低层组件将自己的部分细节挂钩到系统上,但是高层组件会决定什么时候和怎样使用这些低层组件

- 一个类应该只有一个引起变化的原因








