package com.icesi.edu.users.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserErrorCode {
    CODE_01("Debe estar autenticado para realizar esta peticion"),
    CODE_02("Las credenciales de inicio de sesion no son validas");

    private final String message;
}
