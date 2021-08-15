import React, { Component } from "react";
import './Expense.css';
import {connect} from "react-redux";
import { Link } from "react-router-dom";

class ExpenseItem extends Component {
  render() {
    const { expense } = this.props;

    return (
      
      <div className="container">
        <div className="row expense-row">
            <div className="col-md-8">
                <span className="mx-auto">{expense.expenseName}</span>
            </div>
              <div className="col-md-2">
              <Link to={`/updateExpense/${expense.expenseId}`}>
                  <span className="mx-auto">Edit</span> 
                  </Link>
              </div>
            <div className="col-md-2">
                <span className="mx-auto">Delete</span>
            </div>
        </div>
      </div>
    );
  }
}

export default connect(null)(ExpenseItem);
