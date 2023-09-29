import { Box, Button, CardActions, Chip, Divider, Grid } from "@mui/material"
import Card from "@mui/material/Card"
import CardContent from "@mui/material/CardContent"
import Typography from "@mui/material/Typography"
import { useState } from "react"
import Swal from "sweetalert2"
import { useFetch } from "../../../hooks/useFetchAxios"
import Modal from "./Modal"

const Client = ({client, isQueue}) => {
    const [open, setOpen] = useState(false);

    const handleClick = _ => {
        setOpen(true);
    }

    const handleClickDelete = ({id}) => {
        Swal.fire({
            text: 'Are you sure?',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#d33',
            confirmButtonText: 'Confirm',
            cancelButtonText: `Cancel`,
        }).then((result) => {
            if (result.isConfirmed) {
                useFetch({path: `/prospects/${id}`, method: 'delete'})
                    .then(response => {
                        if (response.status === 200) {
                            loadParent()
                        }
                    });
            }
        })
    }

    return(
        <>
            {open ? <Modal setOpen={setOpen} open={open} client={client}/> : ""}
            <Card key={Math.random()} title={`Information about prospect ${client.document}`} variant="outlined">
                <CardActions disableSpacing style={{ justifyContent: "space-between"}}>
                    <Grid container>
                        <Grid item md={4} xs={12} style={{margin: 0}}>
                            <Chip 
                                title={`Prospect type is ${client.clientType.replace("_", " ")}`}
                                label={client.clientType.replace("_", " ")} 
                                color={client.clientType === "BUSINESS_CUSTOMER" ? "secondary" : "primary"} />
                        </Grid>
                        <Grid item md={4} xs={12} style={{margin: 0}}>
                            <Typography gutterBottom variant="h5" component="div">
                                {client.name}
                            </Typography>
                        </Grid>
                        <Grid item md={4} xs={12} style={{margin: 0}}>
                        {
                            (!isQueue && client.id) ? (
                                <Box>
                                    <Button title={`Edit prospect: ${client.document}`} size="small" style={{margin: 2}} variant="outlined" onClick={handleClick}>Edit</Button>
                                    <Button title={`Delete prospect: ${client.document}`} size="small" style={{margin: 2}} color="error" variant="outlined" onClick={_ => handleClickDelete(client)}>Delete</Button>
                                </Box>
                            ): 
                            <Chip 
                                label="WAITING FOR CONTACT"
                                color='warning' />
                        }
                        </Grid>
                    </Grid>
                </CardActions>
                <CardContent >
                    <div style={{textAlign: "left"}}>
                        <Typography title={`Prospect name is ${client.name}`} gutterBottom variant="body1" component="div">
                            Name: {client.name}
                        </Typography>
                        <Typography title={`Prospect document is ${client.document}`} gutterBottom variant="body1" component="div">
                            Document: {client.document}
                        </Typography>
                        <Typography title={`Prospect MCC is ${client.mcc}`} gutterBottom variant="body1" component="div">
                            MCC: {client.mcc}
                        </Typography>
                        {client.clientType === "BUSINESS_CUSTOMER" ?
                            (
                                <>
                                    <Typography title={`Prospect contact name is ${client.contactName}`} gutterBottom variant="body1" component="div">
                                        Contact Name: {client.contactName}
                                    </Typography>
                                    <Typography title={`Prospect contact document is ${client.contactDocument}`} gutterBottom variant="body1" component="div">
                                        Contact Document: {client.contactDocument}
                                    </Typography>
                                </>
                            )
                        : ""}
                        <Typography title={`Prospect contact email is ${client.contactEmail}`} gutterBottom variant="body1" component="div">
                            Contact Email: {client.contactEmail}
                        </Typography>
                    </div>
                    
                </CardContent>
            </Card>
            <br />
            {/* <Divider /> */}
        </>
    )
}

export default Client