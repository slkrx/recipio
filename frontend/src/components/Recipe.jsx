import { useContext, useEffect, useState } from "react"
import { useParams } from "react-router-dom"
import { ChevronDownIcon } from '@heroicons/react/20/solid'
import { Menu, MenuButton, MenuItem, MenuItems } from '@headlessui/react'
import AuthContext from "../context/AuthContext"

export default function Recipe() {
    const { id } = useParams()
    const [recipe, setRecipe] = useState({})
    const [organizations, setOrganizations] = useState([])
    const auth = useContext(AuthContext)

    useEffect(() => {
        getRecipe()
        getOrganizations()
    }, [])

    async function getOrganizations() {
        if (auth.user) {
            const response = await fetch(`http://localhost:8080/api/organizations?username=${auth.user.username}`)
            const data = await response.json()
            let organizationRecipes = {}
            if (data.length > 0) {
                for (const organization of data) {
                    const response = await fetch(`http://localhost:8080/api/organization-recipe/${organization.id}`)
                    const data = await response.json()
                    organizationRecipes = {...organizationRecipes, [organization.id]: data.map(recipe => recipe.id)}
                }
            }
            setOrganizations(data.filter(organization => {
                return !organizationRecipes[organization.id].includes(Number.parseInt(id))
            }))
        }
    }

    async function getRecipe() {
        const response = await fetch(`http://localhost:8080/api/recipes/${id}`)
        const data = await response.json()
        setRecipe(data)
    }

    async function addToOrganization(organizationId) {
        const response = await fetch("http://localhost:8080/api/organization-recipe", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            }, body: JSON.stringify({
                organizationId,
                recipeId: id
            })
        })
        if (response.ok) {
            getOrganizations()
        }
    }

    let addToOrganizationButton
    
    if (organizations.length > 0) {
        addToOrganizationButton = (
            <Menu as="div" className="relative inline-block text-left">
                <div>
                <MenuButton className="inline-flex w-full justify-center gap-x-1.5 rounded-md bg-white px-3 py-2 text-sm font-semibold text-wenge-brown shadow-sm ring-1 ring-inset ring-gray-300 hover:bg-gray-50">
                    Add to organization
                    <ChevronDownIcon aria-hidden="true" className="-mr-1 h-5 w-5 text-gray-400" />
                </MenuButton>
                </div>
      
                <MenuItems
                    transition
                    className="absolute right-0 z-10 mt-2 w-56 origin-top-right rounded-md bg-white shadow-lg ring-1 ring-black ring-opacity-5 transition focus:outline-none data-[closed]:scale-95 data-[closed]:transform data-[closed]:opacity-0 data-[enter]:duration-100 data-[leave]:duration-75 data-[enter]:ease-out data-[leave]:ease-in"
                >
                    <div className="py-1">
                        {organizations.map((organization, i) => {
                                return <MenuItem key={i}>
                                    <a
                                        onClick={() => addToOrganization(organization.id)}
                                        className="block px-4 py-2 text-sm text-wenge-brown data-[focus]:bg-gray-100 data-[focus]:text-gray-900"
                                    >
                                        {organization.name}
                                    </a>
                                </MenuItem>
                        })}
                    </div>
                </MenuItems>
          </Menu>
        )
    } else {
        addToOrganizationButton = "Added to all of your organizations!"
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
                    <div className="flex space-x-3">
                        <article className="bg-rose-white mt-6 p-5 rounded-xl flex-1">
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
                        <div className="mt-6">
                            {addToOrganizationButton}
                        </div>
                    </div>
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