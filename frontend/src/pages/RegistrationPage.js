import React, { useState } from "react";
import { Container, Row, Col, Form, Button } from "react-bootstrap";
import axios from "axios";
import MyNavbar from "../components/MyNavbar";
import { BACKEND_URL } from "../configuration/BackendConfig";
import MyFooter from "../components/MyFooter";

function RegistrationPage() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [passwordConfirmation, setPasswordConfirmation] = useState("");
  const [phoneNumber, setPhoneNumber] = useState("");
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");

  const handleSubmit = (event) => {
    event.preventDefault();

    const postData = {
      email: email,
      password: password,
      phoneNumber: phoneNumber,
      firstName: firstName,
      lastName: lastName,
      username: firstName + lastName,
    };

    console.log("Sending registration data: ", postData);

    axios
      .post(BACKEND_URL + "/api/register", postData)
      .then((response) => {
        console.log("Register response:", response.data);
        alert("Registration successful!");
        window.location.href = "/login";
      })
      .catch((error) => {
        console.error(error.response.data);
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
              Registration Page
            </h2>
          </Col>
        </Row>
        <Row>
          <Col md={{ span: 6, offset: 3 }}>
            <Form onSubmit={handleSubmit}>
              <Row className="mb-3">
                <Col>
                  <Form.Group controlId="firstName">
                    <Form.Control
                      type="text"
                      placeholder="First Name*"
                      value={firstName}
                      onChange={(event) => setFirstName(event.target.value)}
                    />
                  </Form.Group>
                </Col>
                <Col>
                  <Form.Group controlId="lastName">
                    <Form.Control
                      type="text"
                      placeholder="Last Name*"
                      value={lastName}
                      onChange={(event) => setLastName(event.target.value)}
                    />
                  </Form.Group>
                </Col>
              </Row>

              <Row className="mb-3">
                <Col>
                  <Form.Group controlId="password">
                    <Form.Control
                      type="password"
                      placeholder="Password*"
                      value={password}
                      onChange={(event) => setPassword(event.target.value)}
                    />
                  </Form.Group>
                </Col>
                <Col>
                  <Form.Group controlId="passwordConfirmation">
                    <Form.Control
                      type="password"
                      placeholder="Confirm Password*"
                      value={passwordConfirmation}
                      onChange={(event) =>
                        setPasswordConfirmation(event.target.value)
                      }
                    />
                  </Form.Group>
                </Col>
              </Row>

              <Form.Group controlId="email" className="mb-3">
                <Form.Control
                  type="email"
                  placeholder="Your Email*"
                  value={email}
                  onChange={(event) => setEmail(event.target.value)}
                />
              </Form.Group>

              <Form.Group controlId="phoneNumber" className="mb-3">
                <Form.Control
                  type="text"
                  placeholder="Your Phone*"
                  value={phoneNumber}
                  onChange={(event) => setPhoneNumber(event.target.value)}
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
                  Register Now
                </Button>
              </div>

              <p
                style={{
                  color: "black",
                  fontSize: "15px",
                  textAlign: "center",
                  marginTop: "20px",
                }}
              >
                Already have an account? <a href="/login">Login</a>
              </p>
            </Form>
          </Col>
        </Row>
      </Container>
      <MyFooter />
    </>
  );
}

export default RegistrationPage;
