import React, { useState } from "react";
import axios from "axios";
import MyNavbar from "../components/Navbar";
import { Container, Row } from "react-bootstrap";

function Registration() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [passwordConfirmation, setPasswordConfirmation] = useState("");
  const [phoneNumber, setPhoneNumber] = useState("");
  const [firstName, setFirstName] = useState("");
  const [gender, setGender] = useState("");
  const [lastName, setLastName] = useState("");
  const [dob, setDob] = useState("");

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
      .post("http://loadbalancer.vta.com:8090/register", postData)
      .then((response) => {
        console.log(response.data);

        if (response.data === "success") {
          alert("Registration successful!");
          window.location.href = "/login";
        } else {
          alert("Registration failed!");
        }
      })
      .catch((error) => {
        console.error(error);
      });
  };

  return (
    <Container>
      <Row>
        <MyNavbar />
      </Row>
      <Row>
        <h2 style={{ color: "#89CFF0", fontSize: "24px", textAlign: "center" }}>
          Registration Page
        </h2>
      </Row>
      <Row>
        <form onSubmit={handleSubmit}>
          <input
            type="firstName"
            placeholder="First Name*"
            value={firstName}
            onChange={(event) => setFirstName(event.target.value)}
            style={{
              marginLeft: "350px",
              marginRight: "100px",
              marginBottom: "20px",
              marginTop: "50px",
            }}
          />
          <input
            type="lastName"
            placeholder="Last Name*"
            value={lastName}
            onChange={(event) => setLastName(event.target.value)}
          />
          <input
            type="password"
            placeholder="Password*"
            value={password}
            onChange={(event) => setPassword(event.target.value)}
            style={{
              marginLeft: "350px",
              marginRight: "100px",
              marginBottom: "20px",
            }}
          />
          <input
            type="password"
            placeholder="Confirm Password*"
            value={passwordConfirmation}
            onChange={(event) => setPasswordConfirmation(event.target.value)}
          />
          <input
            type="email"
            value={email}
            placeholder="Your Email*"
            onChange={(event) => setEmail(event.target.value)}
            style={{
              marginLeft: "350px",
              marginRight: "100px",
              marginBottom: "20px",
            }}
          />

          <input
            type="phoneNumber"
            value={phoneNumber}
            placeholder="Your Phone*"
            onChange={(event) => setPhoneNumber(event.target.value)}
          />
          <select
            style={{
              marginLeft: "350px",
              marginRight: "170px",
              marginBottom: "20px",
            }}
            value={gender}
            onChange={(event) => setGender(event.target.value)}
          >
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
          <button
            style={{
              marginLeft: "500px",
              marginBottom: "20px",
              marginTop: "20px",
              borderRadius: "10px",
              width: "200px",
              height: "50px",
              backgroundColor: "#89CFF0",
              borderColor: "#89CFF0",
            }}
            onClick={handleSubmit}
          >
            Register Now
          </button>

          <h2
            style={{
              color: "black",
              fontSize: "15px",
              display: "inline-block",
              marginLeft: "480px",
            }}
          >
            Already have an account? Then
          </h2>
          <button
            style={{
              display: "inline-block",
              marginLeft: "10px",
              border: "none",
              textDecoration: "underline",
            }}
            onClick={(e) => {
              e.preventDefault();
              window.location.href = "/login";
            }}
          >
            Login
          </button>
        </form>
      </Row>
    </Container>
  );
}

export default Registration;
