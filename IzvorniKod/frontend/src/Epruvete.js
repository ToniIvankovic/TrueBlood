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
            <div className="progress">
                <div className="progress-done" style={style}></div>
            </div>
        )
}
export default Epruvete;