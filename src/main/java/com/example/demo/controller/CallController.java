package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.CallResponse;
import com.example.demo.dto.NewCall;
import com.example.demo.model.business.CallBusiness;
import com.example.demo.model.entity.Call;
import com.example.demo.model.entity.Call.CallStatus;
import com.example.demo.repository.CallRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/calls")
public class CallController extends AbstractController {

    private final CallRepository callRepository;
    private final CallBusiness callBusiness;

    public CallController(CallRepository callRepository, CallBusiness callBusiness) {
        this.callRepository = callRepository;
        this.callBusiness = callBusiness;
    }

    // POST - Abrir um chamado (status = NOVO)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CallResponse> abrirChamado(@Valid @RequestBody NewCall newCall) {
        Call createdCall = callBusiness.abrirChamado(newCall);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CallResponse(createdCall));
    }

    // GET - Consultar todos os chamados
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CallResponse>> listarChamados() {
        List<CallResponse> chamados = callRepository.findAll()
                .stream()
                .map(CallResponse::new)
                .toList();
        return ResponseEntity.ok(chamados);
    }

    // GET - Consultar um chamado pelo ID
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CallResponse> obterChamadoPorId(@PathVariable Integer id) {
        return callRepository.findById(id)
                .map(call -> ResponseEntity.ok(new CallResponse(call)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<String> alterarStatus(
            @PathVariable Integer id,
            @RequestParam CallStatus novoStatus) {

        try {
            callBusiness.alterarStatus(id, novoStatus);
            return ResponseEntity.ok("Status alterado com sucesso.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
}
