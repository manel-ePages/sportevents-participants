package com.sportevents.participants;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantsRepository extends JpaRepository<Person, Long> {
    Person findByIdCard(String idCard);
}
