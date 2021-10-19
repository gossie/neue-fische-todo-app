import { useState } from "react";
import { TodoList } from "./model";
import TodoItemElement from "./TodoItemElement";

interface TodoListProps {
    todoList: TodoList;
    todoListEmitter: (todoList: TodoList) => void;
}

export default function TodoListElement(props: TodoListProps) {
    const [title, setTitle] = useState('');

    const createTodoItem = () => {
        const link = props.todoList.links.find(link => link.rel === 'createItem')?.href
        fetch(`${process.env.REACT_APP_BASE_URL}${link}`, {
            method: 'POST',
            body: JSON.stringify({
                title: title,
                status: 'OPEN'
            }),
            headers: {
                'Content-Type': 'application/json',
                Accept: 'application/json'
            }
        })
        .then(response => response.json())
        .then((json: TodoList) => { 
            props.todoListEmitter(json);
            setTitle('');
        });
    };

    const openItems = props.todoList.items
        .filter(item => item.status === 'OPEN')
        .map(item => <TodoItemElement key={item.title} item={item} todoListEmitter={props.todoListEmitter} />);
    
    const doneItems = props.todoList.items
        .filter(item => item.status === 'DONE')
        .map(item => <TodoItemElement key={item.title} item={item} todoListEmitter={props.todoListEmitter} />);

    return (
        <div>
            <input type="text" value={title} onChange={(evt) => setTitle(evt.target.value)} /> <button onClick={() => createTodoItem()}>Item erstellen</button>
            <ul>
                {openItems}
                {doneItems}
            </ul>
        </div>
    )
}