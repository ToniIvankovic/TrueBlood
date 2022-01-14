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
import { searchBankWorkerColumns } from "./model/SearchBankWorkerColumns";
import { searchDonationHistoryColumns } from "./model/SearchDonationHistoryCollumns";

const Trazilica = (props) => {
    const history = useHistory();
    const ref = useRef();
    let location = useLocation();

    const [errorMessage, setErrorMessage] = useState("Greška");
    const [errorHidden, setErrorHidden] = useState(true);

    const [userList, setUserList] = useState([]);

    const [expanded, setExpanded] = useState(false);
    const [loading, setLoading] = useState(false);


    const getRows = async (query) => {
        setLoading(true);
        let url = ""
        if(props.userClass == 'history'){
            url = '/api/v1/donation-try/' + props.user.userId;
        } else{
            url = `/api/v1/${props.userClass}?query=` + query;
        }
        await axios
            .get(url, {
                //headers: { Authorization: bearerAuth },
            })
            .then((response) => {
                if(!response.data ||
                    response.data.length == 1 && response.data[0] == null) {
                        setUserList([]);
                    }
                else{
                    let newUserList = response.data.map((el) => {
                        // id has to be set for datagrid to work

                        if(el){
                            if(props.userClass == 'donor'){
                                el.id = el.donorId;
                            } else if(props.userClass == 'bank-worker'){
                                el.id = el.bankWorkerId;
                            } else{
                                el.id = el.donationId;
                            }
                        } 
                        else return {id:9999999};
                        return el;
                    });
                    
                    console.log("query response");
                    setUserList(newUserList);
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
                            columns={(props.userClass == 'donor') ? searchDonorColumns :
                            (props.userClass == 'bank-worker') ? searchBankWorkerColumns : searchDonationHistoryColumns}
                            data={userList}
                            queryFunction={getRows}
                            loading={loading}
                            onSelect={setExpanded}
                            setSelection={(selectedUser) => {
                                if(props.userClass != 'history'){
                                    props.setFoundUser(selectedUser)
                                    props.setExisting(true)
                                    history.goBack();
                                }
                            }}
                            userClass={props.userClass}
                            user={props.user}
                        />
                    </Box>
                </Grid>
            </Grid>
        </>
    );
};

export default Trazilica;
