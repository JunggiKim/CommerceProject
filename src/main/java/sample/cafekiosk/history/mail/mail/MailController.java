package sample.cafekiosk.history.mail.mail;


import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.RestController;

import sample.cafekiosk.history.mail.mail.MailService;

@RestController
@RequiredArgsConstructor
public class MailController {


    private final MailService mailService;



//    @PostMapping("/api/v1/mail/orderCreate")
//    public CustomApiResponse orderCreateMail(@RequestBody MailRequest mailRequest){
//        return CustomApiResponse.ok(mailService.orderCreateMail(mailRequest));
//    }


}