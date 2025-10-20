package com.fiap.aosw.todo;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TodoServiceImpl implements TodoService {
    private final TodoRepository repo;

    public TodoServiceImpl(TodoRepository repo) {
        this.repo = repo;
    }

    @Override @Transactional
    public Todo create(String title, String owner) {
        if (title == null || title.isBlank()) throw new IllegalArgumentException("Título obrigatório");
        return repo.save(new Todo(title, owner));
    }

    @Override
    public List<Todo> list(String owner) { return repo.findByOwner(owner); }

    @Override @Transactional
    public Todo toggle(Long id, String owner) {
        Todo t = repo.findById(id).orElseThrow();
        if (!t.getOwner().equals(owner)) throw new SecurityException("Proibido");
        t.setDone(!t.isDone());
        return t;
    }
}
