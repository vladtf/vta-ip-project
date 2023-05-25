import { Container, Row } from "react-bootstrap";
import MyNavbar from "../components/Navbar";
import { useEffect, useState } from "react";
import axios from "axios";

function HomePage() {
  const [transactions, setTransactions] = useState([]);
  const [accounts, setAccounts] = useState([]);
  const [ibanSource, setIbanSource] = useState("");

  const token =
    localStorage.getItem("token") && JSON.parse(localStorage.getItem("token"));

  if (!token || !token.token) {
    window.location.href = "/login";
  }

  useEffect(() => {
    const fetchTransactions = async () => {
      try {
        const headers = {
          Authorization: token.token,
        };

        const response = await axios.get(
          BACKEND_URL + "/api/transactions",
          { headers: headers }
        );
        console.log(response.data);
        setTransactions(response.data);
      } catch (error) {
        console.error(error.response.data);
      }
    };

    const fetchAccounts = async () => {
      try {
        const headers = {
          Authorization: token.token,
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
    setIbanSource(iban);
    try {
      const headers = {
        Authorization: token.token,
      };

      const response = await axios.get(
        BACKEND_URL + `/api/transactions?iban=${iban}`,
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
        <MyNavbar />
      </Row>

      <Row>
        <h2 style={{ color: "#89CFF0", fontSize: "24px", textAlign: "center" }}>
          Home Page
        </h2>
      </Row>

      <hr />
      <Row>
        <h3>My Transactions</h3>
        <div className="form-group">
          <label htmlFor="ibanSource">IBAN Source</label>
          <select
            className="form-control"
            id="ibanSource"
            onChange={(e) => updateTransactions(e)}
          >
            <option value="">Select an account</option>
            {accounts.map((account) => (
              <option key={account.id} value={account.iban}>
                {account.iban}
              </option>
            ))}
          </select>
        </div>

        <table className="table table-striped">
          <thead>
            <tr>
              <th scope="col">IBAN Source</th>
              <th scope="col">IBAN Dest</th>
              <th scope="col">Amount</th>
              <th scope="col">Date</th>
            </tr>
          </thead>
          <tbody>
            {transactions.map((transaction) => (
              <tr key={transaction.id}>
                <td>{ibanSource}</td>
                <td>{transaction.destAccount}</td>
                <td>{transaction.sum}</td>
                <td>{transaction.createdAt}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </Row>
    </Container>
  );
}

export default HomePage;
