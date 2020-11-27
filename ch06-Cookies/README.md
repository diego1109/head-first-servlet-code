功能：
```txt
测试 Cookie , servlet 创建 cookie 并返回给浏览器，浏览器第二次请求时顺带着 cookie。
servlet: `CookieTest` 创建 cookie 并返回给浏览器。
servlet: `CheckCookie` 校验了浏览器 GET 请求中的 cookie。 
```

NOTE:
```java
// 创建 cookie。
Cookie cookie = new Cookie("username",name);
cookie.setMaxAge(30*60);
response.addCookie(cookie);

// 校验 cookie。
Cookie[] cookies = request.getCookies();
if (cookies != null) {
    Stream.of(cookies).forEach(cookie -> {
    if (cookie.getName().equals("username")) {
        out.println("cookie: " + cookie.toString()+ "<br>");
        out.println("Hello " + cookie.getValue());
    }
    });
}

```

目录结构：
```bash
ch06-cookies
├── WEB-INF
│   ├── classes
│   │   └── com
│   │       └── example
│   │           ├── CheckCookie.class
│   │           └── CookieTest.class
│   └── web.xml
├── cookieresult.jsp
└── form.html


整理一下想法：
1. Servlet 是什么？

servlet 是个 sun 公司开发的一种web技术，这个技术制定了一个规范，落实到代码上他就是一个接口。那这个规范是做什么用的呢？这个规范是处理网络请求用的，所以，实现了这些规范的类是用来处理网络请求的。
```java
// Servlet.java 是源码，在官方 jar 包里。
public interface Servlet {
    public void init(ServletConfig config) throws ServletException;
    public ServletConfig getServletConfig();
    public void service(ServletRequest req, ServletResponse res)
	throws ServletException, IOException;
    public String getServletInfo();
    public void destroy();
}
```
在概念上，狭义的 servlet 是指 java 语言实现的一个接口，广义的 servlet 是指任何实现这个接口的类，一般情况下，人们将 servlet 理解为后者，**所以下面提到的 servlet 均是指实现 servlet 接口的类**，这个类是谁开发的？是程序员们开发的。开发的这个类是干什么用的？是为了处理浏览器的请求用的。

```java
// GenericServlet.java 是源码，在官方的 jar 包里。
public abstract class GenericServlet 
    implements Servlet, ServletConfig, java.io.Serializable{}

// HttpServlet.java 是源码，在官方的 jar 包里。
public abstract class HttpServlet extends GenericServlet{}

```

程序员自己开发的 servlet，都是从 `extend HttpServlet` 开始。然后通过重写父类方法，实现自己的业务逻辑。
NOTE：`CookieTest` 是自己写的类，但它现在也叫 servlet。
```java
public class CookieTest extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        String name = request.getParameter("username");
        Cookie cookie = new Cookie("username", name);
        cookie.setMaxAge(30 * 60);
        response.addCookie(cookie);

        RequestDispatcher view = request.getRequestDispatcher("cookieresult.jsp");
        view.forward(request, response);
    }
}
```

2. 那 tomcat 是干什么的？
继续看 `CookieTest`,这个类里是程序员自己写的逻辑，它从浏览里的请求里拿出了 `username` 参数，接着创建了一个 `cookie`,最后把 `cookie` 返回给浏览器。

上面的代码是处理浏览器请求的，请问，谁监听端口？谁接收请求？谁把处理结果返回给浏览器？

如果只有这个 CookieTest servlet，那是做不了 web 服务的。因为 servlet 还缺一个与浏览器进行 http 通信的“模块”。

怎么办？
有两种办法：
- （1）程序源自己再开发一段程序，监听端口、接收浏览器的请求并把它转交给 servlet、最后再从 servlet 拿到处理结果并将结果返回给浏览器。（这只是最基本的功能要求）
- （2）程序员不自己开发，找一个已有的、成熟的、稳定的、被广泛使用的、具有与（1）中相同功能甚至功能更强大的程序。

那（2）中描述的程序有吗？
有：Tomcat 就是其中之一，Apache 开发的程序并且开源。它能监听端口，接收请求并转发给 servlet、从servlet 拿处理结果并返回给浏览器。其他功能在此不做赘述。

3. Tomcat 是程序？是 servlet 容器？
tomcat 是个具有 HTTP 通信功能的程序，同时也经常看到好多博客中有写 “tomcat 是个 servlet 容器”。

为什么会叫它 “容器” 呢？

这里的 “容器” 跟 docker 攀不上亲戚。
之所以叫它“容器”，是因为要想让 tomcat 把请求转发给 servlet，并从 servlet 拿回处理结果，就必须把 servlet 所在的整个项目文件放到 tomcat 的 `webapps`目录下。
```bash
$ ls apache-tomcat-9.0.40

