[TOC]

# 可见性修饰符

Kotlin中这些修饰符是与我们Java中的使用是有些不同的。在这个语言中默认的修饰符是`public`，这节约了很多的时间和字符。但是这里有一个详细的解释关于在Kotlin中不同的可见性修饰符是怎么工作的。

## 修饰符

### private

`private`修饰符是我们使用的最限制的修饰符。它表示它只能被自己所在的文件可见。所以如果我们给一个类声明为`private`，我们就不能在定义这个类之外的文件中使用它。

另一方面，如果我们在一个类里面使用了`private `修饰符，那访问权限就被限制在这个类里面了。甚至是继承这个类的子类也不能使用它。

所以一等公民，类、对象、接口……（也就是包成员）如果被定义为`private`，那么它们只会对被定义所在的文件可见。如果被定义在了类或者接口中，那它们只对这个类或者接口可见。

### protected

这个修饰符只能被用在类或者接口中的成员上。一个包成员不能被定义为`protected`。定义在一个成员中，就与Java中的方式一样了：它可以被成员自己和继承它的成员可见（比如，类和它的子类）。

### internal

如果是一个定义为`internal`的包成员的话，对所在的整个`module`可见。如果它是一个其它领域的成员，它就需要依赖那个领域的可见性了。比如，如果我们写了一个`private`类，那么它的`internal`修饰的函数的可见性就会限制与它所在的这个类的可见性。

我们可以访问同一个`module`中的`internal`修饰的类，但是不能访问其它`module`的。

> 什么是`module`
>
> > 根据Jetbrains的定义，一个`module`应该是一个单独的功能性的单位，它应该是可以被单独编译、运行、测试、debug的。根据我们项目不同的模块，可以在Android Studio中创建不同的`module`。在Eclipse中，这些`module`可以认为是在一个`workspace`中的不同的`project`。

### public

你应该可以才想到，这是最没有限制的修饰符。__这是默认的修饰符__，成员在任何地方被修饰为`public`，很明显它只限制于它的领域。一个定义为`public`的成员被包含在一个`private`修饰的类中，这个成员在这个类以外也是不可见的。



## 构造器

所有构造函数默认都是`public`的，它们类是可见的，可以被其它地方使用。我们也可以使用这个语法来把构造函数修改为`private`：

```kotlin
class C private constructor(a: Int) { ... }
```



## 润色我们的代码

我们已经准备好使用`public`来进行重构了，但是我们还有很多其它细节需要修改。比如，在`RequestForecastCommand`中，我们在构造函数中我们创建的属性`zipCode`可以定义为`private`：

```kotlin
class RequestForecastCommand(private val zipCode: String)
```

所作的事情就是我们创建了一个不可修改的属性zipCode，它的值我们只能去得到，不能去修改它。所以这个不大的改动让代码看起来更加清晰。如果我们在编写类的时候，你觉得某些属性因为是什么原因不能对别人可见，那就把它定义为`private`。

而且，在Kotlin中，我们不需要去指定一个函数的返回值类型，它可以让编译器推断出来。举个省略返回值类型的例子：

```kotlin
data class ForecastList(...) {
	fun get(position: Int) = dailyForecast[position]
	fun size() = dailyForecast.size()
}
```

我们可以省略返回值类型的典型情景是当我们要给一个函数或者一个属性赋值的时候。而不需要去写代码块去实现。

剩下的修改是相当简单的，你可以在代码库中去同步下来。



# Kotlin Android Extensions

另一个Kotlin团队研发的可以让开发更简单的插件是`Kotlin Android Extensions`。当前仅仅包括了view的绑定。这个插件自动创建了很多的属性来让我们直接访问XML中的view。这种方式不需要你在开始使用之前明确地从布局中去找到这些views。

这些属性的名字就是来自对应view的id，所以我们取id的时候要十分小心，因为它们将会是我们类中非常重要的一部分。这些属性的类型也是来自XML中的，所以我们不需要去进行额外的类型转换。

