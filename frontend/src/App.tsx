import React, { useEffect, useState } from 'react';
import './App.css';
import { TodoList } from './model';
import TodoListElement from './TodoListElement';

function App() {
    const [todoList, setTodoList] = useState({} as TodoList);
    const [existingTodoLists, setExistingTodoLists] = useState([]);

    useEffect(() => {
        fetch(`${process.env.REACT_APP_BASE_URL}/todolists`, {
            method: 'GET',
            headers: {
                Accept: 'application/json'
            }
        })
        .then(response => response.json())
        .then(json => setExistingTodoLists(json));
    }, [todoList]);

    const selectTodoList = (list: TodoList) => {
        const link = list.links.find(link => link.rel === 'self')?.href;
        fetch(`${process.env.REACT_APP_BASE_URL}${link}`, {
            method: 'GET',
            headers: {
                Accept: 'application/json'
            }
        })
        .then(response => response.json())
        .then(json => setTodoList(json));
    };

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

    const listElements = existingTodoLists.map((list: TodoList, index: number) =>
        <li>
            <a href="#" onClick={() => selectTodoList(list)}>TODO List {index + 1}</a>
        </li>
    );

    return (
        <div className="App">
            <button onClick={() => createTodoList()}>TODO Liste erstellen</button>

            <h2>Alle Listen</h2>
            <ul>
                { listElements }
            </ul>

            <h2>Ausgewählte Liste</h2>
            { todoList.links && <TodoListElement todoList={todoList} todoListEmitter={onTodoList} /> }
        </div>
    );
}

export default App;
