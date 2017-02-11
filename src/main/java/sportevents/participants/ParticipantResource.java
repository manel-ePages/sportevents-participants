package sportevents.participants;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.annotation.JsonCreator;

@Relation(value = "participant", collectionRelation = "participants")
public class ParticipantResource extends ResourceSupport {
    private final Long _id;
    private final String dataRegistre;
    private final String dataUltimaModificacio;
    private final String nif;
    private final String dataNaixement;
    private final String nom;
    private final String cognom1;
    private final String cognom2;
    private final String genere;
    private final String adreça;
    private final String codiPostal;
    private final String poblacio;
    private final String pais;
    private final String telefon;
    private final String telefonUrgencia;
    private final String email;

    // @JsonCreator
    // public ParticipantResource(@JsonProperty("_id") Long _id,
    // @JsonProperty("nif") String nif,
    // @JsonProperty("nom") String nom) {
    // this._id = _id;
    // this.nif = nif;
    // this.nom = nom;
    // }

    private ParticipantResource(Long _id, String dataRegistre, String dataUltimaModificacio, String nif,
            String dataNaixement, String nom, String cognom1, String cognom2, String genere, String adreça,
            String codiPostal, String poblacio, String pais, String telefon, String telefonUrgencia, String email) {
        this._id = _id;
        this.dataRegistre = dataRegistre;
        this.dataUltimaModificacio = dataUltimaModificacio;
        this.nif = nif;
        this.dataNaixement = dataNaixement;
        this.nom = nom;
        this.cognom1 = cognom1;
        this.cognom2 = cognom2;
        this.genere = genere;
        this.adreça = adreça;
        this.codiPostal = codiPostal;
        this.poblacio = poblacio;
        this.pais = pais;
        this.telefon = telefon;
        this.telefonUrgencia = telefonUrgencia;
        this.email = email;
    }

    @JsonCreator
    public static ParticipantResource resourceFactory(Participant participant) {
        return new ParticipantResource(participant.getId(), participant.getDataRegistre().toString(),
                participant.getDataUltimaModificacio().toString(), participant.getNif(),
                participant.getDataNaixement().toString(), participant.getNom(), participant.getCognom1(),
                participant.getCognom2(), participant.getGenere(), participant.getAdreça(), participant.getCodiPostal(),
                participant.getPoblacio(), participant.getPais(), participant.getTelefon(),
                participant.getTelefonUrgencia(), participant.getEmail());
    }

    /**
     * @return the _id
     */
    public Long get_id() {
        return _id;
    }

    /**
     * @return the dataRegistre
     */
    public String getDataRegistre() {
        return dataRegistre;
    }

    /**
     * @return the dataUltimaModificacio
     */
    public String getDataUltimaModificacio() {
        return dataUltimaModificacio;
    }

    /**
     * @return the nif
     */
    public String getNif() {
        return nif;
    }

    /**
     * @return the dataNaixement
     */
    public String getDataNaixement() {
        return dataNaixement;
    }

    /**
     * @return the nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * @return the cognom1
     */
    public String getCognom1() {
        return cognom1;
    }

    /**
     * @return the cognom2
     */
    public String getCognom2() {
        return cognom2;
    }

    /**
     * @return the genere
     */
    public String getGenere() {
        return genere;
    }

    /**
     * @return the adreça
     */
    public String getAdreça() {
        return adreça;
    }

    /**
     * @return the codiPostal
     */
    public String getCodiPostal() {
        return codiPostal;
    }

    /**
     * @return the poblacio
     */
    public String getPoblacio() {
        return poblacio;
    }

    /**
     * @return the pais
     */
    public String getPais() {
        return pais;
    }

    /**
     * @return the telefon
     */
    public String getTelefon() {
        return telefon;
    }

    /**
     * @return the telefonUrgencia
     */
    public String getTelefonUrgencia() {
        return telefonUrgencia;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }
}