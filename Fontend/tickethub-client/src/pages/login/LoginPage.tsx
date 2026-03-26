import React, { useState } from 'react';
import './LoginPage.css';
import { Link } from 'react-router-dom';

const LoginPage: React.FC = () => {
    const [form, setForm] = useState({
        email: '',
        password: '',
        keepSignedIn: false,
    });

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value, type, checked } = e.target;
        setForm((prev) => ({ ...prev, [name]: type === 'checkbox' ? checked : value }));
    };

    const handleSubmit = (e: React.MouseEvent) => {
        e.preventDefault();
        console.log('Login:', form);
    };

    return (
        <div className="login-page">
            {/* Header */}
            <header className="auth-header">
                <span className="logo">TicketHub</span>
                <div className="help-btn">
                    <span>Help</span>
                </div>
            </header>

            <div className="login-body">
                {/* Left Image Panel */}
                <div className="login-left">
                    <img
                        src="https://images.unsplash.com/photo-1540039155733-5bb30b53aa14?w=700&q=80"
                        alt="Concert Stage"
                    />
                    <div className="login-left-overlay" />
                    <div className="login-left-text">
                        <h2>Access the<br />World's Finest Stages.</h2>
                        <p>
                            Experience events curated for those who seek the extraordinary.
                            Log in to manage your premium access.
                        </p>
                    </div>
                </div>

                {/* Right Form */}
                <div className="login-right">
                    <div className="login-form-card">
                        <h2>Welcome Back</h2>
                        <p className="form-subtitle">Sign in to your TicketHub account.</p>

                        <div className="form-group">
                            <label>EMAIL ADDRESS</label>
                            <input
                                type="email"
                                name="email"
                                placeholder="name@company.com"
                                value={form.email}
                                onChange={handleChange}
                            />
                        </div>

                        <div className="form-group">
                            <div className="password-label-row">
                                <label>PASSWORD</label>
                                <a href="#" className="forgot-link">Forgot Password?</a>
                            </div>
                            <input
                                type="password"
                                name="password"
                                placeholder="••••••••"
                                value={form.password}
                                onChange={handleChange}
                            />
                        </div>

                        <label className="checkbox-row">
                            <input
                                type="checkbox"
                                name="keepSignedIn"
                                checked={form.keepSignedIn}
                                onChange={handleChange}
                            />
                            <span>Keep me signed in for 30 days</span>
                        </label>

                        <button className="btn-signin" onClick={handleSubmit}>
                            Sign In
                        </button>

                        <div className="divider">
                            <span>OR CONTINUE WITH</span>
                        </div>

                        <div className="social-row">
                            <button className="social-btn">
                                <span className="google-logo">GOOGLE</span>
                                Google
                            </button>
                            <button className="social-btn">
                                <span className="fb-icon">⚙️</span>
                                Facebook
                            </button>
                        </div>

                        <p className="register-link">
                            New to the stage? <Link to="/register">Create an account</Link>
                        </p>
                    </div>
                </div>
            </div>

            {/* Footer */}
            <footer className="auth-footer">
                <span>© 2024 TICKETHUB. THE CURATED STAGE.</span>
                <div className="footer-links">
                    <a href="#">PRIVACY POLICY</a>
                    <a href="#">TERMS OF SERVICE</a>
                    <a href="#">SUPPORT</a>
                </div>
            </footer>
        </div>
    );
};

export default LoginPage;
