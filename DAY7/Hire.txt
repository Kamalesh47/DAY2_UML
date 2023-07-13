import "./hire.css";
import {Link} from "react-router-dom";

// import { Link } from "react-router-dom";
const Hire = () => {

    return (
        
      <div className="navbar">
        <div className="css1">
           <label>
            <p><b>What work do you require?</b></p>
       
        
        <select>
          <option value="select a category of work">select a category of work</option>
          <option value="Websites IT & Software">Websites IT & Software</option>
          <option value="Mobile">Mobile</option>
          <option value="Writing">Writing</option>
          <option value="Design">Design</option>
          <option value="Data Entry">Data Entry</option>
          <option value="Product Sourcing & Manufacturing">Product Sourcing & Manufacturing</option>
          <option value="Sales & Marketing">Sales & Marketing</option>
        </select>

        <p><b>Countries</b></p>
        
        
        <select>
          <option value="Search Countries">select a country of work</option>
          <option value="America">America</option>
          <option value="Bulgaria">Bulgaria</option>
          <option value="Costaricca">Costarica</option>
          <option value="Dubai">Dubai</option>
          <option value="England">England</option>
          <option value="France">France</option>
          
        </select>

        <p><b>Hourly Rate</b></p>
        
        
        <select>
          <option value="Hourly Rate">select rate</option>
          <option value="less than rs 1000 per hour"> less than rs 1000 per hour</option>
          <option value="less than rs 1500 per hour">less than rs 1500 per hour</option>
          <option value="less than rs 2000 per hour">less than rs 2000 per hour</option>
          <option value="less than rs 2500 per hour">less than rs 2500 per hour</option>
          <option value="less than rs 3000 per hour">less than rs 3000 per hour</option>
          <option value="less than rs 3500 per hour">less than rs 3500 per hour</option>
          
        </select>
      </label>
      </div>
      </div>
    )
}
export default Hire;