package com.monese.marra.transfer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class)
public class MoneyTransferApp extends SpringBootServletInitializer 
{
    public static void main( String[] args )
    {
        SpringApplication.run(MoneyTransferApp.class, args);
    }
}
