package com.olivares.denunciaservicie.validator;

import com.olivares.denunciaservicie.entity.Denuncia;
import com.olivares.denunciaservicie.exceptions.ValidateServiceException;

public class DenunciaValidator {
	public static void save(Denuncia denuncia){
        if (denuncia.getDni() == null || denuncia.getDni().isEmpty()) {
            throw new ValidateServiceException("El dni es requerido"); 
        }
        if (denuncia.getDni().length() > 8) {
            throw new ValidateServiceException("El dni es muy largo"); 
        }
        
    }
}
