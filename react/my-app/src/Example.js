import React, {useState} from "react";

export default function Example() {
    const [count, setCount] = useState(0);

    return (<div>
        <p>{count}</p>
        <button onClick={() => {
            setCount(count + 1)
        }}>+
        </button>
    </div>)
}