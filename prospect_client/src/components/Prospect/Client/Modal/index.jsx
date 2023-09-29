import { Autocomplete, Box, Button, Modal as ModalMui, TextField, Typography } from "@mui/material";
import { useContext, useState } from "react";

import Context from "../../Context";

import Swal from "sweetalert2";
import { useFetch } from "../../../../hooks/useFetchAxios";
import _ from "lodash";

const Modal = ({ open, setOpen, client }) => {

    const {
        getQueue,
        getProspects
    } = useContext(Context)

    const [id, setId] = useState(client.id);
    const [name, setName] = useState(client.name);
    const [document, setDocument] = useState(client.document);
    const [MCC, setMCC] = useState(client.mcc);
    const [contactName, setContactName] = useState(client.contactName);
    const [contactDocument, setContactDocument] = useState(client.contactDocument);
    const [contactEmail, setContactEmail] = useState(client.contactEmail);
    const [clientType, setClientType] = useState(client.clientType);

    const optionsClientType = [
        {
            label: "Pessoa Jurídica",
            id: "BUSINESS_CUSTOMER"
        }, {
            label: "Pessoa Fisica",
            id: "INDIVIDUAL_CUSTOMER"
        }
    ];

    const optionClientTypeDefault = _.find(optionsClientType, option => option.id == client.clientType)

    const handleClose = () => setOpen(false);

    const handleClickSave = () => {
        const invalidInput = [];

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

        useFetch({ path: `/prospects/${id}`, method: 'put', data })
            .then(res => {
                Swal.fire({
                    icon: "success",
                    text: `Prospect updated`
                });
                getQueue();
                getProspects();

                handleClose();

            })
            .catch(e => {
                console.log(e);
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
                    <Autocomplete

                        disablePortal
                        onChange={(e, newValue) => setClientType(newValue?.id)}
                        options={optionsClientType}
                        defaultValue={optionClientTypeDefault}
                        renderInput={(params) => <TextField {...params} label="Client Type" variant="standard" />}
                    />
                    {clientType ? (
                        <>
                            <TextField
                                variant="standard"
                                value={name}
                                onChange={e => setName(e.target.value)}
                                label={"Name"}
                            />

                            <TextField
                                variant="standard"
                                value={document}
                                onChange={e => setDocument(_ => {
                                    return e.target.value.replace(/[^0-9]+/g, '');
                                })}
                                label={"Document"}
                            />

                            <TextField
                                variant="standard"
                                value={MCC}
                                onChange={e => setMCC(e.target.value.replace(/[^0-9]+/g, ''))}
                                label={"MCC"}
                            />

                            {clientType === "BUSINESS_CUSTOMER" ? (
                                <>
                                    <TextField
                                        variant="standard"
                                        value={contactName}
                                        onChange={e => setContactName(e.target.value)}
                                        label={"Contact Name"}
                                    />

                                    <TextField
                                        variant="standard"
                                        value={contactDocument}
                                        onChange={e => setContactDocument(e.target.value.replace(/[^0-9]+/g, ''))}
                                        label={"Contact Document"}
                                    />
                                </>
                            ) : ""}


                            <TextField
                                variant="standard"
                                value={contactEmail}
                                onChange={e => setContactEmail(e.target.value)}
                                label={"Contact Email"}
                            />

                            <Button
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