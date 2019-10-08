package com.jaa.ham.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jaa.ham.entities.Abonne;

@Repository
public interface AbonneRepository extends JpaRepository<Abonne, Long> {

}