`Kotlin Android Extensions`的一个优点是它不需要在我们的代码中依赖其它额外的库。它仅仅由插件组层，需要时用于生成工作所需的代码，只需要依赖于Kotlin的标准库。

那它背后是怎么工作的？该插件会代替任何属性调用函数，比如获取到view并具有缓存功能，以免每次属性被调用都会去重新获取这个view。需要注意的是这个缓存装置只会在`Activity`或者`Fragment`中才有效。如果它是在一个扩展函数中增加的，这个缓存就会被跳过，因为它可以被用在`Activity`中但是插件不能被修改，所以不需要再去增加一个缓存功能。



## 怎么去使用Kotlin Android Extensions

如果你还记得，现在项目已经准备好去使用Kotlin Android Extensions。当我们创建这个项目，我们就已经在`build.gradle`中增加了这个依赖：

```groovy
buldscript{
	repositories {
		jcenter()
	}
	dependencies {
		classpath "org.jetbrains.kotlin:kotlin-android-extensions:$kotlin_version"
	}
}
```

唯一一件需要这个插件做的事情是在类中增加一个特定的"手工"`import`来使用这个功能。我们有两个方法来使用它：

#### `Activities`或者`Fragments`的`Android Extensions`

这是最典型的使用方式。它们可以作为`activity`或`fragment`的属性是可以被访问的。属性的名字就是XML中对应view的id。

我们需要使用的`import`语句以`kotlin.android.synthetic`开头，然后加上我们要绑定到Activity的布局XML的名字：

```kotlin
import kotlinx.android.synthetic.activity_main.*
```

此后，我们就可以在`setContentView`被调用后访问这些view。新的Android Studio版本中可以通过使用`include`标签在Activity默认布局中增加内嵌的布局。很重要的一点是，针对这些布局，我们也需要增加手工的import：

```kotlin
import kotlinx.android.synthetic.activity_main.*
import kotlinx.android.synthetic.content_main.*
```

#### `Views`的`Android Extensions`

前面说的使用还是有局限性的，因为可能有很多代码需要访问XML中的view。比如，一个自定义view或者一个adapter。举个例子，绑定一个xml中的view到另一个view。唯一不同的就是需要`import`：

```kotlin
import kotlinx.android.synthetic.view_item.view.*
```

如果我们需要一个adapter，比如，我们现在要从inflater的View中访问属性：

```kotlin
view.textView.text = "Hello"
```



## 重构我们的代码

现在是时候使用`Kotlin Android Extensions`来修改我们的代码了。修改相当简单。

我们从`MainActivity`开始。我们当前只是使用了`forecastList`的RecyclerView。但是我们可以简化一点代码。首先，为`activity_main`XML增加手工import：

```kotlin
import kotlinx.android.synthetic.activity_main.*
```

之前说过，我们使用id来访问views。所以我要修改`RecyclerView`的id，不使用下划线，让它更加适合Kotlin变量的名字。XML最后如下：

```xml
<FrameLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">
    <android.support.v7.widget.RecyclerView
        android:id="@+id/forecastList"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>
</FrameLayout>
```

然后现在，我们可以不需要`find`这一行了：

```kotlin
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    forecastList.layoutManager = LinearLayoutManager(this)
    ...
}
```

这已经是最小的简化了，因为这个布局非常简单。但是`ForecastListAdapter`也可以从这个插件中受益。这里你可以使用一个装置来绑定这些属性到view中，它可以帮助我们移除所有`ViewHolder`的`find`代码。

首先，为`item_forecast`增加手工导入：

```kotlin
import kotlinx.android.synthetic.item_forecast.view.*
```

然后现在我们可以在`ViewHolder`中使用包含在`itemView`中的属性。实际上你可以在任何view中使用这些属性，但是很显然如果view不包含要获取的子view就会奔溃。

现在我们可以直接访问view的属性了：

