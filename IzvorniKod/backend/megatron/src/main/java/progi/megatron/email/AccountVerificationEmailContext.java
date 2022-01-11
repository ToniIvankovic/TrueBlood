package progi.megatron.email;

import org.springframework.web.util.UriComponentsBuilder;
import progi.megatron.model.BankWorker;
import progi.megatron.model.Donor;

public class AccountVerificationEmailContext extends AbstractEmailContext {

    private String token;

    @Override
    public <T> void init(T context){
        //we can do any common configuration setup here
        // like setting up some base URL and context
        Donor donor = (Donor) context;
        put("firstName", donor.getFirstName());
        setTemplateLocation("emails/email-verification");
        setSubject("Završi proces registracije");
        setFrom("truebloodfer@gmail.com");
        setTo(donor.getEmail());
    }

    public <T> void initBankWorker(T context){
        BankWorker bankWorker = (BankWorker) context;
        put("firstName", bankWorker.getFirstName());
        setTemplateLocation("emails/email-verification");
        setSubject("Complete your registration");
        setFrom("truebloodfer@gmail.com");
        setTo(bankWorker.getEmail());
    }

    public void setToken(String token) {
        this.token = token;
        put("token", token);
    }

    public void buildVerificationUrl(final String baseURL, final String token){
        final String url= UriComponentsBuilder.fromHttpUrl(baseURL)
                .path("/verify").queryParam("token", token).toUriString();
        put("verificationURL", url);
    }

}
