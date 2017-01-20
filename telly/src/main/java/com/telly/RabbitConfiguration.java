package com.telly;

import org.apache.log4j.Logger;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
public class RabbitConfiguration {
    Logger logger = Logger.getLogger(RabbitConfiguration.class);

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory =
                new CachingConnectionFactory("localhost");
        return connectionFactory;
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory());
        return rabbitAdmin;
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());    
        rabbitTemplate.setExchange("h");
        return rabbitTemplate;
    }
    
    @Bean
    public Binding infoBinding(){
        return BindingBuilder.bind(myQueue2()).to(exchange()).with("findbusdate");
    }
    
    @Bean
    public Binding infoBinding3(){
        return BindingBuilder.bind(myQueue3()).to(exchange()).with("save");
    }
    
    @Bean
    public Binding infoBinding4(){
        return BindingBuilder.bind(myQueue4()).to(exchange()).with("reserve");
    }
    
    @Bean
    public Binding infoBinding5(){
        return BindingBuilder.bind(myQueue5()).to(exchange()).with("findreservations");
    }
    
    @Bean
    public Binding infoBinding6(){
        return BindingBuilder.bind(myQueue6()).to(exchange()).with("findbus");
    }
    
    @Bean
    public Binding infoBinding7(){
        return BindingBuilder.bind(myQueue7()).to(exchange()).with("delete");
    }
    
    @Bean
    public Queue myQueue2() {
        return new Queue("findbusdate");
    }
    
    @Bean
    public Queue myQueue3() {
        return new Queue("save");
    }
    
    @Bean
    public Queue myQueue4() {
        return new Queue("reserve");
    }
    
    @Bean
    public Queue myQueue5() {
        return new Queue("findreservations");
    }
    
    @Bean
    public Queue myQueue6() {
        return new Queue("findbus");
    }
    
    @Bean
    public Queue myQueue7() {
        return new Queue("delete");
    }
    
    @Bean
    public DirectExchange exchange() {
        return new DirectExchange("h");
    }
}
