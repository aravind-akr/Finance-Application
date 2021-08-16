import React, { useState , useEffect } from 'react';
import {connect} from "react-redux";
import PropTypes from "prop-types";
import CreateExpenseButton from './CreateExpenseButton';
import ExpenseItem from './ExpenseItem';
import ExpenseTableOutline from './ExpenseTableOutline';
import './Expense.css';

function ExpenseDashboard(){
    const [expense, setExpense] = useState([]);
    useEffect(() => {
        fetch('http://localhost:8080/finance/expenses/user-expenses/132')
        .then((res) => res.json())
        .then((expense) => {
            setExpense(expense)
        })
        .catch((err) => {
            console.log(err);
        });
    },[]);
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
                    <ExpenseTableOutline />
                    {expense.map((item) => (
                        <ExpenseItem key={item.id} expense={item}/>
                    ))}                                       
                </div>
            </div>
        </div>
    );    
}

ExpenseDashboard.propTypes = {
    expense: PropTypes.object.isRequired,
    //getExpenses: PropTypes.func.isRequired
}

const mapStateToProps = (state) => ({
    expense: state.expense,
});

export default connect(mapStateToProps)(ExpenseDashboard);