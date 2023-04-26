package org.example.air_model;

import java.sql.Timestamp;


public class Trip {
    private int tripNumber;
    private Company company;
    private String airplane;
    private String townFrom;
    private String townTo;
    private Timestamp timeOut;
    private Timestamp timeIn;
    private String towTo;
    private Object string;

    public Trip(final int tripNumber, final Company company,final String airplane,final String townFrom,final String townTo, final Timestamp timeOut,final Timestamp timeIn){
        setTripNumber(tripNumber);
        setCompany(company);
        setAirplane(airplane);
        setTownFrom(townFrom);
        setTowTo(townTo);
        setTimeOut(timeOut);
        setTimeIn(timeIn);
    }


    public Trip(){

    }
    public void setTripNumber(final int tripNumber) {
        if(tripNumber <= 0){
            throw new IllegalArgumentException("'tripNumber' must be positive number");
        }
        this.tripNumber = tripNumber;
    }

    public int getTripNumber() {
        return tripNumber;
    }

    public void setCompany(final Company company) {
       checkNull(company);
        this.company = company;
    }

    private void checkNull(final Object obj) {
        if(obj == null){
            throw new NullPointerException("Pased null value");
        }
    }

    public Company getCompany() {
        return company;
    }

    public void setAirplane(String airplane) {
        validateString(airplane);
        this.airplane = airplane;
    }

    public String getAirplane() {
        return airplane;
    }

    public void setTownFrom(final String townFrom) {
        validateString(townFrom);
        this.townFrom = townFrom;
    }

    public String getTownFrom() {
        return townFrom;
    }

    public void setTowTo(final String towTo) {
        validateString(towTo);
        this.towTo = towTo;
    }

    public String getTowTo() {
        return towTo;
    }

    public void setTimeOut(final Timestamp timeOut) {
        checkNull(timeOut);
        this.timeOut = timeOut;
    }

    public Timestamp getTimeOut() {
        return timeOut;
    }

    public void setTimeIn(final Timestamp timeIn) {
        checkNull(timeIn);
        this.timeIn = timeIn;
    }

    public Timestamp getTimeIn() {
        return timeIn;
    }

    @Override
    public String toString() {
        return "Trip{" + "tripNumber=" + tripNumber + ", company=" + company + ", airplane='" + airplane + '\'' + ", townFrom='" + townFrom + '\'' + ", townTo='" + townTo + '\'' + ", timeOut=" + timeOut + ", timeIn=" + timeIn + "}\n";
    }

    private void validateString(String airplane) {
        if(string == null || toString().isEmpty()){
            throw new IllegalArgumentException("Passed null or empty value");
        }
    }
}
