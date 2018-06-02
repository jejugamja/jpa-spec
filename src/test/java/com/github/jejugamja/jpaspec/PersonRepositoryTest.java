package com.github.jejugamja.jpaspec;

import com.github.jejugamja.jpaspec.person.Person;
import com.github.jejugamja.jpaspec.person.PersonRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.is;

@SpringBootTest
@RunWith(SpringRunner.class)
@DataJpaTest
public class PersonRepositoryTest {

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
    public void test() {

        Assert.assertThat(personRepository.findAll().size(), is(2));
    }
}
