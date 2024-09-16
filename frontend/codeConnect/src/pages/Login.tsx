import {AuthService} from "../services/auth-service.ts";
import {AuthenticationRequest} from "../models/AuthenticationRequest.ts";
import {useState} from "react";
import {AuthenticationResponse} from "../models/AuthenticationResponse.ts";

const Login = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState<string | null>(null);
    const [response, setResponse] = useState<AuthenticationResponse | null>(null);

    // Handle form submission
    const handleLogin = async (e: React.FormEvent) => {
        e.preventDefault();
        const request: AuthenticationRequest = { username, password };

        try {
            // Call the AuthService to authenticate
            const result = await AuthService.authenticate(request);
            setResponse(result);
            setError(null);
        } catch (err) {
            // Handle authentication error
            setError('Login failed. Please check your credentials.');
            setResponse(null);
        }
    };

    return (
        <form onSubmit={handleLogin}>
            <div>
                <label htmlFor="email">Email</label>
                <input
                    id="email"
                    type="email"
                    placeholder="Enter your email"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    required
                />
            </div>
            <div>
                <label htmlFor="password">Password</label>
                <input
                    id="password"
                    type="password"
                    placeholder="Enter your password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required
                />
            </div>
            <button type="submit">Login</button>
        </form>
    );
}

export default Login;