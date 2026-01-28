package CoserCreation.services;

import CoserCreation.models.ClientModel;
import CoserCreation.models.ItemModel;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService {

    private final JavaMailSender emailSender;
    private final ClientService clientService;

    public EmailService(JavaMailSender emailSender, ClientService clientService) {
        this.emailSender = emailSender;
        this.clientService = clientService;
    }

    @Async
    public void sendNewItemNotification(ItemModel item) {
        List<ClientModel> clients = clientService.getAllActiveClients();
        String subject = "Nouvel article disponible ! " + item.getTitle();
        String text = "Bonjour,\n\nUn nouvel article est disponible sur notre site : " + item.getTitle() + ".\n\n"
                + "Description : " + item.getDescription() + "\n"
                + "Retrouvez le nouvel article sur notre site dès maintenant !\n\n"
                + "Cordialement,\nCoserCreation";

        for (ClientModel client : clients) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("noreply@cosercreation.com");
            message.setTo(client.getEmail());
            message.setSubject(subject);
            message.setText(text);
            emailSender.send(message);
        }
    }
}
