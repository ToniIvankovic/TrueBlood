import React from 'react';

const ErrorCard = (props) => {

    return (
        <div className='errorcard'>
            {props.message}
        </div>
    );
}

export default ErrorCard;

