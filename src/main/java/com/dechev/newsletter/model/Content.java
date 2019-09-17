package com.dechev.newsletter.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "content")
public class Content {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "content_id")
    private int id;

    @Column(name = "title")
    @Length(max = 30, message = "The title must consist of maximum 30 characters")
    @NotEmpty(message = "*Please provide a title")
    private String title;

    @Column(name = "author_email")
    @NotEmpty
    private String authorEmail;

    @Column(name = "description")
    @NotEmpty(message = "*Please provide a description")
    private String description;

    @Column(name = "content")
    @NotEmpty(message = "*Please provide a content")
    private String text;

    @OneToMany(mappedBy = "content")
    Set<Subscription> subscriptions;
}
