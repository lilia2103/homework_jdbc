package org.example.air_repository;

import org.example.air_model.Passenger;
import org.example.air_model.Trip;

import java.util.List;
import java.util.Set;

public interface PassengerRepository {

    Passenger getBy(int id);

    Set<Passenger> getAll();

    Set<Passenger> get(int offset, int perPage, String sort);

    Passenger save(Passenger passenger);

    Passenger updateBy(int id,Passenger passenger);

    boolean deleteBy(int id);

    List<Passenger> getPassengerOfTrip(int tripNumber);

    void registerTrip(Trip trip,Passenger passenger);

    void cancelTrip(int passengerId, int tripNumber);
}
