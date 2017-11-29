package com.sportevents.participants;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RelProvider;
import org.springframework.stereotype.Service;

@Service
public class ParticipantResourceAssembler
        extends EmbeddableResourceAssemblerSupport<Person, ParticipantResource, ParticipantsController> {

    @Autowired
    public ParticipantResourceAssembler(final EntityLinks entityLinks, final RelProvider relProvider) {
        super(entityLinks, relProvider, ParticipantsController.class, ParticipantResource.class);
    }

    @Override
    public Link linkToSingleResource(Person participant) {
        return entityLinks.linkToSingleResource(ParticipantResource.class, participant.getId());
    }

    private ParticipantResource toBaseResource(Person participant) {
        // final ParticipantResource resource =
        // createResourceWithId(getId(participant) , participant);
//        final ParticipantResource resource = new ParticipantResource(participant.getId(), participant.getIdCard(),
//                participant.getName());
//        final ParticipantResource resource = new ParticipantResource(participant);

        return new ParticipantResource(participant);
    }

    @Override
    public ParticipantResource toResource(Person participant) {
        final ParticipantResource resource = this.toBaseResource(participant);
        // Add links to available actions
        addActionLinks(resource, participant);

        resource.add(linkTo(methodOn(ParticipantsController.class).readParticipant(participant.getId())).withSelfRel());

        return resource;
    }

    private void addActionLinks(final ParticipantResource resource, final Person participant) {
    }
}
