package com.dechev.newsletter.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "subscription")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "subscription_id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
//    @NotEmpty
    User user;

    @ManyToOne
    @JoinColumn(name = "content_id")
//    @NotEmpty
    Content content;

    @Column(name = "date_of_subscription")
//    @NotEmpty
    LocalDateTime dateOfSubscription;
}
