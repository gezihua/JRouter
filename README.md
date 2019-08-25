## JRegedit
### 介绍
JRegedit 是一个基于Android 原生工程注册表，无需任何gradle插件，无需apt。
开箱即用。
### 原理
  AndroidManifest 是google 为android 工程设计的注册表工程，AndroidManifest
能容纳固定类型的Tag，例如activity manifest application 和service 等,安装作
用分为两类： 可以拓展的Tag和保留类型关键字。可拓展类型tag包括meta-data等类型。
  参考Spring早期版本实现，就可以实现DI。JRegedit不同于Dagger2最大的一点-
JRegedit不依赖于apt设计，仅在AndroidManifest维护了一张全量的注册表。这就使得
JRegedit具有很强的拓展性。
  设想一种场景，你需要对外提供一个SDK,SDK包括一个已经实现IService接口的MSDKServiceIml，
所有使用IService均通过DI注入。这个时候显然让用户依赖Dagger并且将Dagger2 gradle plugin
添加到用户工程中不是一种好的选择。当然也可以使用生成MSDKServiceIml的方式实现，脱离DI。
 有没有一种不用修改用户grale的DI方案呢？那就是JRegedit。

###  示例代码
#### java 代码调用
``` java
        JrInterface serviceByInterface = JRegeditApi.API.findServiceByInterface(this, JrInterface.class);
        Log.e("MainActivity", serviceByInterface.hashCode() + "");
        serviceByInterface.sayHello();
```
#### 接口实现
JRegeditAnnation 可选，如果加入本注解，那么仅读取interfaces字段作为
interface。否则读取JrInterfaceIml的所有接口作为interface。
```
@JRegeditAnnation(interfaces = {JrInterface.class})
public class JrInterfaceIml implements JrInterface {
    @Override
    public void sayHello() {
        Log.e("JrInterfaceIml", "hello ni 好");
    }
}
```


#### AndroidManifest注册
```
<meta-data
            android:name="com.superx.j.router.JrInterfaceIml"
            android:value="j.regdit.v1"
            />
```