```kotlin
class ViewHolder(view: View, val itemClick: (Forecast) -> Unit) :
        RecyclerView.ViewHolder(view) {
    fun bindForecast(forecast: Forecast) {
        with(forecast){
	        Picasso.with(itemView.ctx).load(iconUrl).into(itemView.icon)
			itemView.date.text = date
			itemView.description.text = description
			itemView.maxTemperature.text = "${high.toString()}￿￿"
			itemView.minTemperature.text = "${low.toString()}￿￿"
			itemView.onClick { itemClick(forecast) }
		} 
	}
}
```

Kotlin Android Extensions插件帮助我们减少了很多模版代码，并且简化了我们访问view的方式。从库中检出最新的代码吧。



# Application单例化和属性的Delegated

我们很快要去实现一个数据库，如果我们想要保持我们代码的简洁性和层次性（而不是把所有代码添加到Activity中），我们就要需要有一个更简单的访问application context的方式。



## Applicaton单例化

按照我们在Java中一样创建一个单例最简单的方式：

```kotlin
class App : Application() {
    companion object {
        private var instance: Application? = null
	    fun instance() = instance!!
    }
    override fun onCreate() {
        super.onCreate()
        instance = this
	}
}
```

为了这个Application实例被使用，要记得在`AndroidManifest.xml`中增加这个`App`：

```xml
<application
    android:allowBackup="true"
    android:icon="@mipmap/ic_launcher"
    android:label="@string/app_name"
    android:theme="@style/AppTheme"
    android:name=".ui.App">
    ...
</application>
```

Android有一个问题，就是我们不能去控制很多类的构造函数。比如，我们不能初始化一个非null属性，因为它的值需要在构造函数中去定义。所以我们需要一个可null的变量，和一个返回非null值的函数。我们知道我们一直都有一个`App`实例，但是在它调用`onCreate`之前我们不能去操作任何事情，所以我们为了安全性，我们假设`instance()`函数将会总是返回一个非null的`app`实例。

但是这个方案看起来有点不自然。我们需要定义个一个属性（已经有了getter和setter），然后通过一个函数来返回那个属性。我们有其他方法去达到相似的效果么？是的，我们可以通过委托这个属性的值给另外一个类。这个就是我们知道的`委托属性`。



## 委托属性

我们可能需要一个属性具有一些相同的行为，使用`lazy`或者`observable`可以被很有趣地实现重用。而不是一次又一次地去声明那些相同的代码，Kotlin提供了一个委托属性到一个类的方法。这就是我们知道的`委托属性`。

当我们使用属性的`get`或者`set`的时候，属性委托的`getValue`和`setValue`就会被调用。

属性委托的结构如下：

```kotlin
class Delegate<T> : ReadWriteProperty<Any?, T> {
	fun getValue(thisRef: Any?, property: KProperty<*>): T {
		return ...
	}
	
	fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
		...
	}
}
```

这个T是委托属性的类型。`getValue`函数接收一个类的引用和一个属性的元数据。`setValue`函数又接收了一个被设置的值。如果这个属性是不可修改（val），就会只有一个`getValue`函数。

下面展示属性委托是怎么设置的：

```kotlin
class Example {
	var p: String by Delegate()
}
```

它使用了`by`这个关键字来指定一个委托对象。



## 标准委托

在Kotlin的标准库中有一系列的标准委托。它们包括了大部分有用的委托，但是我们也可以创建我们自己的委托。

#### Lazy

它包含一个lambda，当第一次执行`getValue`的时候这个lambda会被调用，所以这个属性可以被延迟初始化。之后的调用都只会返回同一个值。这是非常有趣的特性， 当我们在它们第一次真正调用之前不是必须需要它们的时候。我们可以节省内存，在这些属性真正需要前不进行初始化。

```kotlin
class App : Application() {
    val database: SQLiteOpenHelper by lazy {
		MyDatabaseHelper(applicationContext)
	}

	override fun onCreate() {
	    super.onCreate()
	    val db = database.writableDatabase
    }
}
```

在这个例子中，database并没有被真正初始化，直到第一次调用`onCreate`时。在那之后，我们才确保applicationContext存在，并且已经准备好可以被使用了。`lazy`操作符是线程安全的。

