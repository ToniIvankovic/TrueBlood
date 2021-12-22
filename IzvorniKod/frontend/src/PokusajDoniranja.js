import axios from "./util/axios-instance";
import React, { useEffect, useState } from "react";
import { useRef } from "react";
import { useHistory } from "react-router";
import ErrorCard from "./ErrorCard";
import { Route, Link } from "react-router-dom";
import SearchBar from "./components/SearchBar";
import { searchDonorColumns } from "./model/SearchDonorColumns";
import TraziDonora from "./TraziDonora";
import DonorSearchIntegrated from "./components/DonorSearchIntegrated";
import { Box, Button, Divider, Grid } from "@mui/material";

const PokusajDoniranja = (props) => {
    const history = useHistory();
    const ref = useRef();

    const [errorMessage, setErrorMessage] = useState("Greška");
    const [errorHidden, setErrorHidden] = useState(true);
    const [selectedDonor, setSelectedDonor] = useState(null);
    const [shouldUpdateDonor, setShouldUpdateDonor] = useState(true);

    useEffect(() => {
        // if (props.user.userId && props.user.role != 'BANK_WORKER') {
        //     history.push('/');
        // }
    }, []);

    // hide error message on donor change
    useEffect(() => {
        setErrorHidden(true);
    }, [selectedDonor]);

    const handleEditDonor = () => {
        // goto edit donor
    }

    const handleSubmit = async (event) => {
        event.preventDefault();

        if (!selectedDonor) {
            return;
        }

        const donationTry = {
            donorId: selectedDonor.donorId,
            bloodType: selectedDonor.bloodType,
        };

        await submitDonationTry(donationTry);

        console.log("submit");
    };

    const submitDonationTry = async (donationTry) => {
        const url = "/api/v1/donation-try";

        await axios
            .post(url, donationTry)
            .then((response) => {
                console.log(response);
            })
            .catch((error) => {
                if (error.response && error.response.data) {
                    setErrorHidden(false);
                    setErrorMessage(error.response.data);
                }
            });
    };

    return (
        <>
            <div className="tekst">
                <p>Stvori pokušaj doniranja </p>
            </div>
            <br />
            <Grid container justifyContent="center">
                <Grid item xs={10} md={6}>
                    <DonorSearchIntegrated
                        selectedDonor={selectedDonor}
                        setSelectedDonor={setSelectedDonor}
                    />
                </Grid>
            </Grid>
            <Box sx={{ marginTop: 2 }}s ></Box>
            <Grid sx={{ display: ( selectedDonor ? 'flex' : 'none' ) }} container justifyContent="center">
                <Button
                    onClick={ () => handleEditDonor() }
                    style={{ height: 50, marginLeft: 10 }}
                    variant="contained"
                >
                    Uredi donora
                </Button>
            </Grid>
            <div className="reg">
                <form
                    onSubmit={(event) => handleSubmit(event)}
                    className="formular"
                >
                    <div className="label">
                        <label>Osobni podaci</label>
                    </div>
                    <div className="single">
                        <input
                            name="donorId"
                            type="text"
                            placeholder={"donorId: " + selectedDonor?.donorId}
                            disabled
                        ></input>
                    </div>
                    <div className="dupli">
                        <input
                            name="firstName"
                            type="text"
                            placeholder={"ime: " + selectedDonor?.firstName}
                            disabled
                        ></input>
                        <input
                            name="lastName"
                            type="text"
                            placeholder={"prezime: " + selectedDonor?.lastName}
                            disabled
                        ></input>
                    </div>
                    <div className="single">
                        <input
                            name="oib"
                            type="text"
                            placeholder={"OIB: " + selectedDonor?.oib}
                            disabled
                        ></input>
                    </div>
                    <div className="krgrupe">
                        <label>Krvna grupa</label>
                        <select value={selectedDonor?.bloodType} disabled={selectedDonor == null}>
                            <option value="---">Nema</option>
                            <option value="A+">A+</option>
                            <option value="A-">A-</option>
                            <option value="B+">B+</option>
                            <option value="B-">B-</option>
                            <option value="AB+">AB+</option>
                            <option value="AB-">AB-</option>
                            <option value="0+">0+</option>
                            <option value="0-">0-</option>
                        </select>
                    </div>
                    <br />
                    {errorHidden ? null : <ErrorCard message={errorMessage} />}
                    <div className="gumbi">
                        <button className="kreiraj">Doniraj</button>
                    </div>
                </form>
            </div>
        </>
    );
};

export default PokusajDoniranja;
