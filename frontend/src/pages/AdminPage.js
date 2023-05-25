import { Container, Row } from "react-bootstrap";
import MyNavbar from "../components/Navbar";
import { useEffect, useState } from "react";
import axios from "axios";

function AdminPage() {
  const [roles, setRoles] = useState([]);

  const [email, setEmail] = useState("");
  const [role, setRole] = useState("");

  const [emails, setEmails] = useState([]);

  const token =
    localStorage.getItem("token") && JSON.parse(localStorage.getItem("token"));

  if (!token || !token.token) {
    window.location.href = "/login";
  }

  const getMyRoles = () => {
    const headers = {
      Authorization: token.token,
    };

    axios
      .get(BACKEND_URL + "/roles/my-roles", { headers: headers })
      .then((response) => {
        console.log(response.data);
        setRoles(response.data);
      })
      .catch((error) => {
        console.error(error.response.data);
      });
  };

  const addRoleToUser = () => {
    const headers = {
      Authorization: token.token,
    };

    const request = {
      email: email,
      action: role,
    };

    axios
      .post(BACKEND_URL + "/roles/add-role", request, { headers })
      .then((response) => {
        console.log(response.data);
        alert("Role added successfully!");
      })
      .catch((error) => {
        console.error(error.response.data);
      });
  };

  useEffect(() => {
    const fetchEmails = async () => {
      try {
        const headers = {
          Authorization: token.token,
        };
        // Make the API request using Axios
        const response = await axios.get(BACKEND_URL + "/api/emails", {
          headers,
        });
        const responseData = response.data;

        // Save the data
        setEmails(responseData);
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    };

    const fetchMyRoles = async () => {
      try {
        const headers = {
          Authorization: token.token,
        };
        // Make the API request using Axios
        const response = await axios.get(
          BACKEND_URL + "/roles/my-roles",
          {
            headers,
          }
        );
        const responseData = response.data;

        // Save the data
        setRoles(responseData);
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    };

    fetchEmails();
    fetchMyRoles();
  }, []);

  return (
    <Container>
      <Row>
        <MyNavbar />
      </Row>
      <Row>
        <h2 style={{ color: "#89CFF0", fontSize: "24px", textAlign: "center" }}>
          Admin Page
        </h2>
      </Row>
      <hr />
      <Row>
        <h3>My Roles</h3>
        <div className="form-group">
          <button
            className="btn btn-primary btn-block"
            onClick={() => getMyRoles()}
          >
            Refresh My Roles
          </button>
        </div>
        <div className="form-group">
          <label>Roles:</label>
          <ul>
            {roles.map((role, index) => (
              <li key={index}>{role}</li>
            ))}
          </ul>
        </div>
      </Row>
      <hr />
      <Row>
        <h3>Add role to user</h3>
        <div className="form-group">
          <label>Email:</label>
          <select
            className="form-select"
            onChange={(e) => setEmail(e.target.value)}
          >
            <option value="">Select an email</option>
            {emails.map((email) => (
              <option key={email} value={email}>
                {email}
              </option>
            ))}
          </select>
        </div>
        <div className="form-group">
          <label>Role:</label>
          <select
            className="form-select"
            onChange={(e) => setRole(e.target.value)}
          >
            <option value="">Select a role</option>
            <option value="DISPLAY_USERS">DISPLAY_USERS</option>
            <option value="UPDATE_USERS">UPDATE_USERS</option>
            <option value="DISPLAY_ACCOUNTS">DISPLAY_ACCOUNTS</option>
          </select>
        </div>

        <div className="form-group mt-2">
          <button
            className="btn btn-success btn-block"
            onClick={() => addRoleToUser()}
          >
            Add Role
          </button>
        </div>
      </Row>
    </Container>
  );
}

export default AdminPage;
