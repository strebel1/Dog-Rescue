package dog.rescue.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.tools.DocumentationTool.Location;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dog.rescue.controller.model.LocationData;
import dog.rescue.dao.LocationDao;

@Service
public class RescueService {

	@Autowired
	private LocationDao locationDao;

	@Transactional(readOnly = false)
	public LocationData saveLocation(LocationData locationData) {
		Location location = locationData.toLocation();
		Location dbLocation = locationDao.save(location);
		
		return new LocationData (dbLocation);
	
	}

	@Transactional (readOnly = true)
	public LocationData retrieveLocationById(Long locationId) {
		Location location =findLocationById (locationId);
		return new LocationData(location); 
	}

	private Location findLocationById(Long locationId) {
		return locationDao.findById(locationId)
				.orElseThrow(() -> new NoSuchElementException(
				"Location with ID=" + locationId +"was not found."));
		
		
	}

	@Transactional(readOnly = true)
	public List<LocationData> retrieveAllLocations() {
		List <Location> locationEntities = locationDao.findAll();
		List<LocationData> locationDtos = new LinkedList<>();
 
		locationEntities.sort((loc1, loc2) -> loc1.getBusinessName().compareTo(loc2.getBusinessName()));
		
		for (Location location : locationEntities) {
		LocationData locationData = new LocationData(location);
		locationDtos.add(locationData);
		return locationDtos;
		//@formatter:off
		
		/*return locationDao.findAll()
				.stream()
				.sorted((loc1, loc2) -> 
		locl.getBusinessName().compareTo(loc2.getBusinessName()))
				.map(LocationData:: new)
				.toList();
				*/
				//@formatter:on
 }
 
		@Transactional(readOnly = false)
	public void deleteLocation(Long locationId) {
		Location location = findLocationById (locationId);
		locationDao.delete(location);
	}
	
	

}
