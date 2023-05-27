import { Container, Row, Col } from "react-bootstrap";
import MyNavbar from "../components/Navbar";
import { useEffect, useState } from "react";
import axios from "axios";
import { BACKEND_URL } from "../configuration/BackendConfig";

function HomePage() {
  const [transactions, setTransactions] = useState([]);
  const [accounts, setAccounts] = useState([]);
  const [selectedAccount, setSelectedAccount] = useState("");

  const token =
    localStorage.getItem("token");

  if (!token ) {
    window.location.href = "/login";
  }

  useEffect(() => {
    const fetchTransactions = async () => {
      try {
        const headers = {
          Authorization: token,
        };

        const response = await axios.get(BACKEND_URL + "/api/transactions", {
          headers: headers,
        });

        console.log(response.data);
        setTransactions(response.data);
      } catch (error) {
        console.error(error.response.data);
      }
    };

    const fetchAccounts = async () => {
      try {
        const headers = {
          Authorization: token,
        };

        const response = await axios.get(BACKEND_URL + "/api/accounts", {
          headers: headers,
        });

        console.log(response.data);
        setAccounts(response.data);
      } catch (error) {
        console.error(error.response.data);
      }
    };

    fetchTransactions();
    fetchAccounts();
  }, []);

  const updateTransactions = async (event) => {
    const iban = event.target.value;
    setSelectedAccount(iban);

    try {
      const headers = {
        Authorization: token,
      };

      const response = await axios.get(
        BACKEND_URL + `/api/transactions${iban ? `?iban=${iban}` : ""}`,
        { headers: headers }
      );
      console.log(response.data);
      setTransactions(response.data);
    } catch (error) {
      console.error(error.response.data);
    }
  };

  return (
    <Container>
      <Row>
        <Col>
          <MyNavbar />
        </Col>
      </Row>

      <Row>
        <Col>
          <h2
            className="text-center"
            style={{ color: "#89CFF0", fontSize: "24px" }}
          >
            Home Page
          </h2>
        </Col>
      </Row>

      <hr />

      <Row className="mb-3">
        <Col md={6}>
          <h3>My Transactions</h3>
          <div className="form-group">
            <label htmlFor="ibanSource">IBAN Source</label>
            <select
              className="form-select"
              id="ibanSource"
              onChange={(e) => updateTransactions(e)}
              value={selectedAccount}
            >
              <option value="">All Accounts</option>
              {accounts.map((account) => (
                <option key={account.id} value={account.iban}>
                  {account.iban}
                </option>
              ))}
            </select>
          </div>
        </Col>
      </Row>

      <Row>
        <Col>
          <table className="table table-striped">
            <thead>
              <tr>
                <th scope="col">IBAN Source</th>
                <th scope="col">IBAN Dest</th>
                <th scope="col">Amount</th>
                <th scope="col">Transaction Type</th>
                <th scope="col">Date</th>
              </tr>
            </thead>
            <tbody>
              {transactions.map((transaction) => (
                <tr
                  key={transaction.id}
                  className={
                    transaction.transactionType === "INCOME"
                      ? "table-success"
                      : "table-danger"
                  }
                >
                  <td>{transaction.sourceAccount}</td>
                  <td>{transaction.destAccount}</td>
                  <td>{transaction.sum}</td>
                  <td>{transaction.transactionType}</td>
                  <td>{transaction.createdAt}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </Col>
      </Row>
    </Container>
  );
}

export default HomePage;
