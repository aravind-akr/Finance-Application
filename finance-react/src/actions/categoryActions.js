import axios from 'axios';
import { GET_CATEGORIES }  from './types';

export const getCategories = () => async (dispatch) => {
    const res = await axios.get('http://localhost:8080/finance/category/all');
    dispatch({
        type:GET_CATEGORIES,
        payload:res.data
    });
};