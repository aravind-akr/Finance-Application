import React, { Component } from 'react';
import CreateExpenseButton from './CreateExpenseButton';
import './Expense.css';
import {connect} from "react-redux";
import PropTypes from "prop-types";

class ExpenseDashboard extends Component{
    

    render(){
        const { expenses } = this.props.expense;
        return(
            <div className="expenses">
                <div className="container">
                    <div className="row custom-row">
                        <div className="col-md-5 expense-button">
                            <CreateExpenseButton/>
                        </div>
                        <div className="col-md-7 text-custom">
                            Expense DashBoard
                        </div>
                        <hr className="mt-4 mb-5 dotted"/>
                        {console.log(this.props.expense)}
                    </div>
                </div>
            </div>
        )
    }
}

ExpenseDashboard.propTypes = {
    expense: PropTypes.object.isRequired
}

const mapStateToProps = (state) => ({
    expense: state.expense,
});

export default connect(mapStateToProps)(ExpenseDashboard);