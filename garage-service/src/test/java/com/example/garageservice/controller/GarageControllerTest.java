package com.example.garageservice.controller;

import com.example.garageservice.model.Car;
import com.example.garageservice.model.Contact;
import com.example.garageservice.model.Garage;
import com.example.garageservice.model.Owner;
import com.example.garageservice.util.BaseIntegrationTest;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser(value = "user")
public class GarageControllerTest extends BaseIntegrationTest {
    private Owner owner = new Owner("Vasya", "Pupkin");
    private Contact contact = new Contact("2555555");
    private Car car = new Car("Tesla","S","AA5555IM");
    private Garage testGarage = new Garage("1", 1, owner, contact, car);
    private final static int UPDATED_GARAGE_NUMBER = 2;

    @Test
    public void testCreateGarage() throws Exception {
        String jsonContent = mapper.writeValueAsString(testGarage);
        mockMvc
                .perform(MockMvcRequestBuilders.post("/createGarage").accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON).content(jsonContent))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();

    }

    @Test
    public void testCreateGarageWhenGarageAlreadyExists() throws Exception {
        mongoTemplate.save(testGarage);
        Owner owner = new Owner("Tamara", "Muratovna");
        Contact contact = new Contact("2555556");
        Car car = new Car("Zaporozhets","gorbatiy","AA1234IM");
        Garage testGarage2 = new Garage("2", 1, owner, contact, car);
        String jsonContent = mapper.writeValueAsString(testGarage2);
        String response =
        mockMvc
                .perform(MockMvcRequestBuilders.post("/createGarage").accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON).content(jsonContent))
                .andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();
        assertThat(response, is("Garage already exist"));

    }

    @Test
    public void testCreateGarageWithNoOwner() throws Exception {
        Owner owner = new Owner("Vasya", "Pupkin");
        Car car = new Car("Tesla","S","AA5555IM");
        Garage garage = new Garage("1", 1, owner, null, car);
        String jsonContent = mapper.writeValueAsString(garage);
        String response =
        mockMvc
                .perform(MockMvcRequestBuilders.post("/createGarage").accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON).content(jsonContent))
                .andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();
        assertThat(response, containsString("Please provide a contact information"));
    }

    @Test
    public void testListGarages() throws Exception {
        Owner owner = new Owner("Evdokiya", "Evdohovna");
        Car car = new Car("Lada","Kalina","AA4321IM");
        Contact contact = new Contact("1234567");
        Garage testGarage2 = new Garage("2", 2, owner, contact, car);
        List<Garage> expectedList = new ArrayList<>();
        expectedList.add(testGarage);
        expectedList.add(testGarage2);
        for (Garage garage: expectedList) {
            mongoTemplate.save(garage);
        }
                mockMvc.perform( MockMvcRequestBuilders
      .get("/getGarages")
      .accept(MediaType.APPLICATION_JSON))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$").isArray())
      .andExpect(jsonPath("$", hasSize(2)))
      .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", is("1")))
      .andExpect(MockMvcResultMatchers.jsonPath("$[0].garageNumber", is(1)))
                        .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", is("2")))
                        .andExpect(MockMvcResultMatchers.jsonPath("$[1].garageNumber", is(2)));
    }

    @Test
    public void testGetByCarNumber() throws Exception {
        mongoTemplate.save(testGarage);
        mockMvc.perform( MockMvcRequestBuilders
                .get("/getbyCarNumber/{carnumber}", "AA5555IM")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", is("1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].garageNumber", is(1)));
    }

    @Test
    public void testGetByOwnerPhoneNumber() throws Exception {
        mongoTemplate.save(testGarage);
        mockMvc.perform( MockMvcRequestBuilders
                .get("/getbyOwnerPhoneNumber/{owner-phonenumber}", "2555555")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", is("1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].garageNumber", is(1)));
    }

    @Test
    public void testGetByOwnerSurName() throws Exception {
        mongoTemplate.save(testGarage);
        mockMvc.perform( MockMvcRequestBuilders
                .get("/getbyOwnerSurname/{owner-surname}", "Pupkin")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", is("1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].garageNumber", is(1)));
    }

    @Test
    public void testGetById() throws Exception {
        mongoTemplate.save(testGarage);
        mockMvc.perform( MockMvcRequestBuilders
                .get("/getbyid/{garage-id}", "1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is("1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.garageNumber", is(1)));
    }

    @Test
    public void testGetByGarageNumber() throws Exception {
        mongoTemplate.save(testGarage);
        mockMvc.perform( MockMvcRequestBuilders
                .get("/getbyGarageNumber/{garagenumber}", 1)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is("1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.garageNumber", is(1)));
    }

    @Test
    public void testUpdateGarage() throws Exception {
        mongoTemplate.save(testGarage);
        Owner owner = new Owner("Vasya", "Pupkin");
        Contact contact = new Contact("2555555");
        Car car = new Car("Tesla","S","AA5555IM");
        Garage testGarage2 = new Garage("1", UPDATED_GARAGE_NUMBER, owner, contact, car);
        String jsonContent = mapper.writeValueAsString(testGarage2);
        mockMvc.perform( MockMvcRequestBuilders
                .put("/updateGarage/{id}", 1)
                .content(jsonContent)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is("1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.garageNumber", is(UPDATED_GARAGE_NUMBER)));
    }

    @Test
    public void testDeleteGarage() throws Exception {
        mongoTemplate.save(testGarage);
        ResultActions resultActions = mockMvc.perform( MockMvcRequestBuilders
                .delete("/deleteGarage/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        assertThat(contentAsString, is("Contact record for employee-id= 1 deleted."));
        List<Garage> allGarages = mongoTemplate.findAll(Garage.class);
        assertThat(allGarages.isEmpty(), is(true));
    }
}
