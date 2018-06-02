package com.github.jejugamja.jpaspec.person;

import com.github.jejugamja.jpaspec.JpaSpecificationRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaSpecificationRepository<Person, Long> {
}
