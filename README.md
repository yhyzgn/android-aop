# Aop
![plugin-aspectj](https://img.shields.io/badge/aop-1.2.0-brightgreen.svg)  [![996.icu](https://img.shields.io/badge/link-996.icu-red.svg)](https://996.icu) [![LICENSE](https://img.shields.io/badge/license-Anti%20996-blue.svg)](https://github.com/996icu/996.ICU/blob/master/LICENSE) 

> 定义了一些常用的`AOP`功能，比如点击防抖动、防退出提示等。相比常规实现方式，`AOP`简洁方便

## 基本使用

* 添加依赖

  * 引入`plugin-aspectj`插件

    > 在项目下的`build.gradle`中如下配置
    >
    > `lastVersion`请参考[`GradlePlugins`](https://github.com/yhyzgn/GradlePlugins)

    ```groovy
    dependencies {
        classpath 'com.yhy.plugins:plugin-aspectj:lastVersion'
    }
    ```

  * 应用插件

    > 在需要`aop`操作模块的`build.gradle`中如下配置

    ```groovy
    apply plugin: 'plugin-aspectj'
    ```

  * 引入`aop`库

    > 添加`aop`库依赖
    >
    > `lastVersion`是此库的最新版本号

    ```groovy
    dependencies {
        implementation 'com.yhy.aop:aop-core:lastVersion'
    }
    ```

* 初始化

  > 在项目`Application`中进行初始化

  ```java
  public class App extends Application {
      @Override
      public void onCreate() {
          super.onCreate();
          AOP.init(this).debug(BuildConfig.DEBUG).logger(new AOP.Logger(){
              public void log(String log) {
                  // 日志打印
                  LogUtils.i(log);
              }
          });
      }
  }
  ```

* 点击防抖动

  > 为了防止手指抖动连续点击`view`多次而导致打开多个页面，或者执行多次`xx`操作，需要做防抖动操作。
  >
  > **支持多种方式**：
  >
  > - 普通的点击事件
  >
  >   ```java
  >   tvClick.setOnClickListener(new View.OnClickListener() {
  >       @Override
  >       public void onClick(View view) {
  >           log("被单击了");
  >       }
  >   });
  >   ```
  >
  > - `Lambda`形式的点击事件
  >
  >   ```java
  >   tvClickLambda.setOnClickListener(v -> {
  >       log("拉姆达被点击了");
  >   });
  >   ```
  >
  > - `ButterKnife`的点击事件
  >
  >   ```java
  >   @Click(R.id.tv_butter)
  >   public void click(int viewId){
  >       // ...
  >   }
  >   ```

  * 开启防抖动功能

    > 在`Application`中添加`@EnableClickResolver`注解。`value`是防抖动时间，默认为`1000ms`，单位`ms`

    ```java
    @EnableClickResolver(3000)
    public class App extends Application {
    }
    ```

  * 忽略某些点击事件

    > 如果有些控件确实不需要防抖动，需要在点击事件方法上加`@ClickIgnored`注解

    ```java
    tvClickIgnore.setOnClickListener(new View.OnClickListener() {
        @ClickIgnored
        @Override
        public void onClick(View view) {
            log("被忽略了" + MainActivity.this.getPackageName());
        }
    });
    ```

* 防退出提示

  > 一般在应用的主页上按了返回按钮，都会有 “再按一次退出应用” 之类的提示，用`AOP`实现起来更方便。

  * 在主页面（需要作退出提示的页面）的`Activity`上加上`@MainBackResovler`注解

    > 参数说明：
    >
    > - `value`：提示消息。默认值：“再按一次退出应用”
    > - `interval`：时间间隔，在这段时间内再按一次，才能退出。默认值：`3000ms`
    > - `callback`：作提示操作的回调，需要实现`OnBackCallback`接口。默认值：`OnBackCallback.class`

    ```java
    @MainBackResolver(value = "有种再按一次试试", interval = 5000, callback = OnBackListener.class)
    public class MainActivity extends AppCompatActivity {
    }
    ```

  * 重写父类的`onBackPressed()`方法

    > 由于防退出是对主页面的`onBackPressed()`方法做了切面，但`aspectj`无法切到父类的方法，所以此页面必须从写该方法。
    >
    > <font color="orange">**注意：如果不重写该方法，很可能不会有防退出提示**</font>

    ```java
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    ```

----

-- 也就这么多啦~~

