import React from 'react';
import {Label} from "@/components/ui/label.tsx";
import {ModeToggle} from "@/shared/layout/ModeToggle.tsx";
import {Input} from "@/components/ui/input.tsx";
import {Button} from "@/components/ui/button.tsx";
import {Link} from "react-router-dom";

const Header = () => {
    const navigateToPage = (path: string) => () => {
        window.location.href = `/${path}`;
    };
    return (
        <div className={'header_container border'}>
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
                <Link to="/login">
                    <Button>Sign In</Button>
                </Link>
                <Link to="/register">
                    <Button>Get Started</Button>
                </Link>
                <ModeToggle />
            </div>
        </div>
    );
};

export default Header;