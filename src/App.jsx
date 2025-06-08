// import React, { useEffect, useState } from 'react';
// import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
// import Register from './pages/register';
// import Login from './pages/login';
// import Profile from './pages/profile';

// const App = () => {
//   const [isAuthenticated, setIsAuthenticated] = useState(!!localStorage.getItem('token'));

//   // Sync authentication state on localStorage changes
//   useEffect(() => {
//     const checkAuth = () => {
//       setIsAuthenticated(!!localStorage.getItem('token'));
//     };

//     // Listen for login/logout across tabs
//     window.addEventListener('storage', checkAuth);

//     return () => window.removeEventListener('storage', checkAuth);
//   }, []);

//   return (
//     <Router>
//       <Routes>
//         <Route
//           path="/register"
//           element={!isAuthenticated ? <Register /> : <Navigate to="/profile" replace />}
//         />
//         <Route
//           path="/login"
//           element={!isAuthenticated ? <Login /> : <Navigate to="/profile" replace />}
//         />
//         <Route
//           path="/profile"
//           element={isAuthenticated ? <Profile /> : <Navigate to="/login" replace />}
//         />
//         <Route path="/" element={<Navigate to="/register" replace />} />
//         <Route path="*" element={<Navigate to="/" replace />} />
//       </Routes>
//     </Router>
//   );
// };

// export default App;
import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import Register from './pages/register';
import Login from './pages/login';
import Profile from './pages/profile';
import { AuthProvider, useAuth } from './context/AuthContext';

const AppRoutes = () => {
  const { isAuthenticated } = useAuth();

  return (
    <Routes>
      <Route
        path="/register"
        element={!isAuthenticated ? <Register /> : <Navigate to="/profile" replace />}
      />
      <Route
        path="/login"
        element={!isAuthenticated ? <Login /> : <Navigate to="/profile" replace />}
      />
      <Route
        path="/profile"
        element={isAuthenticated ? <Profile /> : <Navigate to="/login" replace />}
      />
      <Route path="/" element={<Navigate to="/register" replace />} />
      <Route path="*" element={<Navigate to="/" replace />} />
    </Routes>
  );
};

const App = () => (
  <AuthProvider>
    <Router>
      <AppRoutes />
    </Router>
  </AuthProvider>
);

export default App;
