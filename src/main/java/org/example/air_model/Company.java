package org.example.air_model;

import java.util.Date;

public class Company {
    private int id;
    private String name;
    private Date foundDate;

    public Company(final  int id, final String name, final Date foundDate){
        setId(id);
        setName(name);
        setFoundDate(foundDate);
    }
    public Company(){

    }

    public void setId( final int id) {
        if(id <= 0){
            throw new IllegalArgumentException("'id' positive number" );
        }
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName(){
        return name;
    }
    public void setName(final String name) {
        if(name == null || name.isEmpty()){
            throw new IllegalArgumentException("pass null or empty value as 'name");
        }
        this.name = name;
    }

    public void setFoundDate(final Date foundDate){
        if (foundDate == null){
            throw  new NullPointerException("Paed null value as foundDate");
        }
        this.foundDate = foundDate;
    }
    public Date getFoundDate() {
        return foundDate;
    }
    @Override
    public String toString() {
        return "Company{" + "id=" + id + ", name='" + name + '\'' + ", foundDate=" + foundDate + "}\n";
    }

    public void getFoundDate(java.sql.Date date) {
    }
}
