import { Container, Row } from "react-bootstrap";
import MyNavbar from "../components/Navbar";

function HomePage() {
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
    </Container>
  );
}

export default HomePage;
