import { useContext, useEffect, useState } from "react"
import AuthContext from "../context/AuthContext"
import { Link } from "react-router-dom"
import { PencilIcon, TrashIcon } from "@heroicons/react/20/solid"

export default function MyOrganizations() {
    const [ownedOrganizations, setOwnedOrganizations] = useState([])
    const [belongedOrganizations, setBelongedOrganizations] = useState([])
    const auth = useContext(AuthContext)

    useEffect(() => {
        getOwnedOrganizations()
        getBelongedOrganizations()
    }, [])

    async function getOwnedOrganizations() {
        const response = await fetch(`https://samrechsteiner.com/recipio-api/api/organizations?username=${auth.user.username}`)
        const data = await response.json()
        setOwnedOrganizations(data)
    }

    async function getBelongedOrganizations() {
        const response = await fetch(`https://samrechsteiner.com/recipio-api/api/organization-app-user?username=${auth.user.username}`)
        const data = await response.json()
        setBelongedOrganizations(data)
    }

    async function handleDelete(id) {
        const response = await fetch(`https://samrechsteiner.com/recipio-api/api/organizations/${id}`, { method: "DELETE" })
        if (response.ok) {
            getOwnedOrganizations()
        }
    }

    return (
        <div className="flex justify-center mt-5">
            <div
                className="bg-[#fff] md:mb-[5rem] pb-3 md:rounded-xl md:max-w-screen-md w-full shadow"
            >
                <div className="bg-light-gray md:rounded-xl md:m-3 p-3">
                    <h2 className="font-fancy text-4xl my-8 text-dark-charcoal px-8">
                        Organizations You Own
                    </h2>
                    <ul>
                        {ownedOrganizations.map((organization, i) => {
                            return (
                                <li className="px-8 py-4 font-outfit text-wenge-brown text-3xl flex items-center space-x-5" key={i}>
                                    <Link to={`/organization/${organization.id}`} state={{owned: true}}>
                                        {organization.name}
                                    </Link>
                                    <button 
                                    className="bg-dark-raspberry text-rose-white rounded py-3 px-2 text-sm flex space-x-2"
                                    onClick={() => handleDelete(organization.id)}>
                                        <span>
                                            Delete
                                        </span>
                                        <TrashIcon className="h-5 w-5" />
                                    </button>
                                    <Link
                                    className="bg-amber-500 text-rose-white rounded py-3 px-2 text-sm flex space-x-2"
                                    to={`/organization/edit/${organization.id}`}>
                                        <span>
                                            Edit
                                        </span>
                                        <PencilIcon className="h-5 w-5" />
                                    </Link>
                                </li>
                            )
                        })}
                    </ul>
                </div>
                <div className="bg-light-gray md:rounded-xl md:m-3 p-3">
                    <h2 className="font-fancy text-4xl my-8 text-dark-charcoal px-8">
                        Organizations You Belong To
                    </h2>
                    <ul>
                        {belongedOrganizations.map((organization, i) => {
                            return (
                                <li className="px-8 py-4 font-outfit text-wenge-brown text-3xl" key={i}>
                                    <Link to={`/organization/${organization.id}`}>
                                        {organization.name}
                                    </Link>
                                </li>
                            )
                        })}
                    </ul>
                </div>
            </div>
        </div>
    )
}