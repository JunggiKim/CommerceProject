package sample.cafekiosk.api.controller.Mail;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import sample.cafekiosk.api.CustomApiResponse;
import sample.cafekiosk.api.service.mail.MailService;

@RestController
@RequiredArgsConstructor
public class MailController {


    private final MailService mailService;



//    @PostMapping("/api/v1/mail/orderCreate")
//    public CustomApiResponse orderCreateMail(@RequestBody MailRequest mailRequest){
//        return CustomApiResponse.ok(mailService.orderCreateMail(mailRequest));
//    }


}