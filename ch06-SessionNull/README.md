测试 session 为 null 的场景。

NOTE：
```java
// 有老的用老的，没有老的返回 null。
HttpSession session  = request.getSession(false);

// 有老的用老的，没有老的建新的。
HttpSession session  = request.getSession(true);

```

目录结构：
```bash
# webapps dir
tree ch06-session-null

ch06-session-null
└── WEB-INF
    ├── classes
    │   └── com
    │       └── example
    │           └── SessionNull.class
    └── web.xml
```

测试 url：
```bash
http://localhost:8080/ch06-session-null/SNull.do
```

> 一句话就也要写个 README.md ,是不是太啰嗦了。
> 想了想，还是写吧。这会不写，后面会更省略的。
> 最后就不知道会变成什么样子了。