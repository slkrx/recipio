import { useContext } from "react";
import { Link, NavLink, useNavigate } from "react-router-dom";
import AuthContext from "../context/AuthContext";

function NavBar() {
  const auth = useContext(AuthContext);
  const navigate = useNavigate();

  return (
    <>
      <nav className="bg-rose-white border-gray-200 dark:bg-gray-900">
          <div className="flex flex-wrap justify-between items-center mx-auto max-w-screen-xl p-4">
              <Link to="/" className="flex items-center space-x-3 rtl:space-x-reverse">
                  <span className="self-center text-2xl font-semibold whitespace-nowrap dark:text-white font-fancy text-dark-charcoal">Recipio</span>
              </Link>
              <div className="flex items-center space-x-6 rtl:space-x-reverse">
                {auth.user ? (
                  <a onClick={() => { auth.logout(); navigate("/") } } className="text-sm  text-blue-600 dark:text-red-500 hover:underline">Logout</a>
                ) : (
                  <Link to="/login" className="text-sm  text-blue-600 dark:text-blue-500 hover:underline">Login</Link>
                )}
              </div>
          </div>
      </nav>
      <nav className="bg-light-gray">
          <div className="max-w-screen-xl px-4 py-3 mx-auto">
              <div className="flex items-center">
                  <ul className="flex flex-row font-medium mt-0 space-x-8 rtl:space-x-reverse text-sm">
                      <li>
                          <NavLink to="/" className="text-gray-900 dark:text-white hover:underline">Home</NavLink>
                      </li>
                      {auth.user && (
                        <>
                          <li>
                              <NavLink to="/saved-recipes" className="text-gray-900 dark:text-white hover:underline">Saved Recipes</NavLink>
                          </li>
                          <li>
                              <NavLink to="/recipe/new" className="text-gray-900 dark:text-white hover:underline">New Recipe</NavLink>
                          </li>
                        </>
                      )}
                  </ul>
              </div>
          </div>
      </nav>
    </>
  );
}

export default NavBar;
