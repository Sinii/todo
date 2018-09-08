package com.titmouse.anton.todo.database;

import android.content.Context;

import com.titmouse.anton.todo.database.entity.TodoEntity;

import java.util.List;

import io.realm.Realm;


public class ToDoRealmDaoImpl implements ToDoRealmDao {

    private static class SingletonHolder {
        static final ToDoRealmDaoImpl HOLDER_INSTANCE = new ToDoRealmDaoImpl();
    }

    public static ToDoRealmDaoImpl getInstance() {
        return SingletonHolder.HOLDER_INSTANCE;
    }

    @Override
    public void setTodo(final TodoEntity entity) {
        final Realm realm = Realm.getDefaultInstance();

        final Long nextId = 0L;
        realm.executeTransaction(realm1 -> {
            final TodoEntity todoEntity = realm1.createObject(TodoEntity.class);
            todoEntity.setId(nextId);
            todoEntity.setText(entity.getText());
        });


        realm.close();
    }

    @Override
    public List<TodoEntity> getToDo() {
        // TODO: 08.09.2018

//		final Realm realm = Realm.getDefaultInstance();
//
//		final List<TodoEntity> todoEntities = new ArrayList<>();
//
//		for (final ToDoEntity todoEntity : realm.where(ToDoEntity.class).findAll().sort("id", Sort.DESCENDING)) {
//			todoEntities.add(
//					new TodoEntity(
//							todoEntity.getId(),
//							todoEntity.getText(),
//							todoEntity.getError()));
//		}
//
//		realm.close();
        return null;
    }

    @Override
    public List<TodoEntity> deleteToDo(final TodoEntity todoEntity) {
        // TODO: 08.09.2018

//		final Realm realm = Realm.getDefaultInstance();
//
//		final List<TodoEntity> todoEntities = new ArrayList<>();
//		for (final TodoEntity translateEntity : realm.where(TodoEntity.class).findAll().sort("id", Sort.DESCENDING)) {
//			if (translateEntity.getId().equals(translate.getId())) {
//				realm.executeTransaction(realm1 -> translateEntity.deleteFromRealm());
//			} else {
//				todoEntities.add(
//						new TodoEntity(
//								translateEntity.getId(),
//								translateEntity.getText(),
//								translateEntity.getError()));
//			}
//		}
//
//		realm.close();
        return null;
    }

    @Override
    public void deleteAll() {
        final Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> realm1.deleteAll());
        realm.close();
    }

    public void initRealm(final Context context) {
        // TODO: 08.09.2018 wait till good times

//		Realm.init(context);
//		RealmConfiguration config = new RealmConfiguration.Builder()
//				.name("todo.realm")
//				.schemaVersion(1L)
//				.build();
//		Realm.setDefaultConfiguration(config);
    }
}
