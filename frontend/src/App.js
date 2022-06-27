import React from "react";
import logo from './logo.svg';
import './App.css';
import HomePage from './pages/HomePage';
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";
import AP from "./pages/AP";

function App() {
  return (
    <Router>
      <Switch>
       <Route exact path="/">
        <HomePage/>
        </Route>
          <Route exact path="/api/hello">
              <AP/>
          </Route>
      </Switch>
    </Router>

  );
}

export default App;
