import React from "react";
import { Link } from 'react-router-dom';

const Home = (props) => {
    return (
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
                    </div>
                }

                {/* <div className="akt">
                    <p>Aktualne zalihe krvi:</p>
                </div> */}
            </div>
            {/* <div className="epruvete">
                <div className="sample">
                    <p>0-</p>
                    <div className="bar"></div>
                </div>
                <div className="sample">
                    <p>0+</p>
                    <div className="bar"></div>
                </div>
                <div className="sample">
                    <p>AB-</p>                
                    <div className="bar"></div>
                </div>
                <div className="sample">
                    <p>AB+</p>
                    <div className="bar"></div>
                </div>
                <div className="sample">
                    <p>B-</p>
                    <div className="bar"></div>
                </div>
                <div className="sample">
                    <p>B+</p>
                    <div className="bar"></div>
                </div>
                <div className="sample">
                    <p>A-</p>
                    <div className="bar"></div>
                </div>
                <div className="sample">
                    <p>A+</p>
                    <div className="bar"></div>
                </div>
            </div> */}
            {/* <div className="info">
                <p>*Zalihe krvi iskazane su u jedinicama (450mL).</p>
            </div> */}
        </div>
    )
}

export default Home;