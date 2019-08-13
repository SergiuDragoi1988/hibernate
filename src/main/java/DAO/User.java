package DAO;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="User")

public class User {

    @Id
    private int id;
    private LocalDate birthDate;

    public long getAge() {
        return age;
    }

    @Transient
    private long age;

    @PostLoad
    public void calculateAge() {
        age = ChronoUnit.YEARS.between(birthDate, LocalDate.now());
    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;

    }

    private String name;
    private String email;

    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(mappedBy = "owner")
    private List<Book> books;

    public Address getAddress() {
        return address;
    }

    public User() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}

