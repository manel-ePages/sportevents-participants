package sportevents.participants;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

//enum Genere {
//    Home, Dona
//}

@Entity
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Timestamp dataRegistre;

    private Timestamp dataUltimaModificacio;

    private String nif;

    private Date dataNaixement;

    private String nom;

    private String cognom1;

    private String cognom2;

    private String genere;

    private String adreça;

    private String codiPostal;

    private String poblacio;

    private String pais;

    private String telefon;

    private String telefonUrgencia;

    private String email;

    private boolean actiu;

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the dataRegistre
     */
    public Timestamp getDataRegistre() {
        return dataRegistre;
    }

    /**
     * @param dataRegistre
     *            the dataRegistre to set
     */
    public void setDataRegistre(Timestamp dataRegistre) {
        this.dataRegistre = dataRegistre;
    }

    /**
     * @return the dataUltimaModificacio
     */
    public Timestamp getDataUltimaModificacio() {
        return dataUltimaModificacio;
    }

    /**
     * @param dataUltimaModificacio
     *            the dataUltimaModificacio to set
     */
    public void setDataUltimaModificacio(Timestamp dataUltimaModificacio) {
        this.dataUltimaModificacio = dataUltimaModificacio;
    }

    /**
     * @return the nif
     */
    public String getNif() {
        return nif;
    }

    /**
     * @param nif
     *            the nif to set
     */
    public void setNif(String nif) {
        this.nif = nif;
    }

    /**
     * @return the dataNaixement
     */
    public Date getDataNaixement() {
        return dataNaixement;
    }

    /**
     * @param dataNaixement
     *            the dataNaixement to set
     */
    public void setDataNaixement(Date dataNaixement) {
        this.dataNaixement = dataNaixement;
    }

    /**
     * @return the nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * @param nom
     *            the nom to set
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * @return the cognom1
     */
    public String getCognom1() {
        return cognom1;
    }

    /**
     * @param cognom1
     *            the cognom1 to set
     */
    public void setCognom1(String cognom1) {
        this.cognom1 = cognom1;
    }

    /**
     * @return the cognom2
     */
    public String getCognom2() {
        return cognom2;
    }

    /**
     * @param cognom2
     *            the cognom2 to set
     */
    public void setCognom2(String cognom2) {
        this.cognom2 = cognom2;
    }

    /**
     * @return the genere
     */
    public String getGenere() {
        return genere;
    }

    /**
     * @param genere
     *            the genere to set
     */
    public void setGenere(String genere) {
        this.genere = genere;
    }

    /**
     * @return the adreça
     */
    public String getAdreça() {
        return adreça;
    }

    /**
     * @param adreça
     *            the adreça to set
     */
    public void setAdreça(String adreça) {
        this.adreça = adreça;
    }

    /**
     * @return the codiPostal
     */
    public String getCodiPostal() {
        return codiPostal;
    }

    /**
     * @param codiPostal
     *            the codiPostal to set
     */
    public void setCodiPostal(String codiPostal) {
        this.codiPostal = codiPostal;
    }

    /**
     * @return the poblacio
     */
    public String getPoblacio() {
        return poblacio;
    }

    /**
     * @param poblacio
     *            the poblacio to set
     */
    public void setPoblacio(String poblacio) {
        this.poblacio = poblacio;
    }

    /**
     * @return the pais
     */
    public String getPais() {
        return pais;
    }

    /**
     * @param pais
     *            the pais to set
     */
    public void setPais(String pais) {
        this.pais = pais;
    }

    /**
     * @return the telefon
     */
    public String getTelefon() {
        return telefon;
    }

    /**
     * @param telefon
     *            the telefon to set
     */
    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    /**
     * @return the telefonUrgencia
     */
    public String getTelefonUrgencia() {
        return telefonUrgencia;
    }

    /**
     * @param telefonUrgencia
     *            the telefonUrgencia to set
     */
    public void setTelefonUrgencia(String telefonUrgencia) {
        this.telefonUrgencia = telefonUrgencia;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email
     *            the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the actiu
     */
    public boolean isActiu() {
        return actiu;
    }

    /**
     * @param actiu
     *            the actiu to set
     */
    public void setActiu(boolean actiu) {
        this.actiu = actiu;
    }

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String participantRequestAsJson = mapper.writeValueAsString(this);

            return participantRequestAsJson;
        } catch (JsonProcessingException jpe) {
            return jpe.getMessage();
        }
    }

    public void fillForInsertion() {
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        this.setDataRegistre(currentTimestamp);
        this.setDataUltimaModificacio(currentTimestamp);
        this.setActiu(true);
    }
    
    public void fillForUpdate(Participant participant) {
        this.setDataRegistre(participant.getDataRegistre());
        this.setDataUltimaModificacio(new Timestamp(System.currentTimeMillis()));
        this.setActiu(participant.isActiu());
        this.setId(participant.getId());
    }
}
