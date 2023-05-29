package timnekk.orderflow.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    public Queue queue() {
        return new Queue("order-queue", false);
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange("order-exchange");
    }

    @Bean
    public Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("order.created");
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new SimpleMessageConverter();
    }

}