# jpa-spec

### Example

**Repository**

```java
@Repository
public interface PersonRepository extends JpaRepository<Person, Long>, JpaSpecificationExecutor<Person> {
}    
```
또는 

```java
@Repository
public interface PersonRepository extends JpaSpecificationRepository<Person, Long> {
}    
```

**Service**

```java
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
```

**Specificationable**
```java
public class PersonSpecification implements Specificationable<Person> {
    private String name;
    @Override
    public Specification<Person> specification() {
        return where(eq("name", name));
    }

    public void setName(String name) {
        this.name = name;
    }
}
```

**Controller**
```java
@Controller
@RequestMapping("/person")
public class PersonController {

    private PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public List<Person> list(PersonSpecification specification) {
        return personService.findAll(specification);
    }

    @GetMapping
    public Page<Person> page(PersonSpecification specification, Pageable pageable) {
        return personService.findAll(specification, pageable);
    }
}
```
