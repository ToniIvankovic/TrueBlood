import React, { useState } from "react";
import { Box } from "@mui/material";
import CustomizedAccordion from "./CustomizedAccordion";
import SearchBar from "./SearchBar";
import { searchDonorColumns } from "../model/SearchDonorColumns";
import axios from '../util/axios-instance';

const DonorSearchIntegrated = (props) => {

    const [expanded, setExpanded] = useState(false);
    const [donorList, setDonorList] = useState([]);
    const [loading, setLoading] = useState(false);

    const queryDonors = async (query) => {
        setLoading(true);
        const url = "/api/v1/donor?query=" + query;
        await axios
            .get(url)
            .then((response) => {
                let newDonorList = response.data.map((el) => {
                    // id has to be set for datagrid to work
                    el.id = el.donorId;
                    return el;
                });

                // if(newDonorList.length == 1) {
                //     setSelectedDonor(newDonorList[0]);
                // }

                setDonorList(newDonorList);
            })
            .finally(() => {
                setLoading(false);
            });
    }

    const formatDonorSummary = (donor) => {
        try {
            return (
                "[" +
                donor.donorId +
                "] " +
                donor.firstName +
                " " +
                donor.lastName
            );
        } catch (e) {
            return "nije odabran";
        }
    };

    return (
        <Box>
            <CustomizedAccordion
                expanded={expanded}
                setExpanded={setExpanded}
                summary={
                    props.selectedDonor
                        ? formatDonorSummary(props.selectedDonor)
                        : "Donor nije odabran"
                }
            >
                <SearchBar
                    columns={searchDonorColumns}
                    data={donorList}
                    queryFunction={queryDonors}
                    loading={loading}
                    selection={props.selectedDonor}
                    setSelection={props.setSelectedDonor}
                    onSelect={setExpanded}
                />
            </CustomizedAccordion>
        </Box>
    );
};

export default DonorSearchIntegrated;