如果你不担心多线程问题或者想提高更多的性能，你也可以使用`lazy(LazyThreadSafeMode.NONE){ ... }`。

#### Observable

这个委托会帮我们监测我们希望观察的属性的变化。当被观察属性的`set`方法被调用的时候，它就会自动执行我们指定的lambda表达式。所以一旦该属性被赋了新的值，我们就会接收到被委托的属性、旧值和新值。

```kotlin
class ViewModel(val db: MyDatabase) {
	var myProperty by Delegates.observable("") {
	    d, old, new ->
	    db.saveChanges(this, new)
	}
}
```

这个例子展示了，一些我们需要关心的ViewMode，每次值被修改了，就会保存它们到数据库。

#### Vetoable

这是一个特殊的`observable`，它让你决定是否这个值需要被保存。它可以被用于在真正保存之前进行一些条件判断。

```kotlin
var positiveNumber = Delegates.vetoable(0) {
    d, old, new ->
	new >= 0
}
```

上面这个委托只允许在新的值是正数的时候执行保存。在lambda中，最后一行表示返回值。你不需要使用return关键字（实质上不能被编译）。

#### Not Null

有时候我们需要在某些地方初始化这个属性，但是我们不能在构造函数中确定，或者我们不能在构造函数中做任何事情。第二种情况在Android中很常见：在Activity、fragment、service、receivers……无论如何，一个非抽象的属性在构造函数执行完之前需要被赋值。为了给这些属性赋值，我们无法让它一直等待到我们希望给它赋值的时候。我们至少有两种选择方案。

第一种就是使用可null类型并且赋值为null，直到我们有了真正想赋的值。但是我们就需要在每个地方不管是否是null都要去检查。如果我们确定这个属性在任何我们使用的时候都不会是null，这可能会使得我们要编写一些必要的代码了。

第二种选择是使用`notNull`委托。它会含有一个可null的变量并会在我们设置这个属性的时候分配一个真实的值。如果这个值在被获取之前没有被分配，它就会抛出一个异常。

这个在单例App这个例子中很有用：

```kotlin
class App : Application() {
    companion object {
        var instance: App by Delegates.notNull()
	}
	
	override fun onCreate() {
        super.onCreate()
        instance = this
	}
}
```

#### 从Map中映射值

另外一种属性委托方式就是，属性的值会从一个map中获取value，属性的名字对应这个map中的key。这个委托可以让我们做一些很强大的事情，因为我们可以很简单地从一个动态地map中创建一个对象实例。如果我们import `kotlin.properties.getValue`，我们可以从构造函数映射到`val`属性来得到一个不可修改的map。如果我们想去修改map和属性，我们也可以import `kotlin.properties.setValue`。类需要一个`MutableMap`作为构造函数的参数。

想象我们从一个Json中加载了一个配置类，然后分配它们的key和value到一个map中。我们可以仅仅通过传入一个map的构造函数来创建一个实例：

```kotlin
import kotlin.properties.getValue

class Configuration(map: Map<String, Any?>) {
    val width: Int by map
    val height: Int by map
    val dp: Int by map
    val deviceName: String by map
}
```

作为一个参考，这里我展示下对于这个类怎么去创建一个必须要的map：

```kotlin
conf = Configuration(mapOf(
    "width" to 1080,
    "height" to 720,
    "dp" to 240,
    "deviceName" to "mydevice"
))
```



## 怎么去创建一个自定义的委托

先来说说我们要实现什么，举个例子，我们创建一个`notNull`的委托，它只能被赋值一次，如果第二次赋值，它就会抛异常。

Kotlin库提供了几个接口，我们自己的委托必须要实现：`ReadOnlyProperty`和`ReadWriteProperty`。具体取决于我们被委托的对象是`val`还是`var`。

我们要做的第一件事就是创建一个类然后继承`ReadWriteProperty`：

```kotlin
private class NotNullSingleValueVar<T>() : ReadWriteProperty<Any?, T> {

		override fun getValue(thisRef: Any?, property: KProperty<*>): T {
	        throw UnsupportedOperationException()
        }
           
		override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
		}
}
```

