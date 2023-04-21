 import { Nav,Navbar, NavDropdown } from 'react-bootstrap'; 


 const MyNavbar = () =>{
  return  <Navbar bg="light" expand="lg">
  <Navbar.Brand href="#home">ATV Online Banking</Navbar.Brand>
  <Navbar.Toggle aria-controls="basic-navbar-nav" />
  <Navbar.Collapse id="basic-navbar-nav">
    <Nav className="mr-auto">
      <Nav.Link href="registration">Registration</Nav.Link>
      <Nav.Link href="login">Login</Nav.Link> 
      
      <NavDropdown title="Life at ATV" id="basic-nav-dropdown">
        <NavDropdown.Item href="#action/3.1">Why ATV?</NavDropdown.Item>
        <NavDropdown.Item href="#action/3.2">Careers</NavDropdown.Item>
        <NavDropdown.Item href="#action/3.3"></NavDropdown.Item>
      </NavDropdown>
    </Nav>
    <Nav> 
    <Nav.Link href="#link">About us</Nav.Link> 
      <Nav.Link href="#link">Contact</Nav.Link>
    </Nav>
  </Navbar.Collapse>
</Navbar> 
    


} 
export default MyNavbar;