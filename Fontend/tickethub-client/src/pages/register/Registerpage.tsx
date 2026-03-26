import React, { useState } from 'react';
import './RegisterPage.css';
import { Link } from 'react-router-dom';

const RegisterPage: React.FC = () => {
    const [form, setForm] = useState({
        fullName: '',
        email: '',
        password: '',
        confirmPassword: '',
        agreed: false,
    });

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value, type, checked } = e.target;
        setForm((prev) => ({ ...prev, [name]: type === 'checkbox' ? checked : value }));
    };

    const handleSubmit = (e: React.MouseEvent) => {
        e.preventDefault();
        console.log('Register:', form);
    };

    return (
        <div className="register-page">
            {/* Header */}
            <header className="auth-header">
                <span className="logo">TicketHub</span>
                <div className="help-btn">
                    <span>HELP</span>
                    <span className="help-icon">?</span>
                </div>
            </header>

            <div className="register-body">
                {/* Left Panel */}
                <div className="register-left">
                    <div className="decorative-image top-left">
                        <img
                            src="https://images.unsplash.com/photo-1511671782779-c97d3d27a1d4?w=200&q=80"
                            alt="Concert"
                        />
                    </div>

                    <div className="left-text">
                        <h1>
                            Join the <span className="accent">Inner<br />Circle</span> of Live<br />Events.
                        </h1>
                        <p>
                            Access exclusive premieres, curated dashboards, and high-fidelity experiences
                            designed for the modern spectator.
                        </p>
                    </div>

                    <div className="verified-badge">
                        <div className="verified-icon">🎟️</div>
                        <div>
                            <strong>Verified Access</strong>
                            <p>Secure digital ticketing with instant delivery.</p>
                        </div>
                    </div>

                    <div className="decorative-image bottom-right">
                        <img
                            src="https://images.unsplash.com/photo-1533174072545-7a4b6ad7a6c3?w=200&q=80"
                            alt="Festival"
                        />
                    </div>
                </div>

                {/* Right Form */}
                <div className="register-form-wrap">
                    <div className="register-form-card">
                        <h2>Create Account</h2>
                        <p className="form-subtitle">Step into the world's most curated stage.</p>

                        <div className="form-row">
                            <div className="form-group">
                                <label>FULL NAME</label>
                                <input
                                    type="text"
                                    name="fullName"
                                    placeholder="Alex Morgan"
                                    value={form.fullName}
                                    onChange={handleChange}
                                />
                            </div>
                            <div className="form-group">
                                <label>EMAIL</label>
                                <input
                                    type="email"
                                    name="email"
                                    placeholder="alex@example.com"
                                    value={form.email}
                                    onChange={handleChange}
                                />
                            </div>
                        </div>

                        <div className="form-row">
                            <div className="form-group">
                                <label>PASSWORD</label>
                                <input
                                    type="password"
                                    name="password"
                                    placeholder="••••••••"
                                    value={form.password}
                                    onChange={handleChange}
                                />
                            </div>
                            <div className="form-group">
                                <label>CONFIRM PASSWORD</label>
                                <input
                                    type="password"
                                    name="confirmPassword"
                                    placeholder="••••••••"
                                    value={form.confirmPassword}
                                    onChange={handleChange}
                                />
                            </div>
                        </div>

                        <label className="checkbox-row">
                            <input
                                type="checkbox"
                                name="agreed"
                                checked={form.agreed}
                                onChange={handleChange}
                            />
                            <span>
                                I agree to the <a href="#">Terms &amp; Conditions</a> and acknowledge the privacy policy.
                            </span>
                        </label>

                        <button className="btn-create" onClick={handleSubmit}>
                            Create Account
                        </button>

                        <div className="divider">
                            <span>OR REGISTER WITH</span>
                        </div>

                        <div className="social-row">
                            <button className="social-btn">
                                <img
                                    src="https://www.google.com/favicon.ico"
                                    alt="Google"
                                    width="18"
                                />
                                Google
                            </button>
                            <button className="social-btn">
                                <span>iOS</span>
                                Apple
                            </button>
                        </div>

                        <p className="login-link">
                            Already have an account? <Link to="/login">Log in here</Link>
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

export default RegisterPage;
