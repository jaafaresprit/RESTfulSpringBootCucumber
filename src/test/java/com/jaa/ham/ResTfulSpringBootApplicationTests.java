package com.jaa.ham;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.jaa.ham.entities.EnumCondition;

@Ignore
@SpringBootTest(classes = ResTfulSpringBootApplication.class)
public class ResTfulSpringBootApplicationTests extends AbstractTestNGSpringContextTests {

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	@BeforeClass
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	
	@Test(priority=0)
	public void test_change_adresse() throws Exception{
		mockMvc.perform(put("/contrats/1")//
				.content("{\"id\":\"2\",\"numero\":\"22\",\"voie\":\"adresse2\",\"ville\":\"Ville2\",\"active\":true,\"pays\":\"France\",\"condition\":\"ADE\"}")//
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)//
				.accept(MediaType.APPLICATION_JSON_UTF8_VALUE))//
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.adresse.id").value("2"));
	}
	
	@Test(priority=1)
	public void test_get_last_adresse_audit() throws Exception {
		mockMvc.perform(get("/contrats/1/audit")).andExpect(status().isOk())
		.andExpect(jsonPath("$.adresse.id").value("1"))
		.andExpect(jsonPath("$.adresse.condition").value(EnumCondition.SDE.name()));
	}
	

}
