import React from 'react';
import { Link } from 'react-router-dom';

const createExpenseButton = () => {
    return(
        <React.Fragment>
            <Link to="/addExpense" className="btn btn-lg btn-info">
                Create an Expense
            </Link>
        </React.Fragment>
    )
}

export default createExpenseButton;