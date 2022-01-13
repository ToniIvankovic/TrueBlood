import React, { useEffect, useMemo, useState } from "react";
import {
    Box,
    debounce,
    InputAdornment,
    TextField,
    Stack
} from "@mui/material";
import { DataGrid } from "@mui/x-data-grid";
import axios from "../util/axios-instance";
import { SearchRounded } from "@mui/icons-material";

const SearchBar = (props) => {

    const [resultsPerPage, setResultsPerPage] = useState(10);
    const [page, setPage] = useState(0);

    const handleCellClick = (event) => {
        let donor = event.row;
        props.setSelection(donor);
        props.onSelect(false);
    };

    const handlePageChange = (page) => {
        setPage(page);
    };

    const handleQueryChange = (event) => {
        let query = event.target.value;
        props.queryFunction(query);
    }

    const debouncedHandleQueryChange = useMemo(
        () => debounce(handleQueryChange, 300)
    , []);

    useEffect(() => {
        if(props.userClass == 'history' && props.user.userId){
            props.queryFunction(null);
        }
    }, [props.user.userId])

    return (
        <>
            {(props.userClass == 'history') ? '' : 
            
            <Box sx={{ marginBottom: 2 }}>
                <TextField
                    sx={{ width: "100%" }}
                    name="search"
                    label= {(props.userClass == 'donor') ? "Pretraži donore" : "Pretraži djelatnike"}
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
            
            }

            <Box
                // sx={{ height: props.donorList.length > 0 ? 300 : "auto" }}
                sx={{ height: 400 }}
            >
                <DataGrid
                    rows={props.data}
                    rowCount={props.data.length}
                    columns={props.columns}
                    page={page}
                    pageSize={resultsPerPage}
                    rowsPerPageOptions={[20,10,5]}
                    onCellClick={(event) => handleCellClick(event)}
                    onPageSizeChange={(size) => setResultsPerPage(size)}
                    onPageChange={(page) => handlePageChange(page)}
                    paginationMode="client"
                    disableSelectionOnClick
                    loading={props.loading}
                    components={{
                        NoRowsOverlay: () => (
                          <Stack height="100%" alignItems="center" justifyContent="center">
                            Nema rezultata
                          </Stack>
                        ),
                        NoResultsOverlay: () => (
                          <Stack height="100%" alignItems="center" justifyContent="center">
                            Nema rezultata
                          </Stack>
                        )
                      }}
                />
            </Box>
        </>
    );
};

export default SearchBar;
