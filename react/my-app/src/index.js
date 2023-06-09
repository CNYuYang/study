import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import UserList from "./UserList";
import Example from "./Example";
import Hoc from  "./hoc/hoc2"
import HookDataView from  "./api/DataView"
import HocHookCounter from "./hook/HookCounter";
import reportWebVitals from './reportWebVitals';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <React.StrictMode>
        {/*<UserList />*/}
        <HocHookCounter/>
    </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
