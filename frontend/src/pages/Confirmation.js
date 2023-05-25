import React, { useState } from "react";
import MyNavbar from "../components/Navbar";
import { Container, Row } from "react-bootstrap";


function Confirmation() {
  const [eToken, setEToken] = useState("");
  const [password, setPassword] = useState("");  
  

  const handleSubmit = (event) => {
    event.preventDefault();

    const postData = {
      eToken:eToken,
      password:password
    };

    
  };
return (
    <Container>
      <Row>
        <MyNavbar />
      </Row>
      <Row>
        <h2 style={{ color: "#89CFF0", fontSize: "24px", textAlign: "center" }}>
          Payment confirmation
        </h2>
      </Row>
      <Row></Row>

      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label
            style={{
              marginLeft: "410px",
              marginBottom: "20px",
              marginTop: "40px",
            }}
          >
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
              marginTop: "40px",
            }}
          />
        </div>

        <div className="form-group">
          <label style={{ marginLeft: "425px", marginBottom: "20px" }}>
            eToken:
          </label>
          <input
            type="eToken"
            value={eToken}
            onChange={(event) => setEToken(event.target.value)}
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
          onClick={(e) => {
            e.preventDefault();
            window.location.href = "/home";
          }}
        >
          Confirm
        </button>
      </form>
      <Row>
        <form action="login">
          <h2
            style={{
              color: "black",
              fontSize: "15px",
              display: "inline-block",
              marginLeft: "480px",
            }}
          >
          </h2>
          
        </form>
      </Row>
    </Container>
  );
}

export default Confirmation;