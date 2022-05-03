import axios from 'axios';

const RESTAURANT_API_BASE_URL = "http://localhost:8080/api/v1/restaurants";

class RestaurantService {

    getRestaurants() {
        return axios.get(RESTAURANT_API_BASE_URL);
    }

}

export default new RestaurantService()