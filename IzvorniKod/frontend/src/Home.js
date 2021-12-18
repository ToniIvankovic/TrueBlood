import React from "react";
import { Link } from 'react-router-dom';
import Epruvete from './Epruvete';

const Home = (props) => {
    return (
        <div className="str">
            <div className="content">
                <div className="naslovna">
                    <div className="pitanje">
                        <p>Želiš donirati krv?</p>
                    </div>
                    {props.loggedIn ?
                        <div>
                            <Link to='/profil'>
                                <button className="registracija">Profil</button>
                            </Link>
                        </div>
                        :
                        <div>
                            <Link to='/prijava'>
                                <button className="registracija">Prijavi se</button>
                            </Link>
                        </div>}
                    <div className="akt">
                        <p>Aktualne zalihe krvi:</p>
                    </div>
                </div>
            </div>
            <div className="epruvete">
                <div className="sample">
                    <Epruvete done = "10"/>
                    <div className="grupa">A-</div>
                </div>
                <div className="sample">
                    <Epruvete done = "20"/>
                    <div className="grupa">A+</div>
                </div>
                <div className="sample">
                    <Epruvete done = "30"/>
                    <div className="grupa">B-</div>
                </div>
                <div className="sample">
                    <Epruvete done = "40"/>
                    <div className="grupa">B+</div>
                </div>
                <div className="sample">
                    <Epruvete done = "50"/>
                    <div className="grupa">AB-</div>
                </div>
                <div className="sample">
                    <Epruvete done = "60"/>
                    <div className="grupa">AB+</div>
                </div>
                <div className="sample">
                    <Epruvete done = "70"/>
                    <div className="grupa">0-</div>
                </div>
                <div className="sample">
                    <Epruvete done = "80"/>
                    <div className="grupa">0+</div>
                </div>
            </div>
        </div>
    )
}

export default Home;