package br.edu.ifrs.riogrande.tads.ppa.domain.listeners;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import br.edu.ifrs.riogrande.tads.ppa.config.RabbitMQConfig;
import br.edu.ifrs.riogrande.tads.ppa.domain.models.NewUserEvent;

@Component
public class NewUserEventListener {

    @RabbitListener(queues = RabbitMQConfig.USER_QUEUE)
    public void newUser(NewUserEvent event) {

    }
}