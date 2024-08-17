import { useEffect, useState } from "react"
import RecipeCard from "./RecipeCard"
import SearchForm from "./SearchForm"

export default function Search() {
    const [query, setQuery] = useState("")
    const [page, setPage] = useState(1)
    const [resultsLength, setResultsLength] = useState(0)
    const [searchResults, setSearchResults] = useState([])
    
    useEffect(() => window.scrollTo(0,0), [searchResults])

    useEffect(() => { getSearchResults() }, [page])

    function handleSubmit(event) {
        event.preventDefault()
        getSearchResults()
    }

    async function getSearchResults() {
        try {
            const response = await fetch(`http://localhost:8080/api/recipes/search?q=${query}&p=${page}`)
            const data = await response.json()
            console.log(data)
            setSearchResults(data.recipes)
            setResultsLength(data['length'])
        } catch(error) {
            console.log(error)
        }
    }

    function prevPage() {
        setPage(page - 1)
    }

    function nextPage() {
        setPage(page + 1)
    }

    return (
        <div className="bg-eggshell">
            <SearchForm handleSubmit={handleSubmit} setQuery={setQuery}/>
            <div className="container my-12 mx-auto px-4 md:px-12">
                <div className="flex flex-wrap -mx-1 lg:-mx-4">
                    {searchResults.map((recipe, i) => <RecipeCard key={i} recipe={recipe}/>)}
                </div>
                {resultsLength > 0 && (
                    <div className="flex items-center justify-between border-t border-nutmeg bg-eggshell px-4 py-3 sm:px-6">
                        <div className="hidden sm:flex sm:flex-1 sm:items-center sm:justify-between">
                            <div>
                                <p className="text-sm text-gray-700">
                                    Showing
                                    <span className="font-medium"> {10 * (page - 1) + 1} </span>
                                    to
                                    <span className="font-medium"> {Math.min(10 * page, resultsLength)} </span>
                                    of
                                    <span className="font-medium"> {resultsLength} </span>
                                    results
                                </p>
                            </div>
                        <div>
                            <nav className="isolate inline-flex -space-x-px rounded-md shadow-sm bg-white" aria-label="Pagination">
                                {page > 1 && (
                                    <a onClick={prevPage} className="relative inline-flex items-center rounded-l-md px-2 py-2 text-gray-400 ring-1 ring-inset ring-gray-300 hover:bg-gray-50 focus:z-20 focus:outline-offset-0">
                                        <span className="sr-only">Previous</span>
                                        <svg className="h-5 w-5" viewBox="0 0 20 20" fill="currentColor" aria-hidden="true">
                                            <path fillRule="evenodd" d="M12.79 5.23a.75.75 0 01-.02 1.06L8.832 10l3.938 3.71a.75.75 0 11-1.04 1.08l-4.5-4.25a.75.75 0 010-1.08l4.5-4.25a.75.75 0 011.06.02z" clipRule="evenodd" />
                                        </svg>
                                    </a>
                                )}
                                {/* <!-- Current: "z-10 bg-indigo-600 text-white focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600", Default: "text-gray-900 ring-1 ring-inset ring-gray-300 hover:bg-gray-50 focus:outline-offset-0" --> */}
                                <span aria-current="page" className="relative z-10 inline-flex items-center bg-indigo-600 px-4 py-2 text-sm font-semibold text-white focus:z-20 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">
                                    {page}
                                </span>
                                {/* <a href="#" className="relative inline-flex items-center px-4 py-2 text-sm font-semibold text-gray-900 ring-1 ring-inset ring-gray-300 hover:bg-gray-50 focus:z-20 focus:outline-offset-0">2</a>
                                <a href="#" className="relative hidden items-center px-4 py-2 text-sm font-semibold text-gray-900 ring-1 ring-inset ring-gray-300 hover:bg-gray-50 focus:z-20 focus:outline-offset-0 md:inline-flex">3</a>
                                <span className="relative inline-flex items-center px-4 py-2 text-sm font-semibold text-gray-700 ring-1 ring-inset ring-gray-300 focus:outline-offset-0">...</span>
                                <a href="#" className="relative hidden items-center px-4 py-2 text-sm font-semibold text-gray-900 ring-1 ring-inset ring-gray-300 hover:bg-gray-50 focus:z-20 focus:outline-offset-0 md:inline-flex">8</a>
                                <a href="#" className="relative inline-flex items-center px-4 py-2 text-sm font-semibold text-gray-900 ring-1 ring-inset ring-gray-300 hover:bg-gray-50 focus:z-20 focus:outline-offset-0">9</a>
                                <a href="#" className="relative inline-flex items-center px-4 py-2 text-sm font-semibold text-gray-900 ring-1 ring-inset ring-gray-300 hover:bg-gray-50 focus:z-20 focus:outline-offset-0">10</a> */}
                                {page * 10 < resultsLength && (
                                    <a onClick={nextPage} className="relative inline-flex items-center rounded-r-md px-2 py-2 text-gray-400 ring-1 ring-inset ring-gray-300 hover:bg-gray-50 focus:z-20 focus:outline-offset-0">
                                        <span className="sr-only">Next</span>
                                        <svg className="h-5 w-5" viewBox="0 0 20 20" fill="currentColor" aria-hidden="true">
                                            <path fillRule="evenodd" d="M7.21 14.77a.75.75 0 01.02-1.06L11.168 10 7.23 6.29a.75.75 0 111.04-1.08l4.5 4.25a.75.75 0 010 1.08l-4.5 4.25a.75.75 0 01-1.06-.02z" clipRule="evenodd" />
                                        </svg>
                                    </a>
                                )}
                            </nav>
                            </div>
                        </div>
                    </div>
                )}
            </div>
        </div>
    )
}