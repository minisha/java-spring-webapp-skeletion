package sg.com.cic.cicportal.web.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static sg.com.cic.cicportal.web.util.ResourceUtil.createJsonResponseCreated;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.service.DocumentService;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by minisha on 27/1/16.
 */
@Slf4j
@Controller
public class DocumentController {

    @Autowired
    DocumentService documentService;

    @RequestMapping(value = "/ajax/getDocument", produces = APPLICATION_JSON_VALUE, method = GET)
    public ResponseEntity<byte[]> getAllDocuments() {
        return createJsonResponseCreated(documentService.getAllDocuments(), log);
    }
}
