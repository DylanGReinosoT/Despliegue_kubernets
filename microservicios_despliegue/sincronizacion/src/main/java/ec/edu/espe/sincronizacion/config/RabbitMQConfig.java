package ec.edu.espe.sincronizacion.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class RabbitMQConfig {
    @Bean
    public Queue relojSolicitd(){
        return QueueBuilder.durable("relof.solicitud").build();
    }

    @Bean
    public Queue relojSincronizacion(){
        return QueueBuilder.durable("reloj.sincronizacion").build();
    }

}
