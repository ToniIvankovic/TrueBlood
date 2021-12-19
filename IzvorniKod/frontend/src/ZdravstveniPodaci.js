import React from 'react';

const ZdravstveniPodaci = ({question, id}) => {
    return (
        <div className="redak">
            <div className="pitanje">
                {question}
            </div>
            <div className="odgovor">
                <label for={id + "yes"}>Da</label>
                <input id={id + "yes"} type="radio" name = {id} value = 'da'/>
            </div>
            <div className="odgovor">
                <label for={id + "no"}>Ne</label>
                <input id={id + "no"} type="radio" name = {id} value = 'ne'/>
            </div>
    </div>        
    )
}

export default ZdravstveniPodaci;