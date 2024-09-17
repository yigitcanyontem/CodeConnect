import React, { useState } from 'react'
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import './App.css'
import HomePage from "./pages/HomePage.tsx";
import Login from "./pages/Login.tsx";
import Register from "./pages/Register.tsx";
import {CardWithForm} from "@/pages/CardWithForm.tsx";

function App() {

    return (
        <Router>
            <Routes>
                <Route path="/" element={<HomePage />} />
                <Route path="/a" element={<CardWithForm />} />
                <Route path="/login" element={<Login />} />
                <Route path="/register" element={<Register />} />
            </Routes>
        </Router>
    );
}

export default App
