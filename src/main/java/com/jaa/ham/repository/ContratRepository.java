package com.jaa.ham.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jaa.ham.entities.Contrat;

@Repository
public interface ContratRepository extends JpaRepository<Contrat, Long> {

}
