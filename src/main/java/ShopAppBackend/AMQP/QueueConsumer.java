package ShopAppBackend.AMQP;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class QueueConsumer {

    private RabbitTemplate rabbitTemplate;
    private RestTemplate restTemplate;
    private SimpMessagingTemplate template;

    @Autowired
    public QueueConsumer(RabbitTemplate rabbitTemplate, RestTemplate restTemplate,SimpMessagingTemplate template) {
        this.rabbitTemplate = rabbitTemplate;
        this.restTemplate = restTemplate;
        this.template = template;
    }


    @RabbitListener(queues = "testMessages")
    private void SendMessageToUser(Message message ){
        template.convertAndSend("/topic/admin",message);

    }


    @RabbitListener(queues = "testMessagesAdmin")
    private void SendMessageToAdmin( Message message){
       template.convertAndSend("/topic/user", message);
    }

}
