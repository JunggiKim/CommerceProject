package sample.cafekiosk.api.service.mail;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sample.cafekiosk.client.mail.MailSendClient;
import sample.cafekiosk.domain.history.mail.MailSendHistory;
import sample.cafekiosk.domain.history.mail.MailSendHistoryRepository;

@Service
@RequiredArgsConstructor
public class MailService {


    private final MailSendClient mailSendClient;

    private final MailSendHistoryRepository mailSendHistoryRepository;


    public boolean sendMail(String fromMail, String toMail, String subject, String content) {

        boolean result = mailSendClient.sendEmail(fromMail, toMail, subject, content);

        if(result){
            mailSendHistoryRepository.save(MailSendHistory.builder()
                            .fromEmial(fromMail)
                            .toEmail(toMail)
                            .subject(subject)
                            .content(content)
                            .build());

                    mailSendClient.a();
                    mailSendClient.b();
                    mailSendClient.c();
                            return true;
        }
        return false;





    }




}
