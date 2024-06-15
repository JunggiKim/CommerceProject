package sample.cafekiosk.api.service.mail;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import sample.cafekiosk.client.mail.MailSendClient;
import sample.cafekiosk.domain.history.mail.MailSendHistory;
import sample.cafekiosk.domain.history.mail.MailSendHistoryRepository;
import sample.cafekiosk.history.mail.mail.MailService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
class MailServiceTest {

    @Mock
    private MailSendClient mailSendClient;

    @Mock
    private MailSendHistoryRepository mailSendHistoryRepository;

    @InjectMocks
    private MailService mailService;



    @DisplayName("메일 전송 테스트")
    @Test
    void testSample(){
        //  given
//        Mockito.when(mailSendClient.sendEmail(anyString(),anyString(),anyString(),anyString()))
//               .thenReturn(true);
//        Mockito.doReturn(true)
//                .when(mailSendClient)
//                .sendEmail(anyString(),anyString(),anyString(),anyString());
        //  when
        BDDMockito.given(mailSendClient.sendEmail(anyString(),anyString(),anyString(),anyString()))
                .willReturn(true);

//        boolean result = mailService.sendMail("", "", "", "");

        //  then

//        assertThat(result).isTrue();
        Mockito.verify(mailSendHistoryRepository,Mockito.times(1)).save(any(MailSendHistory.class));

    }
}