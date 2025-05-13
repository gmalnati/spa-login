import {createContext, type ReactNode, useContext, useEffect, useState} from 'react'
import './App.css'

function Service1Method1() {
    const [data, setData] = useState<Record<string,unknown>>({loading: false, data: null, error: null})
    const loadData = () => {
        setData({loading: true, data: null, error: null})
        fetch('/api/service1/method1')
            .then(res => res.json())
            .then(data => setData({loading: false, data, error: null}))
            .catch(error => setData({loading: false, data: null, error}))
    };
    return (
        <div>
            <h2>Service 1 - Method 1</h2>
        {
            data.loading? <div>Loading...</div>:
            data.error? <div>Error: {(data.error as Error).message}</div>:
            data.data?
                <div style={{textAlign: 'left'}}>
                    <pre>{JSON.stringify(data.data, null, 2)}</pre>
                    <button onClick={loadData}>Reload</button>
                </div>:
            <button onClick={loadData}>Load</button>
        }
        </div>
    )
}
function Service1Method2() {
    const me = useContext(UserContext)
    const [data, setData] = useState<Record<string,unknown>>({loading: false, data: null, error: null})
    const loadData = () => {
        setData({loading: true, data: null, error: null})
        fetch('/api/service1/method2', {method: 'POST', headers: {'X-CSRF-TOKEN': me.csrf as string},body:""})
            .then(res => res.json())
            .then(data => setData({loading: false, data, error: null}))
            .catch(error => setData({loading: false, data: null, error}))
    };
    return (
        <div>
            <h2>Service 1 - Method 2</h2>
        {
            data.loading? <div>Loading...</div>:
            data.error? <div>Error: {(data.error as Error).message}</div>:
            data.data?
                <div style={{textAlign: 'left'}}>
                    <pre>{JSON.stringify(data.data, null, 2)}</pre>
                    <button onClick={loadData}>Reload</button>
                </div>:
            <button onClick={loadData}>Load</button>
        }
        </div>
    )
}
function Service1Root() {
    const [data, setData] = useState<Record<string,unknown>>({loading: false, data: null, error: null})
    const loadData = () => {
        setData({loading: true, data: null, error: null})
        fetch('/api/service1/')
            .then(res => res.json())
            .then(data => setData({loading: false, data, error: null}))
            .catch(error => setData({loading: false, data: null, error}))
    };
    return (
        <div>
            <h2>Service 1 - Root</h2>
            {
                data.loading? <div>Loading...</div>:
                    data.error? <div>Error: {(data.error as Error).message}</div>:
                        data.data?
                            <div style={{textAlign: 'left'}}>
                                <pre>{JSON.stringify(data.data, null, 2)}</pre>
                                <button onClick={loadData}>Reload</button>
                            </div>:
                            <button onClick={loadData}>Load</button>
            }
        </div>
    )
}

function LogoutButton() {
    const me = useContext(UserContext)
    return (
        <form action="/logout" method="post">
            <input type="hidden" name="_csrf" value={me.csrf as string} />
            <button type="submit">Logout</button>
        </form>
    )
}
function LoginButton() {
    return (
        <button onClick={() => window.location.href="/serverLogin"}>Login</button>
    )
}

const UserContext = createContext<Record<string,unknown>>({})

function UserDetails() {
    const user = useContext(UserContext)
    return (
        <div style={{textAlign: 'left'}}>
            <h1>Welcome, {user?.name as string | null}</h1>
            <pre>{JSON.stringify(user, null, 2)}</pre>
        </div>
    )
}

function AnonymousContent({children}: {children: ReactNode}) {
    const user = useContext(UserContext)
    if (user?.name) {
        return null
    } else return children
}

function UserContent({children}: {children: ReactNode}) {
    const user = useContext(UserContext)
    if (user?.name) {
        return children
    } else return null
}


function App() {
    const [me, setMe] = useState<Record<string,unknown>>({})
    useEffect(() => {
        fetch('/me')
            .then(res => res.json())
            .then(setMe)
            .catch(()=>setMe({}))
    }, [setMe]);

    return (
        <UserContext value={me}>
            <AnonymousContent>
                <LoginButton/>
                <hr/>
                <Service1Root/>
                <Service1Method1/>
                <Service1Method2/>
            </AnonymousContent>
            <UserContent>
                <LogoutButton/>
                <hr/>
                <UserDetails/>
                <hr/>
                <Service1Root/>
                <Service1Method1/>
                <Service1Method2/>
            </UserContent>
        </UserContext>
    )
}

export default App
