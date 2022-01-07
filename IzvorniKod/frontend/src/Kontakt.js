import React from "react";
import KontaktImage from "./KontaktImage.png"
const Kontakt = () => {
    return (
        <div className="kontaktpage">
            <div className="kontakt-naslov">
                <h1>Slobodno nas kontaktirajte!</h1>
            </div>
            <div className="kontakt-info">
                Za sve upite vezane uz poteškoće s prijavom i podatcima o računu obratite se administratoru sustava na 
                e-adresu <a href="mailto:toni.ivankovic@gmail.com">toni.ivankovic@gmail.com</a>, a za upite vezane za 
                proces doniranja obratite se na broj telefona <span>01/0000-000.</span>
            </div>
            <div>
                <img src={KontaktImage} alt="image4" className="kontakt-image" />
            </div>
            <div className="kontakt-ref"> 
                <a href='https://www.freepik.com/vectors/people'>People vector created by pikisuperstar - www.freepik.com</a>
                <br/>Icons made by <a href="https://www.freepik.com" title="Freepik">Freepik</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a>
            </div>
        </div>
    )
}
export default Kontakt;