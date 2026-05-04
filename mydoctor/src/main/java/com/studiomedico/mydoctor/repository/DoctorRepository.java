package com.studiomedico.mydoctor.repository;

import com.studiomedico.mydoctor.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, UUID> {
    @Query("select d from Doctor d where " +
            "(:firstName is null or d.firstName = :firstName) and " +
            "(:lastName is null or d.lastName = :lastName) and " +
            "(:specialization is null or d.specialization = :specialization) and " +
            "(:email is null or d.email = :email)")
    List<Doctor> getDoctorByFilters(@Param("firstName") String firstName, @Param("lastName") String lastName, @Param("specialization") String specialization, @Param("email") String email);

    //per il service
    boolean existsByEmail(String email);
}
