package com.dechev.newsletter.repository;

import com.dechev.newsletter.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("subscriptionRepository")
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    List<Subscription> findAllByUserId(int id);

    void deleteById(int id);
}
