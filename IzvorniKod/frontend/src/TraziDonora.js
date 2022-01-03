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

    // const handleSubmit = (event) => {
    //     event.preventDefault();
    //     window.localStorage.setItem("donor", JSON.stringify(donor));
    //     props.setDonor(donor); //Dojavljuje app.js-u da je donor postavljen u localstorage
    //     history.goBack();
    // };

    const queryDonors = async (query) => {
        setLoading(true);
        const url = "/api/v1/donor?query=" + query;
        await axios
            .get(url, {
                //headers: { Authorization: bearerAuth },
            })
            .then((response) => {
                if(!response.data ||
                    response.data.length == 1 && response.data[0] == null){
                        setDonorList([]);
                    }
                else{
                    let newDonorList = response.data.map((el) => {
                        // id has to be set for datagrid to work
                        if(el) el.id = el.donorId;
                        else return {id:9999999};
                        return el;
                    });
                    
                    console.log("query response");
                    setDonorList(newDonorList);
                }
            })
            .finally(() => {
                setLoading(false);
            });
    };

    return (
        <>
            <Grid container justifyContent="center">
                <Grid item xs={11} sm={10} md={8}>
                    <Box>
                        <SearchBar
                            donorList={donorList}
                            columns={searchDonorColumns}
                            data={donorList}
                            queryFunction={queryDonors}
                            loading={loading}
                            selectedDonor={props.donor}
                            setSelectedDonor={props.setDonor}
                            onSelect={setExpanded}
                            setSelection={(donor) => {
                                props.setDonor(donor)
                                props.setExisting(true)
                                history.goBack();
                            }}
                        />
                    </Box>
                </Grid>
            </Grid>
        </>
    );
};

export default TraziDonora;
