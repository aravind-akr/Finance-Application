import React, { Component } from "react";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { FaRupeeSign } from "react-icons/fa";
import classnames from "classnames";
import { getExpense, createExpense } from "../../actions/expenseActions";
import "./Expense.css";

class UpdateExpense extends Component {
  constructor() {
    super();
    this.state = {
      expenseId: "",
      userId: "",
      expenseName: "",
      category: "",
      subCategory: "",
      paymentDate: "",
      paymentMode: "",
      amount: "",
      errors: {},
    };
    this.onChange = this.onChange.bind(this);
    this.onSubmit = this.onSubmit.bind(this);
  }

  componentWillReceiveProps(nextProps) {
    if (nextProps.errors) {
      this.setState({ errors: nextProps.errors });
    }
    const {
      expenseId,
      userId,
      expenseName,
      category,
      subCategory,
      paymentDate,
      paymentMode,
      amount,
    } = nextProps.expense;

    this.setState({
      expenseId,
      userId,
      expenseName,
      category,
      subCategory,
      paymentDate,
      paymentMode,
      amount,
    });
  }

  componentDidMount() {
    const { expenseId } = this.props.match.params;
    this.props.getExpense(expenseId, this.props.history);
  }

  onChange(e) {
    this.setState({
      [e.target.name]: e.target.value,
    });
  }

  onSubmit(e) {
    e.preventDefault();
    const updateExpense = {
      expenseId: this.state.expenseId,
      userId: this.state.userId,
      expenseName: this.state.expenseName,
      category: this.state.category,
      subCategory: this.state.subCategory,
      paymentDate: this.state.paymentDate,
      paymentMode: this.state.paymentMode,
      amount: this.state.amount,
    };
    this.props.createExpense(updateExpense, this.props.history);
  }

  render() {
    const { errors } = this.state;
    return (
      <div className="register">
        <div className="container">
          <div className="row justify-content-center">
            <div className="col-md-9 m-auto">
              <h6 className="display-4 text-center">
                Create a new Expense Item
              </h6>
              <hr className="col-lg-10 center-it" />
              <br />
              <form onSubmit={this.onSubmit}>
                <div className="form-group col-lg-7 mx-auto">
                  <input
                    type="text"
                    placeholder="User ID"
                    name="userId"
                    onChange={this.onChange}
                    value={this.state.userId}
                    className={classnames("form-control form-control-lg", {
                      "is-invalid": errors.userId,
                    })}
                  />
                  {errors.userId && (
                    <div className="invalid-feedback text-justify">
                      {errors.userId}
                    </div>
                  )}
                </div>
                <div className="form-group col-lg-7 mx-auto">
                  <input
                    type="text"
                    placeholder="Expense Name"
                    name="expenseName"
                    className={classnames("form-control form-control-lg", {
                      "is-invalid": errors.expenseName,
                    })}
                    onChange={this.onChange}
                    value={this.state.expenseName}
                  />
                  {errors.expenseName && (
                    <div className="invalid-feedback text-justify">
                      {errors.expenseName}
                    </div>
                  )}
                </div>
                <div className="form-group col-lg-7 mx-auto">
                  <input
                    type="text"
                    placeholder="Enter the category"
                    name="category"
                    className={classnames("form-control form-control-lg", {
                      "is-invalid": errors.category,
                    })}
                    onChange={this.onChange}
                    value={this.state.category}
                  />
                  {errors.category && (
                    <div className="invalid-feedback text-justify">
                      {errors.category}
                    </div>
                  )}
                </div>
                <div className="form-group col-lg-7 mx-auto">
                  <input
                    type="text"
                    placeholder="Enter the sub category"
                    name="subCategory"
                    className={classnames("form-control form-control-lg", {
                      "is-invalid": errors.subCategory,
                    })}
                    onChange={this.onChange}
                    value={this.state.subCategory}
                  />
                  {errors.subCategory && (
                    <div className="invalid-feedback text-justify">
                      {errors.subCategory}
                    </div>
                  )}
                </div>
                <div className="form-group col-lg-7 mx-auto">
                  <h5 className="text-start fw-bold font-sans-serif text-primary">
                    Enter the payment date
                  </h5>
                  <input
                    type="date"
                    placeholder="paymentDate"
                    name="paymentDate"
                    className={classnames("form-control form-control-lg", {
                      "is-invalid": errors.paymentDate,
                    })}
                    onChange={this.onChange}
                    value={this.state.paymentDate}
                  />
                  {errors.paymentDate && (
                    <div className="invalid-feedback text-justify">
                      {errors.paymentDate}
                    </div>
                  )}
                </div>
                <div className="form-group col-lg-7 mx-auto">
                  <input
                    type="text"
                    placeholder="Payment Mode"
                    name="paymentMode"
                    className={classnames("form-control form-control-lg", {
                      "is-invalid": errors.paymentMode,
                    })}
                    onChange={this.onChange}
                    value={this.state.paymentMode}
                  />
                  {errors.paymentMode && (
                    <div className="invalid-feedback text-justify">
                      {errors.paymentMode}
                    </div>
                  )}
                </div>
                <div className="form-group col-lg-7 mx-auto">
                  <div className="input-group">
                    <div className="input-group-prepend">
                      <span className="input-group-text  custom-currency">
                        <FaRupeeSign />
                      </span>
                    </div>
                    <input
                      type="text"
                      name="amount"
                      placeholder="Amount Paid"
                      className={classnames(
                        "form-control form-control-lg custom-amount",
                        {
                          "is-invalid": errors.amount,
                        }
                      )}
                      onChange={this.onChange}
                      value={this.state.amount}
                    />
                    {errors.amount && (
                      <div className="invalid-feedback text-justify">
                        {errors.amount}
                      </div>
                    )}
                  </div>
                </div>

                <input
                  type="submit"
                  className="btn btn-primary btn-block mt-4 col-lg-7"
                />
              </form>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

UpdateExpense.propTypes = {
  getExpense: PropTypes.func.isRequired,
  createExpense: PropTypes.func.isRequired,
  expense: PropTypes.object.isRequired,
  errors: PropTypes.string.isRequired,
};

const mapStateToProps = (state) => ({
  expense: state.expense.expense,
  errors: state.errors,
});

export default connect(mapStateToProps, { getExpense, createExpense })(
  UpdateExpense
);
