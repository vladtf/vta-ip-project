import './App.css';
import React, { useState } from 'react'; 
import { BrowserRouter  ,Route, Link, Routes } from 'react-router-dom'; 
import Login from './pages/Login'; 
import Register from './pages/Register'; 

function App() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  const handleSubmit = (event) => {
    event.preventDefault();
    console.log('Email:', email, 'Password:', password);
  }

  return (
    <BrowserRouter>
      <Routes>
        {/* <Route exact path="/" component={Home} /> */}
        <Route path="/login" element={<Login/>} /> 
        <Route path="/register" element={<Register/>} />
         <Route path="/*" element={<h1>Not Found</h1>} /> 
      </Routes>

    </BrowserRouter>
  );
}

export default App;
