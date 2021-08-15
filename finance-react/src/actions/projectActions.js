import axios from "axios";
import { GET_ERRORS, GET_EXPENSE, GET_EXPENSES } from "./types";

export const createExpense = (expense,history) => async (dispatch) => {
    try{
        const res = await axios.post("http://localhost:8080/finance/expenses/add",expense);
        history.push("/expenses-dashboard");
        dispatch({
            type:GET_ERRORS,
            payload:{},
        });
    }catch(err){
        dispatch({
            type:GET_ERRORS,
            payload:err.response.data,
        });
    }
};

export const getExpenses = () => async (dispatch) => {
    const res = await fetch("http://localhost:8080/finance/expenses/user-expenses/132");
    dispatch({
        type:GET_EXPENSES,
        payload:res.data
    });
};

export const getExpense = (expenseId, history) => async (dispatch) => {
    try{
        const res = await axios.get(`http://localhost:8080/finance/expenses/get/${expenseId}`);
        dispatch({
            type:GET_EXPENSE,
            payload:res.data
        });
    } catch(err){
        history.push("/expenses-dashboard");
    }
};
