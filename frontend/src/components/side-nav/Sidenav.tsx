import './Sidenav.scss'

import { NavLink, useLocation } from 'react-router-dom'
import { useAppSelector } from '../../store/hooks'

interface SidenavProps {
  route: string
}

const Sidenav: React.FC<SidenavProps> = ({ route }) => {
  const state = useAppSelector(state => state.app)
  const location = useLocation()
  const activeApp = location.pathname.split('/')[2]

  return (
    <div className='sidenav'>
      <ul className='sidenav-list'>
        <select className="app-select">
          {
            state.apps.map(app => (
              <option key={app.id} value={app.name} selected={app.name == activeApp}>{app.name}</option>
            ))
          }
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