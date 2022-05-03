import React, { Component } from 'react'
import RestaurantService from '../services/RestaurantService'

export default class ListRestaurantComponent extends Component {
  constructor(props) {
    super(props)

    this.state = {
      restaurants: []
    }
  }
  
  componentDidMount() {
    RestaurantService.getRestaurants().then(res => {
      this.setState({ restaurants: res.data });
    });
  }

  render() {
    return (
      <div>
        <h2 className="text-center">Restaurants</h2>
        <div className="row">
          <table className="table table-striped table-bordered">
            
            <thead>
              <tr>
                <th>Name</th>
                <th>Email</th>
                <th>Address</th>
              </tr>
            </thead>

            <tbody>
              {
                this.state.restaurants.map(
                  restaurant => <tr key={restaurant.id}>
                    <td>{restaurant.name}</td>
                    <td>{restaurant.email}</td>
                    <td>{restaurant.address}</td>
                  </tr>
                )
              }
            </tbody>

          </table>
        </div>
        
      </div>
    )
  }
}
