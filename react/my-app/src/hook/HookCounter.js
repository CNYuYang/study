import React from "react";
import {Component} from "react";
import useCounter from "./useCounter";

class HookCounter extends Component {
    render() {
        const {count, increase, decrement, reset} = this.props.userCount;
        return (
            <div>
                {count}
                <br/>
                <button onClick={increase}>+</button>
                <button onClick={decrement}>-</button>
                <button onClick={reset}>clear</button>
            </div>
        )
    }
}

export default function HocHookCounter() {
    return <HookCounter userCount={useCounter()}/>
}