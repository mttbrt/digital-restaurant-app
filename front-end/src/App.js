import './App.css';
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import ListRestaurantComponent from './components/ListRestaurantComponent';
import HeaderComponent from './components/HeaderComponent';
import FooterComponent from './components/FooterComponent';

function App() {
  return (
    <div>
      <Router>
        <div className='container'>
          <HeaderComponent/>
          <div className="container">
            <Routes>
              <Route path='/' element={<ListRestaurantComponent/>}></Route>
              <Route path='/restaurants' element={<ListRestaurantComponent/>}></Route>
            </Routes>
          </div>
          <FooterComponent/>
        </div>
      </Router>
    </div>
  );
}

export default App;
