package com.amii.plus.api.config;

import java.util.HashMap;
import java.util.Map;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.amii.plus.api.constant.RabbitMqConstant;

@Configuration
@ConfigurationProperties(prefix = "spring.rabbitmq")
public class RabbitMqConfig
{
    private String host;
    private int port;
    private String userName;
    private String password;

    public String getHost ()
    {
        return host;
    }

    public void setHost (String host)
    {
        this.host = host;
    }

    public int getPort ()
    {
        return port;
    }

    public void setPort (int port)
    {
        this.port = port;
    }

    public String getUserName ()
    {
        return userName;
    }

    public void setUserName (String userName)
    {
        this.userName = userName;
    }

    public String getPassword ()
    {
        return password;
    }

    public void setPassword (String password)
    {
        this.password = password;
    }

    /**
     * TODO: 连接工厂
     *
     * @return
     */
    @Bean
    public ConnectionFactory connectionFactory ()
    {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(host, port);
        cachingConnectionFactory.setUsername(userName);
        cachingConnectionFactory.setPassword(password);
        cachingConnectionFactory.setVirtualHost("/");
        cachingConnectionFactory.setPublisherConfirms(true);
        return cachingConnectionFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate ()
    {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        return rabbitTemplate;
    }

    /**
     * TODO: 自定义交换机
     *
     * @return
     */
    @Bean
    public CustomExchange delayExchange ()
    {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("x-delayed-type", "direct");
        return new CustomExchange(RabbitMqConstant.MESSAGE_EXCHANGE_NAME1, "x-delayed-message", true, false, args);
    }

    /**
     * TODO: 队列
     *
     * @return
     */
    @Bean
    public Queue queue ()
    {
        return new Queue(RabbitMqConstant.MESSAGE_QUEUE_NAME1, true);
    }

    /**
     * TODO: 通过指定路由键把交换机与队列进行绑定
     *
     * @return
     */
    @Bean
    public Binding binding ()
    {
        return BindingBuilder.bind(queue()).to(delayExchange()).with(RabbitMqConstant.MESSAGE_ROUTING_KEY1).noargs();
    }
}
