import { useEffect, useState } from "react"
import RecipeCard from "./RecipeCard"
import SearchForm from "./SearchForm"

export default function Search() {
    const [query, setQuery] = useState("")
    const [page, setPage] = useState(1)
    const [searchResults, setSearchResults] = useState([])
    
    useEffect(() => window.scrollTo(0,0), [searchResults])

    function handleSubmit(event) {
        event.preventDefault()
        getSearchResults()
    }

    async function getSearchResults() {
        try {
            const response = await fetch(`http://localhost:8080/api/recipes/search?q=${query}&p=${page}`)
            const data = await response.json()
            console.log(data)
            setSearchResults(data)
        } catch(error) {
            console.log(error)
        }
    }

    function prevPage() {
        setPage(page - 1)
        getSearchResults()
    }

    function nextPage() {
        setPage(page + 1)
        getSearchResults()
    }

    return (
        <div className="bg-gray-50">
            <SearchForm handleSubmit={handleSubmit} setQuery={setQuery}/>
            <div className="container my-12 mx-auto px-4 md:px-12">
                <div className="flex flex-wrap -mx-1 lg:-mx-4">
                    {searchResults.map((recipe, i) => <RecipeCard key={i} recipe={recipe}/>)}
                </div>
                    {searchResults.length > 0 && (
                        <div className='my-5'>
                            <hr/>
                            <div className="flex justify-center my-5">
                                <a onClick={prevPage} className="flex items-center justify-center px-4 h-10 text-base font-medium text-gray-500 bg-white border border-gray-300 rounded-lg hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white">
                                    Previous
                                </a>
                                <a onClick={nextPage} className="flex items-center justify-center px-4 h-10 ms-3 text-base font-medium text-gray-500 bg-white border border-gray-300 rounded-lg hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white">
                                    Next
                                </a>
                            </div>
                        </div>
                    )}
            </div>
        </div>
    )
}