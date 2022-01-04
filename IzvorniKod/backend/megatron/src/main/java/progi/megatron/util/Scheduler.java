package progi.megatron.util;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import progi.megatron.service.DonationTryService;

@Component
public class Scheduler {

    private final DonationTryService donationTryService;

    public Scheduler(DonationTryService donationTryService) {
        this.donationTryService = donationTryService;
    }

    //@Scheduled(cron = "*/5 * * * * *")   // every 5 seconds
    @Scheduled(cron = "0 0 12 * * *")   // at noon every day
    public void performTaskUsingCron() {

//        System.out.println("This happens daily.");
//
//        List<Long> donationTriesToday = donationTryService.getIdsOfDonorsWhoDonatedToday();
//        System.out.println("Donors who donated today: " + donationTriesToday);
//
//        List<Long> donationTriesThreeMonthsAgo = donationTryService.getIdsOfDonorsWhoseWaitingPeriodIsOver();
//        System.out.println("Donors whose waiting period is over: " + donationTriesThreeMonthsAgo);
//
//        System.out.println();

        // todo: notify donors whose waiting period is over

    }

}