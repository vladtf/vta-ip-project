import React from "react";
import { Nav, Navbar, NavDropdown } from "react-bootstrap";

const MyNavbar = () => {
  const handleLogout = () => {
    localStorage.removeItem("token");
    window.location.href = "/login";
  };

  return (
    <Navbar bg="light p-2" expand="lg">
      <Navbar.Brand href="/home">ATV Online Banking</Navbar.Brand>
      <Navbar.Toggle aria-controls="basic-navbar-nav" />
      <Navbar.Collapse id="basic-navbar-nav">
        <Nav className="mr-auto">
          <Nav.Link href="/registration">Registration</Nav.Link>
          <Nav.Link href="/login">Login</Nav.Link>
          <Nav.Link href="/admin">Admin</Nav.Link>
          <NavDropdown title="My Profile" id="logout-nav-dropdown">
            <NavDropdown.Item onClick={handleLogout}>Logout</NavDropdown.Item>
            <NavDropdown.Item onClick={() => {}}>
              Change Password
            </NavDropdown.Item>
            <NavDropdown.Item onClick={() => (window.location.href = "/home")}>
              Home
            </NavDropdown.Item>
            <NavDropdown.Item
              onClick={() => (window.location.href = "/accounts")}
            >
              Accounts
            </NavDropdown.Item>
            <NavDropdown.Item
              onClick={() => (window.location.href = "/transactions")}
            >
              Transactions
            </NavDropdown.Item>
          </NavDropdown>
        </Nav>
      </Navbar.Collapse>
    </Navbar>
  );
};

export default MyNavbar;
