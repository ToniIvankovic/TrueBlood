import axios from './util/axios-instance';
import React, { useEffect, useState } from "react";
import { useRef } from "react";
import { useHistory } from 'react-router';
import ErrorCard from './ErrorCard';
import { getBloodSupply, getDonorById, isEqualWithNull } from './Util';

const OptimalneGranice = (props) => {

    const history = useHistory();
    const ref = useRef();

    const [levels, setLevels] = useState({});
    
    const [bloodSupplyArray, setBloodSupplyArray] = useState({});

    useEffect(()=>{
        getBloodSupply(setBloodSupplyArray)
    },[])
    useEffect(()=>{
        if(isEqualWithNull(bloodSupplyArray)) return;
        let localLevels = {}
        for(let supply of bloodSupplyArray){
            let type = supply.bloodType.trim();
            let typeMin = type.concat("min");
            let typeMax = type.concat("max");
            localLevels[typeMin] = supply.minUnits,
            localLevels[typeMax] = supply.maxUnits 
        }
        
        setLevels(localLevels);
    },[bloodSupplyArray])
    console.log(levels)

    const [errorMessage, setErrorMessage] = useState('Greška');
    const [errorHidden, setErrorHidden] = useState(true);

    const handleChange = (event) => {
        let name = event.target.name;
        let value = event.target.value;
        setLevels({
            ...levels,
            [name]: value
        });
    }

    const handleSubmit = (event) => {
        event.preventDefault();
        let bloodTypes = ['0+', '0-', 'A+', 'A-', 'B+', 'B-', 'AB+', 'AB-']
        let lowerLevels = []
        let upperLevels = []
        for(let bloodType of bloodTypes){
            lowerLevels.push(levels[bloodType.concat("min")]);
            upperLevels.push(levels[bloodType.concat("max")]);
        }
        let arrayLevels = {'bloodTypes': bloodTypes, 'minUnits': lowerLevels, 'maxUnits': upperLevels}
        console.log('Submitting!');
        console.log(arrayLevels);
    
        const url = '/api/v1/blood-supply';

        axios.post(url, arrayLevels)
            .then((response) => {
                console.log('Optimal levels successfully set:');
                console.log(response.data)
                history.goBack();
            })
            .catch((error) => {
                console.log('Error while seting optimal levels. Response: ' + error.response);
                console.log(error.response);
                //edit this
                if (error.response) {
                    if (error.response.status == 400) {
                        const message = error.response.data;
                        if (message.includes('veći je od dostupnog broja')) {
                            setErrorMessage('Greška! Nema toliko jedinica krvi u zalihi.');
                        } 
                        console.log(error.response.data);
                    } else {
                        setErrorMessage('Greška pri registraciji!');
                    }
                } else{
                    setErrorMessage('Unutarnja greška');
                }
                setErrorHidden(false);
            });
    }

    return (
        <div className="reg">
            <div className="roledesplay">({props.user.role})</div>
            <form onSubmit={(event) => handleSubmit(event)} className='formular'>
                <div className="tekst-secondary">
                    <p>Postavi optimalne granice zaliha krvi</p>
                </div>
                <div className="label-secondary">
                    Zadaj granice (broj jedinica od 450mL)
                </div>
                <div className="dupli">
                    <div>
                        <label htmlFor='0+max'>0+ max</label>
                        <input
                            onChange={(event) => handleChange(event)}
                            name='0+max'
                            type="text"
                            defaultValue={levels['0+max']}
                            ></input>
                    </div>
                    <div>
                        <label htmlFor='0+min'>0+ min</label>
                        <input
                            onChange={(event) => handleChange(event)}
                            name='0+min'
                            type="text"
                            defaultValue={levels['0+min']}
                            ></input>
                    </div>
                </div>
                <div className="dupli">
                    <div>
                        <label htmlFor='0+max'>0- max</label>
                        <input
                            onChange={(event) => handleChange(event)}
                            name='0-max'
                            type="text"
                            defaultValue={levels['0-max']}
                            ></input>
                    </div>
                    <div>
                        <label htmlFor='0-min'>0- min</label>
                        <input
                            onChange={(event) => handleChange(event)}
                            name='0-min'
                            type="text"
                            defaultValue={levels['0-min']}
                            ></input>
                    </div>
                </div>
                <div className="dupli">
                    <div>
                        <label htmlFor='A+max'>A+ max</label>
                        <input
                            onChange={(event) => handleChange(event)}
                            name='A+max'
                            type="text"
                            defaultValue={levels['A+max']}
                            ></input>
                    </div>
                    <div>
                        <label htmlFor='A+min'>A+ min</label>
                        <input
                            onChange={(event) => handleChange(event)}
                            name='A+min'
                            type="text"
                            defaultValue={levels['A+min']}
                            ></input>
                    </div>
                </div>
                <div className="dupli">
                    <div>
                        <label htmlFor='A-max'>A- max</label>
                        <input
                            onChange={(event) => handleChange(event)}
                            name='A-max'
                            type="text"
                            defaultValue={levels['A-max']}
                            ></input>
                    </div>
                    <div>
                        <label htmlFor='A-min'>A- min</label>
                        <input
                            onChange={(event) => handleChange(event)}
                            name='A-min'
                            type="text"
                            defaultValue={levels['A-min']}
                            ></input>
                    </div>
                </div>
                <div className="dupli">
                    <div>
                        <label htmlFor='B+max'>B+ max</label>
                        <input
                            onChange={(event) => handleChange(event)}
                            name='B+max'
                            type="text"
                            defaultValue={levels['B+max']}
                            ></input>
                    </div>
                    <div>
                        <label htmlFor='B+min'>B+ min</label>
                        <input
                            onChange={(event) => handleChange(event)}
                            name='B+min'
                            type="text"
                            defaultValue={levels['B+min']}
                            ></input>
                    </div>
                </div>
                <div className="dupli">
                    <div>
                        <label htmlFor='B-max'>B- max</label>
                        <input
                            onChange={(event) => handleChange(event)}
                            name='B-max'
                            type="text"
                            defaultValue={levels['B-max']}
                            ></input>
                    </div>
                    <div>
                        <label htmlFor='B-min'>B- min</label>
                        <input
                            onChange={(event) => handleChange(event)}
                            name='B-min'
                            type="text"
                            defaultValue={levels['B-min']}
                            ></input>
                    </div>
                </div>
                <div className="dupli">
                    <div>
                        <label htmlFor='AB+max'>AB+ max</label>
                        <input
                            onChange={(event) => handleChange(event)}
                            name='AB+max'
                            type="text"
                            defaultValue={levels['AB+max']}
                            ></input>
                    </div>
                    <div>
                        <label htmlFor='AB+min'>AB+ min</label>
                        <input
                            onChange={(event) => handleChange(event)}
                            name='AB+min'
                            type="text"
                            defaultValue={levels['AB+min']}
                            ></input>
                    </div>
                </div>
                <div className="dupli">
                    <div>
                        <label htmlFor='AB-max'>AB- max</label>
                        <input
                            onChange={(event) => handleChange(event)}
                            name='AB-max'
                            type="text"
                            defaultValue={levels['AB-max']}
                            ></input>
                    </div>
                    <div>
                        <label htmlFor='AB-min'>AB- min</label>
                        <input
                            onChange={(event) => handleChange(event)}
                            name='AB-min'
                            type="text"
                            defaultValue={levels['AB-min']}
                            ></input>
                    </div>
                </div>
                {errorHidden ? null : <ErrorCard message={errorMessage} />}
                <div className="gumbi">
                    <button className='kreiraj'>Spremi granice</button>
                </div>
            </form>
        </div>
    )
}

export default OptimalneGranice;
