package spring.Repository;

import spring.Entity.Mail;

public interface MailService {
    public void sendEmail(Mail mail);

    public boolean checkMail(String mail);
}
