import React, { useState } from 'react';
import './HomePage.css';
import { Link } from 'react-router-dom';

const categories = [
    { icon: '🎵', label: 'CONCERTS', color: '#e8eaf6' },
    { icon: '🎭', label: 'COMEDY', color: '#e8eaf6' },
    { icon: '⚽', label: 'SPORTS', color: '#fce4ec' },
    { icon: '🎨', label: 'ARTS', color: '#e8eaf6' },
    { icon: '🏛️', label: 'FESTIVALS', color: '#e8eaf6' },
    { icon: '👨‍👩‍👧', label: 'FAMILY', color: '#e8eaf6' },
];

const events = [
    {
        date: 'MAY 24',
        badge: 'TRENDING',
        badgeColor: '#1a1a2e',
        image: 'https://images.unsplash.com/photo-1598387993441-a364f854cfbd?w=400&q=80',
        location: 'The Blue Note, NYC',
        title: 'Midnight Sessions: Jazz After Dark',
        desc: 'A curated evening of contemporary jazz fusion featuring award-winning instrumentalists from...',
        price: '$85.00',
        cta: 'Book Now',
    },
    {
        date: 'JUN 02',
        badge: null,
        image: 'https://images.unsplash.com/photo-1470229722913-7c0e2dbbafd3?w=400&q=80',
        location: 'Stadium Grand, LA',
        title: 'Summer Solstice Pop Fest',
        desc: 'The biggest pop artists of the year come together for one massive celebration of summ...',
        price: '$120.00',
        cta: 'Book Now',
    },
    {
        date: 'JUN 15',
        badge: 'EXCLUSIVE',
        badgeColor: '#c2185b',
        image: 'https://images.unsplash.com/photo-1533174072545-7a4b6ad7a6c3?w=400&q=80',
        location: 'Main Street District',
        title: 'International Arts & Food Expo',
        desc: 'A world of culture at your doorstep. Explore art installations and culinary delights from 30+...',
        price: 'Free Entry',
        cta: 'RSVP Now',
    },
];

const HomePage: React.FC = () => {
    const [activeNav, setActiveNav] = useState('Explore');

    return (
        <div className="home-page">
            {/* Navbar */}
            <nav className="navbar">
                <div className="navbar-left">
                    <span className="logo">TicketHub</span>
                    <div className="nav-links">
                        {['Explore', 'Venues', 'Categories'].map((item) => (
                            <button
                                key={item}
                                className={`nav-link ${activeNav === item ? 'active' : ''}`}
                                onClick={() => setActiveNav(item)}
                            >
                                {item}
                            </button>
                        ))}
                    </div>
                </div>
                <div className="navbar-center">
                    <div className="search-bar">
                        <span className="search-icon">🔍</span>
                        <input type="text" placeholder="Search events, artists..." />
                    </div>
                </div>
                <div className="navbar-right">
                    <button className="icon-btn">🔔</button>
                    <button className="icon-btn">👤</button>
                    <button className="btn-organizer">Become an Organizer</button>
                    <Link to="/login"><button className="btn-signin">Sign In</button></Link>
                </div>
            </nav>

            {/* Hero */}
            <section className="hero">
                <div className="hero-bg">
                    <img
                        src="https://images.unsplash.com/photo-1540039155733-5bb30b53aa14?w=1200&q=80"
                        alt="Concert"
                    />
                    <div className="hero-overlay" />
                </div>
                <div className="hero-content">
                    <span className="featured-badge">FEATURED EVENT</span>
                    <h1>Neon Dreams<br />World Tour 2024</h1>
                    <p>Experience the visual and auditory spectacle of the decade.<br />Live in the heart of the city for three exclusive nights.</p>
                    <div className="hero-actions">
                        <button className="btn-primary">Get Tickets Now →</button>
                        <button className="btn-secondary">View Schedule</button>
                    </div>
                </div>
            </section>

            {/* Browse by Category */}
            <section className="section categories-section">
                <div className="section-header">
                    <div>
                        <h2>Browse by Category</h2>
                        <p>Find exactly what you're looking for</p>
                    </div>
                    <a href="#" className="view-all">View all ›</a>
                </div>
                <div className="categories-grid">
                    {categories.map((cat) => (
                        <div key={cat.label} className="category-card" style={{ backgroundColor: cat.color }}>
                            <span className="cat-icon">{cat.icon}</span>
                            <span className="cat-label">{cat.label}</span>
                        </div>
                    ))}
                </div>
            </section>

            {/* Picks for You */}
            <section className="section events-section">
                <div className="section-header">
                    <div>
                        <h2>Picks for You</h2>
                        <p>Based on your recent interest in Jazz &amp; Experimental music</p>
                    </div>
                </div>
                <div className="events-grid">
                    {events.map((event, i) => (
                        <div key={i} className="event-card">
                            <div className="event-image-wrap">
                                <img src={event.image} alt={event.title} />
                                <span className="event-date">{event.date}</span>
                                {event.badge && (
                                    <span
                                        className="event-badge"
                                        style={{ backgroundColor: event.badgeColor }}
                                    >
                                        {event.badge}
                                    </span>
                                )}
                            </div>
                            <div className="event-body">
                                <p className="event-location">📍 {event.location}</p>
                                <h3 className="event-title">{event.title}</h3>
                                <p className="event-desc">{event.desc}</p>
                                <div className="event-footer">
                                    <div>
                                        <span className="starting-from">STARTING FROM</span>
                                        <span className="event-price">{event.price}</span>
                                    </div>
                                    <button className="btn-book">{event.cta}</button>
                                </div>
                            </div>
                        </div>
                    ))}
                </div>
            </section>

            {/* CTA Banner */}
            <section className="cta-banner">
                <div className="cta-left">
                    <h2>Become an Organizer.<br />Reach Thousands of Fans.</h2>
                    <p>Join thousands of successful organizers. Use our easy management tools to power your ticket sales and audience growth effortlessly.</p>
                    <button className="btn-start">Start Organizing 🚀</button>
                </div>
                <div className="cta-right">
                    <div className="cta-feature-card">
                        <span className="cta-feature-icon">👥</span>
                        <div>
                            <strong>Reach Thousands</strong>
                            <p>Get your event in front of our massive community of fans.</p>
                        </div>
                    </div>
                    <div className="cta-feature-card">
                        <span className="cta-feature-icon pink">🎟️</span>
                        <div>
                            <strong>Easy Management</strong>
                            <p>Intuitive tools for tracking sales, vouchers, and attendees.</p>
                        </div>
                    </div>
                </div>
            </section>

            {/* Footer */}
            <footer className="footer">
                <div className="footer-left">
                    <span className="logo">TicketHub</span>
                    <p>The world's leading platform for discovery and ticketing of premium live experiences.</p>
                </div>
                <div className="footer-links">
                    <a href="#">Privacy Policy</a>
                    <a href="#">Terms of Service</a>
                    <a href="#">Cookie Settings</a>
                    <a href="#">Press Kit</a>
                </div>
                <div className="footer-right">
                    <span>© 2024 TicketHub Platform. All Rights Reserved.</span>
                </div>
            </footer>
        </div>
    );
};

export default HomePage;
