import React, { useEffect, useState } from "react";
import { Link } from 'react-router-dom';
import Epruvete from './Epruvete';
import { getBloodSupply, isEqualWithNull } from "./Util";

const Home = (props) => {

    const [bloodSupply, setBloodSupply] = useState({});
    const [groups, setGroups] = useState({
        '0+ ': 0,
        '0- ': 0,
        'A+ ': 0,
        'A- ': 0,
        'B+ ': 0,
        'B- ': 0,
        'AB+': 0,
        'AB-': 0,
    });

    useEffect(() => {
        getBloodSupply(setBloodSupply);
    }, [])
    
    //Extracting supply from array to a JSON
    useEffect(() => {
        if(isEqualWithNull(bloodSupply,{})) return; 
        
        let localGroups = {};
        for(let i = 0; i < 8; i++){
            let group = bloodSupply[i];
            let amount = group.numberOfUnits;
            localGroups = {
                ...localGroups,
                [group.bloodType.trim()]: amount
            }
        }
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
                    <Epruvete done = {groups['A-']}/>
                    <div className="grupa">A-</div>
                </div>
                <div className="sample">
                    <Epruvete done = {groups['A+']}/>
                    <div className="grupa">A+</div>
                </div>
                <div className="sample">
                    <Epruvete done = {groups['B-']}/>
                    <div className="grupa">B-</div>
                </div>
                <div className="sample">
                    <Epruvete done = {groups['B+']}/>
                    <div className="grupa">B+</div>
                </div>
                <div className="sample">
                    <Epruvete done = {groups['AB-']}/>
                    <div className="grupa">AB-</div>
                </div>
                <div className="sample">
                    <Epruvete done = {groups['AB+']}/>
                    <div className="grupa">AB+</div>
                </div>
                <div className="sample">
                    <Epruvete done = {groups['0-']}/>
                    <div className="grupa">0-</div>
                </div>
                <div className="sample">
                    <Epruvete done = {groups['0+']}/>
                    <div className="grupa">0+</div>
                </div>
            </div>
        </div>
    )
}

export default Home;