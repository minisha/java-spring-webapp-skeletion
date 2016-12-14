package com.data.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.data.entity.Document;

/**
 * Created by minisha on 27/1/16.
 */
@Transactional
public interface DocumentRepository extends JpaRepository<Document, BigInteger> {

    @Query("SELECT d FROM Document d")
    List<Document> getAllDocuments();

}
