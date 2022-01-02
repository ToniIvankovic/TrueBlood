import React, { useEffect, useState } from "react";
import { Link } from 'react-router-dom';
import Loginimg from './Loginimg.png';
import { useHistory } from "react-router";

import { isEqualWithNull, userPublic } from "./Util";

const RacunNeaktiviran = (props) => {

    const [pageReady, setPageReady] = useState(false);
    let history = useHistory();
    // useEffect(()=>{
    //     //Pusha sve osim prijavljenog usera koji nije aktiviran
    //     if(! ( !isEqualWithNull(props.user, userPublic) && props.accActivated == false)){
    //         history.push('/');
    //     }
    //     setPageReady(true);
    // });

    // if(!pageReady){
    //     return <div></div>
    // }
    return (
        <div className="sent">
            <div className="smile">
                <img src={Loginimg} alt="smileface" />
            </div>
            <div className="big">
                <p>Korisnički račun je kreiran, ali nije aktiviran!</p>
            </div>
            <div className="small">
                <p>
                    Na Vašu e-adresu već je poslana <span>poveznica za aktivaciju</span> računa - molimo prvo pristupom poveznici aktivirajte račun, a onda
                    možete nastaviti s korištenjem usluga sustava
                </p>
            </div>
            <div className="idi">
                <Link to='/'>
                    <button className="registracija">Idi na naslovnicu</button>
                </Link>
            </div>
        </div>
    )
}

export default RacunNeaktiviran;