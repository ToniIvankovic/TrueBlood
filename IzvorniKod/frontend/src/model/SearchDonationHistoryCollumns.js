import React from "react";
import { Button } from "@mui/material";
import { downloadPDF } from "../Util";

export const searchDonationHistoryColumns = [
    {
        field: "donationId",
        headerName: "ID",
        width: 90,
    },
    {
        field: "donationDate",
        headerName: "Datum donacije",
        width: 150,
    },
    {
        field: "donationPlace",
        headerName: "Mjesto donacije",
        width: 150,
    },
    {
        field: "rejectedReason",
        headerName: "Razlog odbijanja",
        width: 300,
    },
    {
        field: "",
        headerName: "Preuzimanje",
        width: 150,
        renderCell: (params) => {
            return (params.row.rejectedReason == null) ? <Button href={'/api/v1/donation-try/pdf/' + params.id}>Preuzmi</Button> : "";
        }
    }
];
