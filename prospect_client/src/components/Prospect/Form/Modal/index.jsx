import { Autocomplete, Box, Button, Modal as ModalMui, TextField, Typography } from "@mui/material";
import { useContext, useState } from "react";
import Context from "../../Context";
import { useFetch } from "../../../../hooks/useFetchAxios";
import Swal from "sweetalert2";

const Modal = ({ open, setOpen }) => {

    const {
        getQueue
    } = useContext(Context)

    const [name, setName] = useState("");
    const [document, setDocument] = useState("");
    const [MCC, setMCC] = useState("");
    const [contactName, setContactName] = useState("");
    const [contactDocument, setContactDocument] = useState("");
    const [contactEmail, setContactEmail] = useState("");
    const [clientType, setClientType] = useState("");

    const optionsClientType = [
        {
            label: "Pessoa Jurídica",
            id: "BUSINESS_CUSTOMER"
        }, {
            label: "Pessoa Fisica",
            id: "INDIVIDUAL_CUSTOMER"
        }
    ];

    const handleClose = () => setOpen(false);

    const handleClickSave = () => {
        const  invalidInput = [];

        if (!name) invalidInput.push("Field name is empty")
        if (!document) invalidInput.push("Field document is empty")
        if (!MCC) invalidInput.push("Field MCC is empty")
        if (clientType == "BUSINESS_CUSTOMER") {
            if (!contactName) invalidInput.push("Field contact name is empty")
            if (!contactDocument) invalidInput.push("Field contact document is empty")
        }
        if (!contactEmail) invalidInput.push("Field contact email is empty")

        if (invalidInput.length > 0) {
            Swal.fire({
                icon: "warning",
                html: `<div style="width: 100%; text-align: left">${invalidInput.join('<br>')}</div>`
            });
            return;
        }

        const data = {
            name,
            document,
            MCC,
            clientType,
            contactEmail
        };

        if (clientType == "BUSINESS_CUSTOMER") {
            data.contactName = contactName;
            data.contactDocument = contactDocument;
        }

        useFetch({ path: '/prospects', method: 'post', data })
            .then(res => {
                Swal.fire({
                    icon: "success",
                    text: `Prospect created`
                });
                getQueue();

                handleClose();

            })
            .catch(e => {
                Swal.fire({
                    icon: "error",
                    html: `<div style="width: 100%; text-align: left">${e.response.data.message.split(',').join('<br>')}</div>`
                })
            });
    }

    const style = {
        position: 'absolute',
        top: '50%',
        left: '50%',
        transform: 'translate(-50%, -50%)',
        width: 400,
        bgcolor: 'background.paper',
        boxShadow: 24,
        p: 4,
        color: "black",
        '& .MuiTextField-root': { m: 1, width: '90%' }
    };

    return (
        <>
            <ModalMui
                open={open}
                onClose={handleClose}
                aria-labelledby="modal-modal-title"
                aria-describedby="modal-modal-description"
            >
                <Box sx={style}>
                    <div style={{textAlign: "center"}}>
                        <Typography variant="h5" color="text.primary" alignContent="center">
                            Create new prospect
                        </Typography>
                    </div>
                    <Autocomplete
                        title="Prospect type"
                        disablePortal
                        onChange={(e, newValue) => setClientType(newValue?.id)}
                        options={optionsClientType}
                        renderInput={(params) => <TextField {...params} label="Client Type" variant="standard" />}
                    />
                    { clientType ? (
                        <>
                        <TextField
                            title="Prospect name"
                            variant="standard"
                            value={name}
                            onChange={e => {
                                if (e.target.value.length > 50) return;
                                setName(e.target.value)}
                            }
                            label={"Name"}
                        />

                        <TextField
                            title="Prospect document"
                            variant="standard"
                            value={document}
                            onChange={e => {
                                if (clientType == "BUSINESS_CUSTOMER" && e.target.value.toString().length > 13) return;
                                if (clientType == "INDIVIDUAL_CUSTOMER" && e.target.value.toString().length > 11) return;

                                setDocument(_ => {
                                    return e.target.value.replace(/[^0-9]+/g, '');
                                })
                            }}
                            label={"Document"}
                        />

                        <TextField
                            title="Prospect MCC"
                            variant="standard"
                            value={MCC}
                            onChange={e => {
                                if (e.target.value.length > 4) return;
                                setMCC(_ => e.target.value.replace(/[^0-9]+/g, ''))}
                            }
                            label={"MCC"}
                        />

                        {clientType === "BUSINESS_CUSTOMER" ? (
                            <>
                                <TextField
                                    title="Prospect contact name"
                                    variant="standard"
                                    value={contactName}
                                    onChange={e => {
                                        if (e.target.value.length > 50) return;
                                        setContactName(e.target.value)}
                                    }
                                    label={"Contact Name"}
                                />

                                <TextField
                                    title="Prospect contact document"
                                    variant="standard"
                                    value={contactDocument}
                                    onChange={e => {
                                        if (e.target.value.toString().length > 11) return;
        
                                        setContactDocument(_ => {
                                            return e.target.value.replace(/[^0-9]+/g, '');
                                        })
                                    }}
                                    label={"Contact Document"}
                                />
                            </>
                        ): ""}

                        <TextField
                            title="Prospect contact email"
                            variant="standard"
                            value={contactEmail}
                            onChange={e => setContactEmail(e.target.value)}
                            label={"Contact Email"}
                        />

                        <Button
                            title="Save prospect"
                            onClick={handleClickSave}
                            variant="outlined"
                            size="large"
                            style={{ width: "100%" }}

                        >Save</Button>
                        </>
                    ) : ""}
                
                </Box>
            </ModalMui>
        </>
    )
}

export default Modal;