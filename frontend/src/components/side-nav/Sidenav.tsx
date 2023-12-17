import './Sidenav.scss'

import { NavLink } from 'react-router-dom'

interface SidenavProps {
  route: string
}

const Sidenav: React.FC<SidenavProps> = ({ route }) => {
  return (
    <div className='sidenav'>
      <ul className='sidenav-list'>
        <select className="app-select">
          <option value="app-1">app-1</option>
          <option value="app-2">app-2</option>
          <option value="app-3">app-3</option>
        </select>
        <hr className="sidenav-hr"/>
        <NavLink className={({ isActive, isPending }) => isPending ? "pending li" : isActive ? "active li" : "li"} to={route + "/overview"}>
          Overview
        </NavLink>
        <NavLink className={({ isActive, isPending }) => isPending ? "pending li" : isActive ? "active li" : "li"} to={route + "/users"}>
          Users
        </NavLink>
        <NavLink className={({ isActive, isPending }) => isPending ? "pending li" : isActive ? "active li" : "li"} to={route + "/clients"}>
          Clients
        </NavLink>
        <NavLink className={({ isActive, isPending }) => isPending ? "pending li" : isActive ? "active li" : "li"} to={route + "/roles"}>
          Roles
        </NavLink>
        <NavLink className={({ isActive, isPending }) => isPending ? "pending li" : isActive ? "active li" : "li"} to={route + "/scopes"}>
          Scopes
        </NavLink>
        <NavLink className={({ isActive, isPending }) => isPending ? "pending li" : isActive ? "active li" : "li"} to={route + "/token"}>
          Token
        </NavLink>
      </ul>
      <span className='version'>{import.meta.env.VITE_APP_VERSION}</span>
    </div>
  )
}

export default Sidenav