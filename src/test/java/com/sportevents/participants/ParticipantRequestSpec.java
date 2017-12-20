package com.sportevents.participants;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ParticipantRequestSpec {

    private ParticipantsRequest participantsRequest;

    @Before
    public void setup() {
        participantsRequest = new ParticipantsRequest();

        participantsRequest.setIdCard("00000001R");
        participantsRequest.setBirthDate("1990-01-01");
        participantsRequest.setName("Juan");
        participantsRequest.setFirstSurname("García");
        participantsRequest.setSecondSurname("Pérez");
        participantsRequest.setGender("masculine");
        participantsRequest.setStreet("Camelias, 4");
        participantsRequest.setZipCode("17310");
        participantsRequest.setCity("Lloret de Mar");
        participantsRequest.setState("Girona");
        participantsRequest.setPhoneNumber("972456232");
        participantsRequest.setEmail("juan.garcia.perez@gmail.com");
    }

    @Test
    public void whenParticipantRequestFullyInitializedThenIsValid() {
        assertTrue(participantsRequest.isValid());
    }

    @Test
    public void whenParticipantRequestHasNoOperationalAttributeThenPersonIsNotOperational() {
        Person person = participantsRequest.generatePerson();
        assertFalse(person.isOperational());
    }
}
