package sample.cafekiosk.domain.history.mail;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sample.cafekiosk.domain.BaseEntity;

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
