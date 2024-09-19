import {AuthService} from "../services/auth-service.ts";
import {UserRegisterDTO} from "../models/auth/UserCreateDto.ts";
import React, {useState} from "react";
import {AuthenticationResponse} from "../models/auth/AuthenticationResponse.ts";
import {Button} from "@/components/ui/button.tsx";
import {Input} from "@/components/ui/input.tsx";
import {Label} from "@/components/ui/label.tsx";
import {Card, CardContent, CardDescription, CardHeader, CardTitle} from "@/components/ui/card.tsx";
import {useToast} from "@/hooks/use-toast.ts";
import {Link} from "react-router-dom";

const Register = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [username, setUsername] = useState('');
    const [error, setError] = useState<string | null>(null);
    const [response, setResponse] = useState<AuthenticationResponse | null>(null);
    const {toast} = useToast();

    const handleRegister = async (e: React.FormEvent) => {
        e.preventDefault();
        const request: UserRegisterDTO = {email, password, username};

        try {
            const result = await AuthService.register(request);
            toast({
                title: "Registration successful",
            });
            setResponse(result);
            setError(null);
        } catch (err) {
            toast({
                title: "Error while registering",
            });
            setError('Registration failed. Please try again.');
            setResponse(null);
        }
    };

    return (
        <div className={'main_container login_main_container'}>
            <Card className="w-[350px]">
                <CardHeader>
                    <CardTitle>Register</CardTitle>
                    <CardDescription>Create a new account</CardDescription>
                </CardHeader>
                <CardContent>
                    <form onSubmit={handleRegister} className="space-y-4">
                        <div>
                            <Label htmlFor="username">Username</Label>
                            <Input
                                id="username"
                                type="text"
                                placeholder="Enter your username"
                                value={username}
                                onChange={(e) => setUsername(e.target.value)}
                                required
                                className="mt-1"
                            />
                        </div>
                        <div>
                            <Label htmlFor="email">Email</Label>
                            <Input
                                id="email"
                                type="email"
                                placeholder="Enter your email"
                                value={email}
                                onChange={(e) => setEmail(e.target.value)}
                                required
                                className="mt-1"
                            />
                        </div>
                        <div>
                            <Label htmlFor="password">Password</Label>
                            <Input
                                id="password"
                                type="password"
                                placeholder="Enter your password"
                                value={password}
                                onChange={(e) => setPassword(e.target.value)}
                                required
                                className="mt-1"
                            />
                        </div>
                        {error && <p className="text-red-500">{error}</p>}
                        <Button type="submit" className="w-full mt-4">Register</Button>
                    </form>
                    <div className={'text-center mt-3'}>
                        <Label>
                            Already have an account?
                            <Link to={'/login'}>
                                <Label className={'cursor-pointer text-blue-500'}>
                                    &nbsp;&nbsp;Login here
                                </Label>
                            </Link>
                        </Label>
                    </div>
                </CardContent>
            </Card>
        </div>
    );
};

export default Register;
