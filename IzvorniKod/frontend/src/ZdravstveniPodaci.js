import React from 'react';

const ZdravstveniPodaci = ({question, id, handleChange}) => {
    return (
        <div className="redak">
            <div className="question">
                {question}
            </div>
            <div className="odgovor">
                <label htmlFor={id + "yes"}>Da</label>
                <input 
                id={id + "yes"} 
                type="radio" 
                name = {id} 
                value = 'da' 
                onChange={(event) => handleChange(event)}
                required/>
            </div>
            <div className="odgovor">
                <label htmlFor={id + "no"}>Ne</label>
                <input 
                id={id + "no"} 
                type="radio" 
                name = {id} 
                onChange={(event) => handleChange(event)}
                value = 'ne'/>
            </div>
        </div>        
    )
}

export default ZdravstveniPodaci;