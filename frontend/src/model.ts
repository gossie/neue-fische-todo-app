interface Link {
    rel: string;
    href: string;
}

export interface TodoItem {
    title: string;
    status: string;
    links: Array<Link>;
}

export interface TodoList {
    items: Array<TodoItem>;
    links: Array<Link>;
}