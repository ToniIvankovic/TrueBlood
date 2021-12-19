import React from 'react';

const ZdravstveniPodaci = ({question}) => {
    return (
        <div className="redak">
            <div className="pitanje">
                {question}
            </div>
            <div className="odgovor">
                <label>Da</label>
                <input type="radio" name = "Da" value = 'da'/>
            </div>
            <div className="odgovor">
                <label>Ne</label>
                <input type="radio" name = "Ne" value = 'ne'/>
            </div>
    </div>        
    )
}

export default ZdravstveniPodaci;