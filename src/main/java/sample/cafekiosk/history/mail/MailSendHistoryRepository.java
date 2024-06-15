package sample.cafekiosk.history.mail;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sample.cafekiosk.domain.history.mail.MailSendHistory;

@Repository
public interface MailSendHistoryRepository  extends JpaRepository<MailSendHistory,Long> {
}
