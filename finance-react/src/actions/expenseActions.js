import axios from "axios";
import { DELETE_EXPENSE, GET_ERRORS, GET_EXPENSE } from "./types";

export const createExpense = (expense, history) => async (dispatch) => {
  try {
    const res = await axios.post(
      "http://localhost:8080/finance/expenses/add",
      expense
    );
    history.push("/expenses-dashboard");
    dispatch({
      type: GET_ERRORS,
      payload: {},
    });
  } catch (err) {
    dispatch({
      type: GET_ERRORS,
      payload: err.response.data,
    });
  }
};

export const getExpense = (expenseId, history) => async (dispatch) => {
  try {
    const res = await axios.get(
      `http://localhost:8080/finance/expenses/get/${expenseId}`
    );
    dispatch({
      type: GET_EXPENSE,
      payload: res.data,
    });
  } catch (err) {
    history.push("/expenses-dashboard");
  }
};

export const deleteExpense = (expenseId, history) => async (dispatch) => {
  if (
    window.confirm(
      "Are you sure you want to delete the expense and all its related data?"
    )
  ) {
    await axios.delete(
      `http://localhost:8080/finance/expenses/deleteEx/${expenseId}`
    );
    dispatch({
      type: DELETE_EXPENSE,
      payload: expenseId,
    });
  }
};
