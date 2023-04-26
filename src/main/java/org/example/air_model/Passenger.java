package org.example.air_model;

public class Passenger {
    private int id;
    private String name;
    private String phone;
    private Address address;

    public Passenger(final int id, final String name, final String phone, final Address address){
        setId(id);
        setName(name);
        setPhone(phone);
        setAddress(address);
    }

    public Passenger(final String name, final String phone, final Address address){
        setName(name);
        setPhone(phone);
        setAddress(address);
    }

    public Passenger(){

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        if(id <= 0){
            throw new IllegalArgumentException("'id' mut be positive number:");
        }
        this.id = id;
    }

    public void setName( final String name) {
        validateString(name);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPhone( final String phone) {
        validateString(phone);
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setAddress( final Address address) {
        this.address = address;
    }

    public Address getAddress() {
        return address;
    }

    public String toString(){
        return "Passenger{" + "id=" + id + ", name='" + name + '\'' + ", phone='" + phone + '\'' + ", address=" + address + "}\n";
    }

    private void validateString(final String name) {
        Object str = null;
        if(str == null || toString().isEmpty()){
            throw new IllegalArgumentException("Passed null or empty value: ");
        }
    }
}
