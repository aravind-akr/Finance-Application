import React, { useState, useEffect } from "react";
import classnames from "classnames";
import "./Expense.css";

function AddAnExpense() {
  const [categoryList, setCategoryList] = useState([]);
  const [subCategoryList, setSubCategoryList] = useState([]);
  const [catSubCategoryMap, setcatSubCategoryMap] = useState();
  const [selectedCategoryName, setSelectedCategoryName] = useState([]);

  useEffect(() => {
    fetch("http://localhost:8080/finance/category/subCategory")
      .then((response) => response.json())
      .then((catSubCategoryData) => {
        setcatSubCategoryMap(new Map(Object.entries(catSubCategoryData)));
      })
      .catch((error) => {
        console.log(error);
      });
  }, []);

  useEffect(() => {
    fetch("http://localhost:8080/finance/category/all")
      .then((response) => response.json())
      .then((categoryList) => {
        setCategoryList(categoryList);
      })
      .catch((error) => {
        console.log(error);
      });
  }, []);

  const onCategoryChange = (obj) => {
    setSelectedCategoryName(obj.target.value);
    getSubCategories();
  };

  function getSubCategories() {
    setSubCategoryList(catSubCategoryMap.get(selectedCategoryName));
  }

  return (
    <div className="register">
      <div className="container">
        <div className="row justify-content-center">
          <div className="col-md-9 m-auto">
            <form>
              <div className="form-group col-lg-7 mx-auto">
                <select
                  className={classnames("form-control form-control-lg")}
                  onChange={onCategoryChange}
                >
                  <option value="" disabled selected>
                    Select the Category
                  </option>
                  {categoryList &&
                    categoryList.length > 0 &&
                    categoryList.map((category) => {
                      return (
                        <option key={category.id} value={category.categoryName}>
                          {category.categoryName}
                        </option>
                      );
                    })}
                </select>
              </div>
              <div className="form-group col-lg-7 mx-auto">
                <select className={classnames("form-control form-control-lg")}>
                  <option value="" disabled selected>
                    Select the Sub Category
                  </option>
                  {subCategoryList &&
                    subCategoryList.length > 0 &&
                    subCategoryList.map((subCategory) => {
                      return (
                        <option key={subCategory.id} value={subCategory}>
                          {subCategory}
                        </option>
                      );
                    })}
                </select>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
}

export default AddAnExpense;
