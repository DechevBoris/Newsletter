package com.dechev.newsletter.repository;

import com.dechev.newsletter.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository("subscriptionRepository")
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    List<Subscription> findAllByUserId(UUID id);

    Subscription findByUserIdAndAndContentId(UUID userId, int contentId);

    void deleteById(int id);
}
