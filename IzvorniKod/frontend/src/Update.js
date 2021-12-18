import React from "react";
import { Link } from 'react-router-dom';
import { useRef } from "react";

const Update = () => {
    const ref = useRef();;
    return (
        <div className="reg">
            <form className='formular'>
                <div className="tekst">
                    <p>Uredi svoje osobne podatke</p>
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
                        type='text'
                        ref={ref}
                        onFocus={() => (ref.current.type = 'date')}
                        onBlur={() => (ref.current.type = 'text')}
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
                <div className="gumbi">
                    <Link to='/profil'>
                        <button className='kreiraj'>Spremi promjene</button>
                    </Link>
                </div>
            </form>
        </div>
    )
}
export default Update;
