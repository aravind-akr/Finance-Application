import React, { Component } from "react";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import classnames from "classnames";
import { FaRupeeSign } from "react-icons/fa";
import { createExpense } from "../../actions/expenseActions";
import { getSubCategories } from "../../actions/categoryActions";
import axios from "axios";
import "./Expense.css";

class AddExpense extends Component {
  constructor(props) {
    super(props);
    this.state = {
      userId: "",
      expenseName: "",
      categories: [],
      category_id: "",
      subCategories: [],
      paymentDate: "",
      paymentMode: "",
      amount: "",
      errors: {},
    };
    this.onChange = this.onChange.bind(this);
    this.onCategoryChange = this.onCategoryChange.bind(this);
    this.onSubmit = this.onSubmit.bind(this);
  }

  // eslint-disable-next-line no-dupe-class-members
  async componentDidMount() {
    axios
      .get("http://localhost:8080/finance/category/all")
      .then((response) => {
        console.log(response.data);
        this.setState({ categories: response.data });
      })
      .catch((err) => console.log(err));
   // this.props.getSubCategories(this.state.category_id);
  }

  // // eslint-disable-next-line no-dupe-class-members
  // async componentDidMount() {
  //   axios
  //     .get("http://localhost:8080/finance/subCategory/all")
  //     .then((response) => {
  //       console.log(response.data);
  //       this.setState({ subCategories: response.data });
  //     })
  //     .catch((err) => console.log(err));
  // }

  // // eslint-disable-next-line no-dupe-class-members
  // async componentDidMount(){
  //   Promise.all([
  //     fetch("http://localhost:8080/finance/category/all").then(response => response.json()),
  //     fetch("http://localhost:8080/finance/category/subCategory/"+this.state.category_id).then(response => response.json())
  //   ]).then(([categories,subCategories]) => {
  //     this.setState({
  //       categories:categories,
  //       subCategories:subCategories
  //   })
  //   console.log(categories);
  //   console.log(subCategories);
  //     })};

  //   }
  //   // catch((err) => console.log(err))

  componentWillReceiveProps(nextProps) {
    if (nextProps.errors) {
      this.setState({
        errors: nextProps.errors,
      });
    }
  }

  onChange(e) {
    this.setState({
      [e.target.name]: e.target.value,
    });
  }

  onCategoryChange(e) {
    const index = e.target.selectedIndex;
    this.setState({
      category_id: e.target.selectedIndex,
    });
  }

  onSubmit(e) {
    e.preventDefault();
    const newExpense = {
      userId: this.state.userId,
      expenseName: this.state.expenseName,
      categories: this.state.categories,
      subCategories: this.state.subCategories,
      paymentDate: this.state.paymentDate,
      paymentMode: this.state.paymentMode,
      amount: this.state.amount,
    };
    this.props.createExpense(newExpense, this.props.history);
  }

  render() {
    const { errors } = this.state;
    const categoryList = this.state.categories;
    const subCategoryList = this.state.subCategories;
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
                  <select
                    name="category"
                    placeholder="Select the Category"
                    onChange={this.onCategoryChange}
                    className={classnames("form-control form-control-lg", {
                      "is-invalid": errors.category,
                    })}
                  >
                    <option value="" disabled selected>
                      Select the Category
                    </option>
                    {categoryList &&
                      categoryList.length > 0 &&
                      categoryList.map((category) => {
                        return (
                          <option
                            key={category.id}
                            value={category.categoryName}
                          >
                            {category.categoryName}
                          </option>
                        );
                      })}
                  </select>
                  {/* <input
                    type="text"
                    placeholder="Enter the category"
                    name="category"
                    className={classnames("form-control form-control-lg", {
                      "is-invalid": errors.category,
                    })}
                    onChange={this.onChange}
                    value={this.state.category.categoryName}
                  /> */}
                  {errors.category && (
                    <div className="invalid-feedback text-justify">
                      {errors.category}
                    </div>
                  )}
                </div>
                <div className="form-group col-lg-7 mx-auto">
                  <select
                    name="subCategory"
                    placeholder="Select the Sub Category"
                    onChange={this.onChange}
                    className={classnames("form-control form-control-lg", {
                      "is-invalid": errors.subCategory,
                    })}
                  >
                    <option value="" disabled selected>
                      Select the Sub Category
                    </option>
                    {subCategoryList &&
                      subCategoryList.length > 0 &&
                      subCategoryList.map((subCategory) => {
                        return (
                          <option
                            key={subCategory.id}
                            value={subCategory.subCategory}
                          >
                            {subCategory.subCategory}
                          </option>
                        );
                      })}
                  </select>
                  {/* <input
                    type="text"
                    placeholder="Select the sub category"
                    name="subCategory"
                    className={classnames("form-control form-control-lg", {
                      "is-invalid": errors.subCategory,
                    })}
                    onChange={this.onChange}
                    value={this.state.subCategory}
                  /> */}
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

AddExpense.propTypes = {
  createExpense: PropTypes.func.isRequired,
  getExpense: PropTypes.func.isRequired,
  getSubCategories: PropTypes.func.isRequired,
  errors: PropTypes.string.isRequired,
};

const mapStateToProps = (state) => ({
  errors: state.errors,
});

export default connect(mapStateToProps, { getSubCategories, createExpense })(
  AddExpense
);
