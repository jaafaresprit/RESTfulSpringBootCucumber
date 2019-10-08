package com.jaa.ham.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jaa.ham.entities.Adresse;
import com.jaa.ham.entities.Contrat;
import com.jaa.ham.entities.ContratAudit;
import com.jaa.ham.exception.RecordNotFoundException;
import com.jaa.ham.repository.AdresseRepository;
import com.jaa.ham.repository.ContratAuditRepository;
import com.jaa.ham.repository.ContratRepository;

@Service
public class ContratService {

	@Autowired
	private AdresseRepository adresseRepository;
	@Autowired
	private ContratRepository contratRepository;
	@Autowired
	private ContratAuditRepository contratAuditRepository;

	public Contrat changerAdresse(Long idContat, Adresse adresse) throws RecordNotFoundException {

		Optional<Contrat> contratExist = contratRepository.findById(idContat);
		Optional<Adresse> adr = adresseRepository.findById(adresse.getId());
		if (!adr.isPresent()) {
			adresse = adresseRepository.save(adresse);
		}
		if (contratExist.isPresent()) {
			Contrat contrat = contratExist.get();
			if (contrat.getAdresse().getPays().equals("France") && contrat.getAdresse().isActive()) {
				ContratAudit contratAudit = cloneContratPourAudit(contrat);
				contratAudit.setDateChangement(new Date());
				contratAuditRepository.save(contratAudit);
			}
			contrat.setAdresse(adresse);
			contratRepository.save(contrat);
			return contrat;
		} else {
			throw new RecordNotFoundException("Contrat n''existe pas");
		}

	}

	public ContratAudit getContractAudit(Long idContAud) throws RecordNotFoundException {
		Optional<ContratAudit>optional  =  contratAuditRepository.findById(idContAud);
		if(optional.isPresent()) {
			return optional.get();
		} else {
			throw new RecordNotFoundException("Pas de trace");
		}
		
	}
	
	public ContratAudit getLastContratAuditOfContrat(Long idCont) throws RecordNotFoundException {
		ContratAudit audit = contratAuditRepository.findByMaxIdContrat(idCont);
		if(audit==null) {
			throw new RecordNotFoundException("Pas de trace");
		}
		return audit;
	}
	
	private ContratAudit cloneContratPourAudit(Contrat contrat) {
		ContratAudit contratAudit = new ContratAudit(contrat.getId(), contrat.getReference(), contrat.getCanal(),
				contrat.getAbonne(), contrat.getAdresse());
		contratAudit.setId(1L);
		return contratAudit;
	}

	public Contrat findContratById(Long idCont) throws RecordNotFoundException {
		Optional<Contrat> optionCont = contratRepository.findById(idCont);
		if(optionCont.isPresent()) {
			return optionCont.get();
		}
		throw new RecordNotFoundException("contrat inexistant");
	}

}
