/**
 * TODO: 使Spring Boot程序支持外部Tomcat服务器（继承SpringBootServletInitializer类，并重写configure方法）
 */
package com.amii.plus.api;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class AmiiPlusApiWebStartApplication extends SpringBootServletInitializer
{
    @Override
    protected SpringApplicationBuilder configure (SpringApplicationBuilder builder)
    {
        // 这里要指向原先用main方法执行的Application启动类（即AmiiPlusApiApplication类）
        return builder.sources(AmiiPlusApiApplication.class);
    }
}
