package com.sportevents.participants;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.apache.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.port;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ParticipantsControllerSpec {

    private static final Person PERSON1 = new Person();

    private static final Person PERSON2 = new Person();

    static {
        PERSON1.setId(1L);
        PERSON1.setIdCard("00000001R");
        PERSON1.setBirthDate(Date.valueOf("1990-05-20"));
        PERSON1.setName("Juan");
        PERSON1.setFirstSurname("García");
        PERSON1.setSecondSurname("Pérez");
        PERSON1.setGender("masculine");
        PERSON1.setStreet("Camelias, 4");
        PERSON1.setZipCode("17310");
        PERSON1.setCity("Lloret de Mar");
        PERSON1.setState("Girona");
        PERSON1.setCountry("Spain");
        PERSON1.setPhoneNumber("972456232");
        PERSON1.setEmergencyPhoneNumber("657895435");
        PERSON1.setEmail("juan.garcia.perez@gmail.com");
        PERSON1.fillForInsertion(Timestamp.valueOf("2017-11-02 18:44:06.000"), Timestamp.valueOf("2017-11-21 09:10:32.140"), true);

        PERSON2.setId(2L);
        PERSON2.setIdCard("00000002W");
        PERSON2.setBirthDate(Date.valueOf("1985-10-06"));
        PERSON2.setName("Susana");
        PERSON2.setFirstSurname("Pujol");
        PERSON2.setSecondSurname("Cañas");
        PERSON2.setGender("feminine");
        PERSON2.setStreet("Avda Rambla Mestre Torrents, 6 1er 2a");
        PERSON2.setZipCode("08430");
        PERSON2.setCity("La Roca del Vallès");
        PERSON2.setState("Barcelona");
        PERSON2.setCountry("Spain");
        PERSON2.setPhoneNumber("938421244");
        PERSON2.setEmergencyPhoneNumber("665821005");
        PERSON2.setEmail("susana.pujol22@hotmail.com");
        PERSON2.fillForInsertion(Timestamp.valueOf("2017-11-09 08:04:22.356"), Timestamp.valueOf("2017-11-09 08:04:22.356"), true);
    }

    private static final List<Person> participants = new ArrayList<>();

    static {
        participants.add(PERSON1);
        participants.add(PERSON2);
    }

    private static final ParticipantsRequest participantsRequest = new ParticipantsRequest();

    static {
        participantsRequest.setIdCard("00000003A");
        participantsRequest.setBirthDate("1978-05-14");
        participantsRequest.setName("Mauricio");
        participantsRequest.setFirstSurname("López");
        participantsRequest.setSecondSurname("Vega");
        participantsRequest.setGender("masculine");
        participantsRequest.setStreet("C/Estrasburgo, 5");
        participantsRequest.setZipCode("08304");
        participantsRequest.setCity("Mataró");
        participantsRequest.setState("Barcelona");
        participantsRequest.setCountry("Spain");
        participantsRequest.setPhoneNumber("937988298");
        participantsRequest.setEmergencyPhoneNumber("901120084");
        participantsRequest.setEmail("mauro.lopez@gmail.com");
    }

    @MockBean
    ParticipantsRepository participantsRepository;

    @LocalServerPort
    int randomPort;

    @Before
    public void setup() {
        port = randomPort;
    }

    @Test
    public void givenParticipantsInRepositoryWhenGettingParticipantsThenReturnAllResourcesOfParticipantResource() {
        given(this.participantsRepository.findAll()).willReturn(participants);

        when()
                .get("/participants")
        .then()
                .statusCode(HttpStatus.SC_OK)
                .and().contentType(ContentType.JSON)
                .and().body("_embedded.participants.idCard", hasItems(participants.stream().map(Person::getIdCard).toArray()))
                .and().body("_embedded.participants._links.self.href",
                        hasItems(participants.stream().map(Person::getId).map(id -> String.format("http://localhost:%d/participants/%d", randomPort, id)).toArray()));
    }

    @Test
    public void givenNoParticipantsInRepositoryWhenGettingParticipantsThenReturnEmpty() {
        given(this.participantsRepository.findAll()).willReturn(new ArrayList<Person>());

        when()
                .get("/participants")
        .then()
                .statusCode((HttpStatus.SC_OK))
                .and().contentType(ContentType.JSON)
                .and().body("_embedded", isEmptyOrNullString())
                .and().body("_links.self.href", equalTo(String.format("http://localhost:%d/participants", randomPort)));
    }

    @Test
    public void whenGettingExistingParticipantByIdThenReturnParticipantResource() {
        given(this.participantsRepository.exists(PERSON2.getId())).willReturn(true);
        given(this.participantsRepository.getOne(PERSON2.getId())).willReturn(PERSON2);

        when()
                .get("/participants/2")
        .then()
                .statusCode(HttpStatus.SC_OK)
                .and().contentType(ContentType.JSON)
                .and().body("idCard", equalTo(PERSON2.getIdCard()))
                .and().body("_links.self.href", equalTo(String.format("http://localhost:%d/participants/%d", randomPort, PERSON2.getId())));
    }

    @Test
    public void whenGettingNotExistingParticipantByIdThenNotFound() {
        given(this.participantsRepository.exists(anyLong())).willReturn(false);

        when()
                .get("/participants/2")
        .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    public void givenInvalidParticipantsRequestWhenPostingRequestThenBadRequest() {
        ParticipantsRequest invalidParticipantsRequest = new ParticipantsRequest();
        invalidParticipantsRequest.setIdCard("00000004G");

        given()
                .contentType(ContentType.JSON)
                .and().body(invalidParticipantsRequest.toString())
        .when()
                .post("/participants")
        .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    public void givenAlreadyExistingNifParticipantWhenPostingRequestThenConflict() {
        Person participantRequestedAlreadyAdded = participantsRequest.generatePerson();
        participantRequestedAlreadyAdded.fillForInsertion();
        participantRequestedAlreadyAdded.setId(1L);

        org.mockito.BDDMockito.given(this.participantsRepository.save(any(Person.class))).willThrow(Exception.class);
        org.mockito.BDDMockito.given(this.participantsRepository.findByIdCard(participantRequestedAlreadyAdded.getIdCard())).willReturn(participantRequestedAlreadyAdded);

        given()
                .contentType(ContentType.JSON)
                .and().body(participantsRequest.toString())
                .when()
                .post("/participants")
                .then()
                .statusCode(HttpStatus.SC_CONFLICT);
    }

    @Test
    public void givenValidParticipantsRequestWhenPostingRequestThenReturnCreatedParticipantResource() {
        Person participantRequestedToAdd = participantsRequest.generatePerson();
        participantRequestedToAdd.fillForInsertion();
        participantRequestedToAdd.setId(1L);

        org.mockito.BDDMockito.given(this.participantsRepository.save(any(Person.class))).willReturn(participantRequestedToAdd);

        given()
                .contentType(ContentType.JSON)
                .and().body(participantsRequest.toString())
        .when()
                .post("/participants")
        .then()
                .statusCode(HttpStatus.SC_OK)
                .and().body("idCard", equalTo(participantRequestedToAdd.getIdCard()))
                .and().body("_links.self.href", equalTo(String.format("http://localhost:%d/participants/%d", randomPort, 1)));
    }

    @Test
    public void givenInvalidParticipantsRequestWhenPuttingRequestThenBadRequest() {
        ParticipantsRequest invalidParticipantsRequest = new ParticipantsRequest();
        invalidParticipantsRequest.setIdCard("00000004G");

        given()
                .contentType(ContentType.JSON)
                .and().body(invalidParticipantsRequest.toString())
        .when()
                .put("/participants/1")
        .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    public void givenNotExistingParticipantRequestIdWhenPuttingRequestThenNotFound() {
        org.mockito.BDDMockito.given(this.participantsRepository.exists(1L)).willReturn(false);
        org.mockito.BDDMockito.given(this.participantsRepository.findOne(1L)).willReturn(null);

        given()
                .contentType(ContentType.JSON)
                .and().body(participantsRequest.toString())
        .when()
                .put("/participants/1")
        .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    public void givenExistingParticipantRequestIdWhenPuttingRequestThenReturnUpdatedParticipantResource() {
        Person participantRequestedAlreadyAdded = participantsRequest.generatePerson();
        participantRequestedAlreadyAdded.fillForInsertion();
        participantRequestedAlreadyAdded.setId(1L);
        participantRequestedAlreadyAdded.setEmail("mauro.lopez@hotmail.com");

        org.mockito.BDDMockito.given(this.participantsRepository.exists(1L)).willReturn(true);
        org.mockito.BDDMockito.given(this.participantsRepository.findOne(1L)).willReturn(participantRequestedAlreadyAdded);

        given()
                .contentType(ContentType.JSON)
                .and().body(participantsRequest.toString())
        .when()
                .put("/participants/1")
        .then()
                .statusCode(HttpStatus.SC_OK)
                .and().body("email", equalTo("mauro.lopez@gmail.com"));
    }

    @Test
    public void givenNotExistingParticipantRequestIdWhenDeletingRequestThenNotFound() {
        org.mockito.BDDMockito.given(this.participantsRepository.exists(1L)).willReturn(false);

        given()
                .contentType(ContentType.JSON)
        .when()
                .delete("/participants/1")
        .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    public void givenExistingParticipantRequestIdWhenDeletingRequestThenNoContent() {
        Person participantRequestedAlreadyAdded = participantsRequest.generatePerson();
        participantRequestedAlreadyAdded.fillForInsertion();
        participantRequestedAlreadyAdded.setId(1L);

        org.mockito.BDDMockito.given(this.participantsRepository.exists(1L)).willReturn(true);
        org.mockito.BDDMockito.given(this.participantsRepository.findOne(1L)).willReturn(participantRequestedAlreadyAdded);

        given()
                .contentType(ContentType.JSON)
        .when()
                .delete("/participants/1")
        .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }
}
