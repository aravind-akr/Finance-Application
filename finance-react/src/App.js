import React, { Component } from "react";
import "./App.css";
import Header from "./layout/Header";
import "bootstrap/dist/css/bootstrap.min.css";
import { BrowserRouter as Router, Route } from "react-router-dom";
import ExpenseDashboard from "./pages/Expenses/ExpenseDashboard";
import { Provider } from "react-redux";
import store from "./store";
import AddExpense from "./pages/Expenses/AddExpense";

class App extends Component {
  render(){
    return (
      <Provider store={store}>
        <Router>
          <div className="App">
            <Header />
            <Route exact path="/expenses-dashboard" component={ExpenseDashboard}/>
            <Route exact path="/addExpense" component={AddExpense}/>
          </div>
        </Router>
      </Provider>
    );
  }
}

export default App;
