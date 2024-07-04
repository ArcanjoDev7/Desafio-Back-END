package desafio.back_end.demo.autorization;

import desafio.back_end.demo.transaction.Transaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class AuthorizerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizerService.class);
    private RestClient restClient;
    public AuthorizerService(RestClient.Builder builder){
        this.restClient = builder
                .baseUrl("https://run.moky.io/v3/5794d450-d2e2-4412-8131-73d0293ac1cc")
                .build();
    }
    public void authorize(Transaction transaction){
        LOGGER.info("Authorizing transaction: {}", transaction);

       var responde = restClient.get()
                .retrieve()
                .toEntity(Authorization.class);

       if(responde.getStatusCode().isError() || !responde.getBody().isAthorized()){
           throw new Authorization.UnauthrizedTransactionException("Unauthrized transaction");

       }
    }
}
