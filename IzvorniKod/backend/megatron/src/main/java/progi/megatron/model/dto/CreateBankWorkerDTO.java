package progi.megatron.model.dto;

import progi.megatron.model.BankWorker;

public class CreateBankWorkerDTO {
    String firstName;
    String lastName;
    String oib;
    String birthDate;
    String birthPlace;
    String address;
    String workPlace;
    String privateContact;
    String workContact;
    String email;


    /*public BankWorker toBankWorker(User user){
        return new BankWorker(user,firstName,lastName,oib,birthDate,birthPlace,address,workPlace,privateContact,workContact,email);
    }*/
}
