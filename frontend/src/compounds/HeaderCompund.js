import React from "react";
import Logo from "../components/Header/Logo";
import SigninButton from "../components/Header/SigninButton";
import NavBar  from "../components/Header/NavBar";
import HeaderWrapper from "../components/Header/HeaderWrapper";
import FeatureWrapper from "../components/Header/FeatureWrapper";
import FeatureTitle from "../components/Header/FeatureTitle";
import Warning from "../components/Header/Warning";

function HeaderCompound({ children }) {
  return (
    <HeaderWrapper className="header-wrapper-home">
        <NavBar className="navbar-home">
        <Logo />
        <SigninButton>Sign in</SigninButton>
        </NavBar>
        <FeatureWrapper className="feature-wrapper-home">
        <FeatureTitle className="feature-title-home">
          Unlimited movies, TV shows and more.
        </FeatureTitle>
        <Warning>
          This is NOT official Netflix
        </Warning>
      </FeatureWrapper>
      {children}
    </HeaderWrapper>

  );
}

export default HeaderCompound;
