package com.psa.flight_reservation_app_1.service;

import com.psa.flight_reservation_app_1.dto.ReservationRequest;
import com.psa.flight_reservation_app_1.entities.Reservation;

public interface ReservationService {
     Reservation bookFlight(ReservationRequest request);
}
