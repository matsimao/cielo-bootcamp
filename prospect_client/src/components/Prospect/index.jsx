import { useEffect, useState } from "react"
import { useFetch } from "../../hooks/useFetchAxios";
import { Scrollable } from "./styled";

import Context from "./Context";

import _ from "lodash";
import Client from "./Client";
import Form from "./Form";
import { CardActions, CardContent, Collapse, IconButton, Typography } from "@mui/material";
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import { styled } from '@mui/material/styles';

const Prospect = () => {
    const ExpandMore = styled((props) => {
        const { expand, ...other } = props;
        return <IconButton {...other} />;
    })(({ theme, expand }) => ({
        transform: !expand ? 'rotate(0deg)' : 'rotate(180deg)',
        marginLeft: 'auto',
        transition: theme.transitions.create('transform', {
            duration: theme.transitions.duration.shortest,
        }),
    }));

    const [listQueue, setListQueue] = useState([]);
    const [listProspects, setListProspects] = useState([]);

    const [expandedQueue, setExpandedQueue] = useState(true);
    const [expandedProspect, setExpandedProspect] = useState(false);

    const handleExpandQueueClick = (e) => {
        setExpandedQueue(!expandedQueue);
    };

    const handleExpandProspectClick = (e) => {
        setExpandedProspect(!expandedProspect);
    };

    let contador = 0;

    const getQueue = () => {
        useFetch({ path: `/prospects/queues` }).then(response => {
            if (response.data) {
                setListQueue(response.data)
            } else {
                setListQueue([]);
            }
        }).catch(err => {

        })
    }

    const getProspects = () => {
        useFetch({ path: `/prospects` }).then(response => {
            if (response.data) {
                setListProspects(response.data)
            } else {
                setListProspects([]);
            }
        }).catch(err => {
        })
    }

    useEffect(_ => {
        getQueue();
        getProspects();
        setInterval(_ => {
            getProspects();
            getQueue();
        }, 2000);
    }, [])

    return (
        <>
            <Context.Provider value={
                {
                    getQueue,
                    getProspects
                }
            }>
                <Scrollable style={{ textAlign: "center" }}>
                    <CardActions 
                        title="Show prospect queue"
                        disableSpacing
                        onClick={handleExpandQueueClick} 
                        style={{ justifyContent: "space-between", padding: 0 }}
                    >
                        <CardContent align={'center'}>
                            <Typography variant="h5" color="text.primary" alignContent="center">
                                Queue List
                            </Typography>
                        </CardContent>

                        <ExpandMore
                            style={{ marginLeft: "unset" }}
                            expand={expandedQueue}
                            onClick={handleExpandQueueClick}
                            aria-expanded={expandedQueue}
                            aria-label="show more"
                        >
                            <ExpandMoreIcon />
                        </ExpandMore>
                    </CardActions>

                    <Collapse in={expandedQueue} timeout="auto" unmountOnExit>
                        {_.filter(listQueue, client => {
                            return true;
                        }).map(client => <Client key={contador++} client={client} isQueue={true} />)}
                    </Collapse>

                    <CardActions 
                        title="Show prospect list"
                        disableSpacing 
                        onClick={handleExpandProspectClick} 
                        style={{ justifyContent: "space-between", padding: 0 }}
                    >
                        <CardContent align={'center'}>
                            <Typography variant="h5" color="text.primary" alignContent="center">
                                Prospect List
                            </Typography>
                        </CardContent>

                        <ExpandMore
                            style={{ marginLeft: "unset" }}
                            expand={expandedProspect}
                            onClick={handleExpandProspectClick}
                            aria-expanded={expandedProspect}
                            aria-label="show more"
                        >
                            <ExpandMoreIcon />
                        </ExpandMore>
                    </CardActions>

                    <Collapse in={expandedProspect} timeout="auto" unmountOnExit>
                        {_.filter(listProspects, client => {
                            return true;
                        }).map(client => <Client key={contador++} client={client} isQueue={false} />)}
                    </Collapse>
                </Scrollable>
                <Form />
            </Context.Provider>
        </>
    )
}

export default Prospect
