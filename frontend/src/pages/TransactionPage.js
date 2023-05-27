import { Col, Container, Row } from "react-bootstrap";
import MyNavbar from "../components/Navbar";
import { useState, useEffect } from "react";
import axios from "axios";
import { BACKEND_URL } from "../configuration/BackendConfig";

function TransactionPage() {
  const [ibanSource, setIbanSource] = useState("");
  const [ibanDest, setIbanDest] = useState("");
  const [amount, setAmount] = useState("");
  const [accounts, setAccounts] = useState([]);

  const token = localStorage.getItem("token");

  if (!token) {
    window.location.href = "/login";
  }

  useEffect(() => {
    getMyAccounts();
  }, []);

  const getMyAccounts = () => {
    const headers = {
      Authorization: token,
    };

    axios
      .get(BACKEND_URL + "/api/accounts", { headers: headers })
      .then((response) => {
        console.log(response.data);
        setAccounts(response.data);
      })
      .catch((error) => {
        console.error(error.response.data);
      });
  };

  const sendTransaction = () => {
    const transactionRequest = {
      ibanSource: ibanSource,
      ibanDest: ibanDest,
      sum: amount,
    };

    console.log("Sending transaction data: ", transactionRequest);

    const headers = {
      Authorization: token,
    };

    axios
      .post(BACKEND_URL + "/api/transaction", transactionRequest, {
        headers,
      })
      .then((response) => {
        console.log(response.data);
        alert("Transaction successful!");
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
          Transaction Page
        </h2>
      </Row>
      <Row>
        <Col md={6}>
          <div className="mb-3">
            <label htmlFor="ibanSource" className="form-label">
              My account IBAN
            </label>
            <select
              className="form-select"
              id="ibanSource"
              onChange={(e) => setIbanSource(e.target.value)}
              value={ibanSource}
            >
              <option value="">Select an account</option>
              {accounts.map((account) => (
                <option key={account.iban} value={account.iban}>
                  {account.iban}
                </option>
              ))}
            </select>
          </div>
          <div className="mb-3">
            <label htmlFor="ibanDest" className="form-label">
              Destination account IBAN
            </label>
            <select
              className="form-select"
              id="ibanDest"
              onChange={(e) => setIbanDest(e.target.value)}
              value={ibanDest}
            >
              <option value="">Select an account</option>
              {accounts.map((account) => (
                <option key={account.iban} value={account.iban}>
                  {account.iban}
                </option>
              ))}
            </select>
          </div>
          <div className="mb-3">
            <label htmlFor="amount" className="form-label">
              Amount
            </label>
            <input
              type="number"
              className="form-control"
              id="amount"
              placeholder="Enter amount"
              value={amount}
              onChange={(event) => setAmount(event.target.value)}
            />
          </div>
          <div className="mb-3">
            <button className="btn btn-primary" onClick={sendTransaction}>
              Send Transaction
            </button>
          </div>
        </Col>
      </Row>
    </Container>
  );
}

export default TransactionPage;
