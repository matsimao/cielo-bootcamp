import React from 'react'
import ReactDOM from 'react-dom/client'
import './index.css'
import { Card, Container } from '@mui/material'
import Prospect from './components/Prospect'

ReactDOM.createRoot(document.getElementById('root')).render(
    <Container>
        <Card sx={{ padding: 5 }}>
            <Prospect />
        </Card>
    </Container>
)
