import { useEffect, useState } from "react"
import { useParams } from "react-router-dom"

export default function Recipe() {
    const { id } = useParams()
    const [recipe, setRecipe] = useState({})

    useEffect(() => {
        getRecipe()
    }, [])

    async function getRecipe() {
        const response = await fetch(`http://localhost:8080/api/recipes/${id}`)
        const data = await response.json()
        setRecipe(data)
    }

    return (
        <div className="flex justify-center mt-5">
            <article
                className="w-full bg-[#fff] md:mb-[5rem] md:py-8 pb-8 md:rounded-xl md:max-w-screen-md"
            >
                <picture>
                <img
                    src={recipe.imageUrl}
                    className="md:max-w-[60%] md:mx-auto md:rounded-xl"
                />
                </picture>
                <div className="px-8 font-outfit text-wenge-brown">
                    <h1 className="font-fancy text-4xl mt-8 text-dark-charcoal">
                        {recipe.title}
                    </h1>
                    <p className="mt-6">
                        {recipe.description}
                    </p>
                    <article className="bg-rose-white mt-6 p-5 rounded-xl">
                        <h2 className="text-dark-raspberry text-xl font-semibold ml-2">
                        Preparation time
                        </h2>
                        <ul className="list-disc mt-3 ml-8 text-lg marker:text-dark-raspberry">
                            <li className="pl-3">
                                <p>
                                <span className="font-semibold">Total: </span>{recipe.time}
                                </p>
                            </li>
                        </ul>
                    </article>
                    <div className="mt-8">
                        <h3 className="font-fancy text-3xl text-nutmeg">Ingredients</h3>
                        <ul
                        className="list-disc marker:text-nutmeg mt-4 ml-6 text-wenge-brown marker:align-middle"
                        >
                            {recipe.ingredients?.map((ingredient, i) => {
                                return <li key={i} className="pl-4 mt-2">{ingredient}</li>
                            })}
                        </ul>
                    </div>
                    <div className="w-full h-px bg-light-gray mx-auto mt-8"></div>
                    <div className="mt-8">
                        <h3 className="font-fancy text-3xl text-nutmeg">Instructions</h3>
                        <ol
                        className="marker:text-nutmeg marker:font-semibold marker:font-outfit list-decimal mt-4 ml-6"
                        >
                            {recipe.steps?.map((step, i) => {
                                return (
                                    <li key={i} className="pl-4">
                                        <p>{step}</p>
                                    </li>
                                )
                            })}
                        </ol>
                    </div>
                </div>
            </article>
        </div>
    )
}