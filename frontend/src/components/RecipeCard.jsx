import { useContext, useEffect, useState } from "react"
import { Link } from "react-router-dom"
import AuthContext from "../context/AuthContext"

export default function RecipeCard({ recipe, owned=false, deleteRecipe }) {
    const auth = useContext(AuthContext)
    const [userSavedRecipes, setUserSavedRecipes] = useState([])

    useEffect(() => {
        getSavedRecipes()
    }, [])

    async function getSavedRecipes() {
        if (auth.user) {
            const response = await fetch(`http://localhost:8080/api/app-user-recipe-saved/${auth.user.username}`)
            const data = await response.json()
            setUserSavedRecipes(data)
        }
    }

    let saveRecipeButton;
    let editRecipeButton;
    let deleteRecipeButton;

    if (auth.user) {
        if (userSavedRecipes.map(recipe => recipe.id).includes(recipe.id)) {
            saveRecipeButton = (
                <button
                className="inline-flex items-center px-3 py-2 text-sm font-medium text-center text-white bg-gray-500 rounded-lg hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
                onClick={unsaveRecipe}>
                    Unsave Recipe
                </button>
            )
        } else {
            saveRecipeButton = (
                <button
                className="inline-flex items-center px-3 py-2 text-sm font-medium text-center text-white bg-blue-700 rounded-lg hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
                onClick={saveRecipe}>
                    Save Recipe
                </button>
            )
        }
        if (owned) {
            editRecipeButton = (
                <Link
                className="inline-flex items-center px-3 py-2 text-sm font-medium text-center text-white bg-amber-400 rounded-lg hover:bg-amber-500 focus:ring-4 focus:outline-none focus:ring-blue-300 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
                to={`/edit/${recipe.id}`}>
                    Edit Recipe
                </Link>
            )
            deleteRecipeButton = (
                <button
                className="inline-flex items-center px-3 py-2 text-sm font-medium text-center text-white bg-red-600 rounded-lg hover:bg-red-700 focus:ring-4 focus:outline-none focus:ring-blue-300 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
                onClick={() => deleteRecipe(recipe.id)}>
                    Delete Recipe
                </button>
            )
        }
    }

    async function saveRecipe() {
        const response = await fetch(`http://localhost:8080/api/app-user-recipe-saved/${auth.user.username}/${recipe.id}`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            }
        })
        if (response.ok) {
            getSavedRecipes()
        }
    }

    async function unsaveRecipe() {
        const response = await fetch(`http://localhost:8080/api/app-user-recipe-saved/${auth.user.username}/${recipe.id}`, {
            method: "DELETE",
            headers: {
                "Content-Type": "application/json"
            }
        })
        if (response.ok) {
            getSavedRecipes()
        }
    }

    return (
        <div className="flex flex-col items-center my-1 px-1 w-full md:w-1/2 lg:my-4 lg:px-4 lg:w-1/3">
            <div className="max-w-sm bg-white border border-gray-200 rounded-lg shadow dark:bg-gray-800 dark:border-gray-700">
                <Link to={`/recipe/${recipe.id}`}>
                    <div className="rounded-t-lg w-full h-64" style={{backgroundImage: `url("${recipe.imageUrl}")`, backgroundPosition: "center", backgroundSize: "cover"}}></div>
                </Link>
                <div className="p-5">
                    <a href="#">
                        <h5 className="mb-2 text-2xl font-bold tracking-tight text-gray-900 dark:text-white">
                            {recipe.title}
                        </h5>
                    </a>
                    <p className="mb-3 font-normal text-gray-700 dark:text-gray-400">
                        {recipe.description.substring(0,100) + '...'}
                    </p>
                    <div className="flex space-x-2">
                        <Link to={`/recipe/${recipe.id}`} className="inline-flex items-center px-3 py-2 text-sm font-medium text-center text-white bg-blue-700 rounded-lg hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">
                            View Recipe
                            <svg className="rtl:rotate-180 w-3.5 h-3.5 ms-2" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 14 10">
                                <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M1 5h12m0 0L9 1m4 4L9 9"/>
                            </svg>
                        </Link>
                        {saveRecipeButton}
                        {editRecipeButton}
                        {deleteRecipeButton}
                    </div>
                </div>
            </div>
        </div>
    )
}