这个委托可以作用在任何非null的类型。它接收任何类型的引用，然后像getter和setter那样使用T。现在我们需要去实现这些函数。

- Getter函数 如果已经被初始化，则会返回一个值，否则会抛异常。
- Setter函数 如果仍然是null，则赋值，否则会抛异常。

```kotlin
private class NotNullSingleValueVar<T>() : ReadWriteProperty<Any?, T> {
    private var value: T? = null
    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return value ?: throw IllegalStateException("${desc.name} " +
                "not initialized")
	}
	
	override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
		this.value = if (this.value == null) value
		else throw IllegalStateException("${desc.name} already initialized")
	}
}
```

现在你可以创建一个对象，然后添加函数使用你的委托：

```kotlin
object DelegatesExt {
    fun notNullSingleValue<T>():
            ReadWriteProperty<Any?, T> = NotNullSingleValueVar()
}
```



## 重新实现Application单例化

在这个情景下，委托就可以帮助我们了。我们直到我们的单例不会是null，但是我们不能使用构造函数去初始化属性。所以我们可以使用`notNull`委托：

```kotlin
class App : Application() {
    companion object {
        var instance: App by Delegates.notNull()
    }

	override fun onCreate() {
	        super.onCreate()
	        instance = this
	}
}
```

这种情况下有个问题，我们可以在app的任何地方去修改这个值，因为如果我们使用`Delegates.notNull()`，属性必须是var的。但是我们可以使用刚刚创建的委托，这样可以多一点保护。我们只能修改这个值一次：

```kotlin
companion object {
	var instance: App by DelegatesExt.notNullSingleValue()
}
```

尽管，在这个例子中，使用单例可能是最简单的方法，但是我想用代码的形式展示给你怎么去创建一个自定义的委托。



# 创建一个SQLiteOpenHelper

如你所知，Android使用SQLite作为它的数据库管理系统。SQLite是一个嵌入app的一个数据库，它的确是非常轻量的。这就是为什么这是手机app的不错的选择。

尽管如此，它的操作数据库的API在Android中是非常原生的。你将会需要编写很多SQL语句和你的对象与`ContentValues`或者`Cursors`之间的解析过程。很感激的，联合使用Kotlin和Anko，我们可以大量简化这些。

当然，有很多Android中可以使用的关于数据库的库，多亏Kotlin的互操作性，所有这些库都可以正常使用。但是针对一个简单的数据库来说可以不使用任何它们，之后的一分钟之内你就可以看到。



## ManagedSqliteOpenHelper

Anko提供了很多强大的SqliteOpenHelper来可以大量简化代码。当我们使用一个一般的`SqliteOpenHelper`，我们需要去调用`getReadableDatabase()`或者`getWritableDatabase()`，然后我们可以执行我们的搜索并拿到结果。在这之后，我们不能忘记调用`close()`。使用`ManagedSqliteOpenHelper`我们只需要：

```kotlin
forecastDbHelper.use {
	...
}
```

在lambda里面，我们可以直接使用`SqliteDatabase`中的函数。它是怎么工作的？阅读Anko函数的实现方式真是一件有趣的事情，你可以从这里学到Kotlin的很多知识：

```kotlin
public fun <T> use(f: SQLiteDatabase.() -> T): T {
    try {
	    return openDatabase().f()
	} finally {
	    closeDatabase()
    }
}
```

首先，`use`接收一个`SQLiteDatabase`的扩展函数。这表示，我们可以使用`this`在大括号中，并且处于`SQLiteDatabase`对象中。这个函数扩展可以返回一个值，所以我们可以像这么做：

```kotin
val result = forecastDbHelper.use {
    val queriedObject = ...
    queriedObject
}
```

要记住，在一个函数中，最后一行表示返回值。因为T没有任何的限制，所以我们可以返回任何对象。甚至如果我们不想返回任何值就使用`Unit`。

由于使用了`try-finall`，`use`方法会确保不管在数据库操作执行成功还是失败都会去关闭数据库。

