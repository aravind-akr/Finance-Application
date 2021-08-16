import React, { Component } from "react";
import './Expense.css';

class ExpenseTableOutline extends Component {
  render() {
    return (
      <div className="container">
        <div className="row row-head">
          <div className="col-1 row-head-border">
            <span className="mx-auto ">User</span>
          </div>
          <div className="col-2 row-head-border">
            <span className="mx-auto">Item Name</span>
          </div>
          <div className="col-2 row-head-border">
            <span className="mx-auto">category</span>
          </div>
          <div className="col-2 row-head-border">
            <span className="mx-auto">Date</span>
          </div>
          <div className="col-2 row-head-border">
            <span className="mx-auto">Price</span>
          </div>
          <div className="col-1 row-head-border">
            <span className="mx-auto">Mode</span>
          </div>
          <div className="col-1 row-head-border">
          <span className="mx-auto">Edit</span>
          </div>
          <div className="col-1 row-head-border">
          <span className="mx-auto">Delete</span>
          </div>
        </div>
      </div>
    );
  }
}

export default ExpenseTableOutline;
