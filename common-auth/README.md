# common-auth

web系统可使用的权限验证器

## 使用方式 ##

1、实现权限校验器
实现 com.sachin.common.auth.Validate
或者继承 com.sachin.common.auth.AbstractValidate
或者使用默认的 com.sachin.common.auth.DefaultValidate

2、在spring配置文件中配置bean

```
    <bean id="validate" class="com.sachin.common.auth.DefaultValidate"/>

    <bean id="authFilter" class="com.sachin.common.auth.filter.AuthFilter">
        <property name="validate" ref="validate"/>
        <property name="authConfig">
            <bean class="com.sachin.common.auth.AuthConfig">
                <property name="loginUri" value="login.do"/>
                <property name="logoutUri" value="logout.do"/>
                <property name="alertUri" value="authWarn.do"/>
                <property name="loginCookieSalt" value="${auth.cookie.salt}"/>
                <property name="loginCookiePath" value="/"/>
                <property name="loginCookieDomain" value="sachin.com"/>
                <property name="loginCookieMaxAge" value="${auth.cookie.age}"/>
                <property name="appCode" value="sachin"/>
                <property name="noCheckUris">
                    <list>
                        <value>/init.do</value>
                    </list>
                </property>
            </bean>
        </property>
    </bean>

```

3、在web.xml中配置过滤器，例如：

```

    <filter>
            <filter-name>delegatingFilterProxy</filter-name>
            <filter-class>org.springframework.web.filter.DelegatingFilterProxy</filter-class>
            <init-param>
                <param-name>targetBeanName</param-name>
                <param-value>authFilter</param-value>
            </init-param>
        </filter>
        <filter-mapping>
            <filter-name>delegatingFilterProxy</filter-name>
            <url-pattern>*.do</url-pattern>
        </filter-mapping>
        <filter-mapping>
            <filter-name>delegatingFilterProxy</filter-name>
            <url-pattern>/</url-pattern>
        </filter-mapping>

```