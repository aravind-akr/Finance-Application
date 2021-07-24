import axios from "axios";
import { GET_ERRORS } from "./types";

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