import React from "react";
import { Link } from 'react-router-dom';
import Loginimg from './Loginimg.png';

const PostPokusajDoniranja = (props) => {
    return (
        <div className="sent">
            <div className="smile">
                <img src={Loginimg} alt="smileface" />
            </div>
            <div className="big">
                {props.successfulDonation?
                <p>Uspješno darivanje krvi!</p>
                :
                <p>Neuspješno darivanje krvi...</p>
                }
            </div>
            <div className="small">
                {props.successfulDonation?
                <p>PDF potvrda o darivanju poslana je na mail</p>
                :
                <p>Razlog odbijanja: {props.rejectReason} </p>
                }
                {props.permRejected? <p>Donor je trajno odbijen.</p>:''}
            </div>
            <div className="idi">
                <Link to='/'>
                    <button className="registracija">Idi na naslovnicu</button>
                </Link>
                <Link to='/pokusaj_doniranja'>
                    <button className="registracija">Nova donacija</button>
                </Link>
            </div>
        </div>
    )
}

export default PostPokusajDoniranja;