BUILDING.txt    LICENSE         README.md       RUNNING.txt     conf            logs            webapps
CONTRIBUTING.md NOTICE          RELEASE-NOTES   bin             lib             temp            work
```
在实际应用中，`webapps` 目录下，可不止一个项目，每个项目里也不只一个 servlet，tomcat 负责给这些 servlet 做 HTTP 通信“接口”。tomcat 就像 “容器” 一样，里面承载了好多个 servlet。所以，tomcat 即是程序，也是“容器”。

4. tomcat 怎么将请求精确转发给 servlet ？
一个 tomcat 下有好多个 servlet，那 tomcat 是怎么请求精确转发给 servlet 的呢？
tomcat 根据 URL 先确定转发给 `webapps` 下的哪个项目，再确定转发给项目中的哪个 servlet。

URL：`http://localhost:8080/ch06-cookies/checkcookie.do` 
通信协议：`http://`（使用 http 通信协议）。
服务器IP：`localhost` (本地)。
端口号：`8080` （tomcat 对应的 socket 的端口号默认是8080，大家常说的 tomcat “监听” 8080 端口）。
uri：`/ch06-cookies/checkcookie.do` （tomcat 用这部分判断是要将请求转发给哪个 servlet）。
- webapps 下的项目名字：`ch06-cookies`。
- 项目中的 servlet：`checkcookie.do`。

在 tomcat 的 `webapps` 目录下，存放的项目文件结构是有要求的。
（1）`webapps` 目录下，每一个文件夹就是一个单独的 web 项目，文件夹的名字会作为 uri 的起始。
```bash
# 这里有两个项目
$ ls webapps
ch06-cookies  helloservlet
```

确定了项目，接下来就是确定请求该转发给哪个 servlet 。
ch06-cookies项目内部文件结构：
```bash
tree ch06-cookies

ch06-cookies
├── WEB-INF
│   ├── classes
│   │   └── com
│   │       └── example
│   │           ├── CheckCookie.class
│   │           └── CookieTest.class
│   └── web.xml
├── cookieresult.jsp
└── form.html
```
每个项目下都必须有有个 `web.xml` 文件。它被叫做（Deployment Descriptor，DD），里面定义了路径和 servlet 的对应关系。
web.xml:
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<web-app xmlns="http://java.sun.com/xml/ns/javaee"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd" id="WebApp_ID" version="2.5">
    <servlet>
        <servlet-name>Ch6_Cookie_Test</servlet-name>
        <servlet-class>com.example.CookieTest</servlet-class>
    </servlet>

    <servlet-mapping>
        <servlet-name>Ch6_Cookie_Test</servlet-name>
        <url-pattern>/cookietest.do</url-pattern>
    </servlet-mapping>

    <servlet>
        <servlet-name>Ch6_Check_Cookie</servlet-name>
        <servlet-class>com.example.CheckCookie</servlet-class>
    </servlet>

    <servlet-mapping>
        <servlet-name>Ch6_Check_Cookie</servlet-name>
        <url-pattern>/checkcookie.do</url-pattern>
    </servlet-mapping>

</web-app>

```
<servlet></servlet>、<servlet-mapping></servlet-mapping> 这样的一对元素定义一个对应关系，这里是定义了两个对应关系：
```java
`/cookietest.do` => `com.example.CookieTest`
`/checkcookie.do` => `com.example.CheckCookie`
```
所以：
`http://localhost:8080/ch06-cookies/checkcookie.do` 请求会被转发给 `CheckCookie` servlet 来处理。（在实际使用的时候还要确定是 GET 还是 POST，这里就先忽略了）。
















