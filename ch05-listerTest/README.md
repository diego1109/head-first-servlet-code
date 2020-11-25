
ServletContext 中存放了全局配置信息，各个 servlet 都可以拿来使用。但是在 DD 中只能配置 string 类型的信息，那如果我们想在 ServletContext 中配置 Object 类型的信息，显然不能放在 DD 配置。

监听器：ServletContextListener 可以让我们在 ServletContext 中配置 Object 信息。


```java
public interface ServletContextListener extends EventListener {
    // 在 servletContext 创建时，会触发这个方法，
    default public void contextInitialized(ServletContextEvent sce) {}
    // 在 servletContext 销毁时，会触发这个方法，
    default public void contextDestroyed(ServletContextEvent sce) {}
}
```

怎么配置 Object：
1. 在 DD 中配置 Object 的标志字符串，比如：“breed”。
2. `ServletContextEvent` 中有 `ServletContext` 的引用，可以拿出 `ServletContext`。
3. 再拿出 “bread” ，并构造出 Object。
4. 调用 `setAttribute()` 方法，将 Object 塞回 `ServletContext`。
这样操作，就可以向 `ServletContext` 中注入 Object 类型的配置信息。后面所有的 servlet 都可以使用。

