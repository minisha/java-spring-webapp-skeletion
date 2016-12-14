package com.data.repository;

import java.math.BigInteger;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.data.entity.Document;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/data-db-context.xml",
        "classpath:/spring/profiles-context.xml"})
@ActiveProfiles("dev")
public class TestRepository {
    @Autowired
    DocumentRepository documentRepository;

    @Test
    public void test_repository_save() {
        Document d = new Document();
        d.setId(new BigInteger("123"));
        d.setName("test document");
        d.setOwner("test owner");
        documentRepository.save(d);

        List<Document> documents = documentRepository.getAllDocuments();
        assert(documents.get(0).getName()).equals("test document");
    }
}
