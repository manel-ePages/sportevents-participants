package sportevents.participants;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RelProvider;
import org.springframework.stereotype.Service;

@Service
public class ParticipantResourceAssembler
        extends EmbeddableResourceAssemblerSupport<Participant, ParticipantResource, ParticipantController> {

    @Autowired
    public ParticipantResourceAssembler(final EntityLinks entityLinks, final RelProvider relProvider) {
        super(entityLinks, relProvider, ParticipantController.class, ParticipantResource.class);
    }

    @Override
    public Link linkToSingleResource(Participant participant) {
        return entityLinks.linkToSingleResource(ParticipantResource.class, participant.getId());
    }

    private ParticipantResource toBaseResource(Participant participant) {
        // final ParticipantResource resource =
        // createResourceWithId(getId(participant) , participant);
//        final ParticipantResource resource = new ParticipantResource(participant.getId(), participant.getNif(),
//                participant.getNom());
        final ParticipantResource resource = ParticipantResource.resourceFactory(participant);

        return resource;
    }

    @Override
    public ParticipantResource toResource(Participant participant) {
        final ParticipantResource resource = this.toBaseResource(participant);
        // Add links to available actions
        addActionLinks(resource, participant);

        resource.add(linkTo(methodOn(ParticipantController.class).readParticipant(participant.getId())).withSelfRel());

        return resource;
    }

    private void addActionLinks(final ParticipantResource resource, final Participant participant) {
    }
}
