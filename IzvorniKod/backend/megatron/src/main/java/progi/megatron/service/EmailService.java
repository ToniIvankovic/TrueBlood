package progi.megatron.service;

import com.itextpdf.html2pdf.HtmlConverter;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import progi.megatron.email.AbstractEmailContext;
import progi.megatron.model.DonationTry;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class EmailService{

    private final JavaMailSender emailSender;
    private final SpringTemplateEngine templateEngine;

    public EmailService(JavaMailSender emailSender, SpringTemplateEngine templateEngine) {
        this.emailSender = emailSender;
        this.templateEngine = templateEngine;
    }

    public void sendMail(AbstractEmailContext email, Long id, String password) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());
        Context context = new Context();
        context.setVariables(email.getContext());
        context.setVariable("username", id);
        context.setVariable("password", password);
        String emailContent = templateEngine.process(email.getTemplateLocation(), context);

        mimeMessageHelper.setTo(email.getTo());
        mimeMessageHelper.setSubject(email.getSubject());
        mimeMessageHelper.setFrom(email.getFrom());
        mimeMessageHelper.setText(emailContent, true);
        emailSender.send(message);
    }

    public void tooLittleBloodEmail(String toAddress, String firstName, boolean isDonor, List<String> bloodTypesUnderLimit) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());
        mimeMessageHelper.setTo(toAddress);
        mimeMessageHelper.setSubject("Smanjene zalihe krvi");
        mimeMessageHelper.setFrom("truebloodfer@gmail.com");
        if(isDonor){
            mimeMessageHelper.setText("Pozdrav " + firstName + ",\n Želimo Vas obavijestiti da nam trenutačno nedostaje Vaše krvne grupe ("+
                    bloodTypesUnderLimit.get(0) + ").\n Ako ste u mogućnosti, molimo Vas da se što prije odazovete akciji darivanja krvi.\n" +
                    "Vaš Trueblood");
        } else{
            String bloodTypeString = "";
            for(String bloodType : bloodTypesUnderLimit){
                bloodTypeString += (bloodType + ", ");
            }
            mimeMessageHelper.setText("Pozdrav " + firstName + ",\n U sustavu je smanjena zaliha krvi za krvne grupe: "+
                    bloodTypeString
                    + "molimo potaknite donore na darivanje ovih krvnih grupa i smanjite slanje tih " +
                    "krvnih grupa u vanjske institucije. Hvala\n" +
                    "Vaš Trueblood");
        }
        emailSender.send(message);
    }

    public void tooMuchBloodEmail(String toAddress, String firstName, List<String> bloodTypesOverLimit) throws MessagingException {
        String bloodTypeString = "";
        for(String bloodType : bloodTypesOverLimit){
            bloodTypeString += (bloodType + ", ");
        }
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());
        mimeMessageHelper.setTo(toAddress);
        mimeMessageHelper.setSubject("Prevelike zalihe!");
        mimeMessageHelper.setFrom("truebloodfer@gmail.com");
        mimeMessageHelper.setText("Pozdrav " + firstName +
                ",\n U sustavu je prekoračena zaliha sljedećih krvnih grupa: " + bloodTypeString +
                "molimo Vas da primate manje donacija navedene krvne grupe dok se zalihe ne smanje, ili da višak zaliha pošaljete " +
                "u vanjsku instituciju. \n" +
                "Hvala, \n Vaš Trueblood");
        emailSender.send(message);
    }

    public  void canDonateAgainEmail(String toAddress, String firstName) throws MessagingException{
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());
        mimeMessageHelper.setTo(toAddress);
        mimeMessageHelper.setSubject("Oslobođen kapacitet");
        mimeMessageHelper.setFrom("truebloodfer@gmail.com");
        mimeMessageHelper.setText("Pozdrav " + firstName + ",\n Želimo te obavijestiti da nam smo oslobodili kapacitet za pohranjivanje tvoje krvne grupe. " +
                "Želimo vam zahvaliti na vašem strpljenju i jedva vas čekamo ponovo vidjeti! \n" +
                "Vaš Trueblood");
        emailSender.send(message);
    }


    public void sendNotificationEmail(String toAddress, String subject, String firstName) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());
        Context context = new Context();
        context.setVariable("firstName", firstName);
        String emailContent = templateEngine.process("/emails/email-donate-again.html", context);

        mimeMessageHelper.setTo(toAddress);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setFrom("truebloodfer@gmail.com");
        mimeMessageHelper.setText(emailContent, true);

        emailSender.send(message);
    }

    public void sendEmailWithAttachment(String toAddress, String subject, String message, String attachment, DonationTry donationTry) throws MessagingException, FileNotFoundException {

        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
        messageHelper.setTo(toAddress);
        messageHelper.setSubject(subject);
        messageHelper.setText(message);
        FileSystemResource file = new FileSystemResource(ResourceUtils.getFile(attachment));

        Context context = new Context();
        context.setVariable("donationId",donationTry.getDonationId());
        context.setVariable("donationDate",donationTry.getDonationDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy.")));
        context.setVariable("donorFirstName",donationTry.getDonor().getFirstName());
        context.setVariable("donorLastName",donationTry.getDonor().getLastName());
        context.setVariable("donorAddress",donationTry.getDonor().getAddress());
        context.setVariable("donorWorkPlace",donationTry.getDonor().getWorkPlace());
        context.setVariable("bankWorkerFirstName",donationTry.getBankWorker().getFirstName());
        context.setVariable("bankWorkerLastName",donationTry.getBankWorker().getLastName());
        context.setVariable("bankWorkerWorkContact",donationTry.getBankWorker().getWorkContact());
        context.setVariable("bankWorkerEmail",donationTry.getBankWorker().getEmail());


        String emailContent = templateEngine.process("emails/pdf.html",context);

        ByteArrayOutputStream target = new ByteArrayOutputStream();

        HtmlConverter.convertToPdf(emailContent,target);

        byte[] bytes = target.toByteArray();
        DataSource dataSource = new ByteArrayDataSource(bytes, "application/pdf");
        MimeBodyPart pdfBodyPart = new MimeBodyPart();
        pdfBodyPart.setDataHandler(new DataHandler(dataSource));
        pdfBodyPart.setFileName("Potvrda o doniranju.pdf");

        //construct the mime multi part
        MimeMultipart mimeMultipart = new MimeMultipart();
        mimeMultipart.addBodyPart(pdfBodyPart);
        mimeMessage.setContent(mimeMultipart);

        emailSender.send(mimeMessage);
    }
}