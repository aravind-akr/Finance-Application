import './App.css';
import Header from './layout/Header';
import "bootstrap/dist/css/bootstrap.min.css";
import { BrowserRouter as Router, Route } from "react-router-dom";
import { Provider } from "react-redux";


function App() {
  return (
    
        <div className="App">
          <Header/>
        </div>
          
  );
}

export default App;
