import { Button, CardActions, Chip, Divider } from "@mui/material"
import Card from "@mui/material/Card"
import CardContent from "@mui/material/CardContent"
import Typography from "@mui/material/Typography"
import { useState } from "react"
import Modal from "./Modal"

const Client = ({client, isQueue}) => {
    const [open, setOpen] = useState(false);

    const handleClick = _ => {
        setOpen(true);
    }

    return(
        <>
            {open ? <Modal setOpen={setOpen} open={open} client={client}/> : ""}
            <Card key={Math.random()} variant="outlined">
                <CardActions disableSpacing style={{ justifyContent: "space-between", height: 40}}>

                    <Chip 
                        label={client.clientType.replace("_", " ")} 
                        color={client.clientType === "BUSINESS_CUSTOMER" ? "secondary" : "primary"} />
                    <CardContent style={{textAlign: "center"}}>
                        <Typography gutterBottom variant="h5" component="div">
                            {client.name}
                        </Typography>
                    </CardContent>
                    {
                        !isQueue ? 
                        <Button size="small" variant="outlined" onClick={handleClick}>Editar</Button> : 
                        <Chip 
                            label="WAITING FOR CONTACT"
                            color='warning' />
                    }
                </CardActions>
                <CardContent >
                    <div style={{textAlign: "left"}}>
                        <Typography gutterBottom variant="body1" component="div">
                            Name: {client.name}
                        </Typography>
                        <Typography gutterBottom variant="body1" component="div">
                            Document: {client.document}
                        </Typography>
                        <Typography gutterBottom variant="body1" component="div">
                            MCC: {client.mcc}
                        </Typography>
                        {client.clientType === "BUSINESS_CUSTOMER" ?
                            (
                                <>
                                    <Typography gutterBottom variant="body1" component="div">
                                        Contact Name: {client.contactName}
                                    </Typography>
                                    <Typography gutterBottom variant="body1" component="div">
                                        Contact Document: {client.contactDocument}
                                    </Typography>
                                </>
                            )
                        : ""}
                        <Typography gutterBottom variant="body1" component="div">
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