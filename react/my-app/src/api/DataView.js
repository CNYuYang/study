import {Component} from "react";
import useData from "./useData";

class DataView extends Component {


    render() {
        const {data, loading, error} = this.props.useData;

        if (loading) {
            return <div>loading</div>
        } else {
            return <div>{JSON.stringify(data)}</div>
        }
    }
}

export default function HookDataView() {
    // const {data, loading, error} = useData(11);
    return <DataView useData={useData(11)}/>
}