package com.gk.study.service;


import com.gk.study.entity.Company;

import java.util.List;

public interface CompanyService {
    public Company getCompanyById(long id);
    List<Company> getCompanyList();
    void createCompany(Company company);
    void deleteCompany(String id);

    void updateCompany(Company company);

    List<Company> getUserCompanyList(String userId);
}
