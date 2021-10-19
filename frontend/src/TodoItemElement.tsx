import { TodoItem, TodoList } from "./model";
import './TodoItemElement.css';

interface TodoItemProps {
    item: TodoItem;
    todoListEmitter: (todoList: TodoList) => void;
}

export default function TodoItemElement(props: TodoItemProps) {

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

    return (
        <li>
            <span className={`${props.item.status === 'DONE' ? 'checked' : ''}`}>{props.item.title}</span>
            {
                props.item.status === 'OPEN' ? <button onClick={() => changeStatus('DONE')}>Abhaken</button> : <button onClick={() => changeStatus('OPEN')}>Doch nicht</button> 
            }
        </li>
    );
}