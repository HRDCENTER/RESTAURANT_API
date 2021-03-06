package kh.com.kshrd.restaurant.repositories.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kh.com.kshrd.restaurant.models.RestaurantLocation;
import kh.com.kshrd.restaurant.repositories.RestaurantLocationRepository;

@Repository
public class RestaurantLocationRepositoryImpl implements RestaurantLocationRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public boolean save(RestaurantLocation location) {
		try{
			int result = jdbcTemplate.update("INSERT INTO restaurant_locations("
											 + "restaurant_id, "
					 						 + "longitude, "
					 						 + "latitude,"
					 						 + "province,"
					 						 + "district, "
					 						 + "commune, "
					 						 + "village, "
					 						 + "street, "
					 						 + "no, "
					 						 + "status)"
							 + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
							 , new Object[]{
									 		location.getRestaurant().getId(),
									 		location.getLongitude(),
									 		location.getLatitude(),
									 		location.getProvince(),
									 		location.getDistrict(),
									 		location.getCommune(),
									 		location.getVillage(),
									 		location.getStreet(),
									 		location.getNo(),
									 		location.getStatus()
							 				});
			if(result>0){
				return true;
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return false;
	}
	
	@Override
	public boolean update(RestaurantLocation location) {
		try{
			int result = jdbcTemplate.update("UPDATE restaurant_locations "
												 + "SET district = ?, "
												 + "	commune = ?, "
												 + "	street = ?, "
												 + "	no = ?, "
												 + "	branch = ?, "
												 + "	village = ?, "
						 						 + "	status =? "
						 						 + "WHERE restaurant_id = ?"
							 , new Object[]{
									 		location.getDistrict(),
									 		location.getCommune(),
									 		location.getStreet(),
									 		location.getNo(),
									 		location.getBranch(),
									 		location.getVillage(),
									 		location.getStatus(),
									 		location.getRestaurant().getId()
							 				});
			if(result>0){
				System.out.println(location);
				return true;
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return false;
	}

}
