// // import React, { useState } from 'react';
// // import { Link, useNavigate } from 'react-router-dom';
// // import '../styles/login.css';
// // import { loginUser } from '../api/auth';

// // const Login = () => {
// //   const [formData, setFormData] = useState({ username: '', password: '' });
// //   const [message, setMessage] = useState('');
// //   const navigate = useNavigate();

// //   const handleChange = (e) => {
// //     setFormData(prev => ({ ...prev, [e.target.name]: e.target.value }));
// //   };

// //   const handleSubmit = async (e) => {
// //     e.preventDefault();
// //     setMessage('');
// //     try {
// //       const res = await loginUser(formData);
      
// //       // Save JWT token & username to localStorage
// //       localStorage.setItem('token', res.jwt);
// //       localStorage.setItem('username', formData.username);
      
// //       setMessage('Login successful! Redirecting...');
      
// //       setTimeout(() => {
// //         navigate('/profile');
// //       }, 100);
// //     } catch (error) {
// //       setMessage(error.message || 'Login failed');
// //     }
// //   };

// //   return (
// //     <div className="login-container">
// //       <form onSubmit={handleSubmit}>
// //         <h2>Login</h2>
// //         {message && <p className="message">{message}</p>}

// //         <input
// //           type="text"
// //           name="username"
// //           placeholder="Username"
// //           value={formData.username}
// //           onChange={handleChange}
// //           required
// //         />

// //         <input
// //           type="password"
// //           name="password"
// //           placeholder="Password"
// //           value={formData.password}
// //           onChange={handleChange}
// //           required
// //         />

// //         <button type="submit">Login</button>

// //         <p style={{ marginTop: '1rem', textAlign: 'center' }}>
// //           Don't have an account? <Link to="/register">Register</Link>
// //         </p>
// //       </form>
// //     </div>
// //   );
// // };

// // export default Login;


// import React, { useState } from 'react';
// import { Link, useNavigate } from 'react-router-dom';
// import '../styles/login.css';
// import { loginUser } from '../api/auth';

// const Login = () => {
//   const [formData, setFormData] = useState({ username: '', password: '' });
//   const [message, setMessage] = useState('');
//   const navigate = useNavigate();

//   const handleChange = (e) => {
//     setFormData(prev => ({ ...prev, [e.target.name]: e.target.value }));
//   };

//   const handleSubmit = async (e) => {
//     e.preventDefault();
//     setMessage('');
//     try {
//       const res = await loginUser(formData);

//       // Save JWT token & username to localStorage
//       localStorage.setItem('token', res.data.jwt);
//       localStorage.setItem('username', formData.username);

//       setMessage('Login successful! Redirecting...');

//       setTimeout(() => {
//         navigate('/profile');
//       }, 1000);
//     } catch (error) {
//       setMessage(error.message || 'Login failed');
//     }
//   };

//   return (
//     <div className="wrapper">
//       <div className="login-container">
//         <form onSubmit={handleSubmit}>
//           <h2>Login</h2>
//           {message && <p className="message">{message}</p>}

//           <input
//             type="text"
//             name="username"
//             placeholder="Username"
//             value={formData.username}
//             onChange={handleChange}
//             required
//           />

//           <input
//             type="password"
//             name="password"
//             placeholder="Password"
//             value={formData.password}
//             onChange={handleChange}
//             required
//           />

//           <button type="submit">Login</button>

//           <p style={{ marginTop: '1rem', textAlign: 'center' }}>
//             Don't have an account? <Link to="/register">Register</Link>
//           </p>
//         </form>
//       </div>
//     </div>
//   );
// };

// export default Login;
import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import '../styles/login.css';
import { loginUser } from '../api/auth';
import { useAuth } from '../context/AuthContext.jsx';

const Login = () => {
  const [formData, setFormData] = useState({ username: '', password: '' });
  const [message, setMessage] = useState('');
  const navigate = useNavigate();
  const { login } = useAuth();

  const handleChange = (e) => {
    setFormData(prev => ({ ...prev, [e.target.name]: e.target.value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setMessage('');
    try {
      const res = await loginUser(formData);
      login(res.data.jwt, formData.username); // Update global state
      navigate('/profile'); // Redirect immediately
    } catch (error) {
      setMessage(error.message || 'Login failed');
    }
  };

  return (
    <div className="wrapper">
      <div className="login-container">
        <form onSubmit={handleSubmit}>
          <h2>Login</h2>
          {message && <p className="message">{message}</p>}

          <input
            type="text"
            name="username"
            placeholder="Username"
            value={formData.username}
            onChange={handleChange}
            required
          />

          <input
            type="password"
            name="password"
            placeholder="Password"
            value={formData.password}
            onChange={handleChange}
            required
          />

          <button type="submit">Login</button>

          <p style={{ marginTop: '1rem', textAlign: 'center' }}>
            Don't have an account? <Link to="/register">Register</Link>
          </p>
        </form>
      </div>
    </div>
  );
};

export default Login;