而且，在`sqliteDatabase`中还有很多有用的扩展函数，我们会在之后使用到他们。但是现在让我们先定义我们的表和实现`SqliteOopenHelper`。



## 定义表

创建几个`objects`可以让我们避免表名列名拼写错误、重复等。我们需要两个表：一个用来保存城市的信息，另一个用来保存某天的天气预报。第二张表会有一个关联到第一张表的字段。

`CityForecastTable`提供了表的名字还有需要列：一个id（这个城市的zipCode），城市的名称和所在国家。

```kotlin
object CityForecastTable {
    val NAME = "CityForecast"
    val ID = "_id"
    val CITY = "city"
    val COUNTRY = "country"
}
```

`DayForecast`有更多的信息，就如你下面看到的有很多的列。最后一列`cityId`，用来保持属于的城市id。

```kotlin
object DayForecastTable {
	val NAME = "DayForecast"
	val ID = "_id"
	val DATE = "date"
	val DESCRIPTION = "description"
	val HIGH = "high"
	val LOW = "low"
	val ICON_URL = "iconUrl"
	val CITY_ID = "cityId"
}
```



## 实现SqliteOpenHelper

我们`SqliteOpenHelper`的基本组成是数据库的创建和更新，并提供了一个`SqliteDatebase`，使得我们可以用它来工作。查询可以被抽取出来放在其它的类中：

```kotlin
class ForecastDbHelper() : ManagedSQLiteOpenHelper(App.instance,
        ForecastDbHelper.DB_NAME, null, ForecastDbHelper.DB_VERSION) {
	...
}
```

我们在前面的章节中使用过我们创建的`App.instance`，这次我们同样的包括数据库名称和版本。这些值我们都会与`SqliteOpenHelper`一起定义在`companion object`中：

```kotlin
companion object {
    val DB_NAME = "forecast.db"
    val DB_VERSION = 1
    val instance: ForecastDbHelper by lazy { ForecastDbHelper() }
}
```

`instance`这个属性使用了`lazy`委托，它表示直到它真的被调用才会被创建。用这种方法，如果数据库从来没有被使用，我们没有必要去创建这个对象。一般`lazy`委托的代码块可以阻止在多个不同的线程中创建多个对象。这个只会发生在两个线程在同事时间访问这个`instance`对象，它很难发生但是发生具体还有看app的实现。无人如何，`lazy`委托是线程安全的。

为了去创建这些定义的表，我们需要去提供一个`onCreate`函数的实现。当没有库使用的时候，创建表会通过我们编写原生的包含我们定义好的列和类型的`CREATE TABLE`语句来实现。然而Anko提供了一个简单的扩展函数，它接收一个表的名字和一系列由列名和类型构建的`Pair`对象：

```kotlin
db.createTable(CityForecastTable.NAME, true,
        Pair(CityForecastTable.ID, INTEGER + PRIMARY_KEY),
        Pair(CityForecastTable.CITY, TEXT),
        Pair(CityForecastTable.COUNTRY, TEXT))
```

- 第一个参数是表的名称
- 第二个参数，当是true的时候，创建之前会检查这个表是否存在。
- 第三个参数是一个`Pair`类型的`vararg`参数。`vararg`也存在在Java中，这是一种在一个函数中传入联系很多相同类型的参数。这个函数也接收一个对象数组。

Anko中有一种叫做`SqlType`的特殊类型，它可以与`SqlTypeModifiers`混合，比如`PRIMARY_KEY`。`+`操作符像之前那样被重写了。这个`plus`函数会把两者通过合适的方式结合起来，然后返回一个新的`SqlType`：

```kotlin
fun SqlType.plus(m: SqlTypeModifier) : SqlType {
    return SqlTypeImpl(name, if (modifier == null) m.toString()
            else "$modifier $m")
}
```

如你所见，她会把多个修饰符组合起来。

但是回到我们的代码，我们可以修改得更好。Kotlin标准库中包含了一个叫`to`的函数，又一次，让我们来展示Kotlin的强大之处。它作为第一参数的扩展函数，接收另外一个对象作为参数，把两者组装并返回一个`Pair`。

