import axios from './util/axios-instance';
import React, { useEffect, useState } from "react";
import { useRef } from "react";
import { useHistory } from 'react-router';
import ErrorCard from './ErrorCard';
import { Link } from 'react-router-dom';

const PokusajDoniranja = (props) => {

    const history = useHistory();
    const ref = useRef();

    const [errorMessage, setErrorMessage] = useState('Greška');
    const [errorHidden, setErrorHidden] = useState(true);

    useEffect(() => {
        if (props.user.role && props.user.role != 'BANK_WORKER' && props.user.role != 'ADMIN') {
            history.push('/profil');
        }
    }, [props.role]);

    const handleSubmit = (event) => {
        console.log("submit")
    }

    return (
        <div className="reg">
            <form onSubmit={(event) => handleSubmit(event)} className='formular'>
                ({props.user.role})
                <div className="tekst">
                    <p>Nova donacija </p>
                </div>
                <div className="gumbi">
                    <br />
                    <Link to='/trazi_donora'>
                        <button className='maligumb' onClick={(event) => {props.setExistingDonor(false); props.setDonor(false)}}>Pronađi donora</button>
                    </Link>
                    {props.existingDonor?
                    <Link to='/stvori_donora'>
                        <button className='maligumb' onClick={(event) => {props.setExistingDonor(true)}}>Uredi donora</button>
                    </Link>
                    :''
                    }
                </div>
                <div className="label">
                    <label>Osobni podaci</label>
                </div>
                <div className="single">
                    <input
                        name='donorId'
                        type="text"
                        placeholder={"donorId: " + props.donor.donorId}
                        ></input>
                </div>
                <div className="dupli">
                    <input
                        name='firstName'
                        type="text"
                        placeholder={"ime: " + props.donor.firstName}
                        ></input>
                    <input
                        name='lastName'
                        type="text"
                        placeholder={"prezime: " + props.donor.lastName}
                        ></input>
                </div>
                <div className="single">
                    <input
                        name='oib'
                        type="text"
                        placeholder={"OIB: " + props.donor.oib}
                        ></input>
                </div>
                <div className="krgrupe">
                    <label>Krvna grupa</label>
                    <select value={props.donor.bloodType}>
                        <option value="---">---</option>
                        <option value="A+">A+</option>
                        <option value="A-">A-</option>
                        <option value="B+">B+</option>
                        <option value="B-">B-</option>
                        <option value="AB+">AB+</option>
                        <option value="AB-">AB-</option>
                        <option value="0+">0+</option>
                        <option value="0-">0-</option>
                    </select>
                </div>

                <div className="label">
                    <label>Zdravstveni podaci</label>
                </div>
                
                <div className="redak">
                    <div className="pitanje">
                        Tjelesna masa ispod 55 kg?
                    </div>
                    <div className="odgovor">
                        <label>Da</label>
                        <input type="radio" name = "Da" value = 'da'/>
                    </div>
                    <div className="odgovor">
                        <label>Ne</label>
                        <input type="radio" name = "Ne" value = 'ne'/>
                    </div>
                </div>
                <div className="redak">
                    <div className="pitanje">
                        Tjelesna temperatura iznad 37°C?
                    </div>
                    <div className="odgovor">
                        <label>Da</label>
                        <input type="radio" name = "Da" value = 'da'/>
                    </div>
                    <div className="odgovor">
                        <label>Ne</label>
                        <input type="radio" name = "Ne" value = 'ne'/>
                    </div>
                </div>
                <div className="redak">
                    <div className="pitanje">
                        Povišen ili prenizak krvni tlak?
                    </div>
                    <div className="odgovor">
                        <label>Da</label>
                        <input type="radio" name = "Da" value = 'da'/>
                    </div>
                    <div className="odgovor">
                        <label>Ne</label>
                        <input type="radio" name = "Ne" value = 'ne'/>
                    </div>
                </div>
                <div className="redak">
                    <div className="pitanje">
                        Ubrzan ili usporen rad srca?
                    </div>
                    <div className="odgovor">
                        <label>Da</label>
                        <input type="radio" name = "Da" value = 'da'/>
                    </div>
                    <div className="odgovor">
                        <label>Ne</label>
                        <input type="radio" name = "Ne" value = 'ne'/>
                    </div>
                </div>
                <div className="redak">
                    <div className="pitanje">
                        Povišena razina hemoglobina u krvi?
                    </div>
                    <div className="odgovor">
                        <label>Da</label>
                        <input type="radio" name = "Da" value = 'da'/>
                    </div>
                    <div className="odgovor">
                        <label>Ne</label>
                        <input type="radio" name = "Ne" value = 'ne'/>
                    </div>
                </div>
                <div className="redak">
                    <div className="pitanje">
                        Osoba trenutno uzima antibiotike ili druge lijekove?
                    </div>
                    <div className="odgovor">
                        <label>Da</label>
                        <input type="radio" name = "Da" value = 'da'/>
                    </div>
                    <div className="odgovor">
                        <label>Ne</label>
                        <input type="radio" name = "Ne" value = 'ne'/>
                    </div>
                </div>
                <div className="redak">
                    <div className="pitanje">
                        Osoba je konzumirala alkoholna pića unutar 8 sati prije darivanja krvi?
                    </div>
                    <div className="odgovor">
                        <label>Da</label>
                        <input type="radio" name = "Da" value = 'da'/>
                    </div>
                    <div className="odgovor">
                        <label>Ne</label>
                        <input type="radio" name = "Ne" value = 'ne'/>
                    </div>
                </div>
                <div className="redak">
                    <div className="pitanje">
                        Osoba trenutno boluje od lakše akutne bolesti?
                    </div>
                    <div className="odgovor">
                        <label>Da</label>
                        <input type="radio" name = "Da" value = 'da'/>
                    </div>
                    <div className="odgovor">
                        <label>Ne</label>
                        <input type="radio" name = "Ne" value = 'ne'/>
                    </div>
                </div>
                <div className="redak">
                    <div className="pitanje">
                        Osoba (žena) je u drugom stanju, doji ili ima menstruaciju?
                    </div>
                    <div className="odgovor">
                        <label>Da</label>
                        <input type="radio" name = "Da" value = 'da'/>
                    </div>
                    <div className="odgovor">
                        <label>Ne</label>
                        <input type="radio" name = "Ne" value = 'ne'/>
                    </div>
                </div>
                <div className="redak">
                    <div className="pitanje">
                        Osoba na dan darivanj krvi obavlja opasne poslove? (rad na visini/dubini)
                    </div>
                    <div className="odgovor">
                        <label>Da</label>
                        <input type="radio" name = "Da" value = 'da'/>
                    </div>
                    <div className="odgovor">
                        <label>Ne</label>
                        <input type="radio" name = "Ne" value = 'ne'/>
                    </div>
                </div>

                <hr className='label' />

                <div className="redak">
                    <div className="pitanje">
                        Osoba je bolovala ili boluje od teških kroničnih bolesti dišnog i probavnog sustava?
                    </div>
                    <div className="odgovor">
                        <label>Da</label>
                        <input type="radio" name = "Da" value = 'da'/>
                    </div>
                    <div className="odgovor">
                        <label>Ne</label>
                        <input type="radio" name = "Ne" value = 'ne'/>
                    </div>
                </div>
                <div className="redak">
                    <div className="pitanje">
                        Osoba boluje od bolesti srca i krvnih žila, zloćudnih bolesti, bolesti jetre, AIDS-a, šećerne bolesti, živčanih i duševnih bolesti?
                    </div>
                    <div className="odgovor">
                        <label>Da</label>
                        <input type="radio" name = "Da" value = 'da'/>
                    </div>
                    <div className="odgovor">
                        <label>Ne</label>
                        <input type="radio" name = "Ne" value = 'ne'/>
                    </div>
                </div>
                <div className="redak">
                    <div className="pitanje">
                        Osoba je ovisnik o alkoholu ili drogama?
                    </div>
                    <div className="odgovor">
                        <label>Da</label>
                        <input type="radio" name = "Da" value = 'da'/>
                    </div>
                    <div className="odgovor">
                        <label>Ne</label>
                        <input type="radio" name = "Ne" value = 'ne'/>
                    </div>
                </div>
                <div className="redak">
                    <div className="pitanje">
                        Osoba (muškarac) je u životu imala spolni odnos s drugim muškarcima?
                    </div>
                    <div className="odgovor">
                        <label>Da</label>
                        <input type="radio" name = "Da" value = 'da'/>
                    </div>
                    <div className="odgovor">
                        <label>Ne</label>
                        <input type="radio" name = "Ne" value = 'ne'/>
                    </div>
                </div>
                <div className="redak">
                    <div className="pitanje">
                        Osoba je imala sploni odnos s prostitutkama?
                    </div>
                    <div className="odgovor">
                        <label>Da</label>
                        <input type="radio" name = "Da" value = 'da'/>
                    </div>
                    <div className="odgovor">
                        <label>Ne</label>
                        <input type="radio" name = "Ne" value = 'ne'/>
                    </div>
                </div>
                <div className="redak">
                    <div className="pitanje">
                        Osoba često mijenja seksualne partnere (promiskuitetna osoba)?
                    </div>
                    <div className="odgovor">
                        <label>Da</label>
                        <input type="radio" name = "Da" value = 'da'/>
                    </div>
                    <div className="odgovor">
                        <label>Ne</label>
                        <input type="radio" name = "Ne" value = 'ne'/>
                    </div>
                </div>
                <div className="redak">
                    <div className="pitanje">
                        Osoba je uzimala drogu intravenskim putem?
                    </div>
                    <div className="odgovor">
                        <label>Da</label>
                        <input type="radio" name = "Da" value = 'da'/>
                    </div>
                    <div className="odgovor">
                        <label>Ne</label>
                        <input type="radio" name = "Ne" value = 'ne'/>
                    </div>
                </div>
                <div className="redak">
                    <div className="pitanje">
                        Osoba se liječi od spolno prenosivih bolesti?
                    </div>
                    <div className="odgovor">
                        <label>Da</label>
                        <input type="radio" name = "Da" value = 'da'/>
                    </div>
                    <div className="odgovor">
                        <label>Ne</label>
                        <input type="radio" name = "Ne" value = 'ne'/>
                    </div>
                </div>
                <div className="redak">
                    <div className="pitanje">
                        Osoba je HIV-pozitivna?
                    </div>
                    <div className="odgovor">
                        <label>Da</label>
                        <input type="radio" name = "Da" value = 'da'/>
                    </div>
                    <div className="odgovor">
                        <label>Ne</label>
                        <input type="radio" name = "Ne" value = 'ne'/>
                    </div>
                </div>
                <div className="redak">
                    <div className="pitanje">
                        Osoba je seksualni partner gore navedenih osoba?
                    </div>
                    <div className="odgovor">
                        <label>Da</label>
                        <input type="radio" name = "Da" value = 'da'/>
                    </div>
                    <div className="odgovor">
                        <label>Ne</label>
                        <input type="radio" name = "Ne" value = 'ne'/>
                    </div>
                </div>


                {errorHidden ? null : <ErrorCard message={errorMessage} />}
                <div className="gumbi">
                    <button className='kreiraj'>Doniraj</button>
                </div>
            </form>
        </div>
    )
}

export default PokusajDoniranja;
