package com.jaa.ham.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Contrat {
	
	@Id
	@Column(name = "CONT_ID")
	private Long id;
	private String reference;
	private String canal;
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="ABN_ID")
	private Abonne abonne;
	
	@OneToOne
	@JoinColumn(name="ADR_ID")
	private Adresse adresse;
	
	public Contrat() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getCanal() {
		return canal;
	}

	public void setCanal(String canal) {
		this.canal = canal;
	}

	public Abonne getAbonne() {
		return abonne;
	}

	public void setAbonne(Abonne abonne) {
		this.abonne = abonne;
	}

	public Adresse getAdresse() {
		return adresse;
	}

	public void setAdresse(Adresse adresse) {
		
		this.adresse = adresse;
		this.adresse.setContrat(this);
	}

	@Override
	public String toString() {
		return "Contrat [id=" + id + ", reference=" + reference + ", canal=" + canal + "]";
	}

	
	
	
	
	
	

}
