package com.masai.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.masai.exception.CustomerException;
import com.masai.exception.HotelException;
import com.masai.model.CurrentUserSession;
import com.masai.model.Customer;
import com.masai.model.Hotel;
import com.masai.repository.AdminDao;
import com.masai.repository.HotelDao;
import com.masai.repository.UserSessionDao;

public class HotelServiceImpl implements HotelService {
	
	@Autowired
	private AdminDao admin;
	
	@Autowired
	private UserSessionDao uSesDao;
	
	@Autowired
	private HotelDao hotelDao;

	@Override
	public Hotel addHotel(Hotel hotel,String key) {
		Optional<CurrentUserSession> opt = uSesDao.findByAuthKey(key);
		if (opt.isEmpty()) {
			throw new HotelException("Invalid Authentication Id of Admin" + key);
		}
		Hotel hot=new Hotel();
		hot.setHotelname(hotel.getHotelname());
		hot.setAddress(hotel.getAddress());
		hot.setHoteltype(hotel.getHoteltype());
		hot.setRent(hotel.getRent());
		hot.setStatus(hotel.getStatus());
		return hot;
		
	}

	@Override
	public String deleteHotel(String name, String key) {
		
		Optional<CurrentUserSession> opt = uSesDao.findByAuthKey(key);
		if (opt.isEmpty()) {
			throw new HotelException("Invalid Authentication Id of Admin" + key);
		}
		
		
		Optional<Hotel> opth = hotelDao.findByName(name);
		if(opth.isEmpty()) {
			throw new HotelException("Hotel not found with this name");
		}
		hotelDao.delete(opth.get());
		
		return "Hotel Delaits Deleted Successfully";
		
		
		
	}

	@Override
	public Hotel updateHotel(Hotel hotel, String key) {
		return hotel;
	}

	@Override
	public List<Hotel> viewAllHotel() {
      List<Hotel> hotels=hotelDao.findAll();
		
		if(hotels.size()==0) {
			throw new CustomerException("Hotels not found");
		}
		
		return hotels;
	}

}