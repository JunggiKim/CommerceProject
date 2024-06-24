package sample.cafekiosk.history.mail.mail.request;


import lombok.Builder;

@Builder
public record MailRequest(
    String toEmail,
    String title,
    String text
) {

}