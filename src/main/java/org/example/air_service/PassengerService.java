package org.example.air_service;

import org.example.air_model.Address;
import org.example.air_model.Passenger;
import org.example.air_model.Trip;
import org.example.air_repository.PassengerRepository;

import java.sql.*;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;



public class PassengerService implements PassengerRepository {

    private Connection connection;

    @Override
    public Passenger getBy(int id) {
        checkId(id);

        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            pst = connection.prepareStatement("select * from passenger where id = ?");
            pst.setInt(1, id);

            rs = pst.executeQuery();

            Passenger result = null;
            while (rs.next()) {
                result = new Passenger();
                result.setId(rs.getInt("id"));
                result.setName(rs.getString("name"));
                result.setPhone(rs.getString("phone"));

                Address address = getAddressByPassengerId(id);

                result.setAddress(address);
            }

            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                assert rs != null;
                rs.close();
                pst.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


    @Override
    public Set<Passenger> getAll() {
        Statement st = null;
        ResultSet rs = null;

        try {
            st = connection.createStatement();
            rs = st.executeQuery("select * from passenger");

            Set<Passenger> passengers = new LinkedHashSet<>();

            while (rs.next()) {
                Passenger tempPass = new Passenger();
                tempPass.setId(rs.getInt("id"));
                tempPass.setName(rs.getString("name"));
                tempPass.setPhone(rs.getString("phone"));

                passengers.add(tempPass);
            }

            return passengers.isEmpty() ? null : passengers;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                assert rs != null;
                rs.close();
                st.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


    @Override
    public Set<Passenger> get(int offset, int perPage, String sort) {
        if (offset <= 0 || perPage <= 0) {
            throw new IllegalArgumentException("Passed non-positive value as 'offset' or 'perPage': ");
        }
        if (sort == null || sort.isEmpty()) {
            throw new IllegalArgumentException("Passed null or empty value as 'sort': ");
        }
        if (!sort.equals("id") && !sort.equals("name") && !sort.equals("phone")) {
            throw new IllegalArgumentException("Parameter 'sort' must be 'id' or 'name' or 'found_date': ");
        }

        Set<Passenger> passengers = new LinkedHashSet<>();
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            pst = connection.prepareStatement("select * from passenger order by " + sort + " limit ? offset ?");
            pst.setInt(1, perPage);
            pst.setInt(2, offset);

            rs = pst.executeQuery();

            while (rs.next()) {
                Passenger tempPassenger = new Passenger();
                tempPassenger.setId(rs.getInt(1));
                tempPassenger.setName(rs.getString(2));
                tempPassenger.setPhone(rs.getString(3));
                passengers.add(tempPassenger);
            }

            return passengers.isEmpty() ? null : passengers;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                assert pst != null;
                assert rs != null;
                pst.close();
                rs.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


    @Override
    public Passenger save(Passenger passenger) {
        checkNull(passenger);

        if (passenger.getAddress() == null) {
            return saveWithNullAddress(passenger);
        }

        int addressId = getAddressIdByFields(passenger.getAddress().getCountry(), passenger.getAddress().getCity());

        if (addressId == -1) {
            return saveWithUnsavedAddress(passenger);
        }

        return saveWithExistingAddress(passenger);
    }


    @Override
    public Passenger updateBy(int id, Passenger passenger) {
        return null;
    }


    @Override
    public boolean deleteBy(int id) {
        checkId(id);

        if (getBy(id) == null) {
            System.out.println("Passenger with " + id + " id does not exists: ");
            return false;
        }

        if (getTripIdByPassengerId(id) != -1) {
            System.out.println("First remove the row from the 'pass_in_trip' table where 'pass_id' is " + id + ": ");
            return false;
        }

        PreparedStatement pst = null;

        try {
            pst = connection.prepareStatement("delete from passenger where id = ?");
            pst.setInt(1, id);
            pst.executeUpdate();

            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                assert pst != null;
                pst.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public List<Passenger> getPassengerOfTrip(int tripNumber) {
        return null;
    }


    public List<Passenger> getPassengersOfTrip(int tripNumber) {
        return null;
    }


    @Override
    public void registerTrip(Trip trip, Passenger passenger) {

    }


    @Override
    public void cancelTrip(int passengerId, int tripNumber) {

    }


    public void setConnection(Connection connection) {
        checkNull(connection);
        this.connection = connection;
    }


    private void checkNull(Object obj) {
        if (obj == null) {
            throw new NullPointerException("Passed null value: ");
        }
    }


    private void checkId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("'Id' must be positive number: ");
        }
    }


    private Address getAddressByPassengerId(int passId) {
        checkId(passId);

        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            pst = connection.prepareStatement("select address_id from passenger where id = ?");
            pst.setInt(1, passId);
            rs = pst.executeQuery();

            int addressId = 0;
            while (rs.next()) {
                addressId = rs.getInt("address_id");
            }

            pst = connection.prepareStatement("select * from address where id = " + addressId);
            rs = pst.executeQuery();

            Address address = null;
            while (rs.next()) {
                address = new Address();
                address.setId(rs.getInt("id"));
                address.setCountry(rs.getString("country"));
                address.setCity(rs.getString("city"));
            }

            return address;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                assert pst != null;
                assert rs != null;
                pst.close();
                rs.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


    private Passenger saveWithNullAddress(Passenger passenger) {
        checkNull(passenger);

        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            pst = connection.prepareStatement("insert into passenger(name, phone) values(?, ?)");
            pst.setString(1, passenger.getName());
            pst.setString(2, passenger.getPhone());

            pst.executeUpdate();

            return passenger;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                assert pst != null;
                pst.close();
                rs.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


    private Passenger saveWithExistingAddress(Passenger passenger) {
        checkNull(passenger);

        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            int addressId = getAddressIdByFields(passenger.getAddress().getCountry(), passenger.getAddress().getCity());

            pst = connection.prepareStatement("insert into passenger(name, phone, address_id) values(?, ?, ?)");
            pst.setString(1, passenger.getName());
            pst.setString(2, passenger.getPhone());
            pst.setInt(3, addressId);

            pst.executeUpdate();

            return passenger;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                assert pst != null;
                pst.close();
                rs.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


    private Passenger saveWithUnsavedAddress(Passenger passenger) {
        checkNull(passenger);

        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            pst = connection.prepareStatement("insert into address(country, city) values(?, ?)");
            pst.setString(1, passenger.getAddress().getCountry());
            pst.setString(2, passenger.getAddress().getCity());
            pst.executeUpdate();

            int addressId = getAddressIdByFields(passenger.getAddress().getCountry(), passenger.getAddress().getCity());

            pst = connection.prepareStatement("insert into passenger(name, phone, address_id) values(?, ?, ?)");
            pst.setString(1, passenger.getName());
            pst.setString(2, passenger.getPhone());
            pst.setInt(3, addressId);

            pst.executeUpdate();

            return passenger;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                assert pst != null;
                pst.close();
                rs.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


    private int getAddressIdByFields(String country, String city) {
        if (country == null || city == null) {
            throw new NullPointerException("Passed null value: ");
        }
        if (country.isEmpty() || city.isEmpty()) {
            throw new IllegalArgumentException("Passed empty value: ");
        }

        Set<Address> allAddresses = getAllAddresses();

        if (allAddresses == null) {
            System.out.println("Address list is empty: ");
            return -1;
        }

        for (Address item : allAddresses) {
            if (item.getCountry().equals(country) && item.getCity().equals(city)) {
                return item.getId();
            }
        }

        return -1;
    }


    private Set<Address> getAllAddresses() {
        Statement st = null;
        ResultSet rs = null;

        try {
            st = connection.createStatement();
            rs = st.executeQuery("select * from address");

            Set<Address> addresses = new LinkedHashSet<>();

            while (rs.next()) {
                Address tempAddress = new Address();
                tempAddress.setId(rs.getInt("id"));
                tempAddress.setCountry(rs.getString("country"));
                tempAddress.setCity(rs.getString("city"));

                addresses.add(tempAddress);
            }

            return addresses.isEmpty() ? null : addresses;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                assert rs != null;
                rs.close();
                st.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


    private int getTripIdByPassengerId(int passengerId) {
        checkId(passengerId);

        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            pst = connection.prepareStatement("select trip_id from pass_in_trip where pass_id = ?");
            pst.setInt(1, passengerId);

            rs = pst.executeQuery();

            int tripId = -1;
            while (rs.next()) {
                tripId = rs.getInt("trip_id");
            }
            return tripId;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                assert pst != null;
                assert rs != null;
                rs.close();
                pst.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
   

