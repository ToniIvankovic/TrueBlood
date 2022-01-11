import React, { useEffect } from "react";

const Epruvete = ({done, maxUnits, minUnits}) => {
        const [style, setStyle] = React.useState({});
        
        //S obzirom na buggy prikaz pri niskim razinama, razmisliti o postavljanju minimalne razine na 5
        //{groups['max0+'] ? 80*groups['0+']/groups['max0+'] : groups['0+']}
        useEffect(() => {
            let progress = maxUnits ? 90*done/maxUnits : done;
            if(progress > 98) progress=98;
            const newStyle = {
                opacity: 1,
                width: `${progress}%`
            }
            
            setStyle(newStyle);
        }, [done]);
        
        const css = (color, left) => {
            return {
            position:"absolute", 
            left: left + "%", 
            top:"-10%", 
            width:"4px", 
            height:"120%", 
            backgroundColor: color
        }}
            
        return (
            <div>
                <div className="progress">
                    <div className="progress-done" style={style}></div>
                    <div style={css("teal", 90)}></div>
                    <div style={css("red", maxUnits && minUnits ? 90*minUnits/maxUnits : 20)}></div>
                </div>
            </div>
        )
}
export default Epruvete;