目的：
```txt
测试 新建 session 的场景。
```

NOTE：
```java
    HttpSession session = request.getSession();
    if (session.isNew()){
      out.println("this is new session !");
    }else {
      out.println("Welcome back");
    }
```

目录结构：
```bash
# webapps dir
tree ch06-session-new

ch06-session-new
└── WEB-INF
    ├── classes
    │   └── com
    │       └── example
    │           └── SessionNew.class
    └── web.xml
```

测试 url：
```bash
http://localhost:8080/ch06-session-new/SNew.do
```
