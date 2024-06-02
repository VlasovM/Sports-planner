package ru.javlasov.sportsplanner.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Table(name = "users")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@NamedEntityGraph(name = "author-genre-entity-graph",
        attributeNodes = {@NamedAttributeNode("trains"), @NamedAttributeNode("tournaments"),
                @NamedAttributeNode("checkUp")})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "age", nullable = false)
    private Integer age;

    @Column(name = "birthday", nullable = false)
    private LocalDate birthday;

    @Column(name = "biography")
    private String biography;

    @Column(name = "sport_id")
    private Long sport;

    @OneToMany(mappedBy = "user")
    private List<Article> articles;

    @OneToMany(mappedBy = "user")
    private List<Train> trains;

    @OneToMany(mappedBy = "user")
    private List<Tournament> tournaments;

    @OneToMany(mappedBy = "user")
    private List<Health> checkUp;

}
