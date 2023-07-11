import React, { useState } from "react";

function Body() {
  const [login, setLogin] = useState(true);

  return (
    <>
      <div className="bodyChild">
        <div
          className={login && "bodyChildForm"}
          onClick={() => setLogin(true)}
        >
          Login
        </div>
        <div
          className={!login && "bodyChildForm"}
          onClick={() => setLogin(false)}
        >
          Signup
        </div>
      </div>
      <form>
        {!login && <input type="text" placeholder="Enter name" />}
        <input type="email" placeholder="Email Address" />
        <input type="password" placeholder="Password" />
        <button type="submit">{login ? "LOGIN" : "SIGNUP"}</button>
      </form>
      {login && <h4>Forgot Password?</h4>}
      {<h5>or {login ? "login" : "signup"} with</h5>}
    </>
  );
}

export default Body;
