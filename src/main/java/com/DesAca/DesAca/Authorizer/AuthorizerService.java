package com.DesAca.DesAca.Authorizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DesAca.DesAca.Diagnosis.Diagnosis;
import com.DesAca.DesAca.Diagnosis.DiagnosisRepository;
import java.util.Optional;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthorizerService {

    private final AuthorizerRepository authorizerRepository;

    @Autowired
    private DiagnosisRepository diagnosisRepository;

    public AuthorizerDTO createAuthorizer(Authorizer authorizer) {
        Authorizer savedAuthorizer = authorizerRepository.save(authorizer);

        return new AuthorizerDTO(
                savedAuthorizer.getId(),
                savedAuthorizer.getName(),
                savedAuthorizer.getPosition(),
                savedAuthorizer.getAuthKey(),
                savedAuthorizer.getEmail(),
                savedAuthorizer.getPassword());
    }

    @Transactional
    public boolean authorizeDiagnosis(Long diagnosisId, String claveAutorizacion) {
        Logger logger = LoggerFactory.getLogger(getClass());
        Optional<Authorizer> autorizador = authorizerRepository.findByauthKey(claveAutorizacion);

        if (!autorizador.isPresent()) {
            logger.warn("Autorizador no encontrado con clave: {}", claveAutorizacion);
            return false;
        }

        Optional<Diagnosis> optionalDiagnosis = diagnosisRepository.findById(diagnosisId);
        if (!optionalDiagnosis.isPresent()) {
            logger.warn("Diagnóstico no encontrado con ID: {}", diagnosisId);
            return false;
        }

        Diagnosis diag = optionalDiagnosis.get();
        String position = autorizador.get().getPosition();
        boolean isUpdated = false;

        // Define los puestos de los autorizadores
        final String PUESTO_JEFE_DESARROLLO_ACADEMICO = "Jefe Departamento Desarrollo Académico";
        final String PUESTO_SUBDIRECTOR_ACADEMICO = "Subdirector Académico";

        switch (position) {
            case PUESTO_JEFE_DESARROLLO_ACADEMICO:
                if (!diag.isAuthorizedByFirst()) {
                    diag.setAuthorizedByFirst(true);
                    isUpdated = true;
                }
                break;
            case PUESTO_SUBDIRECTOR_ACADEMICO:
                if (!diag.isAuthorizedBySecond()) {
                    diag.setAuthorizedBySecond(true);
                    isUpdated = true;
                }
                break;
            default:
                logger.warn("Posición de autorizador no reconocida: {}", position);
                return false;
        }

        if (isUpdated) {
            checkAndFinalizeAuthorization(diag);
            diagnosisRepository.save(diag);
            logger.info("Autorización actualizada para diagnóstico ID: {}", diagnosisId);
            return true;
        }

        return false;
    }

    private void checkAndFinalizeAuthorization(Diagnosis diag) {
        if (diag.isAuthorizedByFirst() && diag.isAuthorizedBySecond()) {
            diag.setStatus("Totalmente Autorizado");
            diag.setFeedback(" ");
        } else {
            if (diag.isAuthorizedByFirst() || diag.isAuthorizedBySecond()) {
                diag.setStatus("Parcialmente Autorizado");
            }
        }
    }

    /**
     * Agrega o actualiza el feedback para un diagnóstico específico y cambia su
     * estado a "Rechazado".
     *
     * @param diagnosisId El ID del diagnóstico para el cual se desea agregar o
     *                    actualizar el feedback.
     * @param feedback    El feedback proporcionado por el autorizador.
     * @return true si el feedback y el estado fueron actualizados correctamente,
     *         false de lo contrario.
     */
    public boolean addOrUpdateFeedback(Long diagnosisId, String feedback) {
        Optional<Diagnosis> diagnosisOpt = diagnosisRepository.findById(diagnosisId);
        if (diagnosisOpt.isPresent()) {
            Diagnosis diagnosis = diagnosisOpt.get();
            diagnosis.setFeedback(feedback);
            diagnosis.setStatus("Retroalimentación"); // Cambiar el estado a Retroalimentación
            diagnosisRepository.save(diagnosis);
            return true;
        }

        return false;
    }

}
