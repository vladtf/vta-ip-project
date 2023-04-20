import React, { useState } from 'react'; 
import { Card, Nav,Navbar } from 'react-bootstrap';
import './register-page.css'
 const Register = () =>{
  return <Card >
    <Card.Title className="header">VTA ONLINE BANKING</Card.Title> 
    <Nav className="navbar">
      <Nav.Link href="#" className='HomeButton'>Home</Nav.Link>
      <Nav.Link href="#" className='LoginButton'>Login</Nav.Link>
      <Nav.Link href="#" className='RegisterButton'>Register</Nav.Link>
    </Nav>
  
  <Card.Body>
  </Card.Body>
</Card>
    
  
} 
export default Register;