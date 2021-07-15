package ru.butakov.teachertelegrambot.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Entity
@Table(name = "activities")
@NoArgsConstructor
@AllArgsConstructor
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Lob
    @Type(type="text")
    String quiz;

    @Lob
    @Type(type="text")
    String answer;
}
