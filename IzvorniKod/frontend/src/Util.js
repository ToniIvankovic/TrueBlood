import React from "react";
import axios from 'axios';
import { Buffer } from "buffer";
import fileSaver from 'file-saver'
import { useHistory } from "react-router";

//globalne varijable za aplikaciju
const roleNone = 'JAVNO';
const workerNone = {};
const donorNone = {};
const userNone = {};
const userPublic = {
    userId: null,
    role: roleNone
};

const generateBasicAuthHeader = (userId, password) => {
    const credentials = userId + ":" + password;
    const basicAuth = Buffer.from(credentials).toString('base64');
    const basicAuthHeader = {
        'Authorization': 'Basic ' + basicAuth
    };
    return basicAuthHeader;
}

const getCurrentUserIdAndRole = async (user, setUser) => {

    const url = '/api/v1/user';
    setUser(userPublic);

    await axios.get(url)
        .then((response) => {
            if (response.data != null && response.data != "") {
                setUser({
                    userId: response.data.userId,
                    role: response.data.userRole
                });
            } else {
                setUser(userPublic);
                console.log("Prazan odgovor poslužitelja")
            }
        })
        .catch((error) => {
        })
}

const getDonorById = async (donorId, setDonor) => {

    const url = '/api/v1/donor/id/' + donorId;
    await axios.get(url)
        .then((response) => {
            if (response.data != null) {
                if(response.data.donorId != donorId){
                    console.log("Greška - id u odgovoru poslužitelja se ne podudara s trenutnim donorIdjem " + donorId);
                    return;
                }

                setDonor({
                    ...response.data,
                    role: "DONOR",
                    userId: response.data.donorId
                });
            } else {
                console.log("Prazan odgovor poslužitelja")
            }
        })
        .catch((error) => {
            console.log('Error retrieving user info: ' + error);
        })
}

const getWorkerById = async (workerId, setWorker) => {

    const url = '/api/v1/bank-worker/id/' + workerId;
    await axios.get(url)
        .then((response) => {
            if (response.data != null) {
                if(response.data.bankWorkerId != workerId){
                    console.log("Greška - id u odgovoru poslužitelja se ne podudara s trenutnim donorIdjem " + workerId);
                    return;
                }

                setWorker({
                    ...response.data,
                    role: "BANK_WORKER",
                    userId: response.data.bankWorkerId
                });
            } else {
                console.log("Prazan odgovor poslužitelja")
            }
        })
        .catch((error) => {
            console.log('Error retrieving user info: ' + error);
        })
}


const getBloodSupply = async (setBloodSupply) => {

    const url = '/api/v1/blood-supply';

    await axios.get(url, { withCredentials: false })
        .then((response) => {
            if (response.data != null) {
                setBloodSupply(response.data);
            } else {
                console.log("Prazan odgovor poslužitelja")
            }
        })
        .catch((error) => {
            console.log("Greška - nije moguće dohvatiti stanja krvnih grupa:");
            console.log(error);
        })
}

const downloadPDF = async (donationId) => {

    const url = '/api/v1/donation-try/pdf/' + donationId;
    history = useHistory();
    history.push(url);
    // await axios.get(url)
    //     .then((response) => {
    //         let data = response.data;
    //         var byteNumbers = new Array(data.length);
    //         for (var i = 0; i < data.length; i++) {
    //             byteNumbers[i] = data.charCodeAt(i);
    //         }
    //         var byteArray = new Uint8Array(byteNumbers);
    //         var blob = new Blob([byteArray], { type: "application/pdf" });
    //         fileSaver.saveAs(blob, "Potvrda o donaciji.pdf")
    //         // console.log(response.data)
    //     })
    //     .catch((error) => {
    //         console.log("Greška - nije moguće preuzeti PDF potvrdu");
    //         console.log(error);
    //     })
}

const getDonorPermRejected = async (userId, setPermDeactivated) => {

    return;

    const url = '/api/v1/user/activated' + userId; //potencijalna promjena
    
    await axios.get(url)
        .then((response) => {
            if (response.data != null) {
                setPermDeactivated(response.data.permDeavtivated == 0 ? false : true);
            } else {
                console.log("Server nije pronašao usera s id " + userId);
            }
        })
        .catch((error) => {
            console.log('Error retrieving user info: ' + error);
        })
}

const getDonorBloodType = async (donorId, setBloodType) => {
    
    const url = '/api/v1/donor/id/' + donorId;
    
    await axios.get(url)
        .then((response) => {
            if (response.data != null) {
                setBloodType(response.data.bloodType);
                return;
            } else {
                console.log("Server nije pronašao usera s id " + userId);
            }
        })
        .catch((error) => {
            console.log('Error retrieving user info: ' + error);
        })
}

const getDonorNextDonation = async (donorId, setNextDonation) => {
    
    const url = '/api/v1/donation-try/days-until-next-donation/' + donorId;
    
    await axios.get(url)
        .then((response) => {
            if (response.data != null) {
                console.log(response.data)
                setNextDonation(response.data);
                return;
            } else {
                console.log("Server nije pronašao usera s id " + userId);
            }
        })
        .catch((error) => {
            console.log('Error retrieving user info: ' + error);
        })
}

const formatDateToCro = (americanDate) => {
    let dateParts = americanDate.split("-")
    return dateParts[2] + "." + dateParts[1] + "." + dateParts[0]
}
const formatDateToEng = (americanDate) => {
    if(!americanDate) return americanDate
    let dateParts = americanDate.split(".")
    return dateParts[2] + "-" + dateParts[1] + "-" + dateParts[0]
}
const formatDateToSlash = (dotDate) => {
    if(!dotDate) return dotDate
    let dateParts = dotDate.split(".")
    return dateParts[0] + "/" + dateParts[1] + "/" + dateParts[2]
}

const isJSONEqual = (v1,v2) =>{
    if(JSON.stringify(v1) == JSON.stringify(v2))
        return true;
    else if(_.isEqual(v1,v2))
        return true;
    else return false;
}
const isEqualWithNull = (v1, v2) =>{
    if(v1 == v2
        || isJSONEqual(v1,v2)
        || !v1 && isJSONEqual(v2,{})
        || !v2 && isJSONEqual(v1,{})){
        return true;
    }
    else {
        return false;
    }
}

export { generateBasicAuthHeader };
export { getCurrentUserIdAndRole };
export { getDonorById };
export { getWorkerById };
export { getDonorPermRejected };
export { isEqualWithNull };
export { getBloodSupply };
export { downloadPDF };
export { getDonorBloodType };
export { getDonorNextDonation };
export { formatDateToCro }
export { formatDateToEng }
export { formatDateToSlash }
export { userNone };
export { userPublic };
export { donorNone };
export { workerNone };