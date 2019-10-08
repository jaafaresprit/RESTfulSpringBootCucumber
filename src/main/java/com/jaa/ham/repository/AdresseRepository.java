package com.jaa.ham.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jaa.ham.entities.Adresse;

@Repository
public interface AdresseRepository extends JpaRepository<Adresse, Long> {

}
