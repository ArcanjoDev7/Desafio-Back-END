package desafio.back_end.demo.notification;

import desafio.back_end.demo.autorization.AuthorizerService;
import desafio.back_end.demo.transaction.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class NotificationConsomer {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizerService.class);
    private RestClient restClient;
    public NotificationConsomer(RestClient.Builder builder){
        this.restClient = builder
                .baseUrl("https://run.moky.io/v3/5794d450-d2e2-4412-8131-73d0293ac1cc")
                .build();
    }
    @KafkaListener(topics = "transaction-notification", groupId = "picpay-desafio-bachendC")
    public void receiveNotification(Transaction transaction){
        var response = restClient.get()
                .retrieve()
                .toEntity(Notification.class);

        if(response.getStatusCode().isError() || !response.getBody().message())
            throw new NotificationException("Error sendind notification!");
        LOGGER.info("notification has been sent: {}...", response.getBody());
    }
}
