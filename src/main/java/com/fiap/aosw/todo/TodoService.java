package com.fiap.aosw.todo;

import java.util.List;

public interface TodoService {
    Todo create(String title, String owner);
    List<Todo> list(String owner);
    Todo toggle(Long id, String owner);
}
