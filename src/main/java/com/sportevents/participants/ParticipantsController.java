package com.sportevents.participants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/participants")
public class ParticipantsController {
    private static Logger LOG = LoggerFactory.getLogger(ParticipantsController.class);
    private final ParticipantsRepository participantsRepository;
    private final ParticipantResourceAssembler participantResourceAssembler;

    @Autowired
    public ParticipantsController(ParticipantsRepository participantsRepository,
            ParticipantResourceAssembler participantResourceAssembler) {
        this.participantsRepository = participantsRepository;
        this.participantResourceAssembler = participantResourceAssembler;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Resources<ParticipantResource>> readParticipants() {
        final Iterable<Person> participants = participantsRepository.findAll();
        final Resources<ParticipantResource> wrappedParticipants = participantResourceAssembler.toEmbeddedList(participants);

        return ResponseEntity.ok(wrappedParticipants);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<ParticipantResource> readParticipant(@PathVariable("id") Long id) {
        if (!participantsRepository.exists(id)) {
            return new ResponseEntity<ParticipantResource>(HttpStatus.NOT_FOUND);
        }

        final Person participant = participantsRepository.getOne(id);

        final ParticipantResource participantResource = participantResourceAssembler.toResource(participant);

        return ResponseEntity.ok(participantResource);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ParticipantResource> createParticipant(@RequestBody ParticipantsRequest participantsRequest) {
        if (!participantsRequest.isValid()) {
            LOG.info(String.format("Invalid Request: %s%n", participantsRequest.toString()));
            return new ResponseEntity<ParticipantResource>(HttpStatus.BAD_REQUEST);
        }

        if (participantsRepository.findByIdCard(participantsRequest.getIdCard()) != null) {
            LOG.info(String.format("%s Id Card for new Participant already exists%n", participantsRequest.getIdCard()));
            return new ResponseEntity<ParticipantResource>(HttpStatus.CONFLICT);
        }

        Person participantToAdd = participantsRequest.generatePerson();
        participantToAdd.fillForInsertion();

        Person addedParticipant = participantsRepository.save(participantToAdd);

        final ParticipantResource addedParticipantResource = participantResourceAssembler.toResource(addedParticipant);

        return ResponseEntity.ok(addedParticipantResource);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<ParticipantResource> updateParticipant(@PathVariable("id") Long id,
            @RequestBody ParticipantsRequest participantsRequest) {
        if (!participantsRequest.isValid()) {
            return new ResponseEntity<ParticipantResource>(HttpStatus.BAD_REQUEST);
        }

        if (!participantsRepository.exists(id)) {
            return new ResponseEntity<ParticipantResource>(HttpStatus.NOT_FOUND);
        }

        final Person currentParticipant = participantsRepository.findOne(id);
        Person updateParticipant = participantsRequest.generatePerson();
        updateParticipant.fillForUpdate(currentParticipant);
        participantsRepository.save(updateParticipant);

        final ParticipantResource updateParticipantResource = participantResourceAssembler.toResource(updateParticipant);

        return ResponseEntity.ok(updateParticipantResource);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteParticipant(@PathVariable("id") Long id) {
        if (!participantsRepository.exists(id)) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }

        Person participantToSetInactive = participantsRepository.findOne(id);

        if (participantToSetInactive.isOperational()) {
            participantToSetInactive.setOperational(false);
            participantsRepository.save(participantToSetInactive);
        }

        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}
