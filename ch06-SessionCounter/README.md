功能：
```java

测试 HttpSessionListener 功能，当 session 被创建和销毁是会触发事件，开发者可以在对应的方法中编写逻辑代码。
使用场景之一就是：统计当前的并发用户数。 
``` 

NOTE：
``` java
public class BeerSessionCounter implements HttpSessionListener {
  private static int activeSessions = 0;

  public static int getActiveSessions(){
    return activeSessions;
  }

    // session 创建时被执行
  @Override
  public void sessionCreated(HttpSessionEvent sessionEvent) {
    activeSessions ++;
  }

    // session 销毁时被执行
  @Override
  public void sessionDestroyed(HttpSessionEvent sessionEvent) {
    activeSessions --;
  }
}

```


目录结构：
``` bash
ch06-session-counter
└── WEB-INF
    ├── classes
    │   └── com
    │       └── example
    │           ├── BeerSessionCounter.class
    │           └── SessionTest.class
    └── web.xml
```

测试 URL：
```bash
http://localhost:8080/ch06-session-counter/sessiontest.do
```