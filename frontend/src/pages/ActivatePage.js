import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { BACKEND_URL } from "../configuration/BackendConfig";
import axios from "axios";

function ActivatePage() {
  const navigate = useNavigate();
  const { token } = useParams();
  const [emailSent, setEmailSent] = useState(false);

  useEffect(() => {
    // Send request to backend with the token
    sendActivationRequest(token);
  }, [token]);

  const sendActivationRequest = async (token) => {
    try {
      // Send the activation request to the backend using Axios
      console.log("Sending activation request with token:", token);
      const response = await axios.get(
        BACKEND_URL + `/api/activate?token=${token}`
      );

      if (response.status === 200) {
        const { token, email } = response.data;

        // Store the token and email in local storage
        localStorage.setItem("token", token);
        localStorage.setItem("email", email);

        // Email sent successfully
        setEmailSent(true);
      } else {
        // Handle error case
        console.log("Activation request failed.");
      }
    } catch (error) {
      // Handle error case
      console.log("Error sending activation request:", error);
      alert("Error sending activation request.");

      // Redirect to login page
      navigate("/login");
    }
  };

  useEffect(() => {
    if (emailSent) {
      // Redirect to the home page if email sent successfully
      navigate("/home");
    }
  }, [emailSent, navigate]);

  return (
    <div>
      {emailSent ? (
        <h1>Activation Email Sent</h1>
      ) : (
        <h1>Processing Activation...</h1>
      )}
    </div>
  );
}

export default ActivatePage;
