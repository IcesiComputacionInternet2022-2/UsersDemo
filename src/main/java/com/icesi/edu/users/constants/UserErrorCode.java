package com.icesi.edu.users.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserErrorCode {

    CODE_401("Debe estar authenticado para realizar este request"),
    CODE_001("Usuario invalido"),
    CODE_002("Solo puedes consultar tu propia informacion"),
    CODE_003("Email o telefono repetido"),
    CODE_004("La contraseña debe tener al menos una letra mayuscula, una minuscula, un numero y un simbolo como #$%@");


    private String message;

    public String getMessage(){
        return message;
    }
}