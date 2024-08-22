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

    let saveRecipeButton
    let editRecipeButton
    let deleteRecipeButton

    if (auth.user) {
        if (userSavedRecipes.map(recipe => recipe.id).includes(recipe.id)) {
            saveRecipeButton = (
                <button
                className="inline-flex items-center px-3 py-2 text-sm font-medium text-center text-light-gray bg-gray-500 rounded-lg hover:bg-gray-800 focus:ring-4 focus:outline-none focus:ring-blue-300 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
                onClick={unsaveRecipe}>
                    Unsave Recipe
                    <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" className="size-6 ms-2">
                        <path strokeLinecap="round" strokeLinejoin="round" d="m3 3 1.664 1.664M21 21l-1.5-1.5m-5.485-1.242L12 17.25 4.5 21V8.742m.164-4.078a2.15 2.15 0 0 1 1.743-1.342 48.507 48.507 0 0 1 11.186 0c1.1.128 1.907 1.077 1.907 2.185V19.5M4.664 4.664 19.5 19.5" />
                    </svg>
                </button>
            )
        } else {
            saveRecipeButton = (
                <button
                className="inline-flex items-center px-3 py-2 text-sm font-medium text-center text-blue-gray bg-pine rounded-lg hover:bg-pine-700 focus:ring-4 focus:outline-none focus:ring-blue-300 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
                onClick={saveRecipe}>
                    Save Recipe
                    <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" className="size-6">
                        <path strokeLinecap="round" strokeLinejoin="round" d="M17.593 3.322c1.1.128 1.907 1.077 1.907 2.185V21L12 17.25 4.5 21V5.507c0-1.108.806-2.057 1.907-2.185a48.507 48.507 0 0 1 11.186 0Z" />
                    </svg>
                </button>
            )
        }
        if (owned) {
            editRecipeButton = (
                <Link
                className="inline-flex items-center px-3 py-2 text-sm font-medium text-center text-rose-white bg-amber-400 rounded-lg hover:bg-amber-500 focus:ring-4 focus:outline-none focus:ring-blue-300 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
                to={`/edit/${recipe.id}`}>
                    Edit Recipe
                    <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" className="size-6 ms-2">
                        <path strokeLinecap="round" strokeLinejoin="round" d="m16.862 4.487 1.687-1.688a1.875 1.875 0 1 1 2.652 2.652L10.582 16.07a4.5 4.5 0 0 1-1.897 1.13L6 18l.8-2.685a4.5 4.5 0 0 1 1.13-1.897l8.932-8.931Zm0 0L19.5 7.125M18 14v4.75A2.25 2.25 0 0 1 15.75 21H5.25A2.25 2.25 0 0 1 3 18.75V8.25A2.25 2.25 0 0 1 5.25 6H10" />
                    </svg>
                </Link>
            )
            deleteRecipeButton = (
                <button
                className="inline-flex items-center px-3 py-2 text-sm font-medium text-center text-rose-white bg-dark-raspberry rounded-lg hover:bg-dark-raspberry-700 focus:ring-4 focus:outline-none focus:ring-blue-300 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
                onClick={() => deleteRecipe(recipe.id)}>
                    Delete Recipe
                    <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" className="size-6 ms-2">
                        <path strokeLinecap="round" strokeLinejoin="round" d="m14.74 9-.346 9m-4.788 0L9.26 9m9.968-3.21c.342.052.682.107 1.022.166m-1.022-.165L18.16 19.673a2.25 2.25 0 0 1-2.244 2.077H8.084a2.25 2.25 0 0 1-2.244-2.077L4.772 5.79m14.456 0a48.108 48.108 0 0 0-3.478-.397m-12 .562c.34-.059.68-.114 1.022-.165m0 0a48.11 48.11 0 0 1 3.478-.397m7.5 0v-.916c0-1.18-.91-2.164-2.09-2.201a51.964 51.964 0 0 0-3.32 0c-1.18.037-2.09 1.022-2.09 2.201v.916m7.5 0a48.667 48.667 0 0 0-7.5 0" />
                    </svg>
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
            <div className="w-full max-w-sm bg-white border border-gray-200 rounded-lg shadow dark:bg-gray-800 dark:border-gray-700">
                <Link to={`/recipe/${recipe.id}`}>
                    <div
                        className="rounded-t-lg w-full h-64"
                        style={{backgroundImage: `url("${recipe.imageUrl ? recipe.imageUrl : '/default_image.webp'}")`, backgroundPosition: "center", backgroundSize: "cover"}}
                        loading="lazy"></div>
                </Link>
                <div className="p-5">
                    <a href="#">
                        <h5 className="flex items-center h-24 font-fancy mb-2 text-2xl font-bold tracking-tight text-nutmeg">
                            {recipe.title}
                        </h5>
                    </a>
                    <p className="h-24 mb-3 font-normal text-wenge-brown">
                        {recipe.description.substring(0,100) + '...'}
                    </p>
                    <div className="flex gap-2 flex-wrap">
                        <Link to={`/recipe/${recipe.id}`} className="inline-flex items-center px-3 py-2 text-sm font-medium text-center text-light-gray bg-water rounded-lg hover:bg-water-700 focus:ring-4 focus:outline-none focus:ring-blue-300 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">
                            View Recipe
                            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" className="size-6 ms-2">
                                <path strokeLinecap="round" strokeLinejoin="round" d="M2.036 12.322a1.012 1.012 0 0 1 0-.639C3.423 7.51 7.36 4.5 12 4.5c4.638 0 8.573 3.007 9.963 7.178.07.207.07.431 0 .639C20.577 16.49 16.64 19.5 12 19.5c-4.638 0-8.573-3.007-9.963-7.178Z" />
                                <path strokeLinecap="round" strokeLinejoin="round" d="M15 12a3 3 0 1 1-6 0 3 3 0 0 1 6 0Z" />
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