import React, { useState, useEffect } from "react";
import Select from "react-select";
import classnames from "classnames";
import "./Expense.css";

function AddAnExpense(){

    const [categoryList, setCategoryList] = useState([]);
    const [subCategory, setSubCategory] = useState([]);

    useEffect(()=> {
      fetch('http://localhost:8080/finance/category/all')
      .then((response)=>response.json())
      .then((categoryList)=>{
        setCategoryList(categoryList);
        console.log(categoryList);
      })
      .catch((error)=>{
        console.log(error);
      })
    },[]);

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
              <form>
              <div className="form-group col-lg-7 mx-auto">
                <select
                  className={classnames("form-control form-control-lg")}
                  placeholder="Select the category"
                  
                >
                  {categoryList.map((category) => {
                    console.log(category.categoryName)
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
              </div>
                <div className="form-group col-lg-7 mx-auto">
                <Select
                  placeholder="Select the Sub category"
                  
                />
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
    )

}

export default AddAnExpense;