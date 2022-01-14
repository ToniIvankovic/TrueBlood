import React from "react";

const Faq = () => {
    return (
        <div className="faqpage">
            <h1>Česta pitanja</h1>
            <div className="faq-pitanje">
                <h2>Kako mogu otvoriti novi korisnički račun?</h2>
            </div>
            <div className="faq-odgovor">
                <p>
                    Na početnoj stranici pritisnite na gumb "Prijavi se" te potom pritisnite na gumb "Registriraj se".
                    <br/>Nakon toga, popunite sve obavezne podatke, i neobavezne ako ih imate. Krvnu grupu ne popunjavate sami,
                    već će ju popuniti djelatnik pri vašem prvom doniranju krvi.
                    <br/>Nakon pritiska na gumb "Kreiraj račun", stvorit će Vam se novi račun, a podatci o računu bit će poslani na
                    unesenu e-adresu. Zato molimo da unesete ispravnu e-adresu kojoj možete pristupiti kako biste mogli pronaći svoje
                    inicijalne podatke - donorId, inicijalnu lozinku i poveznicu za aktivaciju.
                </p>
            </div>

            <div className="faq-pitanje">
                <h2>Nisam siguran/sigurna imam li već izrađen račun ili ne.</h2>
            </div>
            <div className="faq-odgovor">
                <p>
                    Provjerite svoju e-adresu. Ako ste nekad napravili račun, trebali biste imati poruku s inicijalnim podatcima.
                    <br/>Ukoliko ne možete pronaći tu poruku, pokušajte napraviti račun i pripazite da unesete ispravan OIB. Ako 
                    se račun izradi, Vaš račun nije postojao, ali upravo ste ga napravili. Ako dobijete poruku da OIB već postoji,
                    to znači da ste nekad već izradili račun pa pokušajte pronaći podatke za prijavu, ili se obratite administratoru sustava.
                </p>
            </div>
                        
            <div className="faq-pitanje">
                <h2>Treabm li raditi račun ako sam već donirao/donirala krv?</h2>
            </div>
            <div className="faq-odgovor">
                <p>
                    Na svakom doniranju krvi djelatnik banke za Vas će napraviti račun ako ga već nemate. Iz tog razloga, ako ste već donirali krv,
                    ne trebate raditi novi račun, već svoje podatke možete pronaći na svojoj e-adresi.
                </p>
            </div>

            <div className="faq-pitanje">
                <h2>Zašto ne mogu unijeti svoju krvnu grupu pri stvaranju računa?</h2>
            </div>
            <div className="faq-odgovor">
                <p>
                    S obzirom na to da nam je krvna grupa donora izrazito bitna informacija, krvnu grupu provjeravamo pri svakoj donaciji.
                    Stoga će vašu krvnu grupu odrediti djelatnik banke pri prvom doniranju i za Vas ju upisati na Vaš račun. Nakon toga
                    moći ćete dobivati obavijesti o nedostatkui Vaše krvne grupe.
                </p>
            </div>

            <div className="faq-pitanje">
                <h2>Mogu li izmijeniti podatke pogrešno unesene pri registraciji?</h2>
            </div>
            <div className="faq-odgovor">
                <p>
                    Jednostavno nakon prijave u sustav, na svom profilu pritisnite gumb "Uredi podatke" i promijenite bilo koji podatak koji nije ispravan.
                    Podsjećamo da podatci koje unosite moraju biti ispravni te da će ih djelatnik pri svakom doniranju obavezno provjeriti.
                    Jedini podatci koje ne možete mijenjati su vaš donorId i krvna grupa.
                </p>
            </div>
            
            <div className="faq-pitanje">
                <h2>Kako mogu dobiti potvrdu o uspješnom doniranju?</h2>
            </div>
            <div className="faq-odgovor">
                <p>
                    Potvrdu možete preuzeti tako da se prijavite u sustav sa svojim računom te na stranici profila pritisnete gumb "Prošle donacije".
                    Ovdje ćete pronaći popis svih svojih prošlih donacija, uspješnih i neuspješnih. Uz neuspješne donacije naveden je i razlog odbijanja,
                    a uz uspješne stoji gumb "Preuzmi", čijim pritiskom započinje preuzimanje PDF potvrde o tom pokušaju doniranja.
                </p>
            </div>            
        </div>
    )
}
export default Faq;