package com.psa.flight_reservation_app_1.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.psa.flight_reservation_app_1.dto.ReservationRequest;
import com.psa.flight_reservation_app_1.entities.Flight;
import com.psa.flight_reservation_app_1.entities.Passenger;
import com.psa.flight_reservation_app_1.entities.Reservation;
import com.psa.flight_reservation_app_1.repository.FlightRepository;
import com.psa.flight_reservation_app_1.repository.PassengerRepository;
import com.psa.flight_reservation_app_1.repository.ReservationRepository;
import com.psa.flight_reservation_app_1.utilities.EmailUtil;
import com.psa.flight_reservation_app_1.utilities.PDFGenerator;


@Service
public class ReservationServiceimpl implements ReservationService {

	@Autowired
	private PassengerRepository passengerRepo;
	
	@Autowired
	private FlightRepository flightRepo;
	
	@Autowired
	private ReservationRepository reservationRepo;
	
	@Autowired
	PDFGenerator pdfGenerator;

	@Autowired
	private EmailUtil emailUtil;
	
	@Override
	public Reservation bookFlight(ReservationRequest request) {
		
		
		Passenger passenger = new Passenger();
		passenger.setFirstName(request.getFirstName());
		passenger.setLastName(request.getLastName());
		passenger.setMiddleName(request.getMiddleName());
		passenger.setEmail(request.getEmail());
		passenger.setPhone(request.getPhone());
		passengerRepo.save(passenger);
		
		long flightId = request.getFlightId();
		Optional<Flight> findById = flightRepo.findById(flightId);
		Flight flight = findById.get();
		
		Reservation reservation = new Reservation();
		reservation.setFlight(flight);
		reservation.setPassenger(passenger);
		reservation.setCheckedIn(false);
		reservation.setNumberOfBags(0);
		reservationRepo.save(reservation);
		String filepath ="D:\\stsexample\\flight_reservation_app_1\\Tickets\\reservations"+reservation.getId()+".pdf";

		
		pdfGenerator.generateItinerary(reservation, filepath);
		emailUtil.sendItinerary(passenger.getEmail(), filepath);
		
		return reservation;
	}

}
