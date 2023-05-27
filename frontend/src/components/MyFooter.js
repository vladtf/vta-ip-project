import React from "react";
import { Nav } from "react-bootstrap";
import { FaGithub } from "react-icons/fa";

const MyFooter = () => {
  const footerStyle = {
    backgroundColor: "#e3f2fd",
    fontSize: "14px",
    padding: "10px",
    textAlign: "center",
    position: "fixed",
    left: 0,
    bottom: 0,
    width: "100%",
  };

  const containerStyle = {
    minHeight: "100vh",
    display: "flex",
    flexDirection: "column",
  };

  return (
    <div style={containerStyle}>
      <Nav style={footerStyle}>
        <Nav.Item>
          <Nav.Link
            href="https://github.com/vladtf/vta-ip-project"
            target="_blank"
          >
            <FaGithub style={{ marginRight: "5px" }} />
            Visit Our GitHub Page
          </Nav.Link>
        </Nav.Item>
      </Nav>
    </div>
  );
};

export default MyFooter;
