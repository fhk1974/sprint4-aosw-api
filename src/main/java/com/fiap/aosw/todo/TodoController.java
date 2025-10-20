package com.fiap.aosw.todo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

record NewTodo(String title) {}

@RestController
@RequestMapping("/api/todos")
@Tag(name = "Tarefas")
public class TodoController {
    private final TodoService service;
    public TodoController(TodoService service) { this.service = service; }

    @PostMapping
    @Operation(summary = "Criar tarefa")
    public Todo create(@RequestBody NewTodo req, Authentication auth) {
        return service.create(req.title(), auth.getName());
    }

    @GetMapping
    @Operation(summary = "Listar minhas tarefas")
    public List<Todo> list(Authentication auth) { return service.list(auth.getName()); }

    @PatchMapping("/{id}/toggle")
    @Operation(summary = "Alternar concluído/não concluído")
    public Todo toggle(@PathVariable Long id, Authentication auth) {
        return service.toggle(id, auth.getName());
    }

    @GetMapping("/admin/ping")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Exemplo de endpoint somente ADMIN")
    public String adminOnly() { return "pong"; }
}
