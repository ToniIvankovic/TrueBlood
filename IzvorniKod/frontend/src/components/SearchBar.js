import React, { useMemo, useState } from "react";
import {
    Box,
    debounce,
    InputAdornment,
    TextField
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

    return (
        <>
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
                    rows={props.data}
                    rowCount={props.data.length}
                    columns={props.columns}
                    page={page}
                    pageSize={resultsPerPage}
                    rowsPerPageOptions={[5]}
                    onCellClick={(event) => handleCellClick(event)}
                    onPageSizeChange={(size) => setResultsPerPage(size)}
                    onPageChange={(page) => handlePageChange(page)}
                    paginationMode="server"
                    disableSelectionOnClick
                    loading={props.loading}
                />
            </Box>
        </>
    );
};

export default SearchBar;
