import { useEffect, useState } from "react"
import RecipeCard from "./RecipeCard"
import SearchForm from "./SearchForm"
import { useSearchParams } from "react-router-dom"
import { ChevronLeftIcon, ChevronRightIcon } from '@heroicons/react/20/solid'

export default function Search() {
    const [searchParams, setSearchParams] = useSearchParams()
    const [resultsLength, setResultsLength] = useState(0)
    const [pages, setPages] = useState(0)
    const [searchResults, setSearchResults] = useState([])
    const [spinner, setSpinner] = useState(false)
    const activePageClasses = "relative z-10 inline-flex items-center bg-indigo-600 px-4 py-2 text-sm font-semibold text-white focus:z-20 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
    const inactivePageClasses = "relative inline-flex items-center px-4 py-2 text-sm font-semibold text-gray-900 ring-1 ring-inset ring-gray-300 hover:bg-gray-50 focus:z-20 focus:outline-offset-0"
    
    useEffect(() => window.scrollTo(0,0), [searchResults])
    useEffect(() => {getSearchResults()}, [searchParams])
    useEffect(() => {setPages(Math.ceil(resultsLength / 10))}, [resultsLength])

    function handleSubmit(event) {
        event.preventDefault()

        const newSearchParams = { q: event.target.querySelector("input").value, p: 1 } 
        setSearchParams(newSearchParams)
    }

    async function getSearchResults() {
        if (searchParams.get("q") && searchParams.get("q")) {
            setSpinner(true)
            try {
                const response = await fetch(`http://localhost:8080/api/recipes/search?q=${searchParams.get("q")}&p=${searchParams.get("p")}`)
                const data = await response.json()
                console.log(data)
                setSpinner(false)
                setSearchResults(data.recipes)
                setResultsLength(data['length'])
            } catch(error) {
                console.log(error)
            }
        } else {
            setSearchResults([])
            setResultsLength(0)
        }
    }

    function prevPage() {
        setSearchParams({...Object.fromEntries([...searchParams]), p: Number.parseInt(searchParams.get("p")) - 1})
    }

    function nextPage() {
        setSearchParams({...Object.fromEntries([...searchParams]), p: Number.parseInt(searchParams.get("p")) + 1})
    }

    function setPage(i) {
        setSearchParams({...Object.fromEntries([...searchParams]), p: i})
    }

    return (
        <div className="bg-eggshell">
            <SearchForm handleSubmit={handleSubmit}/>
            <div className="container my-12 mx-auto px-4 md:px-12">
                <div className="flex flex-wrap -mx-1 lg:-mx-4">
                    {spinner ? (
                        <div className="flex items-center justify-center w-full mb-12 p-8" role="status">
                            <svg aria-hidden="true" className="w-8 h-8 text-gray-200 animate-spin dark:text-gray-600 fill-blue-600" viewBox="0 0 100 101" fill="none" xmlns="http://www.w3.org/2000/svg">
                                <path d="M100 50.5908C100 78.2051 77.6142 100.591 50 100.591C22.3858 100.591 0 78.2051 0 50.5908C0 22.9766 22.3858 0.59082 50 0.59082C77.6142 0.59082 100 22.9766 100 50.5908ZM9.08144 50.5908C9.08144 73.1895 27.4013 91.5094 50 91.5094C72.5987 91.5094 90.9186 73.1895 90.9186 50.5908C90.9186 27.9921 72.5987 9.67226 50 9.67226C27.4013 9.67226 9.08144 27.9921 9.08144 50.5908Z" fill="currentColor"/>
                                <path d="M93.9676 39.0409C96.393 38.4038 97.8624 35.9116 97.0079 33.5539C95.2932 28.8227 92.871 24.3692 89.8167 20.348C85.8452 15.1192 80.8826 10.7238 75.2124 7.41289C69.5422 4.10194 63.2754 1.94025 56.7698 1.05124C51.7666 0.367541 46.6976 0.446843 41.7345 1.27873C39.2613 1.69328 37.813 4.19778 38.4501 6.62326C39.0873 9.04874 41.5694 10.4717 44.0505 10.1071C47.8511 9.54855 51.7191 9.52689 55.5402 10.0491C60.8642 10.7766 65.9928 12.5457 70.6331 15.2552C75.2735 17.9648 79.3347 21.5619 82.5849 25.841C84.9175 28.9121 86.7997 32.2913 88.1811 35.8758C89.083 38.2158 91.5421 39.6781 93.9676 39.0409Z" fill="currentFill"/>
                            </svg>
                            <span className="sr-only">Loading...</span>
                        </div>
                    ) : searchResults.map((recipe, i) => <RecipeCard key={i} recipe={recipe}/>)}
                </div>
                {resultsLength > 0 && (
                    <div className="flex items-center justify-between border-t border-gray-300 bg-eggshell px-4 py-3 sm:px-6">
                        <div className="hidden sm:flex sm:flex-1 sm:items-center sm:justify-between">
                            <div>
                                <p className="text-sm text-gray-700">
                                    Showing
                                    <span className="font-medium"> {10 * (searchParams.get("p") - 1) + 1} </span>
                                    to
                                    <span className="font-medium"> {Math.min(10 * searchParams.get("p"), resultsLength)} </span>
                                    of
                                    <span className="font-medium"> {resultsLength} </span>
                                    results
                                </p>
                            </div>
                        <div>
                            <nav className="isolate inline-flex -space-x-px rounded-md shadow-sm bg-white" aria-label="Pagination">
                                {searchParams.get("p") > 1 && (
                                    <a onClick={prevPage} className="relative inline-flex items-center rounded-l-md px-2 py-2 text-gray-400 ring-1 ring-inset ring-gray-300 hover:bg-gray-50 focus:z-20 focus:outline-offset-0">
                                        <span className="sr-only">Previous</span>
                                        <ChevronLeftIcon aria-hidden="true" className="h-5 w-5" />
                                    </a>
                                )}
                                {pages > 1 && (
                                    [...Array(pages).keys()].map(i => i + 1).map(i => {
                                        if (searchParams.get("p") == 1 && i == 3 || pages > 6 && searchParams.get("p") > pages - 5 && i > pages - 6) {
                                            return (
                                                <a
                                                    key={i}
                                                    onClick={() => setPage(i)}
                                                    className={searchParams.get("p") == i ? activePageClasses : inactivePageClasses}
                                                >{i}</a>
                                            )
                                        }
                                        else if (pages > 6 && Math.abs(searchParams.get("p") - i) > 1 && i < pages - 2) {
                                            if (i == pages - 3) {
                                                return <a
                                                    key={i}
                                                    className={inactivePageClasses}
                                                    disabled=""
                                                >...</a>
                                            } else {
                                                return null
                                            }
                                        } else {
                                            return (
                                                <a
                                                    key={i}
                                                    onClick={() => setPage(i)}
                                                    className={searchParams.get("p") == i ? activePageClasses : inactivePageClasses}
                                                >{i}</a>
                                            )
                                        }
                                    }).filter(elt => elt)
                                )}
                                {searchParams.get("p") < pages && (
                                    <a onClick={nextPage} className="relative inline-flex items-center rounded-r-md px-2 py-2 text-gray-400 ring-1 ring-inset ring-gray-300 hover:bg-gray-50 focus:z-20 focus:outline-offset-0">
                                        <span className="sr-only">Next</span>
                                        <ChevronRightIcon aria-hidden="true" className="h-5 w-5" />
                                    </a>
                                )}
                            </nav>
                        </div>
                    </div>
                </div>)}
            </div>
        </div>
    )
}