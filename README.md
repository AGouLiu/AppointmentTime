# AppointmentTime

[![](https://jitpack.io/v/AGouLiu/AppointmentTime.svg)](https://jitpack.io/#AGouLiu/AppointmentTime)
[![License](https://img.shields.io/badge/license-Apache%202-green.svg)](https://www.apache.org/licenses/LICENSE-2.0)

* 基于recyclerView  日历 制作的 星期 预约控件

### 添加 Gradle 依赖

* 把 `maven { url 'https://jitpack.io' }` 加入到 repositories 中
* 添加如下依赖，末尾的「latestVersion」 [![Download](https://jitpack.io/v/AGouLiu/AppointmentTime.svg)](https://jitpack.io/#AGouLiu/AppointmentTime)里的版本名称，请自行替换。
```groovy
dependencies {
    implementation 'com.github.AGouLiu:AppointmentTime:latestVersion'
}
```

### 效果gif

![AppointmentTime](https://github.com/AGouLiu/AppointmentTime/blob/master/res/xiaoguo.gif)


### 使用步骤 

* 通过数据驱动 。数据使用map 查询的效率更高效 

```
 val map: MutableMap<String, List<String>> = HashMap()  // key  日期 - 时段 
 
```
* list<String>  存时段和预约状态 
  
```
        var  array =ArrayList<String>()
        array.add("8:00-9:00-true")     //  true  可约
        array.add("10:00-12:00-false")   // false 约满
        map["2021-9-29-2"] = array  // 日期  -时段  1，上午 2 下午 ，3晚上
```
  
* 事件回调  
  
```
       var onPageChanged2: ((String, String) -> Unit?)? =null  // 页面 切换 
       var onClickListener: ((CalendarView, Cell) -> Unit)? = null // 点击回调
```
