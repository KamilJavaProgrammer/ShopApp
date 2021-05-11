package ShopAppBackend.AMQP;


import ShopAppBackend.Message.Message;
import ShopAppBackend.Message.MessageRepository;
import ShopAppBackend.User.User;
import ShopAppBackend.User.UserRepo;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")

public class QueueProducer {


    private final AmqpAdmin amqpAdmin;
    private final RabbitTemplate rabbitTemplate;
    private final UserRepo userRepo;
    private final MessageRepository messageRepository;


    @Autowired
    public QueueProducer(AmqpAdmin amqpAdmin, RabbitTemplate rabbitTemplate, UserRepo userRepo, MessageRepository messageRepository) {
        this.amqpAdmin = amqpAdmin;
        this.rabbitTemplate = rabbitTemplate;
        this.userRepo = userRepo;
        this.messageRepository = messageRepository;
    }

    @PostConstruct
    public void createQueues() {
        amqpAdmin.declareQueue(new Queue("testMessages", true));
        amqpAdmin.declareQueue(new Queue("testMessagesAdmin", true));
    }


    @PostMapping("/messages")
    public HttpStatus get(@RequestBody Message message) {

        rabbitTemplate.convertAndSend("testMessages", message);


         Message message1 = new Message();
         message1.setMessageText(message.getMessageText());
         message1.setDate(message.getDate());
        messageRepository.save(message1);

        User user = userRepo.getOne(message.getAuthor().getId());
        List<Message> list = user.getMessages();
        list.add(message1);
        user.setMessages(list);
        userRepo.save(user);

        return HttpStatus.OK;
    }

}
