package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.data.entity.Document;
import com.data.repository.DocumentRepository;

import java.util.List;

/**
 * Created by minisha on 27/1/16.
 */
@Service
public class DocumentService {

    @Autowired
    DocumentRepository documentRepository;

    public List<Document> getAllDocuments() {
        return documentRepository.getAllDocuments();
    }
}
