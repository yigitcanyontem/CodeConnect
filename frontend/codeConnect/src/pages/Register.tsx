import {AuthService} from "../services/auth-service.ts";
import {UserRegisterDTO} from "../models/UserCreateDto.ts";
import {useState} from "react";
import {AuthenticationResponse} from "../models/AuthenticationResponse.ts";

const Register = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [username, setUsername] = useState('');
    const [error, setError] = useState<string | null>(null);
    const [response, setResponse] = useState<AuthenticationResponse | null>(null);

    const handleRegister = (e: React.FormEvent) => {
        e.preventDefault();
        const request: UserRegisterDTO = {email, password, username};

        AuthService.register(request)
            .then((data: AuthenticationResponse) => {
                setResponse(data);
                setError(null);
            })
            .catch(() => {
                setError('Registration failed');
                setResponse(null);
            });
    };

    return (
        <form onSubmit={handleRegister}>
            <input
                type="text"
                placeholder="Name"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
                required
            />
            <input
                type="email"
                placeholder="Email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                required
            />
            <input
                type="password"
                placeholder="Password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                required
            />
            <button type="submit">Register</button>
            {error && <p>{error}</p>}
            {response && <p>Registration successful!</p>}
        </form>
    );
};

export default Register;