package sample.cafekiosk.history.mail.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sample.cafekiosk.order.response.OrderResponse;
import sample.cafekiosk.client.mail.MailSendClient;

@Service
@Transactional
@RequiredArgsConstructor
public class MailEventService {

    private final MailService mailService;
    private final MailSendClient mailSendClient;


    @EventListener
    public void orderCreateMail(OrderResponse orderResponse){
        mailService.orderCreateMail(orderResponse);
    }

}