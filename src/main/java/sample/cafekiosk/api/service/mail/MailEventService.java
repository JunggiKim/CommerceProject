package sample.cafekiosk.api.service.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
@Transactional
@RequiredArgsConstructor
public class MailEventService {

    private final MailService mailService;


    @EventListener
    public void orderCreateMail(OrderCreateMailEvent orderCreateMailEvent){
        mailService.orderCreateMail(orderCreateMailEvent.orderResponse());
    }

}