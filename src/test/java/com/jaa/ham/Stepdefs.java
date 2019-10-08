package com.jaa.ham;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.jaa.ham.entities.Abonne;
import com.jaa.ham.entities.Adresse;
import com.jaa.ham.entities.Contrat;
import com.jaa.ham.entities.EnumCondition;
import com.jaa.ham.repository.AbonneRepository;
import com.jaa.ham.repository.AdresseRepository;
import com.jaa.ham.repository.ContratRepository;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = SpringBootContextLoader.class)
@WebAppConfiguration
@SpringBootTest(classes = ResTfulSpringBootApplication.class)
public class Stepdefs{

	@Autowired
	ContratRepository contratRepository;
	@Autowired
	AdresseRepository adresseRepository;
	@Autowired
	AbonneRepository abonneRepository;

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockServer;

	@Before
	public void setUpMockServer() {
		mockServer = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		Abonne abonne = new Abonne();
		abonne.setId(1L);
		abonne.setNom("ABo1");
		abonne.setPrenom("AB1");

		abonneRepository.save(abonne);

		Adresse adresse = new Adresse();
		adresse.setId(1L);
		adresse.setNumero("12");
		adresse.setVoie("adresse");
		adresse.setVille("Ville1");
		adresse.setActive(true);
		adresse.setPays("France");
		adresse.setCondition(EnumCondition.SDE);
		adresseRepository.save(adresse);

		Adresse adresse2 = new Adresse();
		adresse2.setId(2L);
		adresse2.setNumero("22");
		adresse2.setVoie("adresse2");
		adresse2.setVille("Ville2");
		adresse2.setActive(true);
		adresse2.setPays("France");
		adresse2.setCondition(EnumCondition.ADE);
		adresseRepository.save(adresse2);

		adresse = adresseRepository.findById(1L).get();
		Contrat contrat = new Contrat();
		contrat.setId(1L);
		contrat.setAbonne(abonne);
		contrat.setAdresse(adresse);
		contrat.setCanal("canal1");
		contratRepository.save(contrat);
	}

	@Given("^un abonné avec une adresse principale active en France$")
	public void un_abonné_avec_une_adresse_principale_active_en_France() throws Throwable {
		mockServer.perform(get("/contrats/1")).andExpect(status().isOk())//
				.andExpect(content().contentType("application/json;charset=UTF-8"))//
				.andExpect(jsonPath("$.id").value("1"))//
				.andExpect(jsonPath("$.canal").value("canal1"))//
				.andExpect(jsonPath("$.adresse.id").value("1"))//
				.andExpect(jsonPath("$.adresse.ville").value("Ville1"));
	}

	@When("^le conseiller connecté à canal modifie l'adresse de l'abonné condition$")
	public void le_conseiller_connecté_à_canal_modifie_l_adresse_de_l_abonné_condition() throws Throwable {
		mockServer.perform(put("/contrats/1")//
				.content(
						"{\"id\":\"2\",\"numero\":\"22\",\"voie\":\"adresse2\",\"ville\":\"Ville2\",\"active\":true,\"pays\":\"France\",\"condition\":\"ADE\"}")//
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)//
				.accept(MediaType.APPLICATION_JSON_UTF8_VALUE))//
				.andExpect(status().isOk()).andExpect(jsonPath("$.adresse.id").value("2"));
	}

	@Then("^l'adresse de l'abonné modifiée est enregistrée sur l'ensemble des contrats de l'abonné$")
	public void l_adresse_de_l_abonné_modifiée_est_enregistrée_sur_l_ensemble_des_contrats_de_l_abonné()
			throws Throwable {
		mockServer.perform(get("/contrats/1")).andExpect(status().isOk())//
				.andExpect(content().contentType("application/json;charset=UTF-8"))//
				.andExpect(jsonPath("$.id").value("1"))//
				.andExpect(jsonPath("$.canal").value("canal1"))//
				.andExpect(jsonPath("$.adresse.id").value("2"))//
				.andExpect(jsonPath("$.adresse.ville").value("Ville2"));
	}

	@Then("^un mouvement de modification d'adresse est créés$")
	public void un_mouvement_de_modification_d_adresse_est_créés() throws Throwable {
		mockServer.perform(get("/contrats/1/audit")).andExpect(status().isOk())
				.andExpect(jsonPath("$.adresse.id").value("1")).andExpect(jsonPath("$.adresse.ville").value("Ville1"))
				.andExpect(jsonPath("$.adresse.condition").value(EnumCondition.SDE.name()));
	}
}
