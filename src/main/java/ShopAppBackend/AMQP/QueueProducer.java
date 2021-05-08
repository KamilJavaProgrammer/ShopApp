package ShopAppBackend.AMQP;


import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

@RestController
@CrossOrigin(origins = "http://localhost:4200")

public class QueueProducer {


    private AmqpAdmin amqpAdmin;
    private RabbitTemplate rabbitTemplate;

    @Autowired
    public QueueProducer(AmqpAdmin amqpAdmin, RabbitTemplate rabbitTemplate) {
        this.amqpAdmin = amqpAdmin;
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostConstruct
    public void createQueues() {
        amqpAdmin.declareQueue(new Queue("testMessages", true));
        amqpAdmin.declareQueue(new Queue("testMessagesAdmin", true));
    }


    @PostMapping("/messages")
    public HttpStatus get(@RequestBody Message message) {

        rabbitTemplate.convertAndSend("testMessages", message);
        return HttpStatus.OK;
    }

    @PostMapping("/messagesAdmin")
    public HttpStatus getADMIN(@RequestBody Message message) {

      rabbitTemplate.convertAndSend("testMessagesAdmin", message);
        return HttpStatus.OK;
    }

}
