import Autocomplete from "@mui/material/Autocomplete";
import Box from "@mui/material/Box";
import Button from "@mui/material/Button";
import TextField from "@mui/material/TextField"

import { useContext } from "react";
import { useState } from "react"

import Swal from "sweetalert2";
import { useFetch } from "../../../hooks/useFetchAxios";
import Context from "../Context";
import Modal from "./Modal";

const Form = _ => {
    const [open, setOpen] = useState(false);


    const handleClickNewProspect = _ => {
        setOpen(true);
    }

    const handleClickRetrieveProspect = _ => {
        useFetch({ path: '/prospects/queues/retrieve', })
            .then(res => {
                if (res.status === 200) {
                    const data = res.data;
                    let html = '';

                    html += `<div><strong>Name</strong>: ${data.name}</div>`
                    html += `<div><strong>Document</strong>: ${data.name}</div>`
                    html += `<div><strong>MCC</strong>: ${data.name}</div>`

                    if (data.clientType == "BUSINESS_CUSTOMER") {
                        html += `<div><strong>Contact Name</strong>: ${data.contactName}</div>`
                        html += `<div><strong>Contact Document</strong>: ${data.contactDocument}</div>`
                    }

                    html += `<div><strong>Contact Email</strong>: ${data.contactEmail}</div>`
                    Swal.fire({
                        icon: 'success',
                        html: `<div style="width: 100%; text-align: left">${html}</div>`,
                        showConfirmButton: false,
                    });
                } else if (res.status) {
                    Swal.fire({
                        icon: "info",
                        text: "Queue is empty"
                    });
                }
            })
            .catch(_ => {
                Swal.fire({
                    icon: "info",
                    text: "Queue is empty"
                });
            });
    }

    return (
        <>
            {open ? <Modal setOpen={setOpen} open={open} /> : ""}
            <Box
                component="form"
                sx={{
                    '& > :not(style)': { m: 1, width: '25ch' },
                    display: "inline",
                }}
                noValidate
                autoComplete="off">
                <Button
                    onClick={handleClickNewProspect}
                    variant="outlined"
                    size="large"
                    style={{ width: "48%" }}
                >Create new prospect</Button>
                <Button
                    onClick={handleClickRetrieveProspect}
                    variant="outlined"
                    size="large"
                    style={{ width: "48%" }}
                >Retrieve prospect</Button>
            </Box>
        </>
    )
}

export default Form;