package com.titmouse.anton.todo.todolist.model;

import com.titmouse.anton.todo.database.entity.TodoEntity;

import java.util.ArrayList;
import java.util.List;


public class TodoModelImpl implements TodoModel {
    private List<TodoEntity> mCachedTodoList = new ArrayList<>();

    @Override
    public List<TodoEntity> getTodoList() {
        // TODO: 08.09.2018 add database
        if (mCachedTodoList.size() == 0) {
            mCachedTodoList = getMockData();
        }
        return mCachedTodoList; //ToDoRealmDaoImpl.getInstance().getToDo();
    }

    @Override
    public void deleteTodo(final int position) {
        mCachedTodoList.remove(position);
        // TODO: 08.09.2018
    }

    @Override
    public void addTodo(final TodoEntity todoEntity) {
        // TODO: 09.09.2018
        mCachedTodoList.add(mCachedTodoList.size(), todoEntity);
    }
    
    @Override
    public void editTodo(final TodoEntity oldTodo, final TodoEntity newTodo) {
        final int index = mCachedTodoList.indexOf(oldTodo);
        if (index >= 0) {
            mCachedTodoList.set(index, newTodo);
        } else {
            // TODO: 16.09.2018 notify server about exception
        }
    }
    
    @Override
    public void addNotification(final TodoEntity todoEntity) {

    }


    private static List<TodoEntity> getMockData() {
        final List<TodoEntity> list = new ArrayList<>();
        list.add(new TodoEntity(0L, false, "Антон, найди время меня сделать", 0));
        list.add(new TodoEntity(1L, false, "Удали меня свайпом", 0));
        list.add(new TodoEntity(2L, false, "Удали меня полностью", 0));
        return list;
    }
}
