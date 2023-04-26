package org.example.air_repository;

import org.example.air_model.Company;

import java.util.Set;

public interface CompanyRepository {
    Company getById(int id);
    Set<Company> getAll();
    Company save(Company company);
    boolean deleteBy(int id);
    int updateBy(int id, Company company);
    Set<Company> get(int offset, int perPage, String sort);

}