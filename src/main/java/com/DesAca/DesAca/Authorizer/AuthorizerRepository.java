package com.DesAca.DesAca.Authorizer;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;





@Repository
public interface AuthorizerRepository extends JpaRepository<Authorizer, Long> {
 // Definir el método personalizado para buscar por email
    Optional<Authorizer>  findByauthKey(String authKey);
    
}