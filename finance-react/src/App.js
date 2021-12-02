import React, { Component } from "react";
import { BrowserRouter as Router, Route } from "react-router-dom";
import { Provider } from "react-redux";
import store from "./store";
import "bootstrap/dist/css/bootstrap.min.css";
import Header from "./layout/Header";
import ExpenseDashboard from "./pages/Expenses/ExpenseDashboard";
import AddExpense from "./pages/Expenses/AddExpense";
import UpdateExpense from "./pages/Expenses/UpdateExpense";
import "./App.css";

class App extends Component {
  render(){
    return (
      <Provider store={store}>
        <Router>
          <div className="App">
            <Header />
            <Route exact path="/expenses-dashboard" component={ExpenseDashboard}/>
            <Route exact path="/addExpense" component={AddExpense}/>
            <Route exact path="/updateExpense/:expenseId" component={UpdateExpense}/>
          </div>
        </Router>
      </Provider>
    );
  }
}

export default App;
