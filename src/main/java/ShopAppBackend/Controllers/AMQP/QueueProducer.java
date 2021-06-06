package ShopAppBackend.Controllers.AMQP;


import ShopAppBackend.Entities.Message;
import ShopAppBackend.Repositories.MessageRepository;
import ShopAppBackend.Enums.MessageState;
import ShopAppBackend.Entities.User;
import ShopAppBackend.Repositories.UserRepo;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
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
    public void createQueues(){
        amqpAdmin.declareQueue(new Queue("testMessages4", true));
    }


    @Transactional
    @PutMapping("/messages")
    public HttpStatus get(@RequestBody Message message) {

        rabbitTemplate.convertAndSend("testMessages4", message);


        messageRepository.save(message);
        User user = userRepo.getOne(message.getAuthor().getId());
        List<Message> list = user.getMessages();
        list.add(message);
        user.setMessages(list);
        userRepo.save(user);

        return HttpStatus.OK;
    }


    @Transactional
    @PatchMapping("/messages")
    public HttpStatus ChangeMessageState(@RequestBody List<Message> messages) {


        messages.forEach(message -> {
           Message messageInstant =  messageRepository.getOne(message.getId());
           messageInstant.setState(MessageState.displayed.name());
           messageRepository.save(messageInstant);

        });



        return HttpStatus.OK;
    }

}
