package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.demo.model.entity.Call;
import com.example.demo.model.entity.Call.CallStatus;

@Repository
public interface CallRepository extends BaseRepository<Call, Integer> {

    // Encontre um chamado por ID
    Optional<Call> findById(Integer id);

    // Encontre chamados por status
    List<Call> findByStatus(CallStatus status);

    // Encontre chamados por ID de usuário
    List<Call> findByUserId(Integer userId);

    // Encontre chamados por ação (case insensitive)
    List<Call> findByActionContainingIgnoreCase(String action);

    // Encontre chamados por objeto (case insensitive)
    List<Call> findByObjectContainingIgnoreCase(String object);
}
