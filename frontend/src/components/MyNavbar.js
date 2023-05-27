import React from "react";
import { Nav, Navbar, NavDropdown } from "react-bootstrap";
import { FaPiggyBank } from "react-icons/fa"; // Import the piggy bank icon from react-icons

const MyNavbar = () => {
  const email = localStorage.getItem("email");

  const handleLogout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("email");
    window.location.href = "/login";
  };

  const dropdownContainerStyle = {
    marginLeft: "auto",
  };

  const brandStyle = {
    display: "flex",
    alignItems: "center",
    fontWeight: "bold",
  };

  const navLinkStyle = {
    marginRight: "10px",
  };

  const dropdownItemStyle = {
    fontSize: "14px",
  };

  return (
    <Navbar
      expand="lg"
      variant="light"
      style={{
        backgroundColor: "#e3f2fd",
        fontSize: "24px",
        width: "100%",
        left: 0,
        right: 0,
      }}
      className="mb-4 navbar-full-width" // Added a custom class for full width
    >
      <Navbar.Brand href="/home" style={brandStyle}>
        <FaPiggyBank style={{ marginRight: "5px", marginLeft: "10px" }} /> ATV
        Bank
      </Navbar.Brand>
      <Navbar.Toggle aria-controls="basic-navbar-nav" />
      <Navbar.Collapse id="basic-navbar-nav">
        <Nav className="ml-auto">
          {!email ? (
            <>
              <Nav.Link href="/registration" style={navLinkStyle}>
                Registration
              </Nav.Link>
              <Nav.Link href="/login" style={navLinkStyle}>
                Login
              </Nav.Link>
            </>
          ) : (
            <>
              <Nav.Link href="/home" style={navLinkStyle}>
                Home
              </Nav.Link>
              <Nav.Link href="/accounts" style={navLinkStyle}>
                Accounts
              </Nav.Link>
              <Nav.Link href="/transactions" style={navLinkStyle}>
                Transactions
              </Nav.Link>
            </>
          )}
        </Nav>
        {email && (
          <Nav style={dropdownContainerStyle}>
            <Nav.Link href="/admin" style={navLinkStyle}>
              Admin
            </Nav.Link>
            <NavDropdown
              title={email}
              id="profile-nav-dropdown"
              style={navLinkStyle}
            >
              <NavDropdown.Item onClick={() => {}} style={dropdownItemStyle}>
                Change Password
              </NavDropdown.Item>
              <NavDropdown.Item
                onClick={handleLogout}
                style={dropdownItemStyle}
              >
                Logout
              </NavDropdown.Item>
            </NavDropdown>
          </Nav>
        )}
      </Navbar.Collapse>
    </Navbar>
  );
};

export default MyNavbar;
