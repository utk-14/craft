import React from 'react';
import { useNavigate } from 'react-router-dom';
import '../styles/profile.css'; // <-- Import the CSS

const Profile = () => {
  const navigate = useNavigate();

  const username = localStorage.getItem('username');
  const role = localStorage.getItem('role');

  const handleLogout = () => {
    localStorage.clear();
    navigate('/login');
  };

  return (
    <div className="profile-container">
      <h2>Welcome, {username}!</h2>
      {role && <p>Your role: <strong>{role}</strong></p>}
      <button onClick={handleLogout}>Logout</button>
    </div>
  );
};

export default Profile;
