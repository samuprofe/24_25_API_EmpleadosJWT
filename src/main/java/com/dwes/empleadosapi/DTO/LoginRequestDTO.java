package com.dwes.empleadosapi.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequestDTO {
    private String username;
    private String password;
}

/*
 * También podríamos utilizar un record de Java 14:
 * public record DTOLoginRequest(String username, String password) { }
 * 🔹 Con esta única línea de código, Java automáticamente genera:
 * ✔️ Constructor
 * ✔️ Getters (username() y password())
 * ✔️ equals() y hashCode()
 * ✔️ toString()
 */
