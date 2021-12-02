import React, { Component } from "react";
import { connect } from "react-redux";
import { Link } from "react-router-dom";
import PropTypes from "prop-types";
import { FaEdit, FaTrash } from "react-icons/fa";
import { deleteExpense } from "../../actions/expenseActions";
import "./Expense.css";

class ExpenseItem extends Component {
  onDeleteClick = (expenseId) => {
    this.props.deleteExpense(expenseId);
    window.location.reload(false);
  };

  render() {
    const { expense } = this.props;
    return (
      <div className="container">
        <div className="row expense-row">
          <div className="col-1">
            <span className="mx-auto">{expense.userId}</span>
          </div>
          <div className="col-2">
            <span className="mx-auto">{expense.expenseName}</span>
          </div>
          {/* <div className="col-2">
            <span className="mx-auto">{expense.category.categoryName}</span>
          </div> */}
          <div className="col-2">
            <span className="mx-auto">{expense.paymentDate}</span>
          </div>
          <div className="col-2">
            <span className="mx-auto">{expense.amount}</span>
          </div>
          <div className="col-1">
            <span className="mx-auto">{expense.paymentMode}</span>
          </div>
          <div className="col-1">
            <Link to={`/updateExpense/${expense.expenseId}`}>
              <FaEdit />
            </Link>
          </div>
          <div
            className="col-1"
            onClick={this.onDeleteClick.bind(this, expense.expenseId)}
          >
            <FaTrash />
          </div>
        </div>
      </div>
    );
  }
}

ExpenseItem.propTypes = {
  deleteExpense: PropTypes.func.isRequired,
};

export default connect(null, { deleteExpense })(ExpenseItem);
