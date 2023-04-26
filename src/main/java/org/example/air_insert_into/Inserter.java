package org.example.air_insert_into;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.nio.file.Path;
import java.sql.Connection;

public class Inserter {
    private static final String INSERT_INTO_COMPANY_SQL =
            "insert into company(name, found_date) values(?, ?)";
    private static final String INSERT_INTO_ADDRESS_SQL =
            "insert into address(country, city) values(?, ?)";
    private static final String INSERT_INTO_PASSENGER_SQL =
            "insert into passenger(name, phone, address_id) values(?, ?, ?)";
    private static final String INSERT_INTO_TRIP_SQL =
            "insert into trip(trip_number, company_id, airplane, town_from, town_to, time_out, time_in) " +
                    "values(?, ?, ?, ?, ?, ?, ?)";
    private static final String INSERT_INTO_PASSINTRIP_SQL =
            "insert into pass_in_trip(trip_id, pass_id, date, place) values(?, ?, ?, ?)";

    private static final String ROOT_PATH =
            "C:\\Users\\user\\Java Projects\\airport_management_system\\src\\main\\resources\\";
    private static final Path PATH_COMPANY_TXT = Path.of(ROOT_PATH + "companies.txt");
    private static final Path PATH_ADDRESS_TXT = Path.of(ROOT_PATH + "addresses.txt");
    private static final Path PATH_PASSENGER_TXT = Path.of(ROOT_PATH + "passengers.txt");
    private static final Path PATH_TRIP_TXT = Path.of(ROOT_PATH + "trip.txt");
    private static final Path PATH_PASSINTRIP_TXT = Path.of(ROOT_PATH + "pass_in_trip.txt");


    public void insertCompanyTable(Connection con) {
        checkNullConnection(con);

        PreparedStatement pst = null;

        try {
            List<String> lines = readLinesOfFileFrom(PATH_COMPANY_TXT);

            for (int i = 0; i < (lines != null ? lines.size() : 0); i++) {
                String line = lines.get(i);
                String[] fields = line.split(",");

                String[] dateParts = fields[1].split("/");

                pst = con.prepareStatement(INSERT_INTO_COMPANY_SQL);

                pst.setString(1, fields[0]);
                pst.setDate(
                        2,
                        Date.valueOf(
                                LocalDate.of(
                                        Integer.parseInt(dateParts[2]),
                                        Integer.parseInt(dateParts[0]),
                                        Integer.parseInt(dateParts[1])
                                )
                        )
                );

                pst.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                assert pst != null;
                pst.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public void insertAddressTable(Connection con) {
        checkNullConnection(con);

        PreparedStatement pst = null;

        try {
            List<String> lines = readLinesOfFileFrom(PATH_ADDRESS_TXT);

            for (int i = 0; i < (lines != null ? lines.size() : 0); i++) {
                String line = lines.get(i);
                String[] fields = line.split(",");

                pst = con.prepareStatement(INSERT_INTO_ADDRESS_SQL);

                pst.setString(1, fields[0]);
                pst.setString(2, fields[1]);

                pst.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                assert pst != null;
                pst.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public void insertPassengerTable(Connection con) {
        checkNullConnection(con);

        PreparedStatement pst = null;

        try {
            List<String> lines = readLinesOfFileFrom(PATH_PASSENGER_TXT);

            for (int i = 0; i < (lines != null ? lines.size() : 0); i++) {
                String line = lines.get(i);
                String[] fields = line.split(",");

                pst = con.prepareStatement(INSERT_INTO_PASSENGER_SQL);

                pst.setString(1, fields[0]);
                pst.setString(2, fields[1]);
                pst.setInt(3, Integer.parseInt(fields[2]));

                pst.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                assert pst != null;
                pst.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public void insertTripTable(Connection con) {
        checkNullConnection(con);

        PreparedStatement pst = null;

        try {
            List<String> lines = readLinesOfFileFrom(PATH_TRIP_TXT);

            for (int i = 0; i < (lines != null ? lines.size() : 0); i++) {
                String line = lines.get(i);
                String[] fields = line.split(",");

                pst = con.prepareStatement(INSERT_INTO_TRIP_SQL);

                pst.setInt(1, Integer.parseInt(fields[0]));
                pst.setInt(2, Integer.parseInt(fields[1]));
                pst.setString(3, fields[2]);
                pst.setString(4, fields[3]);
                pst.setString(5, fields[4]);
                pst.setTimestamp(6, Timestamp.valueOf(fields[5]));
                pst.setTimestamp(7, Timestamp.valueOf(fields[6]));

                pst.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                assert pst != null;
                pst.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public void insertPassInTripTable(Connection con) {
        checkNullConnection(con);

        PreparedStatement pst = null;

        try {
            List<String> lines = readLinesOfFileFrom(PATH_PASSINTRIP_TXT);

            for (int i = 0; i < (lines != null ? lines.size() : 0); i++) {
                String line = lines.get(i);
                String[] fields = line.split(",");

                pst = con.prepareStatement(INSERT_INTO_PASSINTRIP_SQL);

                pst.setInt(1, Integer.parseInt(fields[0]));
                pst.setInt(2, Integer.parseInt(fields[1]));
                pst.setTimestamp(3, Timestamp.valueOf(fields[2]));
                pst.setString(4, fields[3]);

                pst.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                assert pst != null;
                pst.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


    private List<String> readLinesOfFileFrom(Path path) {
        if (path == null) {
            throw new NullPointerException("Passed null value as 'path': ");
        }
        try {
            return Files.readAllLines(path, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    private static void checkNullConnection(Connection con) {
        if (con == null) {
            throw new NullPointerException("Passed null value as 'con': ");
        }
    }
}

