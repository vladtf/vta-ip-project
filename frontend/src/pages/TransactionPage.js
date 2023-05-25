import { Col, Container, Row } from "react-bootstrap";
import MyNavbar from "../components/Navbar";
import { useState } from "react";
import axios from "axios";

function TransactionPage() {
  const [ibanSource, setIbanSource] = useState("");
  const [ibanDest, setIbanDest] = useState("");
  const [amount, setAmount] = useState("");
  const [accounts, setAccounts] = useState([]);

  const token =
    localStorage.getItem("token") && JSON.parse(localStorage.getItem("token"));

  if (!token || !token.token) {
    window.location.href = "/login";
  }

  const getMyAccounts = () => {
    const headers = {
      Authorization: token.token,
    };

    axios
      .get(BACKEND_URL + "/api/accounts", { headers: headers })
      .then((response) => {
        console.log(response.data);
        setAccounts(response.data);
        return response.data;
      })
      .catch((error) => {
        console.error(error.response.data);
        return [];
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
      Authorization: token.token,
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
          <button className="btn btn-success" onClick={() => getMyAccounts()}>
            Refresh Accounts List
          </button>
          <div className="form-group">
            <label htmlFor="iban">My account IBAN</label>
            <select
              className="form-select"
              id="iban"
              onChange={(e) => setIbanSource(e.target.value)}
            >
              <option value="">Select an account</option>
              {accounts.map((account) => (
                <option key={account.iban} value={account.iban}>
                  {account.iban}
                </option>
              ))}
            </select>
          </div>
          <div className="form-group">
            <label htmlFor="iban">Destination account IBAN</label>
            <select
              className="form-select"
              id="iban"
              onChange={(e) => setIbanDest(e.target.value)}
            >
              <option value="">Select an account</option>
              {accounts.map((account) => (
                <option key={account.iban} value={account.iban}>
                  {account.iban}
                </option>
              ))}
            </select>
          </div>
          <div className="form-group">
            <label htmlFor="amount">Amount</label>
            <input
              type="number"
              className="form-control"
              id="amount"
              placeholder="Enter amount"
              value={amount}
              onChange={(event) => setAmount(event.target.value)}
            />
          </div>
          <div className="form-group">
            <button
              className="btn btn-primary"
              onClick={() => sendTransaction()}
            >
              Send Transaction
            </button>
          </div>
        </Col>
      </Row>
    </Container>
  );
}

export default TransactionPage;
