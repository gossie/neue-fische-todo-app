import { useState } from "react";
import { TodoItem, TodoList } from "./model";
import './TodoItemElement.css';

interface TodoItemProps {
    item: TodoItem;
    todoListEmitter: (todoList: TodoList) => void;
}

export default function TodoItemElement(props: TodoItemProps) {

    const [editMode, setEditMode] = useState(false);
    const [newTitle, setNewTitle] = useState(props.item.title);

    const changeStatus = (status: string) => {
        const link = props.item.links.find(link => link.rel === 'self')?.href
        fetch(`${process.env.REACT_APP_BASE_URL}${link}`, {
            method: 'PUT',
            body: JSON.stringify({
                title: props.item.title,
                status: status
            }),
            headers: {
                'Content-Type': 'application/json',
                Accept: 'application/json'
            }
        })
        .then(response => response.json())
        .then((json: TodoList) => props.todoListEmitter(json));
    };

    const changeTitle = () => {
        const link = props.item.links.find(link => link.rel === 'self')?.href
        fetch(`${process.env.REACT_APP_BASE_URL}${link}`, {
            method: 'PUT',
            body: JSON.stringify({
                title: newTitle,
                status: props.item.status
            }),
            headers: {
                'Content-Type': 'application/json',
                Accept: 'application/json'
            }
        })
        .then(response => response.json())
        .then((json: TodoList) => {
            setEditMode(false);
            props.todoListEmitter(json);
        });
    };

    return (
        <li>
            {
                editMode
                ?
                    <input type="text" value={newTitle} onChange={(evt) => setNewTitle(evt.target.value)} onKeyUp={(evt) => {if (evt.keyCode === 13) changeTitle();}} />
                :
                    <span className={`${props.item.status === 'DONE' ? 'checked' : ''}`} onClick={() => setEditMode(true)}>{props.item.title}</span>
            }
            
            {
                props.item.status === 'OPEN' ? <button onClick={() => changeStatus('DONE')}>Abhaken</button> : <button onClick={() => changeStatus('OPEN')}>Doch nicht</button> 
            }
        </li>
    );
}