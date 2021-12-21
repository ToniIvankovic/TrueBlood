package progi.megatron.service;

import com.itextpdf.html2pdf.HtmlConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
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

@Service
public class EmailService{

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    public void sendMail(AbstractEmailContext email) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());
        Context context = new Context();
        context.setVariables(email.getContext());
        String emailContent = templateEngine.process(email.getTemplateLocation(), context);

        mimeMessageHelper.setTo(email.getTo());
        mimeMessageHelper.setSubject(email.getSubject());
        mimeMessageHelper.setFrom(email.getFrom());
        mimeMessageHelper.setText(emailContent, true);
        emailSender.send(message);
    }

    public void sendSimpleEmail(String toAddress, String subject, String message) {

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(toAddress);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);
        emailSender.send(simpleMailMessage);
    }

    public void sendEmailWithAttachment(String toAddress, String subject, String message, String attachment, DonationTry donationTry) throws MessagingException, FileNotFoundException {

        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
        messageHelper.setTo(toAddress);
        messageHelper.setSubject(subject);
        messageHelper.setText(message);
        FileSystemResource file = new FileSystemResource(ResourceUtils.getFile(attachment));

        Context context = new Context();
        context.setVariable("donationTry.donationId",donationTry.getDonationId());
        context.setVariable("donationTry.donationDate",donationTry.getDonationDate());
        context.setVariable("donationTry.donor.firstName",donationTry.getDonor().getFirstName());
        context.setVariable("donationTry.donor.LastName",donationTry.getDonor().getLastName());
        context.setVariable("donationTry.donor.address",donationTry.getDonor().getAddress());
        context.setVariable("donationTry.donor.workPlace",donationTry.getDonor().getWorkPlace());
        context.setVariable("donationTry.bankWorker.firstName",donationTry.getBankWorker().getFirstName());
        context.setVariable("donationTry.bankWorker.lastName",donationTry.getBankWorker().getLastName());
        context.setVariable("donationTry.bankWorker.workContact",donationTry.getBankWorker().getWorkContact());

        String emailContent = templateEngine.process("emails/pdf.html",context);

        ByteArrayOutputStream target = new ByteArrayOutputStream();

        HtmlConverter.convertToPdf(emailContent,target);

        byte[] bytes = target.toByteArray();
        DataSource dataSource = new ByteArrayDataSource(bytes, "application/pdf");
        MimeBodyPart pdfBodyPart = new MimeBodyPart();
        pdfBodyPart.setDataHandler(new DataHandler(dataSource));
        pdfBodyPart.setFileName("potvrda.pdf");

        //construct the mime multi part
        MimeMultipart mimeMultipart = new MimeMultipart();
        mimeMultipart.addBodyPart(pdfBodyPart);
        mimeMessage.setContent(mimeMultipart);

        emailSender.send(mimeMessage);
    }
}