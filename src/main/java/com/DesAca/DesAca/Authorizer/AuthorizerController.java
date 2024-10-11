package com.DesAca.DesAca.Authorizer;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/authorizer")
@RequiredArgsConstructor
public class AuthorizerController {
    @Autowired
    private AuthorizerService authorizerService;

    @PostMapping("/create")
    public ResponseEntity<AuthorizerDTO> createAuthorizer(@Valid @RequestBody Authorizer authorizer) {
        AuthorizerDTO createdAuthorizer = authorizerService.createAuthorizer(authorizer);
        return new ResponseEntity<>(createdAuthorizer, HttpStatus.CREATED);
    }

    @PostMapping("/authorize/{diagnosisId}")
    public ResponseEntity<String> authorizeDiagnosis(@PathVariable Long diagnosisId, @RequestParam String claveAutorizacion) {
        boolean isAuthorized = authorizerService.authorizeDiagnosis(diagnosisId, claveAutorizacion);
        if (isAuthorized) {
            return ResponseEntity.ok("Diagnóstico autorizado exitosamente.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No autorizado. Clave de autorización incorrecta o diagnóstico no encontrado.");
        }
    }

    @PostMapping("/feedback/{diagnosisId}")
    public ResponseEntity<String> updateFeedback(@PathVariable Long diagnosisId, @RequestBody String feedback) {
        boolean updated = authorizerService.addOrUpdateFeedback(diagnosisId, feedback);
        if (updated) {
            return ResponseEntity.ok("Feedback actualizado correctamente y el diagnóstico ha sido marcado como rechazado.");
        } else {
            return ResponseEntity.badRequest().body("Error al actualizar el feedback, por favor verifica que el diagnóstico exista.");
        }
    }
}

