import axios from "./util/axios-instance";
import React, { useEffect, useState } from "react";
import { useRef } from "react";
import { useHistory } from "react-router";
import ErrorCard from "./ErrorCard";
import { searchDonorColumns } from "./model/SearchDonorColumns";
import SearchBar from "./components/SearchBar";
import { Grid, Box, Button, Divider } from "@mui/material";
import CustomizedAccordion from "./components/CustomizedAccordion";
import { useLocation } from "react-router-dom/cjs/react-router-dom.min";

const TraziDonora = (props) => {
    const history = useHistory();
    const ref = useRef();
    let location = useLocation();

    const [errorMessage, setErrorMessage] = useState("Greška");
    const [errorHidden, setErrorHidden] = useState(true);

    const donorNone = {};
    const [donor, setDonor] = useState(donorNone);
    const [donorList, setDonorList] = useState([]);

    const [expanded, setExpanded] = useState(false);
    const [loading, setLoading] = useState(false);

    const handleSubmit = (event) => {
        event.preventDefault();
        window.localStorage.setItem("donor", JSON.stringify(donor));
        props.setDonor(donor); //Dojavljuje app.js-u da je donor postavljen u localstorage
        history.goBack();
    };

    useEffect(() => {
        console.log("TraziDonora props");
        console.log(props);
    }, []);

    const queryDonors = async (query) => {
        setLoading(true);
        const url = "/api/v1/donor?query=" + query;
        await axios
            .get(url, {
                //headers: { Authorization: bearerAuth },
            })
            .then((response) => {
                let newDonorList = response.data.map((el) => {
                    // id has to be set for datagrid to work
                    el.id = el.donorId;
                    return el;
                });

                // if(newDonorList.length == 1) {
                //     setSelectedDonor(newDonorList[0]);
                // }
                console.log("query response");

                setDonorList(newDonorList);
            })
            .finally(() => {
                setLoading(false);
            });
    };

    const findDonor = (event) => {
        //treba slati upit na endpoint i dohvatiti preostale podatke - ovo je samo fake placeholder
        //TODO: u tražilici napraviti onchange koji će mijenjati neke stateove i oni se šalju u requestu
        setDonor({
            donorId: 1234567,
            firstName: "toni",
            lastName: "ivankovic",
            oib: "24144225112",
            bloodType: "0+",
        });
    };

    useEffect(() => {
        console.log("Donor u trazidonora:");
        console.log(donor);
    }, [donor]);

    return (
        <>
            <Grid container justifyContent="center">
                <Grid item xs={11} sm={10} md={8}>
                    <Box>
                        <SearchBar
                            columns={searchDonorColumns}
                            data={donorList}
                            queryFunction={queryDonors}
                            loading={loading}
                            selectedDonor={props.donor}
                            setSelectedDonor={props.setDonor}
                            onSelect={setExpanded}
                        />
                    </Box>
                </Grid>
            </Grid>
        </>
        // <div className="reg">
        //     <form onSubmit={(event) => handleSubmit(event)} className='formular'>
        //         ({props.user.role})
        //         <div className="tekst">
        //             <p>Pronađi donora</p>
        //         </div>
        //         <br />
        //         <div className="label">
        //             <label>Osobni podaci</label>
        //         </div>
        //         <div className="single">
        //             <input
        //                 name='donorId'
        //                 type="text"
        //                 placeholder={"donorId: " + donor.donorId}
        //             ></input>
        //         </div>
        //         <div className="dupli">
        //             <input
        //                 name='firstName'
        //                 type="text"
        //                 placeholder={"ime: " + donor.firstName}
        //             ></input>
        //             <input
        //                 name='lastName'
        //                 type="text"
        //                 placeholder={"prezime: " + donor.lastName}
        //             ></input>
        //         </div>
        //         <div className="single">
        //             <input
        //                 name='oib'
        //                 type="text"
        //                 placeholder={"OIB: " + donor.oib}
        //             ></input>
        //         </div>

        //         {errorHidden ? null : <ErrorCard message={errorMessage} />}
        //         <div className="gumbi">
        //             <button type="button" onClick={(event) => findDonor(event)} className='kreiraj'>Pronađi</button>
        //         </div>
        //         <br />
        //         <div className="gumbi">
        //             <button className='kreiraj'>Uzmi ovog donora</button>
        //         </div>
        //     </form>
        // </div>
    );
};

export default TraziDonora;
