import { Container, Row, Col, Card, Button, ListGroup } from "react-bootstrap";
import MyNavbar from "../components/MyNavbar";
import { useEffect, useState } from "react";
import axios from "axios";
import { BACKEND_URL } from "../configuration/BackendConfig";
import MyFooter from "../components/MyFooter";

function HomePage() {
  const token = localStorage.getItem("token");

  if (!token) {
    window.location.href = "/login";
  }

  const [accounts, setAccounts] = useState([]);
  const [transactions, setTransactions] = useState([]);
  const [exchangeResults, setExchangeResults] = useState([]);
  const [news, setNews] = useState([]);

  useEffect(() => {
    getAccounts();
    getTransactions();
    getExchangeResults();
    getNews();
  }, []);

  const getAccounts = () => {
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
        alert("Error retrieving accounts!");
        console.error(error.response.data);
      });
  };

  const getTransactions = () => {
    const headers = {
      Authorization: token,
    };

    axios
      .get(BACKEND_URL + "/api/transactions", { headers: headers })
      .then((response) => {
        console.log(response.data);
        setTransactions(response.data);
      })
      .catch((error) => {
        alert("Error retrieving transactions!");
        console.error(error.response.data);
      });
  };

  const getExchangeResults = () => {
    const headers = {
      Authorization: token,
    };

    axios
      .get(BACKEND_URL + "/api/exchange", { headers: headers })
      .then((response) => {
        console.log(response.data);
        setExchangeResults(response.data);
      })
      .catch((error) => {
        alert("Error retrieving exchange results!");
        console.error(error.response.data);
      });
  };

  const getNews = () => {
    const headers = {
      Authorization: token,
    };

    axios
      .get(BACKEND_URL + "/api/news", { headers: headers })
      .then((response) => {
        console.log(response.data);
        setNews(response.data);
      })
      .catch((error) => {
        alert("Error retrieving news!");
        console.error(error.response.data);
      });
  };

  return (
    <>
      <MyNavbar />
      <Container>
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
        <Row className="mt-4">
          <Col>
            <Card>
              <Card.Header>Recent Transactions</Card.Header>
              <Card.Body>
                {transactions.length === 0 ? (
                  <p>No recent transactions found.</p>
                ) : (
                  transactions.slice(0, 5).map((transaction, index) => (
                    <Card
                      key={index}
                      className={
                        transaction.transactionType === "INCOME"
                          ? "bg-success text-white mb-3"
                          : "bg-danger text-white mb-3"
                      }
                      style={{ height: "5rem" }}
                    >
                      <Card.Body>
                        <Card.Title>Amount: {transaction.sum}</Card.Title>
                        <Card.Text>Date: {transaction.createdAt}</Card.Text>
                      </Card.Body>
                    </Card>
                  ))
                )}
              </Card.Body>
              <Card.Footer>
                <Button variant="primary" href="/transactions">
                  View All Transactions
                </Button>
              </Card.Footer>
            </Card>

            <Card className="mt-4">
              <Card.Header>Exchange Values</Card.Header>
              <Card.Body>
                {exchangeResults.length === 0 ? (
                  <p>No exchange results found.</p>
                ) : (
                  <table className="table table-striped">
                    <thead>
                      <tr>
                        <th>From</th>
                        <th>To</th>
                        <th>Amount</th>
                        <th>Result</th>
                      </tr>
                    </thead>
                    <tbody>
                      {exchangeResults.map((result, index) => (
                        <tr key={index}>
                          <td>{result.from}</td>
                          <td>{result.to}</td>
                          <td>{result.amount}</td>
                          <td>{result.result}</td>
                        </tr>
                      ))}
                    </tbody>
                  </table>
                )}
              </Card.Body>
            </Card>
          </Col>
          <Col>
            <Card>
              <Card.Header>Accounts</Card.Header>
              <Card.Body>
                {accounts.length === 0 ? (
                  <p>No accounts found.</p>
                ) : (
                  accounts.map((account, index) => (
                    <Card key={index} className="mb-3">
                      <Card.Body>
                        <Card.Title>{account.iban}</Card.Title>
                        <Card.Subtitle className="mb-2 text-muted">
                          {account.currency}
                        </Card.Subtitle>
                        <Card.Text>{account.type}</Card.Text>
                        <Card.Text>Balance: {account.balance}</Card.Text>
                      </Card.Body>
                    </Card>
                  ))
                )}
              </Card.Body>
              <Card.Footer>
                <Button variant="primary" href="/accounts">
                  View All Accounts
                </Button>
              </Card.Footer>
            </Card>

            <Card className="mt-4">
              <Card.Header>News</Card.Header>
              <Card.Body>
                {news.length === 0 ? (
                  <p>No news found.</p>
                ) : (
                  <ListGroup variant="flush">
                    {news.map((item, index) => (
                      <ListGroup.Item key={index}>
                        <h5 className="card-title">{item.title}</h5>
                        <p className="card-text">{item.description}</p>
                      </ListGroup.Item>
                    ))}
                  </ListGroup>
                )}
              </Card.Body>
            </Card>
          </Col>
        </Row>
      </Container>
      <MyFooter />
    </>
  );
}

export default HomePage;
