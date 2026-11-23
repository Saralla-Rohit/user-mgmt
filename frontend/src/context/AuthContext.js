import React, { createContext, useContext, useState } from 'react';
import UserService from '../components/service/UserService';
const AuthContext = createContext(null);

export function AuthProvider({ children }) {
    // Initialize from localStorage so a refresh keeps you logged in
    const [auth, setAuth] = useState({
        token: localStorage.getItem('token'),
        role: localStorage.getItem('role'),
    });

    const login = (token, role) => {
        localStorage.setItem('token', token);
        localStorage.setItem('role', role);
        setAuth({ token, role });          // <-- this triggers re-render everywhere
    };

    const logout = () => {
        UserService.logout();
        setAuth({ token: null, role: null }); // <-- triggers re-render everywhere
    };

    const isAuthenticated = !!auth.token;
    const isAdmin = auth.role === 'ADMIN';

    return (
        <AuthContext.Provider value={{ auth, login, logout, isAuthenticated, isAdmin }}>
            {children}
        </AuthContext.Provider>
    );
}

export const useAuth = () => useContext(AuthContext);