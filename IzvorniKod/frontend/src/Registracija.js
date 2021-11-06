import React from "react";
import { useRef } from "react";
import {Link} from 'react-router-dom';

const Registracija = () => {
    const ref = useRef();
    return(
        <div className="reg">
            <form className='formular'>
                <div className="tekst">
                <p>Kreiraj korisnički račun!</p>
                </div>
                <div className="label">
                    <label>Osobni podaci</label>
                </div>
                <div className="dupli">
                    <input
                        name='ime' 
                        type="text"
                        placeholder="Ime *"
                        required></input>
                    <input
                        name='prezime' 
                        type="text"
                        placeholder="Prezime *"
                        required></input>
                </div>
                <div className="single">
                    <input
                        name='oib' 
                        type="text"
                        placeholder="OIB *"
                        maxLength='11'
                        required></input>
                </div>
                <div className="dupli">
                    <input
                        name='datumrod' 
                        type = 'text'
                        ref={ref}
                        onFocus = {() => (ref.current.type = 'date')}
                        onBlur = {() => (ref.current.type = 'text')}
                        placeholder="Datum rođenja"></input>
                    <input
                        name='mjestorod' 
                        type="text"
                        placeholder="Mjesto rođenja *"
                        required></input>
                </div>
                <div className="single">
                    <input
                        name='adresa' 
                        type="text"
                        placeholder="Adresa stanovanja *"
                        required></input>
                </div>
                <div className="label">
                    <label>Kontakt podaci</label>
                </div>
                <div className="single">
                    <input
                        name='email' 
                        type="text"
                        placeholder="Email *"
                        required></input>
                </div>
                <div className="single">
                    <input
                        name='mobitel' 
                        type="text"
                        placeholder="Kontakt (osobni) *"
                        maxLength='10'
                        required></input>
                </div>
                <div className="dupli">
                    <input
                        name='firma' 
                        type="text"
                        placeholder="Mjesto zaposlenja (firma)"></input>
                    <input
                        name='telefon' 
                        type="text"
                        placeholder="Kontakt (poslovni)"
                        maxLength='10'></input>                    
                </div>
                {/* <div className="label">
                    <label>Zdravstveni podaci*</label>
                </div> */}
                {/* <div className="krgrupe">
                    <label>Krvna grupa</label>
                    <select>
                        <option value="A+">A+</option>
                        <option value="A-">A-</option>
                        <option value="B+">B+</option>
                        <option value="B-">B-</option>
                        <option value="AB+">AB+</option>
                        <option value="AB-">AB-</option>
                        <option value="0+">0+</option>
                        <option value="0-">0-</option>
                    </select>       
                </div> */}
                <div className="gumbi">
                    <Link to='/autorizacija'>
                        <button className='kreiraj'>Kreiraj račun</button>
                    </Link> 
                </div>
                {/* <div className="napomena">
                    <p>*Vaše zdravstvene podatke popunjava djelatnik prije doniranja krvi.</p>
                </div> */}
            </form>
        </div>
    )
}

export default Registracija;