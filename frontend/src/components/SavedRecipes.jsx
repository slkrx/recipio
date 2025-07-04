import { useContext, useEffect, useState } from "react"
import AuthContext from "../context/AuthContext"
import RecipeCard from "./RecipeCard"

export default function SavedRecipes() {
    const auth = useContext(AuthContext)
    const [savedRecipes, setSavedRecipes] = useState([])

    useEffect(() => {
        getSavedRecipes()
    }, [])

    async function getSavedRecipes() {
        if (auth.user) {
            const response = await fetch(`https://samrechsteiner.com/recipio-api/api/app-user-recipe-saved/${auth.user.username}`)
            const data = await response.json()
            setSavedRecipes(data)
        }
    }

    return (
        <div className="container my-12 mx-auto px-4 md:px-12">
            <div className="flex flex-wrap -mx-1 lg:-mx-4">
                {savedRecipes.map((recipe, i) => <RecipeCard key={i} recipe={recipe}/>)}
            </div>
        </div>
    )
}