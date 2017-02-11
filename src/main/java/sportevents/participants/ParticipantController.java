package sportevents.participants;

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
@RequestMapping("/participant")
public class ParticipantController {
    private static Logger LOG = LoggerFactory.getLogger(ParticipantController.class);
    private final ParticipantRepository participantRepository;
    private final ParticipantResourceAssembler participantResourceAssembler;

    @Autowired
    public ParticipantController(ParticipantRepository participantRepository,
            ParticipantResourceAssembler participantResourceAssembler) {
        this.participantRepository = participantRepository;
        this.participantResourceAssembler = participantResourceAssembler;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Resources<ParticipantResource>> readParticipants() {
        final Iterable<Participant> participants = participantRepository.findAll();
        final Resources<ParticipantResource> wrapped = participantResourceAssembler.toEmbeddedList(participants);

        return ResponseEntity.ok(wrapped);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<ParticipantResource> readParticipant(@PathVariable("id") Long id) {
        if (participantRepository.exists(id)) {
            final Participant participant = participantRepository.getOne(id);

            final ParticipantResource participantResource = participantResourceAssembler.toResource(participant);

            return ResponseEntity.ok(participantResource);
        } else {
            return new ResponseEntity<ParticipantResource>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ParticipantResource> createParticipant(@RequestBody ParticipantRequest participantRequest) {

        if (participantRequest.isValid()) {
            Participant insertParticipant = participantRequest.generateParticipant();
            insertParticipant.fillForInsertion();
            Participant newParticipant = participantRepository.save(insertParticipant);
            
            final ParticipantResource newParticipantResource = participantResourceAssembler.toResource(newParticipant);

            return ResponseEntity.ok(newParticipantResource);
        } else {
            LOG.info(String.format("Invalid Request: \n%s", participantRequest.toString()));

            return new ResponseEntity<ParticipantResource>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<ParticipantResource> updateParticipant(@PathVariable("id") Long id,
            @RequestBody ParticipantRequest participantRequest) {
        if (participantRequest.isValid()) {
            if (participantRepository.exists(id)) {
                final Participant currentParticipant = participantRepository.findOne(id);
                Participant updateParticipant = participantRequest.generateParticipant();
                updateParticipant.fillForUpdate(currentParticipant);
                participantRepository.save(updateParticipant);

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
        if (participantRepository.exists(id)) {
            Participant inactiveParticipant = participantRepository.getOne(id);
            if (inactiveParticipant.isActiu()) {
                inactiveParticipant.setActiu(false);
            }

            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
    }
}
