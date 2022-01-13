package progi.megatron.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import progi.megatron.model.Donor;
import progi.megatron.service.DonationTryService;
import progi.megatron.service.DonorService;
import progi.megatron.service.EmailService;

import java.util.List;

@Component
public class Scheduler {

    private final DonationTryService donationTryService;
    @Autowired
    private final DonorService donorService;

    @Autowired
    private EmailService emailService;

    public Scheduler(DonationTryService donationTryService, DonorService donorService) {
        this.donationTryService = donationTryService;
        this.donorService = donorService;
    }

    //@Scheduled(cron = "*/20 * * * * *")   // every 20 seconds
    //@Scheduled(cron = "* */5 * * * *")   // every 5 minutes
    @Scheduled(cron = "0 0 12 * * *")   // at noon every day
    public void performTaskUsingCron() {

//        List<Long> donationTriesToday = donationTryService.getIdsOfDonorsWhoDonatedToday();
//        System.out.println("Donors who donated today: " + donationTriesToday);
//
       List<Long> donationTriesThreeMonthsAgo = donationTryService.getIdsOfDonorsWhoDonated34MonthsAgo();
       for (Long id : donationTriesThreeMonthsAgo){
           System.out.println("DONORS id: " + id);
           Donor donor = donorService.getDonorByDonorId(id.toString());
           if (donor.getPermRejectedReason() == null){
               donorService.sendCanDonateAgain(donor);
           }
       }
//        System.out.println("Donors whose waiting period is over: " + donationTriesThreeMonthsAgo);


    }

}