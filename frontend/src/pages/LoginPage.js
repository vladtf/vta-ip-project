import React, { useState } from "react";
import MyNavbar from "../components/Navbar";
import { Container, Row } from "react-bootstrap";
import axios from "axios";

function Login() {
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
      .post("http://localhost:8090/api/login", postData)
      .then((response) => {
        console.log(response.data);

        if (response.data.token) {
          // Assuming you have an object called 'myObject'
          const myObject = { token: response.data.token };

          // Convert the object to a string using JSON.stringify
          const serializedObject = JSON.stringify(myObject);

          // Store the serialized object in the local storage
          localStorage.setItem("token", serializedObject);


          alert("Login successful!");

          window.location.href = "/home";
        } else {
          alert("Login failed!");
        }
      })
      .catch((error) => {
        console.error(error.response.data);
      });
  };

  return (
    <Container>
      <Row>
        <MyNavbar />
      </Row>
      <Row>
        <h2 style={{ color: "#89CFF0", fontSize: "24px", textAlign: "center" }}>
          Login Page
        </h2>
      </Row>
      <Row></Row>

      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label
            style={{
              marginLeft: "500px",
              marginBottom: "20px",
              marginTop: "40px",
            }}
          >
            Email:
          </label>
          <input
            type="email"
            value={email}
            onChange={(event) => setEmail(event.target.value)}
            style={{
              marginLeft: "10px",
              marginRight: "100px",
              marginBottom: "20px",
              marginTop: "40px",
            }}
          />
        </div>

        <div className="form-group">
          <label style={{ marginLeft: "472px", marginBottom: "20px" }}>
            Password:
          </label>
          <input
            type="password"
            value={password}
            onChange={(event) => setPassword(event.target.value)}
            style={{
              marginLeft: "10px",
              marginRight: "100px",
              marginBottom: "20px",
            }}
          />
        </div>

        <button
          type="submit"
          className="btn btn-primary"
          style={{
            marginLeft: "500px",
            marginBottom: "20px",
            marginTop: "40px",
          }}
          onClick={handleSubmit}
        >
          Login
        </button>
      </form>
      <Row>
        <form action="registration">
          <h2
            style={{
              color: "black",
              fontSize: "15px",
              display: "inline-block",
              marginLeft: "480px",
            }}
          >
            Don't have an account? Then
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
              window.location.href = "/registration";
            }}
          >
            Register
          </button>
        </form>
      </Row>
    </Container>
  );
}

export default Login;
