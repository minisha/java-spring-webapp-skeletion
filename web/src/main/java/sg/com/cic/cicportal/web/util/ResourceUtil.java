package sg.com.cic.cicportal.web.util;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.MULTI_STATUS;
import static org.springframework.http.HttpStatus.OK;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import sg.com.cic.cicportal.web.dto.MultiStatusMessage;
import sg.com.cic.cicportal.web.dto.MultiStatusResult;
import sg.com.cic.cicportal.web.dto.SimpleMessage;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ResourceUtil {
    private static ObjectMapper mapper = configureObjectMapper();
    private static String SUCCESS = "success";
    private static String ERROR = "error";
    private static String ERROR_404 = "404";

    private static ObjectMapper configureObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);
        mapper.setSerializationInclusion(Include.NON_NULL);
        DefaultSerializerProvider.Impl sp = new DefaultSerializerProvider.Impl();
        mapper.setSerializerProvider(sp);
        return mapper;
    }

    private static byte[] getJsonRepresentation(Object source) {
        try {
            String json = mapper.writeValueAsString(source);
            return json.getBytes(StandardCharsets.UTF_8);
        } catch (JsonProcessingException e) {
            log.error("Unable to serialize object of class [{}].", source.getClass().getName(), e);
            return null;
        }
    }

    private static byte[] getJsonRepresentation(Object source, Class serializationView) {
        try {
            ObjectWriter writer = mapper.writerWithView(serializationView);
            String json = writer.writeValueAsString(source);
            return json.getBytes(StandardCharsets.UTF_8);
        } catch (JsonProcessingException e) {
            log.error("Unable to serialize object of class [{}].", source.getClass().getName(), e);
            return null;

        }
    }

    public static ResponseEntity<byte[]> createJsonResponseCreated(Object source, Class<?> serializationView,
                                                                   Logger logger) {
        try {
            byte[] result = getJsonRepresentation(source, serializationView);
            return new ResponseEntity<>(result, getHeaders(), OK);
        } catch (RuntimeException e) {
            logger.error("Error while getting json representation of an object of type [" + source.getClass() + "]", e);
            return createJsonErrorResponse(e.getMessage());
        }
    }

    public static ResponseEntity<byte[]> createJsonResponseCreated(Object source, Logger logger) {
        try {
            byte[] result = getJsonRepresentation(source);
            return new ResponseEntity<>(result, getHeaders(), OK);
        } catch (RuntimeException e) {
            logger.error("Error while getting json representation of an object of type [" + source.getClass() + "]", e);
            return createJsonErrorResponse(e.getMessage());
        }
    }

    public static ResponseEntity<byte[]> ok() {
        return new ResponseEntity<>(OK);
    }

    public static ResponseEntity<byte[]> createJsonErrorResponse(String message) {
        return new ResponseEntity<>(getJsonRepresentationOfMessage(message), getHeaders(), BAD_REQUEST);
    }

    public static ResponseEntity<byte[]> createJsonSuccessResponse() {
        return new ResponseEntity<>(getJsonRepresentationOfMessage(SUCCESS), getHeaders(), OK);
    }

    public static <T> ResponseEntity<byte[]> createJsonMultiStatusResponse(List<MultiStatusResult<T>> results) {
        return new ResponseEntity<>(getJsonRepresentationOfMessage(results), getHeaders(), MULTI_STATUS);
    }

    public static ResponseEntity<byte[]> badRequest(String message, Exception e, Logger logger) {
        return respondError(message, e, logger, BAD_REQUEST);
    }

    public static ResponseEntity<byte[]> badRequest(String message, Logger logger) {
        logger.error(message);
        return new ResponseEntity<>(getJsonRepresentationOfMessage(message), getHeaders(), BAD_REQUEST);
    }

    public static ResponseEntity<byte[]> badRequest(Exception e, Logger logger) {
        logger.error(e.getMessage(), e);
        return new ResponseEntity<>(getJsonRepresentationOfMessage(e.getMessage()), getHeaders(), BAD_REQUEST);
    }

    public static ResponseEntity<byte[]> respondError(HttpStatusException e, Logger logger) {
        return respondError(e.getMessage(), e, logger);
    }

    private static ResponseEntity<byte[]> respondError(String msg, Exception e, Logger logger, HttpStatus statusCode) {
        logger.error(msg, e);
        return new ResponseEntity<>(getJsonRepresentationOfMessage(msg), getHeaders(), statusCode);
    }

    private static HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        return headers;
    }

    private static byte[] getJsonRepresentationOfMessage(String source) {
        return getJsonRepresentation(new SimpleMessage(source));
    }

    private static <T> byte[] getJsonRepresentationOfMessage(List<MultiStatusResult<T>> source) {
        return getJsonRepresentation(new MultiStatusMessage(source));
    }

    private static ResponseEntity<byte[]> respondError(String msg, HttpStatusException e, Logger logger) {
        logger.error(msg, e);
        return new ResponseEntity<>(getJsonRepresentationOfMessage(msg), getHeaders(), e.getHttpStatus());
    }

}
