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
        final Resources<ParticipantResource> wrapped = participantResourceAssembler.toEmbeddedList(participants);

        return ResponseEntity.ok(wrapped);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<ParticipantResource> readParticipant(@PathVariable("id") Long id) {
        if (participantsRepository.exists(id)) {
            final Person participant = participantsRepository.getOne(id);

            final ParticipantResource participantResource = participantResourceAssembler.toResource(participant);

            return ResponseEntity.ok(participantResource);
        } else {
            return new ResponseEntity<ParticipantResource>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ParticipantResource> createParticipant(@RequestBody ParticipantsRequest participantsRequest) {

        if (participantsRequest.isValid()) {
            Person insertParticipant = participantsRequest.generatePerson();
            insertParticipant.fillForInsertion();
            Person newParticipant = participantsRepository.save(insertParticipant);
            
            final ParticipantResource newParticipantResource = participantResourceAssembler.toResource(newParticipant);

            return ResponseEntity.ok(newParticipantResource);
        } else {
            LOG.info(String.format("Invalid Request: \n%s", participantsRequest.toString()));

            return new ResponseEntity<ParticipantResource>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<ParticipantResource> updateParticipant(@PathVariable("id") Long id,
            @RequestBody ParticipantsRequest participantsRequest) {
        if (participantsRequest.isValid()) {
            if (participantsRepository.exists(id)) {
                final Person currentParticipant = participantsRepository.findOne(id);
                Person updateParticipant = participantsRequest.generatePerson();
                updateParticipant.fillForUpdate(currentParticipant);
                participantsRepository.save(updateParticipant);

                final ParticipantResource updateParticipantResource = participantResourceAssembler
                        .toResource(updateParticipant);

                return ResponseEntity.ok(updateParticipantResource);
            } else {
                return new ResponseEntity<ParticipantResource>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<ParticipantResource>(HttpStatus.NOT_FOUND);
        }

    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteParticipant(@PathVariable("id") Long id) {
        if (participantsRepository.exists(id)) {
            Person inactiveParticipant = participantsRepository.getOne(id);
            if (inactiveParticipant.isOperational()) {
                inactiveParticipant.setOperational(false);
            }

            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path = "/callback", method = RequestMethod.POST)
    public ResponseEntity<Void> processCallback() {
        System.out.println("INSIDE CALLBACK: ");
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}
