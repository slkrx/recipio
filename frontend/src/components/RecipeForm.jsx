import { useContext, useState } from "react"
import AuthContext from "../context/AuthContext"
import { useNavigate } from "react-router-dom"

export default function RecipeForm() {
    const INITIAL_RECIPE = {
        title: "",
        description: "",
        time: "",
        categories: [],
        ingredients: [],
        steps: [],
        imageUrl: ""
    }
    const [recipe, setRecipe] = useState(INITIAL_RECIPE)
    const auth = useContext(AuthContext)
    const navigate = useNavigate()

    async function handleSubmit(event) {
        event.preventDefault()

        const response = await fetch(`http://localhost:8080/api/recipes/${auth.user.username}`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(recipe)
        })
        const data = await response.json()
        navigate(`/recipe/${data.id}`)
    }

    function handleChange(event) {
        setRecipe({...recipe, [event.target.name]: event.target.value})
        console.log(recipe)
    }

    function handleArrayChange(event, i) {
        let array = [...recipe[event.target.name]]
        array[i] = event.target.value
        setRecipe({...recipe, [event.target.name]: array})
        console.log(recipe)
    }

    return (
        <div className="flex justify-center mt-5">
            <form
                className="bg-[#fff] md:mb-[5rem] pb-8 md:rounded-xl md:max-w-screen-md w-full shadow"
                onSubmit={handleSubmit}
            >
                <h1 className="font-fancy text-4xl my-8 text-dark-charcoal px-8">
                    Create New Recipe
                </h1>
                <div className="px-8 font-outfit text-wenge-brown">
                    <div>
                        <label htmlFor="title">Title</label>
                        <input
                            type="text"
                            name="title"
                            id="title"
                            className="mb-3 bg-gray-50 border border-gray-300 text-gray-900 rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" 
                            required=""
                            onChange={handleChange}
                        />
                    </div>
                    <div>
                        <label htmlFor="description">Description</label>
                        <textarea
                            name="description"
                            id="description"
                            className="mb-3 bg-gray-50 border border-gray-300 text-gray-900 rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" 
                            required=""
                            onChange={handleChange}
                        />
                    </div>
                    <div className="mb-3 flex space-x-3">
                        <div className="w-1/2">
                            <label htmlFor="time">Total time required</label>
                            <input
                                type="text"
                                name="time"
                                id="time"
                                className="bg-gray-50 border border-gray-300 text-gray-900 rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" 
                                required=""
                                onChange={handleChange}
                            />
                        </div>
                        <div className="w-1/2">
                            <label htmlFor="imageUrl">Image Url</label>
                            <input
                                type="text"
                                name="imageUrl"
                                id="imageUrl"
                                className="bg-gray-50 border border-gray-300 text-gray-900 rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" 
                                required=""
                                onChange={handleChange}
                            />
                        </div>
                    </div>
                    <div className="mb-3 flex space-x-3">
                        <div className="w-1/2">
                            <label htmlFor="categories" className="block text-center">Categories</label>
                            <div id="categories">
                                {recipe.categories.map((category, i) => {
                                    return (
                                        <input
                                            key={i}
                                            type="text"
                                            name="categories"
                                            className="mb-3 bg-gray-50 border border-gray-300 text-gray-900 rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" 
                                            required=""
                                            onChange={(event) => handleArrayChange(event, i)}
                                            value={category}
                                        />
                                    )
                                })}
                            </div>
                            <div className="flex justify-center">
                                <button
                                    type="button"
                                    className="w-10 h-10 text-lg text-dark-charcoal bg-rose-white rounded-full"
                                    onClick={() => { setRecipe({...recipe, categories: [...recipe.categories, ""]}) }}
                                >
                                    +
                                </button>
                            </div>
                        </div>
                        <div className="w-1/2">
                            <label htmlFor="ingredients" className="block text-center">Ingredients</label>
                            <div id="ingredients">
                                {recipe.ingredients.map((ingredient, i) => {
                                    return (
                                        <input
                                            key={i}
                                            type="text"
                                            name="ingredients"
                                            className="mb-3 bg-gray-50 border border-gray-300 text-gray-900 rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" 
                                            required=""
                                            onChange={(event) => handleArrayChange(event, i)}
                                            value={ingredient}
                                        />
                                    )
                                })}
                            </div>
                            <div className="flex justify-center">
                                <button
                                    type="button"
                                    className="w-10 h-10 text-lg text-dark-charcoal bg-rose-white rounded-full"
                                    onClick={() => { setRecipe({...recipe, ingredients: [...recipe.ingredients, ""]}) }}
                                >
                                    +
                                </button>
                            </div>
                        </div>
                    </div>
                    <div className="mb-3">
                        <label htmlFor="steps" className="block text-center">Instructions</label>
                        <div id="steps">
                            {recipe.steps.map((step, i) => {
                                return (
                                    <textarea
                                        key={i}
                                        name="steps"
                                        className="mb-3 bg-gray-50 border border-gray-300 text-gray-900 rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" 
                                        required=""
                                        onChange={(event) => handleArrayChange(event, i)}
                                        value={step}
                                    />
                                )
                            })}
                        </div>
                        <div className="flex justify-center">
                            <button
                                type="button"
                                className="w-10 h-10 text-lg text-dark-charcoal bg-rose-white rounded-full"
                                onClick={() => { setRecipe({...recipe, steps: [...recipe.steps, ""]}) }}
                            >
                                +
                            </button>
                        </div>
                    </div>
                    <div>
                        <button
                            type="submit"
                            className="text-white bg-primary-600 hover:bg-primary-700 focus:ring-4 focus:outline-none focus:ring-primary-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-primary-600 dark:hover:bg-primary-700 dark:focus:ring-primary-800"
                        >
                            Create
                        </button>
                    </div>
                </div>
            </form>
        </div>
    )
}