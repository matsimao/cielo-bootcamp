import styled from "styled-components";

export const Scrollable = styled.div`
    overflow-x: auto;
    
    height: 80vh;
    margin: 1rem auto;
    width: 100%;

    &::-webkit-scrollbar {
        width: 6px;
        height: 6px;
    } 
    &::-webkit-scrollbar-track {
        border-radius: 10px;
        background: rgba(0,0,0,0.1);
    }
    &::-webkit-scrollbar-thumb {
        border-radius: 10px;
        background: rgba(0,0,0,0.2);
    }
    &::-webkit-scrollbar-thumb:hover{
        background: rgba(0,0,0,0.4);
    }
`