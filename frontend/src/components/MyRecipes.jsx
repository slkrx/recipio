import { useContext, useEffect, useState } from "react";
import RecipeCard from "./RecipeCard";
import AuthContext from "../context/AuthContext";

export default function MyRecipes() {
    const [recipes, setRecipes] = useState([])
    const auth = useContext(AuthContext)

    useEffect(() => {
        getRecipes()
    }, [])

    async function getRecipes() {
        const response = await fetch(`https://samrechsteiner.com/recipio-api/api/app-user-recipe-created/${auth.user.username}`)
        const data = await response.json()
        setRecipes(data)
    }

    async function deleteRecipe(id) {
        const response = await fetch(`https://samrechsteiner.com/recipio-api/api/recipes/${id}`, { method: "DELETE" })
        if (response.ok) {
            getRecipes()
        }
    }

    return (
        <div className="bg-eggshell">
            <div className="container my-12 mx-auto px-4 md:px-12">
                <div className="flex flex-wrap -mx-1 lg:-mx-4">
                    {recipes.map((recipe, i) => <RecipeCard key={i} recipe={recipe} owned={true} deleteRecipe={deleteRecipe}/>)}
                </div>
            </div>
        </div>
    )
}