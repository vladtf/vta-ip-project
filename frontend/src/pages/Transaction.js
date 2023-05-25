import React, { useState } from "react";
import MyNavbar from "../components/Navbar";
import { Container, Row } from "react-bootstrap";


function Transaction() {
  const [iban, setIban] = useState("");
  const [recipient, setRecipient] = useState("");  
  const [amount, setAmount] = useState("");

  const handleSubmit = (event) => {
    event.preventDefault();

    const postData = {
      iban: iban,
      recipient: recipient, 
      amount:amount
    };

    
  };
return (
    <Container>
      <Row>
        <MyNavbar />
      </Row>
      <Row>
        <h2 style={{ color: "#89CFF0", fontSize: "24px", textAlign: "center" }}>
          Transaction form
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
            IBAN:
          </label>
          <input
            type="iban"
            value={iban}
            onChange={(event) => setIban(event.target.value)}
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
            Recipient Name:
          </label>
          <input
            type="recipient"
            value={recipient}
            onChange={(event) => setRecipient(event.target.value)}
            style={{
              marginLeft: "10px",
              marginRight: "100px",
              marginBottom: "20px",
            }}
          />
        </div> 

        <div className="form-group">
          <label style={{ marginLeft: "478px", marginBottom: "20px" }}>
            Amount:
          </label>
          <input
            type="amount"
            value={amount}
            onChange={(event) => setAmount(event.target.value)}
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
            window.location.href = "/confirmation";
          }}
        >
          Transfer Now
        </button>
      </form>
      
    </Container>
  );
}

export default Transaction;