import {Component} from "react";

const withSize = (Component) => {
    return class toSize extends Component {
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
            return (<Component {...this.state}/>)
        }
    }
}

class Foo extends Component {
    render() {
        return (<div>
            <p>x: {this.props.xPos} -- y :{this.props.yPos}</p>
        </div>)
    }
}

class Bar extends Component {
    render() {
        return (<div>
            <button>x: {this.props.xPos} -- y :{this.props.yPos}</button>
        </div>)
    }
}

const FooWithSize = withSize(Foo);

const BarWithSize = withSize(Bar);

export default class Hoc2 extends Component {

    render() {
        return (
            <div>
                <FooWithSize/>
                <BarWithSize/>
            </div>
        );
    }
}