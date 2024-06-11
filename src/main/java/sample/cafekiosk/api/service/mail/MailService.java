package sample.cafekiosk.api.service.mail;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sample.cafekiosk.api.service.order.response.OrderResponse;
import sample.cafekiosk.client.mail.MailSendClient;
import sample.cafekiosk.domain.history.mail.MailSendHistory;
import sample.cafekiosk.domain.history.mail.MailSendHistoryRepository;
//import sample.cafekiosk.domain.order.OrderCreateMailEvent;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MailService {

        private final MailSendHistoryRepository mailSendHistoryRepository;


        private final JavaMailSender mailSender;


        public boolean orderCreateMail(OrderResponse orderResponse) {

            SimpleMailMessage emailForm = orderCreateEmailForm(orderResponse);
            try {
                mailSender.send(emailForm);
            } catch (RuntimeException e) {
                log.debug("MailService.sendEmail 이메일 전송 실패 이메일: {}, " +
                        "주문id: {}", orderResponse.getUserEmail(), orderResponse.getId());
                throw new RuntimeException("이메일 전송에 실패했습니다.");
            }

            return true;
        }


        private SimpleMailMessage orderCreateEmailForm(OrderResponse orderResponse) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(orderResponse.getUserEmail());
            message.setSubject("주문이 완료되었습니다.");

            StringBuffer str = new StringBuffer();
            str.append("주문 번호: " + orderResponse.getId() + "\n")
                    .append("총 가격: " + orderResponse.getTotalPrice() + "\n")
                    .append("주문 날짜: " + orderResponse.getRegisteredDateTime() + "\n")
                    .append("주문 상품들: " + orderResponse.getProducts() + "\n")
                    .append("주문 개수: " + orderResponse.getProducts().size() + "\n");


            message.setText(str.toString());
            return message;
        }


    }