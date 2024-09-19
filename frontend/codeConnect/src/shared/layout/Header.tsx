import React, {useEffect, useState} from 'react';
import {Label} from "@/components/ui/label.tsx";
import {ModeToggle} from "@/shared/layout/ModeToggle.tsx";
import {Input} from "@/components/ui/input.tsx";
import {Button} from "@/components/ui/button.tsx";
import {Link} from "react-router-dom";
import {AuthService} from "@/services/auth-service.ts";
import {useToast} from "@/hooks/use-toast.ts";
import { useNavigate } from "react-router-dom";

const Header = () => {
    const [isAuthenticated, setIsAuthenticated] = useState(false);
    const navigate = useNavigate();

    const { toast } = useToast()

    useEffect(() => {
        setIsAuthenticated(AuthService.isAuthenticated());
    });

    const logout = () => () => {
        AuthService.logout();
        toast({
            title: "Logged out successfully"
        })
        navigate("/")
    };

    const navigateToPage = (path: string) => () => {
        navigate(`/${path}`)
    };

    return (
        <div className={'header_container border'}>
            <div className={'common_container'}>
                <div style={{flexBasis: '33.33%'}}>
                    <Link to={'/'}>
                        <Label className={'nova-mono-regular'} style={{cursor: 'pointer'}}>CodeConnect</Label>
                    </Link>
                </div>
                <div style={{flexBasis: '33.33%', justifyContent: 'center', display: 'flex'}}>
                    <div style={{width: '50%'}}>
                        <Input placeholder={'Search'}/>
                    </div>
                </div>
                <div style={{display: "flex", columnGap: '10px', justifyContent: 'flex-end', flexBasis: '33.33%'}}>
                    {isAuthenticated ? (
                        <>
                            <Button onClick={navigateToPage('profile')}>Profile</Button>
                            <Button onClick={logout()}>Logout</Button>
                        </>
                    ) : (
                        <>
                            <Button onClick={navigateToPage('login')}>Login</Button>
                            <Button onClick={navigateToPage('register')}>Register</Button>
                        </>
                    )}
                    <ModeToggle/>
                </div>
            </div>
        </div>
    );
};

export default Header;