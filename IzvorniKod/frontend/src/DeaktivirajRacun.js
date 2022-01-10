import axios from "./util/axios-instance";
import React, { useEffect, useState } from "react";
import { Route, Link } from "react-router-dom";
import { useHistory } from 'react-router';
import ErrorCard from './ErrorCard';
import { donorNone, workerNone } from "./Util";

const DeaktivirajRacun = (props) => {

    const reset = () => {
        props.setExistingDonor(false); 
        props.setDonor(donorNone)
        props.setExistingWorker(false); 
        props.setWorker(workerNone)
    }

    const history = useHistory();
    
    const [errorMessage, setErrorMessage] = useState("Greška");
    const [errorHidden, setErrorHidden] = useState(true);
    
    const submit = () => {
        const id = props.donor.donorId? props.donor.donorId : props.worker.bankWorkerId;
        const url = '/api/v1/user/deactivate/' + id;
        axios.get(url)
            .then((response) => {
                history.push('/racun_deaktiviran');
            })
    }

    return (
        <div className="reg">
            <div className='roledesplay'>
                ({props.user.role})
            </div>
            <div className="tekst">
                <p>Deaktiviraj račun </p>
            </div>
            <div className="gumbi">
                <br />
                <Link to='/trazi_donora'>
                    <button className='maligumb' onClick={reset}>Pronađi donora</button>
                </Link>
                <Link to='/trazi_djelatnika'>
                    <button className='maligumb' onClick={reset}>Pronađi djelatnika</button>
                </Link>
            </div>
            { (props.existingDonor || props.existingWorker) && (props.donor && props.donor.donorId || props.worker && props.worker.bankWorkerId)? 
                <div>
                    <div className="label">
                        <label>Osobni podaci</label>
                    </div>
                    <div className="single">
                        <input
                            name='userId'
                            type="text"
                            defaultValue={"userId: " + (props.donor.donorId? props.donor.donorId : '' + props.worker.bankWorkerId? props.worker.bankWorkerId : '')}
                            disabled></input>
                    </div>
                    <div className="dupli">
                        <input
                            name='firstName'
                            type="text"
                            defaultValue={"Ime: " + (props.donor.firstName? props.donor.firstName : '' + props.worker.firstName? props.worker.firstName : '')}
                            disabled></input>
                        <input
                            name='lastName'
                            type="text"
                            defaultValue={"Prezime: " + (props.donor.lastName? props.donor.lastName : '' + props.worker.lastName? props.worker.lastName : '')}
                            disabled></input>
                    </div>
                    <div className="single">
                        <input
                            name='oib'
                            type="text"
                            defaultValue={"OIB: " + (props.donor.oib? props.donor.oib : '' + props.worker.oib? props.worker.oib : '')}
                            disabled></input>
                    </div>
                    <div className="gumbi">
                        <button className='kreiraj' onClick={submit}>Deaktiviraj račun</button>
                    </div>
                </div>
            :''}
        </div>
    )
}

export default DeaktivirajRacun;
