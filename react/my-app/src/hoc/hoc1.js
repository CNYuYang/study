import {Component} from "react";

class Foo extends Component {
    state = {
        xPos: document.documentElement.clientWidth,
        yPos: document.documentElement.clientHeight
    }

    getPos = () => {
        this.setState({
            xPos: document.documentElement.clientWidth,
            yPos: document.documentElement.clientHeight
        })
    }

    componentDidMount() {
        window.addEventListener('resize', this.getPos);
    }

    componentWillUnmount() {
        window.removeEventListener('resize', this.getPos)
    }

    render() {
        return (<div>
            <p>x: {this.state.xPos} -- y :{this.state.yPos}</p>
        </div>)
    }
}

class Bar extends Component {
    state = {
        xPos: document.documentElement.clientWidth,
        yPos: document.documentElement.clientHeight
    }

    getPos = () => {
        this.setState({
            xPos: document.documentElement.clientWidth,
            yPos: document.documentElement.clientHeight
        })
    }

    componentDidMount() {
        window.addEventListener('resize', this.getPos);
    }

    componentWillUnmount() {
        window.removeEventListener('resize', this.getPos)
    }

    render() {
        return (<div>
            <button>x: {this.state.xPos} -- y :{this.state.yPos}</button>
        </div>)
    }
}

export default class Hoc1 extends Component {

    render() {
        return (
            <div>
                <Foo/>
                <Bar/>
            </div>
        );
    }
}