功能：
```txt
测试 servlet 过滤器功能。过滤器存在与 客户端和 servlet 之间，更准确的说是存在 tomcat 和 servlet 之间。请求先被过滤器处理（比如：权限校验）后再交给servlet，同样响应可以被过滤器处理后再返回给浏览器。（要过滤响应需要创建包装器）

本例测试 请求过滤器。

``

NOTE：
```java
public class Login implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException{}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        PrintWriter out = response.getWriter();
        out.write("<html><body><div id='servletResponse' style='text-align: center;'>");

        String password = request.getParameter("password");
        System.out.println("Password Is?= " + password);

        if (password != null && password.equals("admin")) {
            chain.doFilter(request, response);
            // 这个不会起作用的。
            out.write("--- after chian.doFilter() ---");
        } else {
            // 如果密码写的不对，前端会一直渲染出 index.jsp 界面。
            out.print(
                    "<p id='errMsg' style='color: red; font-size: larger;'>Username Or Password Is Invalid. Please Try Again ....!</p>");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/index.jsp");
            requestDispatcher.forward(request, response);
        }

    }

    @Override
    public void destroy() {}
}
```

过滤器的业务逻辑全部都卸载 `doFilter()` 方法中，调用 `chain.doFilter(request, response)` 是将请求发送到下一个过滤器或者 servlet。

web.xml
```xml
    <!-- 将 servlet 和 url 路径绑定起来 -->
    <servlet>
        <servlet-name>Admin</servlet-name>
        <servlet-class>com.example.Admin</servlet-class>
    </servlet> 
    <servlet-mapping>
        <servlet-name>Admin</servlet-name>
        <url-pattern>/servlet1</url-pattern>
    </servlet-mapping>

    <!-- 过滤器必须和 servlet、url 两者中的一个绑定 -->
    
    <!-- 过滤器必须和 servlet 绑定 -->
    <filter>
        <filter-name>Login</filter-name>
        <filter-class>com.example.Login</filter-class>
    </filter>
    <!-- 过滤器必须和 url 绑定 -->
    <filter-mapping>
        <filter-name>Login</filter-name>
        <url-pattern>/servlet1</url-pattern>
    </filter-mapping>
```

目录结构： 
```bash
ch13-filter
├── WEB-INF
│   ├── classes
│   │   └── com
│   │       └── example
│   │           ├── Admin.class
│   │           └── Login.class
│   └── web.xml
└── index.jsp
```

测试 URL：
```bash
http://localhost:8080/ch13-filter/servlet1
```
