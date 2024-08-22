import { useContext, useEffect, useState } from "react"
import { useNavigate, useParams } from "react-router-dom"
import AuthContext from "../context/AuthContext"

export default function OrganizationForm() {
    const INITIAL_ORGANIZATION = { name: "" }
    const { id } = useParams()
    const [organization, setOrganization] = useState(INITIAL_ORGANIZATION)
    const auth = useContext(AuthContext)
    const navigate = useNavigate()
    const [errors, setErrors] = useState([])

    async function handleSubmit(event) {
        event.preventDefault()

        try {
            const response = await fetch(`http://localhost:8080/api/organizations/${id ? id : auth.user.username}`, {
                method: `${id ? "PUT" : "POST"}`,
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(organization),
            })
            let data
            if (!id) {
                data = await response.json()
            }
            if (response.ok) {
                navigate(`/organization/${id ? id : data.id}`, { state: {owned: true} })
            } else {
                setErrors(data)
            }
        } catch(error) {
            console.log(error)
            setErrors(["Server failure"])
        }
    }

    function handleChange(event) {
        setOrganization({...organization, [event.target.name]: event.target.value})
    }

    useEffect(() => {
        if (id) {
            getOrganization()
        }
    }, [id])

    async function getOrganization() {
        const response = await fetch(`http://localhost:8080/api/organizations/${id}`)
        const data = await response.json()
        setOrganization(data)
    }

    return (
        <div className="flex justify-center mt-5">
            <form
                className="bg-[#fff] md:mb-[5rem] pb-8 md:rounded-xl md:max-w-screen-md w-full shadow"
                onSubmit={handleSubmit}
            >
                <h1 className="font-fancy text-4xl my-8 text-dark-charcoal px-8">
                    {id ? "Update Organization" : "Create New Organization"}
                </h1>
                <div className="mx-8 mb-5 font-outfit text-dark-raspberry">
                    {errors.map((error, i) => {
                        return <p key={i} className="text-sm font-light text-red-500 dark:text-gray-400">
                            {error}
                        </p>
                    })}
                </div>
                <div className="px-8 font-outfit text-wenge-brown">
                    <div>
                        <label htmlFor="title">Organization Name</label>
                        <input
                            type="text"
                            name="name"
                            id="name"
                            className="mb-3 bg-gray-50 border border-gray-300 text-gray-900 rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" 
                            required=""
                            onChange={handleChange}
                            value={organization.name}
                        />
                    </div>
                    <div>
                        <button
                            type="submit"
                            className="text-white bg-water hover:bg-water-700 focus:ring-4 focus:outline-none focus:ring-primary-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-primary-600 dark:hover:bg-primary-700 dark:focus:ring-primary-800"
                        >
                            {id ? "Update" : "Create"}
                        </button>
                    </div>
                </div>
            </form>
        </div>
    )
}