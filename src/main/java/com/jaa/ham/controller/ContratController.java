package com.jaa.ham.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jaa.ham.entities.Adresse;
import com.jaa.ham.entities.Contrat;
import com.jaa.ham.entities.ContratAudit;
import com.jaa.ham.exception.RecordNotFoundException;
import com.jaa.ham.service.ContratService;

@RestController
@RequestMapping("/contrats")
public class ContratController {
	
	@Autowired
	ContratService contratService;

	@GetMapping("/{idCont}")
	public ResponseEntity<Contrat> getContrat(@PathVariable("idCont")Long idCont) throws RecordNotFoundException{
		Contrat contrat = contratService.findContratById(idCont);
		return new ResponseEntity<Contrat>(contrat,new HttpHeaders(),HttpStatus.OK);
	}
	
	@PutMapping("/{idCont}")
	public ResponseEntity<Contrat> changerAdresseContrat(@PathVariable("idCont")Long idCont,@RequestBody Adresse adresse) throws RecordNotFoundException{
		Contrat contrat = contratService.changerAdresse(idCont, adresse);
		return new ResponseEntity<Contrat>(contrat,new HttpHeaders(),HttpStatus.OK);
	}
	
	@GetMapping("/{idCont}/audit")
	public ResponseEntity<ContratAudit> getLastAdressAuditMouvement(@PathVariable("idCont")Long idCont) throws RecordNotFoundException{
		ContratAudit contratAudit = contratService.getLastContratAuditOfContrat(idCont);
		System.out.println(contratAudit.toString());
		return new ResponseEntity<ContratAudit>(contratAudit,new HttpHeaders(),HttpStatus.OK);
	}
}
