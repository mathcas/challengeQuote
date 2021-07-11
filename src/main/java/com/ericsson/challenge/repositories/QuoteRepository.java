package com.ericsson.challenge.repositories;

import com.ericsson.challenge.entities.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, Long> {

    Quote findByStockCode(String stockCode);
}
