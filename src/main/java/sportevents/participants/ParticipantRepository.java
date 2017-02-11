package sportevents.participants;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRepository extends JpaRepository<Participant, Long>{
    Participant findByNif(String nif);
}
