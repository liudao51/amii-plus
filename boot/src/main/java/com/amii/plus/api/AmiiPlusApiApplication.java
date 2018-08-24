/**
 * TODO: Spring Boot程序入口文件
 */
package com.amii.plus.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

//@SpringBootApplication
// @EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class})
public class AmiiPlusApiApplication
{
    public static void main (String[] args)
    {
        SpringApplication.run(AmiiPlusApiApplication.class, args);
    }
}
