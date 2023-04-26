package org.example.air_model;

public class Address {
    private int id;
    private String country;
    private String city;

    public Address(final int id, final String country, final String city){
        setId(id);
        setCountry(country);
        setCity(city);
    }

    public Address(final String country, final String city){
        setCountry(country);
        setCity(city);
    }

    public Address(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if(id <= 0){
            throw  new IllegalArgumentException("'id must be positive number: ");
        }
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        validateString(country);
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        validateString(city);
        this.city = city;
    }

    public String toString(){
        return "Addres{" + "id=" + id + ", country='" + country + '\'' + ", city='" + city +'\'' + "}\n";
    }


    private void validateString( final String country) {
        Object str = null;
        if (str == null || toString().isEmpty()){
            throw new IllegalArgumentException("Pased null or empty value: ");
        }
    }
}
