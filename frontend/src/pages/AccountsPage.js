import { Col, Container, Row } from "react-bootstrap";
import MyNavbar from "../components/Navbar";
import axios from "axios";
import { useState, useEffect } from "react";
import BackendConfig, { BACKEND_URL } from "../configuration/BackendConfig";

function AccountsPage() {
  const [accounts, setAccounts] = useState([]);
  const [currency, setCurrency] = useState("EUR");
  const [type, setType] = useState("SAVINGS");

  const token =
    localStorage.getItem("token") && JSON.parse(localStorage.getItem("token"));

  if (!token || !token.token) {
    window.location.href = "/login";
  }

  const generateIBAN = () => {
    const randomNumber =
      Math.floor(Math.random() * 9000000000000000) + 1000000000000000;
    const iban = "IBAN" + randomNumber;
    return iban;
  };

  useEffect(() => {
    getAccounts();
  }, []);

  const getAccounts = () => {
    const headers = {
      Authorization: token.token,
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

  const addAccount = (e) => {
    const confirmAdd = window.confirm(
      "Are you sure you want to add this account?"
    );
    if (!confirmAdd) {
      return;
    }

    const accountRequest = {
      iban: generateIBAN(),
      currency: currency,
      balance: 100,
      type: type,
    };

    console.log("Sending account data: ", accountRequest);

    const headers = {
      Authorization: token.token,
    };

    axios
      .post(BACKEND_URL + "/api/accounts", accountRequest, { headers })
      .then((response) => {
        console.log(response.data);
        setAccounts(response.data);
      })
      .catch((error) => {
        console.error(error.response.data);
      });
  };

  const deleteAccount = (iban) => {
    const headers = {
      Authorization: token.token,
    };

    const confirmDelete = window.confirm(
      "Are you sure you want to delete this account?"
    );

    if (!confirmDelete) {
      return;
    }

    const deleteRequest = {
      iban: iban,
    };

    axios
      .delete(BACKEND_URL + "/api/accounts", {
        headers: headers,
        data: deleteRequest,
      })
      .then((response) => {
        console.log(response.data);
        alert("Account deleted successfully!");
        getAccounts(); // Refresh accounts after deletion
      })
      .catch((error) => {
        alert("Error deleting account!");
        console.error(error.response.data);
      });
  };

  return (
    <Container>
      <Row>
        <MyNavbar />
      </Row>
      <Row>
        <h2
          className="text-center"
          style={{ color: "#89CFF0", fontSize: "24px" }}
        >
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
                placeholder="IBAN"
                value={generateIBAN()}
                readOnly={true}
                style={{ backgroundColor: "#e9ecef" }}
              />
            </div>
            <div className="form-group">
              <label htmlFor="currency">Currency:</label>
              <select
                className="form-select"
                id="currency"
                value={currency}
                onChange={(event) => setCurrency(event.target.value)}
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
                onChange={(event) => setType(event.target.value)}
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
            Add Account
          </button>
        </Col>
      </Row>
      <hr />
      <Row>
        {accounts.length === 0 ? (
          <h2 className="text-center">No accounts found</h2>
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
                    <button
                      className="btn btn-danger"
                      onClick={() => deleteAccount(account.iban)}
                    >
                      Delete Account
                    </button>
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
