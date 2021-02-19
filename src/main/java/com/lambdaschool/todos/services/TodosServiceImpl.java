package com.lambdaschool.todos.services;


import com.lambdaschool.todos.models.Todos;
import com.lambdaschool.todos.models.User;
import com.lambdaschool.todos.repository.TodosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Transactional
@Service(value = "todosService")
public class TodosServiceImpl implements  TodosService{
    @Autowired
    private TodosRepository todosRepository;

    @Autowired
    private UserService userService;

    @Override
    public Todos save(long userid, Todos newTodo){
        User CurrentU = userService.findUserById(userid);
        Todos saveTodos = new Todos(CurrentU, newTodo.getDescription());
        todosRepository.save(saveTodos);
        return saveTodos;
    }

    @Override
    public Todos markComplete(long todoid){
        Todos updateTodo = todosRepository.findById(todoid)
                .orElseThrow(()-> new EntityNotFoundException(("Didn't find Todo item:"+todoid )));
        updateTodo.setCompleted(true);
        return todosRepository.save(updateTodo);
    }

}
