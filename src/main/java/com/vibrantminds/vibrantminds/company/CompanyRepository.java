package com.vibrantminds.vibrantminds.company;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CompanyRepository
        extends JpaRepository<CompanyEntity, Long> {

    List<CompanyEntity> findByActiveTrue();

    List<CompanyEntity> findByCity(String city);

    List<CompanyEntity> findByState(String state);

    List<CompanyEntity> findByIndustry(String industry);

    List<CompanyEntity> findByCompanySize(String companySize);

    List<CompanyEntity> findByNameContainingIgnoreCase(String name);

    boolean existsByName(String name);
}