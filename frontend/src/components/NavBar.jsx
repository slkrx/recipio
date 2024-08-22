import { useContext } from "react";
import { Link, NavLink, useNavigate } from "react-router-dom";
import AuthContext from "../context/AuthContext";
import { ArrowRightEndOnRectangleIcon, ArrowTurnDownLeftIcon, BookmarkSquareIcon, FolderPlusIcon, MagnifyingGlassIcon, RectangleGroupIcon, UserGroupIcon, UserIcon } from "@heroicons/react/20/solid";

function NavBar() {
  const auth = useContext(AuthContext);
  const navigate = useNavigate();
  let userLinks

  if (auth.user) {
    userLinks = (
      <>
        <li>
            <NavLink to="/saved-recipes" className="flex items-center p-2 text-gray-900 rounded-lg dark:text-white hover:bg-gray-100 dark:hover:bg-gray-700 group">
              <BookmarkSquareIcon className="text-gray-500 group-hover:text-gray-900 w-5 h-5"/>
              <span className="ms-3">Saved Recipes</span>
            </NavLink>
        </li>
        <li>
            <NavLink to="/recipe/new" className="flex items-center p-2 text-gray-900 rounded-lg dark:text-white hover:bg-gray-100 dark:hover:bg-gray-700 group">
              <FolderPlusIcon className="text-gray-500 group-hover:text-gray-900 w-5 h-5"/>
              <span className="ms-3">New Recipe</span>
            </NavLink>
        </li>
        <li>
            <NavLink to="/my-recipes" className="flex items-center p-2 text-gray-900 rounded-lg dark:text-white hover:bg-gray-100 dark:hover:bg-gray-700 group">
              <UserIcon className="text-gray-500 group-hover:text-gray-900 w-5 h-5"/>
              <span className="ms-3">My Recipes</span>
            </NavLink>
        </li>
        <li>
            <NavLink to="/organization/new" className="flex items-center p-2 text-gray-900 rounded-lg dark:text-white hover:bg-gray-100 dark:hover:bg-gray-700 group">
              <UserGroupIcon className="text-gray-500 group-hover:text-gray-900 w-5 h-5"/>
              <span className="ms-3">New Organization</span>
            </NavLink>
        </li>
        <li>
            <NavLink to="/my-organizations" className="flex items-center p-2 text-gray-900 rounded-lg dark:text-white hover:bg-gray-100 dark:hover:bg-gray-700 group">
              <RectangleGroupIcon className="text-gray-500 group-hover:text-gray-900 w-5 h-5"/>
              <span className="ms-3">My Organizations</span>
            </NavLink>
        </li>
      </>
    )
  }

  return (
    <>
      <button data-drawer-target="default-sidebar" data-drawer-toggle="default-sidebar" aria-controls="default-sidebar" type="button" className="inline-flex items-center p-2 mt-2 ms-3 text-sm text-gray-500 rounded-lg sm:hidden hover:bg-gray-100 focus:outline-none focus:ring-2 focus:ring-gray-200 dark:text-gray-400 dark:hover:bg-gray-700 dark:focus:ring-gray-600">
        <span className="sr-only">Open sidebar</span>
        <svg className="w-6 h-6" aria-hidden="true" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
        <path clipRule="evenodd" fillRule="evenodd" d="M2 4.75A.75.75 0 012.75 4h14.5a.75.75 0 010 1.5H2.75A.75.75 0 012 4.75zm0 10.5a.75.75 0 01.75-.75h7.5a.75.75 0 010 1.5h-7.5a.75.75 0 01-.75-.75zM2 10a.75.75 0 01.75-.75h14.5a.75.75 0 010 1.5H2.75A.75.75 0 012 10z"></path>
        </svg>
      </button>

      <aside id="default-sidebar" className="shadow fixed top-0 left-0 z-40 w-64 h-screen transition-transform -translate-x-full sm:translate-x-0" aria-label="Sidebar">
        <div className="h-full px-3 py-4 overflow-y-auto bg-rose-white">
            <ul className="space-y-2 font-medium font-outfit">
              <li>
                <Link to="/" className="flex items-center space-x-3 rtl:space-x-reverse">
                    <span className="self-center text-2xl font-semibold whitespace-nowrap dark:text-white font-fancy text-dark-raspberry">Recipio</span>
                </Link>
              </li>
              <li>
                  <NavLink to="/" className="flex items-center p-2 text-gray-900 rounded-lg dark:text-white hover:bg-gray-100 dark:hover:bg-gray-700 group">
                    <MagnifyingGlassIcon className="text-gray-500 group-hover:text-gray-900 w-5 h-5"/>
                    <span className="ms-3">Search</span>
                  </NavLink>
              </li>
              {userLinks}
              <li>
                {auth.user ? (
                  <a onClick={() => { auth.logout(); navigate("/") } } className="flex items-center p-2 text-gray-900 rounded-lg dark:text-white hover:bg-gray-100 dark:hover:bg-gray-700 group">
                    <ArrowTurnDownLeftIcon className="text-gray-500 group-hover:text-gray-900 w-5 h-5"/>
                    <span className="ms-3">Logout</span>
                  </a>
                ) : (
                  <NavLink to="/login" className="flex items-center p-2 text-gray-900 rounded-lg dark:text-white hover:bg-gray-100 dark:hover:bg-gray-700 group">
                    <ArrowRightEndOnRectangleIcon className="text-gray-500 group-hover:text-gray-900 w-5 h-5"/>
                    <span className="ms-3">Login</span>
                    </NavLink>
                )}
              </li>
            </ul>
        </div>
      </aside>
    </>
  );
}

export default NavBar;
