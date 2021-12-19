import React, { useEffect } from "react";

const Epruvete = ({done}) => {
        const [style, setStyle] = React.useState({});
        
        useEffect(() => {
            const newStyle = {
                opacity: 1,
                width: `${done}%`
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