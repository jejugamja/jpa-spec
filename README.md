# jpa-spec

Specification patter을 조금 더 쉽게 사용할 수 있게 만들어 봤습니다.
서비스 레이어에 SpecificationService implemets 한후 
Specificationable에 검색 로직을 구현합니다.

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

**Specificationable**
```java
public class PersonSpecification implements Specificationable<Person> {
private String name;
    private String email;
    // private Optional<String> email = Optional.empty();

    @Override
    public Specification<Person> specification() {
        return where(eqName()
                .and(likeEmail())
        );
    }

    private Specification<Person> eqName() {
        return eq("name", name);
    }

    // Optional 검색. email이 null인경우 검색 제외
    private Specification<Person> likeEmail() {
        return like("email", Optional.ofNullable(email));
    }
}
```
