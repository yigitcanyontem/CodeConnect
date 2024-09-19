import React from 'react'
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import './App.css'
import HomePage from "./pages/HomePage.tsx";
import Login from "./pages/Login.tsx";
import Register from "./pages/Register.tsx";
import {ThemeProvider} from "@/components/theme-provider.tsx";
import {Toaster} from "@/components/ui/toaster.tsx";
import Header from "@/shared/layout/Header.tsx";
import Profile from "@/pages/Profile.tsx";
import CreateTopic from "@/pages/CreateTopic.tsx";
import TopicPage from "@/pages/TopicPage.tsx";

function App() {

    return (
        <ThemeProvider defaultTheme="dark" storageKey="vite-ui-theme">
            <Router>
                <Header />
                <Routes>
                    <Route path="/" element={<HomePage />} />
                    <Route path="/login" element={<Login />} />
                    <Route path="/register" element={<Register />} />
                    <Route path="/profile" element={<Profile />} />
                    <Route path="/new/topic" element={<CreateTopic />} />
                    <Route path="/topic/:slug" element={<TopicPage/>} />
                    <Route path="*" element={<div>404</div>} />
                </Routes>
            </Router>
            <Toaster />
        </ThemeProvider>
    );
}

export default App
