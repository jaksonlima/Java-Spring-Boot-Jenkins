package jws.jenkins.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class PathController {

    private final static List<Integer> COUNT_REQUEST = new ArrayList<Integer>();

    private final Environment environment;

    @GetMapping
    public ResponseEntity<Map<String, Object>> path(HttpServletRequest request) {
        final Map<String, Object> toMap = new HashMap<String, Object>();
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        final Calendar cal = Calendar.getInstance();
        final TimeZone timeZone = cal.getTimeZone();
        COUNT_REQUEST.add(1);

        toMap.put("timezone", timeZone.getDisplayName());
        toMap.put("version", Optional.ofNullable(environment.getProperty("VERSION")).orElse("3.0.0"));
        toMap.put("author", Optional.ofNullable(environment.getProperty("AUTHOR")).orElse("Jakson Wilson Bonfim de Lima"));
        toMap.put("locale", request.getLocale().toLanguageTag());
        toMap.put("machine", request.getLocalName());
        toMap.put("date", formatter.format(LocalDateTime.now()));
        toMap.put("totalRequisitions", COUNT_REQUEST.size());
        toMap.put("exit", "Deployment Kubernetes With GitHub Actions");

        return ResponseEntity.ok(toMap);
    }

    @GetMapping("/back")
    public ResponseEntity<Map<String, Object>> pathBack(HttpServletRequest request) {
        return path(request);
    }

}
