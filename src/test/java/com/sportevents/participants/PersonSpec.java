package com.sportevents.participants;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PersonSpec {

    ParticipantsRequest participantsRequest;

    Person participant;

    @Before
    public void setup() {
        participantsRequest = new ParticipantsRequest();
        participantsRequest.setIdCard("00000001R");
        participantsRequest.setBirthDate("1990-01-01");
        participantsRequest.setName("Juan");
        participantsRequest.setFirstSurname("García");
        participantsRequest.setSecondSurname("Pérez");
        participantsRequest.setGender("Male");
        participantsRequest.setStreet("Camelias, 4");
        participantsRequest.setZipCode("17310");
        participantsRequest.setCity("Lloret de Mar");
        participantsRequest.setState("Girona");
        participantsRequest.setPhoneNumber("972111111");
        participantsRequest.setEmail("juan.garcia.perez@gmail.com");

        participant = participantsRequest.generatePerson();
    }

    @Test
    public void givenParticipantFromRequestWhenFillForInsertionThenDateFieldsAreSet() {
        assertNull(participant.getRegistrationDate());
        assertNull(participant.getModificationDate());
        participant.fillForInsertion();
        assertNotNull(participant.getRegistrationDate());
        assertNotNull(participant.getModificationDate());
        assertEquals(participant.getRegistrationDate(), participant.getModificationDate());
    }

    @Test
    public void givenParticipantFromRequestWhenFillForUpdateThenModificationDateFieldIsSet() {
        Person updaterParticipant = participantsRequest.generatePerson();
        updaterParticipant.fillForUpdate(participant);
        assertNotNull(updaterParticipant.getModificationDate());
    }

    @Test
    public void givenParticipantFromRequestWhenFillForUpdateThenRequestChangesAreApplied() {
        participantsRequest.setPhoneNumber("972333333");
        Person updaterParticipant = participantsRequest.generatePerson();
        updaterParticipant.fillForUpdate(participant);
        assertEquals("972333333", updaterParticipant.getPhoneNumber());
    }

    @Test
    public void givenParticipantFromRequestWhenFillForUpdateThenIdGetsCopiedFromUpdatedParticipant() {
        participant.setId(25L);
        Person updaterParticipant = participantsRequest.generatePerson();
        updaterParticipant.fillForUpdate(participant);
        assertEquals(participant.getId(), updaterParticipant.getId());
    }
}
