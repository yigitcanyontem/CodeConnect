import React, { useState } from 'react'
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import './App.css'
import HomePage from "./pages/HomePage.tsx";
import Login from "./pages/Login.tsx";
import Register from "./pages/Register.tsx";
import {CardWithForm} from "@/pages/CardWithForm.tsx";
import {ThemeProvider} from "@/components/theme-provider.tsx";
import {ModeToggle} from "@/shared/layout/ModeToggle.tsx";
import {Toaster} from "@/components/ui/toaster.tsx";
import Header from "@/shared/layout/Header.tsx";

function App() {

    return (
        <ThemeProvider defaultTheme="dark" storageKey="vite-ui-theme">
            <Router>
                <Header />
                <Routes>
                    <Route path="/" element={<HomePage />} />
                    <Route path="/a" element={<CardWithForm />} />
                    <Route path="/login" element={<Login />} />
                    <Route path="/register" element={<Register />} />
                </Routes>
            </Router>
            <Toaster />
        </ThemeProvider>
    );
}

export default App
