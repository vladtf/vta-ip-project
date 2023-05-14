import { Col, Container, Row } from "react-bootstrap";
import MyNavbar from "../components/Navbar";
import axios from "axios";
import { useState } from "react";

function AccountsPage() {
  const [accounts, setAccounts] = useState([]);

  const [iban, setIban] = useState("");
  const [currency, setCurrency] = useState("EUR");
  const [type, setType] = useState("SAVINGS");

  const token =
    localStorage.getItem("token") && JSON.parse(localStorage.getItem("token"));

  if (!token || !token.token) {
    window.location.href = "/login";
  }

  const getAccounts = () => {
    const headers = {
      Authorization: token.token,
    };

    axios
      .get("http://localhost:8090/api/accounts", { headers: headers })
      .then((response) => {
        console.log(response.data);
        setAccounts(response.data);
      })
      .catch((error) => {
        console.error(error.response.data);
      });
  };

  const addAccount = (e) => {
    const accountRequest = {
      iban: iban,
      currency: currency,
      balance: 0,
      type: type,
    };

    console.log("Sending account data: ", accountRequest);

    const headers = {
      Authorization: token.token,
    };

    axios
      .post("http://localhost:8090/api/accounts", accountRequest, { headers })
      .then((response) => {
        console.log(response.data);
        setAccounts(response.data);
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
          Accounts Page
        </h2>
      </Row>
      <Row className="mb-3">
        <Col md={6}>
          <form onSubmit={(e) => e.preventDefault()}>
            <div className="form-group">
              <label htmlFor="iban">IBAN:</label>
              <input
                type="text"
                className="form-control"
                id="iban"
                placeholder="iban"
                value={iban}
                onChange={(event) => setIban(event.target.value)}
                required={true}
              />
            </div>
            <div className="form-group">
              <label htmlFor="currency">Currency:</label>
              <select
                className="form-select"
                id="currency"
                value={currency}
                onChange={(event) => {
                  console.log(event.target.value);
                  setCurrency(event.target.value);
                }}
              >
                <option value="EUR">EUR</option>
                <option value="USD">USD</option>
                <option value="RON">RON</option>
              </select>
            </div>
            <div className="form-group">
              <label htmlFor="type">Type:</label>
              <select
                className="form-select"
                id="type"
                value={type}
                onChange={(event) => {
                  setType(event.target.value);
                }}
              >
                <option value="SAVINGS">SAVINGS</option>
                <option value="CURRENT">CURRENT</option>
                <option value="DEPOSIT">DEPOSIT</option>
                <option value="CREDIT">CREDIT</option>
              </select>
            </div>
          </form>
        </Col>
      </Row>
      <Row>
        <Col>
          <button className="btn btn-success" onClick={addAccount}>
            addAccount
          </button>
        </Col>
        <Col>
          <button className="btn btn-primary" onClick={getAccounts}>
            Refresh Accounts
          </button>
        </Col>
      </Row>
      <hr />
      <Row>
        {accounts.length == 0 ? (
          <h2>No accounts found</h2>
        ) : (
          accounts.map((account, index) => {
            return (
              <Col md="3" key={index} className="p-2">
                <div className="card" style={{ width: "18rem" }}>
                  <div className="card-body">
                    <h5 className="card-title">{account.iban}</h5>
                    <h6 className="card-subtitle mb-2 text-muted">
                      {account.currency}
                    </h6>
                    <p className="card-text">{account.type}</p>
                    <p className="card-text">{account.balance}</p>
                  </div>
                </div>
              </Col>
            );
          })
        )}
      </Row>
    </Container>
  );
}

export default AccountsPage;
