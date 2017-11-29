package com.sportevents.participants;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

@Relation(value = "participant", collectionRelation = "participants")
public class ParticipantResource extends ResourceSupport {
    private final Person delegate;


    protected ParticipantResource(Person delegate) {
        this.delegate = delegate;
    }

//    @JsonCreator
//    public static ParticipantResource resourceFactory(Participant participant) {
//        return new ParticipantResource(participant.getId(), participant.getDataRegistre().toString(),
//                participant.getDataUltimaModificacio().toString(), participant.getNif(),
//                participant.getDataNaixement().toString(), participant.getNom(), participant.getCognom1(),
//                participant.getCognom2(), participant.getGenere(), participant.getAdreça(), participant.getCodiPostal(),
//                participant.getPoblacio(), participant.getPais(), participant.getTelefon(),
//                participant.getTelefonUrgencia(), participant.getEmail());
//    }

    public Long get_id() {
        return delegate.getId();
    }

    public String getRegistrationDate() {
        return delegate.getRegistrationDate().toString();
    }

    public String getModificationDate() {
        return delegate.getModificationDate().toString();
    }

    public String getIdCard() {
        return delegate.getIdCard();
    }

    public String getBirthDate() {
        return delegate.getBirthDate().toString();
    }

    public String getName() {
        return delegate.getName();
    }

    public String getFirstSurname() {
        return delegate.getFirstSurname();
    }

    public String getSecondSurname() {
        return delegate.getSecondSurname();
    }

    public String getGender() {
        return delegate.getGender();
    }

    public String getStreet() {
        return delegate.getStreet();
    }

    public String getZipCode() {
        return delegate.getZipCode();
    }

    public String getCity() {
        return delegate.getCity();
    }

    public String getState() {
        return delegate.getState();
    }

    public String getCountry() {
        return delegate.getCountry();
    }

    public String getPhoneNumber() {
        return delegate.getPhoneNumber();
    }

    public String getEmergencyPhoneNumber() {
        return delegate.getEmergencyPhoneNumber();
    }

    public String getEmail() {
        return delegate.getEmail();
    }
}
