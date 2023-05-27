import React, { useState } from "react";
import { Container, Row, Col, Form, Button } from "react-bootstrap";
import axios from "axios";
import MyNavbar from "../components/Navbar";
import { BACKEND_URL } from "../configuration/BackendConfig";

function LoginPage() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const handleSubmit = (event) => {
    event.preventDefault();

    const postData = {
      email: email,
      password: password,
    };

    console.log("Sending login data: ", postData);
    axios
      .post(BACKEND_URL + "/api/login", postData)
      .then((response) => {
        console.log(response.data);

        if (response.data.token) {
          const myObject = { token: response.data.token };
          const serializedObject = JSON.stringify(myObject);
          localStorage.setItem("token", serializedObject);

          alert("Login successful!");

          window.location.href = "/home";
        } else {
          alert("Login failed!");
        }
      })
      .catch((error) => {
        console.error(error.response.data);
        alert("Login failed!");
      });
  };

  return (
    <Container>
      <Row>
        <MyNavbar />
      </Row>
      <Row>
        <Col>
          <h2
            style={{ color: "#89CFF0", fontSize: "24px", textAlign: "center" }}
          >
            Login Page
          </h2>
        </Col>
      </Row>
      <Row>
        <Col md={{ span: 6, offset: 3 }}>
          <Form onSubmit={handleSubmit}>
            <Form.Group controlId="email" className="mb-3">
              <Form.Label>Email:</Form.Label>
              <Form.Control
                type="email"
                value={email}
                onChange={(event) => setEmail(event.target.value)}
              />
            </Form.Group>

            <Form.Group controlId="password" className="mb-3">
              <Form.Label>Password:</Form.Label>
              <Form.Control
                type="password"
                value={password}
                onChange={(event) => setPassword(event.target.value)}
              />
            </Form.Group>

            <div className="d-flex justify-content-center">
              <Button
                variant="primary"
                type="submit"
                style={{
                  width: "200px",
                  height: "50px",
                  backgroundColor: "#89CFF0",
                  borderColor: "#89CFF0",
                }}
                onClick={handleSubmit}
              >
                Login
              </Button>
            </div>
          </Form>

          <Row className="mt-3">
            <Col>
              <p
                style={{
                  color: "black",
                  fontSize: "15px",
                  textAlign: "center",
                }}
              >
                Don't have an account? <a href="/registration">Register</a>
              </p>
            </Col>
          </Row>
        </Col>
      </Row>
    </Container>
  );
}

export default LoginPage;
