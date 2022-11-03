## 语法

1. 取地址@{}

   这么写是相对路径,如果用取值也可以取到,但是取得的其实是字符串,只不过这个字符串对应的路径,但是字符串是绝对路径

2. 取值${}

3. 取片段 fragment

4. 插入片段insert or replace or include

   三者区别很小,如下

   前两个的区别在于被插入fragment的地方,最后一个在于插入的fragment

   一般情况我们在要插入的地方写个div然后insert即可

   ```html
   <div th:insert....>的结果被插入的地方还有这个div
   ```

   ```html
   <div th:replace....>的结果被插入的地方没有这个div
   ```

   ```html
   <div th:include....>的结果是直接把fragment里面的内容放进去,而fragment的标签不要了.  也就是一般我们设置fragment的话,是把他放在一个div里面的. 我们使用include,就是这个div我们不要了,只插入里面的内容
   ```

   

5. th:each 遍历表格

## 用法

### 提取公告部分

1. 设为片段

   ```html
   <div th:fragment="left-side">
   ```

2. ```html
   <div th:insert="~{common::header-section}">
   <!--common是片段所在html文件的名称--> 
   ```

### 动态遍历表格

```java
    public String dynamicTable(Model model){
        List<User> users= Arrays.asList(
                new User("zhangsan","1"),
                new User("lisi","2"),
                new User("wangwu","3"),
                new User("zhaoliu","4"));
        model.addAttribute("users",users);
        return "table/dynamic_table";
    }
```



```html
<thead>
<tr>
    <th>ID</th>
    <th>User</th>
    <th>Password</th>
</tr>
</thead>
<tbody>
<tr class="gradeX" th:each="user,stats:${users}">
    <td th:text="${stats.count}"></td>
    <td th:text="${user.userName}"></td>
    <td th:text="${user.password}"></td>
    <--这个users是model里面的users,stats是状态,stats.count用于记录顺序-->    
</tr>
</tbody>
```

