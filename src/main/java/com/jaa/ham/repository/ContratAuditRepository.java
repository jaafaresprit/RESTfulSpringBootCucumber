package com.jaa.ham.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jaa.ham.entities.ContratAudit;

@Repository
public interface ContratAuditRepository extends JpaRepository<ContratAudit, Long> {

	@Query("select ca from ContratAudit ca where ca.id = ("
			+ "select max(ca1.id) from ContratAudit ca1 where ca1.idContrat = :idContrat)")
	ContratAudit findByMaxIdContrat(@Param("idContrat") Long idContrat);
}
