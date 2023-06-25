import React from "react";

export default function useCounter() {
    const [count, setCount] = React.useState(0);

    const increase = React.useCallback(
        () => {
            setCount(count + 1);
        }, [count]
    )

    const decrement = React.useCallback(() => {
        setCount(count - 1);
    }, [count])

    const reset = React.useCallback(() => {
        setCount(0);
    }, [count])

    return {count, increase, decrement, reset};
}