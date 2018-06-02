package com.github.jejugamja.jpaspec;

import com.github.jejugamja.jpaspec.person.Person;
import com.github.jejugamja.jpaspec.person.PersonRepository;
import com.github.jejugamja.jpaspec.person.PersonService;
import com.github.jejugamja.jpaspec.person.PersonSpecification;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;

@DataJpaTest
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PersonSpecificationTest.PersonConfiguration.class)
public class PersonSpecificationTest {
    @Autowired
    private PersonService personService;
    @Autowired
    private PersonRepository personRepository;

    @Before
    public void setup() {
        Person person1 = new Person("name1", "a@a.com");
        personRepository.save(person1);
        Person person2 = new Person("name2", "b@a.com");
        personRepository.save(person2);
    }

    @Test
    public void eqTest() {
        PersonSpecification specification = new PersonSpecification("name1", null);
        List<Person> list = personService.findAll(specification);
        Assert.assertThat(list.get(0).getName(), is("name1"));
        Assert.assertThat(list.size(), is(1));

        specification.setName("name");
        list = personService.findAll(specification);
        Assert.assertThat(list.size(), is(0));
    }

    @Test
    public void optionalTest() {
        PersonSpecification specification = new PersonSpecification("name1", null);
        List<Person> list = personService.findAll(specification);
        Assert.assertThat(list.get(0).getName(), is("name1"));
        Assert.assertThat(list.size(), is(1));

        // like
        specification.setEmail("a.co.kr");
        list = personService.findAll(specification);
        Assert.assertThat(list.size(), is(0));

        // like
        specification.setEmail("a.com");
        list = personService.findAll(specification);
        Assert.assertThat(list.size(), is(1));
    }

    @TestConfiguration
    public static class PersonConfiguration {
        @Bean
        public PersonService personService(PersonRepository personRepository) {
            return new PersonService(personRepository);
        }
    }
}
