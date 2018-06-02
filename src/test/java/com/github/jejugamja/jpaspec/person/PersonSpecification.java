package com.github.jejugamja.jpaspec.person;

import com.github.jejugamja.jpaspec.Specificationable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
public class PersonSpecification implements Specificationable<Person> {

    private String name;
    private String email;
    // private Optional<String> email;

    @Override
    public Specification<Person> specification() {
        return where(eqName()
                .and(likeEmail())
        );
    }

    private Specification<Person> eqName() {
        return eq("name", name);
    }
    private Specification<Person> likeEmail() {
        return like("email", Optional.ofNullable(email).map(s -> "%" + s + "%"));
    }
}
