package sample.cafekiosk.domain.history.mail;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sample.cafekiosk.domain.BaseEntity;
import sample.cafekiosk.domain.order.OrderStatus;
import sample.cafekiosk.domain.orderproduct.OrderProduct;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class MailSendHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fromEmial;

    private String toEmail;

    private String subject;

    private String content;


    @Builder
    public MailSendHistory(Long id, String fromEmial, String toEmail, String subject, String content) {
        this.id = id;
        this.fromEmial = fromEmial;
        this.toEmail = toEmail;
        this.subject = subject;
        this.content = content;
    }





}
