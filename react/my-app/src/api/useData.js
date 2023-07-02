import {useEffect, useState} from "react";

export default function useData(id) {

    const [data, setData] = useState(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    useEffect(
        () => {
            setData(null);
            setLoading(true);
            setError(null);
            setTimeout(() => {
                setData({count: 1});
                setLoading(false);
                setError(null);
            }, 2000);
        }
        , [id]
    )

    return {data, loading, error};

}


