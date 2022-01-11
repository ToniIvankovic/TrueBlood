import React, { useEffect, useState } from "react";
import { Link } from 'react-router-dom';
import Epruvete from './Epruvete';
import { getBloodSupply, isEqualWithNull } from "./Util";

const Home = (props) => {

    const [bloodSupply, setBloodSupply] = useState({});
    const [groups, setGroups] = useState({
        '0+ ': 0,
        'max0+ ': 0,
        'min0+ ': 0,
        '0- ': 0,
        'max0- ': 0,
        'min0- ': 0,
        'A+ ': 0,
        'maxA+ ': 0,
        'minA+ ': 0,
        'A- ': 0,
        'maxA- ': 0,
        'minA- ': 0,
        'B+ ': 0,
        'maxB+ ': 0,
        'minB+ ': 0,
        'B- ': 0,
        'maxB- ': 0,
        'minB- ': 0,
        'AB+': 0,
        'maxAB+': 0,
        'minAB+': 0,
        'AB-': 0,
        'maxAB-': 0,
        'minAB-': 0,
    });

    const[warningMessage, setWarningMessage] = useState(undefined)

    useEffect(() => {
        getBloodSupply(setBloodSupply);
    }, [])
    
    //Extracting supply from array to a JSON
    useEffect(() => {
        if(isEqualWithNull(bloodSupply,{})) return; 
        
        let localGroups = {};
        let missingString = "";
        for(let i = 0; i < 8; i++){
            let group = bloodSupply[i];
            let amount = group.numberOfUnits;
            let name = group.bloodType.trim();
            let maxName = "max"+name;
            let minName = "min"+name;
            let max = group.maxUnits;
            let min = group.minUnits;
            if(amount < min) missingString += name + ", ";
            localGroups = {
                ...localGroups,
                [name]: amount,
                [maxName]: max,
                [minName]: min
            }
        }
        if(missingString != ""){
            missingString = "Nedostaje krvnih grupa: " + missingString;
            missingString = missingString.trim();
            missingString = missingString.substring(0, missingString.length-1)
        }
        setWarningMessage(missingString);
        setGroups(localGroups);
    }, [bloodSupply])
 
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
                    <Epruvete done = {groups['0+']} maxUnits={groups['max0+']} minUnits={groups['min0+']}/>
                    <div className="grupa">0+</div>
                </div>
                <div className="sample">
                    <Epruvete done = {groups['0-']} maxUnits={groups['max0-']} minUnits={groups['min0-']}/>
                    <div className="grupa">0-</div>
                </div>
                <div className="sample">
                    <Epruvete done = {groups['A+']} maxUnits={groups['maxA+']} minUnits={groups['minA+']}/>
                    <div className="grupa">A+</div>
                </div>
                <div className="sample">
                    <Epruvete done = {groups['A-']} maxUnits={groups['maxA-']} minUnits={groups['minA-']}/>
                    <div className="grupa">A-</div>
                </div>
                <div className="sample">
                    <Epruvete done = {groups['B+']} maxUnits={groups['maxB+']} minUnits={groups['minB+']}/>
                    <div className="grupa">B+</div>
                </div>
                <div className="sample">
                    <Epruvete done = {groups['B-']} maxUnits={groups['maxB-']} minUnits={groups['minB-']}/>
                    <div className="grupa">B-</div>
                </div>
                <div className="sample">
                    <Epruvete done = {groups['AB+']} maxUnits={groups['maxAB+']} minUnits={groups['minAB+']}/>
                    <div className="grupa">AB+</div>
                </div>
                <div className="sample">
                    <Epruvete done = {groups['AB-']} maxUnits={groups['maxAB-']} minUnits={groups['minAB-']}/>
                    <div className="grupa">AB-</div>
                </div>
            </div>
            <div className="image-alert home-alert">
                {warningMessage? <p className="alert">{warningMessage}<br/>Molimo odazovite se na akciju darivanja krvi</p> : ''}
            </div>
        </div>
    )
}

export default Home;