import {
    Box,
    Button,
    debounce,
    Divider,
    FormControlLabel,
    Grid,
    InputAdornment,
    TextField,
    Typography,
} from "@mui/material";
import { DataGrid } from "@mui/x-data-grid";
import React, { useEffect, useMemo, useState } from "react";
import CustomizedAccordion from "./CustomizedAccordion";
import axios from "../util/axios-instance";
import { SearchRounded } from "@mui/icons-material";

const DonationTry = () => {
    const [selectedDonor, setSelectedDonor] = useState(null);
    const [resultsPerPage, setResultsPerPage] = useState(10);
    const [page, setPage] = useState(0);
    const [donorList, setDonorList] = useState([]);
    const [expanded, setExpanded] = useState(false);
    const [loading, setLoading] = useState(false);

    const handleCellClick = (event) => {
        let donor = event.row;
        setSelectedDonor(donor);
        setExpanded(false);
    };

    const handlePageChange = (page) => {
        setPage(page);
    };

    const handleQueryChange = (event) => {
        let query = event.target.value;
        queryDonors(query);
        //fetchDonors();
    }

    const debouncedHandleQueryChange = useMemo(
        () => debounce(handleQueryChange, 300)
    , []);

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

    // useEffect(() => {
    //     fetchDonors();
    // }, []);

    const handleSubmit = () => {
        // post donation try , check response and handle redirect / error reporting
        alert(selectedDonor.firstName + " " + selectedDonor.lastName);
    };

    const queryDonors = async (query) => {
        setLoading(true);
        const url = "/api/v1/donor?query=" + query;
        await axios
            .get(url, {
                // headers: { Authorization: bearerAuth },
            })
            .then((response) => {
                let newDonorList = response.data.map((el) => {
                    return {
                        id: el.donorId,
                        donorId: el.donorId,
                        firstName: el.firstName,
                        lastName: el.lastName,
                        oib: el.oib,
                        birthDate: el.birthDate,
                    };
                });

                // if(newDonorList.length == 1) {
                //     setSelectedDonor(newDonorList[0]);
                // }

                setDonorList(newDonorList);
            })
            .finally(() => {
                setLoading(false);
            })
    }
    const fetchDonors = async (query) => {
        const url =
            "/api/v1/donor/all?resultsPerPage=" +
            resultsPerPage +
            "&page=" +
            page;
        // const token = window.localStorage.getItem("token");
        // const bearerAuth = "Bearer " + token;
        await axios
            .get(url, {
                // headers: { Authorization: bearerAuth },
            })
            .then((response) => {
                let newDonorList = response.data.map((el) => {
                    return {
                        id: el.donorId,
                        donorId: el.donorId,
                        firstName: el.firstName,
                        lastName: el.lastName,
                        oib: el.oib,
                        birthDate: el.birthDate,
                    };
                });

                if(newDonorList.length == 1) {
                    setSelectedDonor(newDonorList[0]);
                }

                setDonorList(newDonorList);
            });
    };

    const columns = [
        { field: "donorId", headerName: "ID", width: 90 },
        {
            field: "firstName",
            headerName: "First name",
            width: 150,
        },
        {
            field: "lastName",
            headerName: "Last name",
            width: 150,
        },
        {
            field: "oib",
            headerName: "OIB",
            width: 150,
        },
        {
            field: "birthDate",
            headerName: "Datum rođenja",
            width: 150,
        },
    ];

    return (
        <>
            <Grid container justifyContent="center">
                <Grid item xs={11} sm={10} md={8}>
                    <Box>
                    <CustomizedAccordion
                        expanded={expanded}
                        setExpanded={setExpanded}
                        summary={
                            selectedDonor
                                ? formatDonorSummary(selectedDonor)
                                : "Donor nije odabran"
                        }
                    >
                        <Box sx={{ marginBottom: 2 }}>
                            <TextField
                                sx={{ width: "100%" }}
                                name="search"
                                label="Pretraži donore"
                                onChange={(event) => debouncedHandleQueryChange(event)}
                                InputProps={{
                                    startAdornment: (
                                        <InputAdornment position="start">
                                            <SearchRounded />
                                        </InputAdornment>
                                    ),
                                }}
                                color="primary"
                                variant="outlined"
                            />
                        </Box>

                        <Box
                            // sx={{ height: donorList.length > 0 ? 300 : "auto" }}
                            sx={{ height: 300 }}
                        >
                            <DataGrid
                                rows={donorList}
                                rowCount={donorList.length}
                                columns={columns}
                                page={page}
                                pageSize={resultsPerPage}
                                rowsPerPageOptions={[5]}
                                onCellClick={(event) => handleCellClick(event)}
                                onPageSizeChange={(size) =>
                                    setResultsPerPage(size)
                                }
                                onPageChange={(page) => handlePageChange(page)}
                                paginationMode="server"
                                disableSelectionOnClick
                                loading={loading}
                            />
                        </Box>
                    </CustomizedAccordion>
                    </Box>
                    <Divider sx={{ marginTop: 2, marginBottom: 2, backfaceVisibility: "none"}} />
                    <Box sx={{ display: "flex", justifyContent: "right" }}>
                        <Button onClick={() => handleSubmit()} variant="contained" disabled={selectedDonor == null}>
                            Kreiraj doniranje
                        </Button>
                    </Box>
                </Grid>
            </Grid>
        </>
    );
};

export default DonationTry;
