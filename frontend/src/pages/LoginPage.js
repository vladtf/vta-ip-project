import React, { useState } from "react";
import { Container, Row, Col, Form, Button, Spinner } from "react-bootstrap";
import axios from "axios";
import MyNavbar from "../components/MyNavbar";
import { BACKEND_URL } from "../configuration/BackendConfig";
import MyFooter from "../components/MyFooter";

function LoginPage() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [loading, setLoading] = useState(false);

  const handleSubmit = (event) => {
    event.preventDefault();

    const postData = {
      email: email,
      password: password,
      device: window.location.protocol + "//" + window.location.host,
    };

    setLoading(true); // Set loading state to true

    console.log("Sending login data: ", postData);
    axios
      .post(BACKEND_URL + "/api/login", postData)
      .then((response) => {
        console.log(response.data);

        alert("Login successful! Check your email for the activation link.");
        window.location.href = "/login";
      })
      .catch((error) => {
        alert("Login failed!");
        console.error(error.response.data);
      })
      .finally(() => {
        setLoading(false); // Set loading state to false after the request completes
      });
  };

  return (
    <>
      <MyNavbar />
      <Container>
        <Row>
          <Col>
            <h2
              style={{
                color: "#89CFF0",
                fontSize: "24px",
                textAlign: "center",
              }}
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
                  disabled={loading} // Disable the button when loading is true
                >
                  {loading ? ( // Conditionally render the button text or a spinner based on the loading state
                    <Spinner animation="border" size="sm" role="status">
                      <span className="visually-hidden">Loading...</span>
                    </Spinner>
                  ) : (
                    "Login"
                  )}
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
      <MyFooter />
    </>
  );
}

export default LoginPage;
