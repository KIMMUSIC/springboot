import React from "react";
import "./HeaderStyle.css";

function NavBar({ children, ...restProps }) {
  return <nav {...restProps}>{children}</nav>;
}

export default NavBar;
