import { useEffect, useState } from "react"
import { Link, useLocation, useParams } from "react-router-dom"

export default function Organization() {
    const [organization, setOrganization] = useState({})
    const { id } = useParams()
    const [errors, setErrors] = useState([])
    const [username, setUsername] = useState("")
    const [members, setMembers] = useState([])
    const [recipes, setRecipes] = useState([])
    const location = useLocation()
    let owned = false
    if (location?.state) {
        owned = location.state.owned
    }

    useEffect(() => {
        getOrganization()
        if (owned) {
            getMembers()
        }
        getRecipes()
    }, [])

    async function getRecipes() {
        try {
            const response = await fetch(`http://localhost:8080/api/organization-recipe/${id}`)
            const data = await response.json()
            if (response.ok) {
                setRecipes(data)
            }
        } catch(error) {
            console.log(error)
        }
    }

    async function getOrganization() {
        try {
            const response = await fetch(`http://localhost:8080/api/organizations/${id}`)
            const data = await response.json()
            if (response.ok) {
                setOrganization(data)
            }
        } catch(error) {
            console.log(error)
        }
    }

    async function getMembers() {
        try {
            const response = await fetch(`http://localhost:8080/api/organization-app-user/${id}`)
            const data = await response.json()
            if (response.ok) {
                setMembers(data)
            }
        } catch(error) {
            console.log(error)
        }
    }

    async function addMember(event) {
        event.preventDefault()

        try {
            const response = await fetch(`http://localhost:8080/api/organization-app-user`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                }, body: JSON.stringify({
                    organizationId: id,
                    username: username
                })
            })
            if (response.ok) {
                document.querySelector("form").reset()
                setUsername("")
                getMembers()
            }
        } catch(error) {
            console.log(error)
        }
    }

    let membersHtml;

    if (owned) {
        membersHtml = (
            <>
                <article className="bg-rose-white m-6 p-5 rounded-xl">
                    <h2 className="text-dark-raspberry text-xl font-semibold ml-2">
                    Members:
                    </h2>
                    <ul className="list-disc mt-3 ml-8 text-lg marker:text-dark-raspberry">
                        {members.map((member, i) => {
                            return(
                                <li className="pl-3" key={i}>
                                    {member.username}
                                </li>
                            )
                        })}
                    </ul>
                </article>
                <form className="px-8 font-outfit text-wenge-brown" onSubmit={addMember}>
                    <div className="mx-8 mb-5 font-outfit text-dark-raspberry">
                        {errors.map((error, i) => {
                            return <p key={i} className="text-sm font-light text-red-500 dark:text-gray-400">
                                {error}
                            </p>
                        })}
                    </div>
                    <h2 className="font-fancy text-2xl mt-8 text-dark-charcoal">
                        Add a Member:
                    </h2>
                    <div className="px-8 font-outfit text-wenge-brown">
                        <div>
                            <label htmlFor="username">User Email</label>
                            <input
                                type="text"
                                name="username"
                                id="username"
                                className="mb-3 bg-gray-50 border border-gray-300 text-gray-900 rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" 
                                required=""
                                onChange={(event) => setUsername(event.target.value)}
                            />
                        </div>
                        <div>
                            <button
                                type="submit"
                                className="text-white bg-water hover:bg-water-700 focus:ring-4 focus:outline-none focus:ring-primary-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-primary-600 dark:hover:bg-primary-700 dark:focus:ring-primary-800"
                            >
                                Add Member
                            </button>
                        </div>
                    </div>
                </form>
            </>
        )
    }

    return (
        <div className="flex justify-center mt-5">
            <article
                className="w-full bg-[#fff] md:mb-[5rem] pb-8 md:rounded-xl md:max-w-screen-md"
            >
                <div className="px-8 font-outfit text-wenge-brown">
                    <h1 className="font-fancy text-4xl mt-8 text-dark-charcoal">
                        {organization?.name}
                    </h1>
                </div>
                {membersHtml}
                <article className="bg-light-gray m-6 p-5 rounded-xl">
                    <h2 className="text-nutmeg text-xl font-semibold ml-2">
                    Recipes:
                    </h2>
                    <ul className="list-disc mt-3 ml-8 text-lg marker:text-nutmeg">
                        {recipes.map((recipe, i) => {
                            return(
                                <li key={i}>
                                    <Link className="pl-3" to={`/recipe/${recipe.id}`}>
                                        {recipe.title}
                                    </Link>
                                </li>
                            )
                        })}
                    </ul>
                </article>
            </article>
        </div>
    )
}