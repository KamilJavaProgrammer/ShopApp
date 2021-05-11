package ShopAppBackend.AMQP;

import ShopAppBackend.Message.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;



@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class QueueConsumer {

    private SimpMessagingTemplate template;



    @Autowired
    public QueueConsumer(SimpMessagingTemplate template) {
        this.template = template;
    }


    @RabbitListener(queues = "testMessages")
    private void SendMessageToAdmin(Message message ){
        template.convertAndSend("/topic/" + message.getRecipient(),message);
    }


}
