import { combineReducers } from "redux";
import errorReducer from "./errorReducer";
import expenseReducer from "./expenseReducer";

export default combineReducers({
    errors:errorReducer,
    expense:expenseReducer
})