import React from "react";
import Image from './Image.png';


const Home = () => {
    return(
        <div className="homepage">
            <div className="text-login">
                <div className="text">
                    <div><p>Doniraj krv, spasi život!</p></div>
                    <button className="registracija"> Registriraj se</button>
                </div>
                <div className="login">
                    <p>Prijavi se!</p>
                    <input 
                        className='input'
                        type='text'
                        placeholder="Email"
                        required
                    ></input>
                    <input
                        className='input'
                        type='password'
                        placeholder="Lozinka"
                        required
                    ></input>
                    <button
                        className='submit'
                        type="submit"
                    >Prijava</button>
                </div>
            </div>
            <div className="image-alert">
                <p className="alert">Nedostaje krvi krvne grupe x</p>
                <img src={Image} alt="image1" className="image"/>
            </div>
        </div>
    )
}
export default Home;