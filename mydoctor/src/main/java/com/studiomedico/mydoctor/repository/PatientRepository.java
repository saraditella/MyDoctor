package com.studiomedico.mydoctor.repository;

import com.studiomedico.mydoctor.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID> {
    @Query("select p from Patient p where " +
            "(:firstName is null or p.firstName = :firstName) AND " +
            "(:lastName is null or p.lastName = :lastName) AND " +
            "(:email is null or p.email = :email) AND " +
            "(:fiscalCode is null or p.fiscalCode = :fiscalCode)")
    List<Patient> findByFilters(@Param("firstName") String firstName, @Param("lastName") String lastName, @Param("email") String email, @Param("fiscalCode") String fiscalCode);

    //controllo sul campo unique
    boolean existsByFiscalCode(String fiscalCode);
}
