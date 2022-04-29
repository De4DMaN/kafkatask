package com.cloudmore.kafkatask.consumer.repository;

import com.cloudmore.kafkatask.consumer.model.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, Long> {
}