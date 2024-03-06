import {Route, Routes} from "react-router-dom";


export default function App() {

    return (
        <main>
            <Routes>
                <Route path={"/"} element={<h2>Hello World </h2>}/>
            </Routes>
        </main>
    )
}
