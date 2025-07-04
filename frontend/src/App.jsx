import { useEffect, useState } from "react"
import { jwtDecode } from "jwt-decode"
import AuthContext from './context/AuthContext'
import { BrowserRouter as Router, Route, Routes, Navigate } from "react-router-dom"
import Login from './components/Login'
import NavBar from "./components/NavBar"
import Home from './components/Home'
import Register from './components/Register'
import SavedRecipes from './components/SavedRecipes'
import Recipe from "./components/Recipe"
import RecipeForm from "./components/RecipeForm"
import MyRecipes from "./components/MyRecipes"
import OrganizationForm from "./components/OrganizationForm"
import Organization from "./components/Organization"
import MyOrganizations from "./components/MyOrganizations"

const LOCAL_STORAGE_TOKEN_KEY = "recipioToken";

function App() {
  const [user, setUser] = useState(null);
  const [restoreLoginAttemptCompleted, setRestoreLoginAttemptCompleted] = useState(false);

  useEffect(() => {
    const token = localStorage.getItem(LOCAL_STORAGE_TOKEN_KEY)
    if (token) {
      login(token)
    }
    setRestoreLoginAttemptCompleted(true)
  }, [])

  function login(token) {
    localStorage.setItem(LOCAL_STORAGE_TOKEN_KEY, token);
    const { sub: username } = jwtDecode(token);
    const user = { username, token }
    setUser(user)
    return user
  }

  function logout() {
    setUser(null)
    localStorage.removeItem(LOCAL_STORAGE_TOKEN_KEY)
  }

  const auth = { user: user ? {...user} : null, login, logout }

  if (!restoreLoginAttemptCompleted) {
    return null;
  }

  return (
    <AuthContext.Provider value={auth}>
      <Router basename="/recipio">
        <div>
          <NavBar />
        </div>
        <div className="sm:ml-64">
          <Routes>
            <Route path="/" element={<Home />}/>
            <Route path="/login" element={user ? <Navigate to="/" replace={true}/> : <Login/>}/>
            <Route path="/register" element={user ? <Navigate to="/" replace={true}/> : <Register/>}/>
            <Route path="/saved-recipes" element={user ? <SavedRecipes/> : <Navigate to="/" replace={true}/>}/>
            <Route path="/recipe/:id" element={<Recipe />}/>
            <Route path="/recipe/new" element={user ? <RecipeForm/> : <Navigate to="/" replace={true}/>}/>
            <Route path="/my-recipes" element={user ? <MyRecipes/> : <Navigate to="/" replace={true}/>}/>
            <Route path="/edit/:id" element={user ? <RecipeForm/> : <Navigate to="/" replace={true}/>}/>
            <Route path="/organization/new" element={user ? <OrganizationForm/> : <Navigate to="/" replace={true}/>}/>
            <Route path="/organization/:id" element={user ? <Organization/> : <Navigate to="/" replace={true}/>}/>
            <Route path="/organization/edit/:id" element={user ? <OrganizationForm/> : <Navigate to="/" replace={true}/>}/>
            <Route path="/my-organizations" element={user ? <MyOrganizations/> : <Navigate to="/" replace={true}/>}/>
          </Routes>
        </div>
      </Router>
    </AuthContext.Provider>
  )
}

export default App
