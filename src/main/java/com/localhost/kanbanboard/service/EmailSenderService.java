package com.localhost.kanbanboard.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.stereotype.Service;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.Request;
import java.io.IOException;
import com.sendgrid.Method;

/**
 * EmailSenderService
 */
@Service
public class EmailSenderService {
    @Value("${sendgrid.api.key}")
    private String apiKey;

    @Async
    public void sendEmail(Email from, String subject, Email to, Content content) throws IOException {
        Mail mail = new Mail(from, subject, to, content);
        SendGrid sg = new SendGrid(apiKey);

        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());

        Response response = sg.api(request);
        if(response == null)
            throw new IOException("An internal error has occurred!.");
    }
}