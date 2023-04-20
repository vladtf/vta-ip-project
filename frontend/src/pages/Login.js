import React, { useState } from 'react';
import MyNavbar from './MyNavbar';

function Login() {
 const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  const handleSubmit = (event) => {
    event.preventDefault();
    console.log('Email:', email, 'Password:', password);
  }

  return (
    <div>
    <MyNavbar/>
    <h2 style={{ color: "#89CFF0", fontSize: "24px" ,marginLeft:"575px"}}>Login Page</h2> 
    
    <div style={{ display: "block", justifyContent: "column" }}>
      <div style={{ marginRight: "50px" }}>
        <form onSubmit={handleSubmit}>
          <label style={{ marginLeft:"500px",marginBottom: "20px",marginTop:"40px"}}>Email:</label>
          <input
            type="email"
           value={email}
           onChange={(event) => setEmail(event.target.value)} 
           style={{marginLeft:"10px",marginRight: "100px" ,marginBottom: "20px",marginTop:"40px"}}
          />
          
        </form>
      </div> 
      <form onSubmit={handleSubmit}>
      <label style={{ marginLeft:"472px",marginBottom: "20px"}}>Password:</label>
          <input 
            
            type="password"
            value={password}
            onChange={(event) => setPassword(event.target.value)} 
            style={{ marginLeft:"10px",marginRight: "100px" ,marginBottom: "20px"}}
            
          />
          
        </form>
      <div>
        <form action="registration">
          <h2 style={{color: "black",fontSize: "15px", display: "inline-block", marginLeft: "480px"}}>Don't have an account? Then</h2>
          <button style={{display: "inline-block", marginLeft: "10px",border:"none",textDecoration:"underline" }}type="submit">Register</button>
        </form>
        
      </div>
    </div>
  </div>
  );
}

export default Login;
