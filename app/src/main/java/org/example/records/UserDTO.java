package org.example.records;

import java.time.LocalDateTime;

public record UserDTO(Integer id, String nome, String email, String senha, String token, LocalDateTime createdAt) {}
