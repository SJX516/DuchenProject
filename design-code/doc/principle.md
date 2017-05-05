# Head First 设计模式

## 设计模式

- **策略模式** 定义了算法族，分别封装起来，让它们之间可以互相替换，此模式让算法的变化独立于使用算法的客户。

- **观察者模式** 定义了对象之间的一对多依赖，这样一来，当一个对象改变状态时，它的所有依赖者都会收到通知并自动更新。
![observer](http://note.youdao.com/yws/public/resource/d5272cb82a3d24975b3a1798e08f0c43/xmlnote/WEBRESOURCE6093b84e2a557e5f6a7392513782d08e/1723)

- **装饰者模式** 动态地将责任附加到对象上。若要扩展功能，装饰者提供了比继承更有弹性的替代方案。
![decorator](http://note.youdao.com/yws/public/resource/d5272cb82a3d24975b3a1798e08f0c43/xmlnote/WEBRESOURCEe18537b3d0a3a8f9a4152bd941d5e496/1725)

- **工厂方法模式** 定义了一个创建对象的接口,但由子类决定要实例化的类是哪一个。工厂方法让类把实例化推迟到子类。
![factoryMethod](http://note.youdao.com/yws/public/resource/d5272cb82a3d24975b3a1798e08f0c43/xmlnote/WEBRESOURCEdc90c73693c9d6c4eddad2bf67ad2188/1717)

- **抽象工厂模式** 提供一个接口,用于创建相关或依赖对象的家族,而不需要明确指定具体类
![absFactory](http://note.youdao.com/yws/public/resource/d5272cb82a3d24975b3a1798e08f0c43/xmlnote/WEBRESOURCE9772fc9950a73f76aa20456de90bbf1c/1720)

    - 所有的工厂都是用来封装对象的创建,以便将代码从具体类解耦

- **单例模式** 确保一个类只有一个实例,并提供一个全局访问点
![singleton](http://note.youdao.com/yws/public/resource/d5272cb82a3d24975b3a1798e08f0c43/xmlnote/WEBRESOURCE193d5f5faa6f3dba521899b39f804a70/1728)

- **命令模式** 将"请求"封装成对象,以便使用不同的请求、队列或者日志来参数化其他对象。命令模式也支持可撤销的操作。
![command](http://note.youdao.com/yws/public/resource/d5272cb82a3d24975b3a1798e08f0c43/xmlnote/WEBRESOURCEeba73f358486584a40d43099d3db9443/1730)

    - 命令模式将发出请求的对象和执行请求的对象解耦
    - 被解耦的两者之间通过命令对象进行沟通,命令对象封装了接受者和一个或一组动作

## 设计原则

- 找出应用中可能需要变化之处，把他们独立出来，不需要和那些不需要变化的代码混在一起

- 针对接口编程，而不是针对实现编程

- 多用组合，少用继承

- 为了交互对象之间的松耦合设计而努力

- 类应该对扩展开放，对修改关闭

- 要依赖抽象，不要依赖具体类 (不能让高层组件依赖低层组件,而且不管高层或低层组件,都应该依赖于抽象)