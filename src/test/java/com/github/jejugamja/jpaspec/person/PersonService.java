package com.github.jejugamja.jpaspec.person;


import com.github.jejugamja.jpaspec.JpaSpecificationRepository;
import com.github.jejugamja.jpaspec.SpecificationService;
import org.springframework.stereotype.Service;

@Service
public class PersonService implements SpecificationService<Person, Long> {

    private PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public JpaSpecificationRepository<Person, Long> getRepository() {
        return personRepository;
    }
}
