# jpa-spec

### Example

**Repository**

```java
public interface PersonRepository extends JpaRepository<Person, Long>, JpaSpecificationExecutor<Person> {
}    
```
또는 

```java
public interface PersonRepository extends JpaSpecificationRepository<Person, Long> {
}    
```
