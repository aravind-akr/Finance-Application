import {GET_EXPENSES, GET_EXPENSE, DELETE_EXPENSE} from "../actions/types";

const initialState = {
    expenses:[],
    expense:{}
};

// eslint-disable-next-line import/no-anonymous-default-export
export default function(state = initialState, action){
    switch (action.type){
        case GET_EXPENSES:
            return{
                ...state,
                expenses:action.payload
            };
        case GET_EXPENSE:
            return{
                ...state,
                expense:action.payload
            };
        case DELETE_EXPENSE:
            return{
                ...state,
                expenses:state.payload.filter(
                    (expense) => expense.expenseId !== action.payload
                ),
            };
        default:
            return state;
    }
}