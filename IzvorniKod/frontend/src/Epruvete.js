import React, { useEffect } from "react";

const Epruvete = ({done}) => {
        const [style, setStyle] = React.useState({});
        
        //S obzirom na buggy prikaz pri niskim razinama, razmisliti o postavljanju minimalne razine na 5
        useEffect(() => {
            let progress = done;
            if(progress > 98) progress=98;
            const newStyle = {
                opacity: 1,
                width: `${progress}%`
            }
            
            setStyle(newStyle);
        }, [done]);
        
        return (
            <div>
                <div className="progress">
                    <div className="progress-done" style={style}></div>
                </div>
                <div>{done}</div>
            </div>
        )
}
export default Epruvete;