```kotlin
public fun <A, B> A.to(that: B): Pair<A, B> = Pair(this, that)
```

因为带有一个函数参数的函数可以被用于inline，所以结果非常清晰：

```kotlin
val pair = object1 to object2
```

然后，把他们应用到表的创建中：

```kotlin
db.createTable(CityForecastTable.NAME, true,
        CityForecastTable.ID to INTEGER + PRIMARY_KEY,
        CityForecastTable.CITY to TEXT,
        CityForecastTable.COUNTRY to TEXT)
```

这就是整个函数看起来的样子：

```kotlin
override fun onCreate(db: SQLiteDatabase) {
    db.createTable(CityForecastTable.NAME, true,
            CityForecastTable.ID to INTEGER + PRIMARY_KEY,
            CityForecastTable.CITY to TEXT,
            CityForecastTable.COUNTRY to TEXT)
            
    db.createTable(DayForecastTable.NAME, true,
            DayForecastTable.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            DayForecastTable.DATE to INTEGER,
            DayForecastTable.DESCRIPTION to TEXT,
            DayForecastTable.HIGH to INTEGER,
            DayForecastTable.LOW to INTEGER,
            DayForecastTable.ICON_URL to TEXT,
            DayForecastTable.CITY_ID to INTEGER)
}
```

我们有一个相似的函数用于删除表。`onUpgrade`将只是删除表，然后重建它们。我们只是把我们数据库作为一个缓存，所以这是一个简单安全的方法保证我们的表会如我们所期望的那样被重建。如果我有很重要的数据需要保留，我们就需要优化`onUpgrade`的代码，让它根据数据库版本来做相应的数据转移。

```kotlin
override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    db.dropTable(CityForecastTable.NAME, true)
    db.dropTable(DayForecastTable.NAME, true)
    onCreate(db)
}
```



## 依赖注入

我试图不去增加很复杂的结构代码，保持简洁可测试性的代码和好的实践，我想我应该用Kotlin从其它方面去简化代码。如果你想了解一些控制反转或者依赖注入的话题，你可以查看我关于[Android中使用Dagger注入]的一系列文章。第一篇文章有关于他们这个团队的简单描写。

一种简单的方式，如果我们想拥有一些独立于其他类的类，这样更容易测试，并编写代码，易于扩展和维护，这时我们需要使用依赖注入。我们不去在类内部去实例化，我们在其它地方提供它们（通常通过构造函数）或者实例化它们。用这种方式，我们可以用其它的对象来替代他们。比如可以实现同样的接口或者在tests中使用mocks。

但是现在，这些依赖必须要在某些地方被提供，所以依赖注入由提供合作者的类组成。这些通常使用依赖注入器来完成。[Dagger]可能是Android上最流行的依赖注入器。当然当我们提供依赖有一定复杂性的时候是个很好的替代品。

但是最小的替代是可以在这个构造函数中使用默认值。我们可以给构造函数的参数通过分配默认值的方式提供一个依赖，然后在不同的情况下提供不同的实例。比如，在我们的`ForecastDbHelper`我们可以用更智能的方式提供一个context：

```kotlin
class ForecastDbHelper(ctx: Context = App.instance) :
        ManagedSQLiteOpenHelper(ctx, ForecastDbHelper.DB_NAME, null,
        ForecastDbHelper.DB_VERSION) {
        ...
}
```

现在我们有两种方式来创建这个类：

```kotlin
val dbHelper1 = ForecastDbHelper() // 它会使用 App.instance
val dbHelper2 = ForecastDbHelper(mockedContext) // 比如，提供给测试tests
```

我会到处使用这个特性，所以我在解释清楚之后再继续往下。我们已经有了表，所以是时候开始对它们增加和请求了。但是在这之前，我想先讲讲结合和函数操作符。别忘了查看代码库找到最新的代码。

[Android中使用Dagger注入]: http://http://antonioleiva.com/dependency-injection-android-dagger-part-1/
[Dagger]: http://square.github.io/dagger/