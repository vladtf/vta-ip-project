import React, { useState } from 'react';
import MyNavbar from './MyNavbar';
import axios from 'axios';

function Registration() {
 const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');  
  const [passwordConfirmation, setPasswordConfirmation] = useState(''); 
  const [phoneNumber, setPhoneNumber] = useState(''); 
  const [firstName, setFirstName] = useState('');  
  const [gender, setGender] = useState('');
  const [lastName, setLastName] = useState(''); 
  const [dob, setDob] = useState('');

  const handleSubmit = (event) => {
    event.preventDefault();

    const postData = {
      email: email,
      password: password,
      phoneNumber: phoneNumber,
      firstName: firstName,
      lastName: lastName,
      username: firstName+lastName,
    }

    console.log("Sending registration data: ", postData);

    axios
      .post("http://localhost:8090/register", postData)
      .then((response) => {
        console.log(response.data);
      })
      .catch((error) => {
        console.error(error);
      });
  }

  return (
    <div>
  <MyNavbar/>
  <h2 style={{ color: "#89CFF0", fontSize: "24px" ,marginLeft:"500px"}}>Registration Page</h2> 
  
  <div style={{ display: "block", justifyContent: "column" }}>
    <div style={{ marginRight: "50px" }}>
      <form onSubmit={handleSubmit}>
      
        <input 
          
          type="firstName"
          placeholder="First Name*"
          value={firstName}
          onChange={(event) => setFirstName(event.target.value)} 
          style={{ marginLeft:"350px",marginRight: "100px" ,marginBottom: "20px",marginTop:"50px" }}
          
        />
        <input
         type="lastName"
         placeholder="Last Name*"
         value={lastName}
         onChange={(event) => setLastName(event.target.value)}
        />
        
      </form>
    </div> 
    <form onSubmit={handleSubmit}>
      
        <input 
          
          type="password"
          placeholder="Password*"
          value={password}
          onChange={(event) => setPassword(event.target.value)} 
          style={{ marginLeft:"350px",marginRight: "100px" ,marginBottom: "20px"}}
          
        />
        <input
         type="passwordConfirmation"
         placeholder="Confirm Password*"
         value={lastName}
         onChange={(event) => setPasswordConfirmation(event.target.value)}
        />
        
      </form>
    <div>
      <form onSubmit={handleSubmit}>
        <input
          type="email"
          value={email} 
          placeholder="Your Email*"
          onChange={(event) => setEmail(event.target.value)} 
          style={{ marginLeft:"350px",marginRight: "100px" ,marginBottom: "20px"}}
        />
        
        <input
          type="phoneNumber"
          value={phoneNumber} 
          placeholder="Your Phone*"
          onChange={(event) => setPhoneNumber(event.target.value)}
        />
        
      </form> 
      <form onSubmit={handleSubmit}> 
      <select style={{ marginLeft:"350px",marginRight: "170px" ,marginBottom: "20px"}} value={gender} onChange={(event) => setGender(event.target.value)}>
        <option value="">Select gender</option>
        <option value="male">Male</option>
        <option value="female">Female</option>
        <option value="other">Other</option> 
        
      </select> 
      
      <input
        type="date"
        value={dob}
        onChange={(event) => setDob(event.target.value)}
        min="1900-01-01"
        max="2099-12-31"
        />

        
      </form> 
      <input style={{ marginLeft:"500px",marginBottom: "20px",marginTop:"20px",borderRadius: '10px',
        width:"200px",height:"50px",backgroundColor:"#89CFF0",borderColor:"#89CFF0"}} type="submit" value="Register Now" />

      <button className='btn btn-primary' onClick={handleSubmit}>Register</button>
      
      <form action="login">
        <h2 style={{color: "black",fontSize: "15px", display: "inline-block", marginLeft: "480px"}}>Already have an account? Then</h2>
        <button style={{display: "inline-block", marginLeft: "10px",border:"none",textDecoration:"underline" }}type="submit">Login</button>
      </form>
      
    </div>
  </div>
</div>
    
    
  );
}

export default Registration;