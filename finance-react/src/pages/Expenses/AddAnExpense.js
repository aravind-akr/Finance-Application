import React, { useState, useEffect } from "react";
import Select from "react-select";
import classnames from "classnames";
import "./Expense.css";

function AddAnExpense() {
  const [categoryList, setCategoryList] = useState([]);
  const [subCat, setSubCat] = useState(null);
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
        console.log(categoryList);
      })
      .catch((error) => {
        console.log(error);
      });
  }, []);

  const onCategoryChange = (obj) => {
    const categoryId = obj.target.value;
    setSelectedCategoryName(obj.target.value);
    console.log(categoryId);
    getSubCategories();
  };

  function getSubCategories() {
    console.log(selectedCategoryName);
    console.log(catSubCategoryMap);
    setSubCategoryList(catSubCategoryMap.get(selectedCategoryName));
    console.log(subCategoryList);
  }

  return (
    <div className="register">
      <div className="container">
        <div className="row justify-content-center">
          <div className="col-md-9 m-auto">
            <h6 className="display-4 text-center">Create a new Expense Item</h6>
            <hr className="col-lg-10 center-it" />
            <br />
            <form>
              <div className="form-group col-lg-7 mx-auto">
                <select
                  className={classnames("form-control form-control-lg")}
                  onChange={onCategoryChange}
                >
                  <option value="" disabled selected>
                    Select the Category
                  </option>
                  {categoryList && categoryList.length>0 &&
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
                  {/* {console.log(catSubCategoryMap.get(selectedCategoryName))}
                  {console.log("selectedCategoryName:" + typeof(selectedCategoryName))} */}

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

              <input
                type="submit"
                className="btn btn-primary btn-block mt-4 col-lg-7"
              />
            </form>
          </div>
        </div>
      </div>
    </div>
  );
}

export default AddAnExpense;
