package beevengers.billiardmanager.service;

public interface MailService {
    void sendMail(String to, String subject, String body);
}
