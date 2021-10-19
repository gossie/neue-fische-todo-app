import React, { useState } from 'react';
import './App.css';
import { TodoList } from './model';
import TodoListElement from './TodoListElement';

function App() {
    const [todoList, setTodoList] = useState({} as TodoList);

    const onTodoList = (newTodoList: TodoList) => setTodoList(newTodoList);

    const createTodoList = () => {
        fetch(`${process.env.REACT_APP_BASE_URL}/todolists`, {
            method: 'POST',
            headers: {
                Accept: 'application/json'
            }
        })
        .then(response => response.json())
        .then(json => setTodoList(json));
    };

    return (
        <div className="App">
            <button onClick={() => createTodoList()}>TODO Liste erstellen</button>

            { todoList.links && <TodoListElement todoList={todoList} todoListEmitter={onTodoList} /> }
        </div>
    );
}

export default App;
