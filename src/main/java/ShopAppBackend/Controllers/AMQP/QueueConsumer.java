package ShopAppBackend.Controllers.AMQP;

import ShopAppBackend.Entities.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;



@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class QueueConsumer{

    private SimpMessagingTemplate template;



    @Autowired
    public QueueConsumer(SimpMessagingTemplate template) {
        this.template = template;
    }



    @RabbitListener(queues = "testMessages4")
    private void SendMessageToAdmin(Message message ){

        if(message.getRecipient() ==null || message.getRecipient().getUsername() ==null)
        {
            throw new NullPointerException();
        }
        else

        {
            template.convertAndSend("/topic/" + message.getRecipient().getUsername(),message);

        }
    }


}
