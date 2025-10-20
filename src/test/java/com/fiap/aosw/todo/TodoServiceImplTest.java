package com.fiap.aosw.todo;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TodoServiceImplTest {
    @Test
    void create_and_list() {
        TodoRepository repo = mock(TodoRepository.class);
        when(repo.save(any())).thenAnswer(inv -> inv.getArgument(0));
        when(repo.findByOwner("fabio")).thenReturn(List.of(new Todo("Estudar", "fabio")));
        TodoService service = new TodoServiceImpl(repo);

        Todo t = service.create("Estudar", "fabio");
        assertEquals("Estudar", t.getTitle());
        assertEquals(1, service.list("fabio").size());
    }
}
