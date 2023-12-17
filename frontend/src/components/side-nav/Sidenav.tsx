import './Sidenav.scss'

import { NavLink, useLocation } from 'react-router-dom'
import { useAppSelector } from '../../store/hooks'

const Sidenav: React.FC = () => {
  const state = useAppSelector(state => state.app)
  const location = useLocation()
  const activeApp = location.pathname.split('/')[2]

  return (
    <div className='sidenav'>
      <ul className='sidenav-list'>
        <select className='app-select'>
          {
            state.apps.map(app => (
              <option key={app.id} value={app.name} selected={app.name == activeApp}>{app.name}</option>
            ))
          }
        </select>
        <hr className='sidenav-hr'/>
        <NavLink className={({ isActive, isPending }) => isPending ? 'pending li' : isActive ? 'active li' : 'li'} to={'/apps/' + activeApp + '/overview'}>
          Overview
        </NavLink>
        <NavLink className={({ isActive, isPending }) => isPending ? 'pending li' : isActive ? 'active li' : 'li'} to={'/apps/' + activeApp + '/users'}>
          Users
        </NavLink>
        <NavLink className={({ isActive, isPending }) => isPending ? 'pending li' : isActive ? 'active li' : 'li'} to={'/apps/' + activeApp + '/clients'}>
          Clients
        </NavLink>
        <NavLink className={({ isActive, isPending }) => isPending ? 'pending li' : isActive ? 'active li' : 'li'} to={'/apps/' + activeApp + '/roles'}>
          Roles
        </NavLink>
        <NavLink className={({ isActive, isPending }) => isPending ? 'pending li' : isActive ? 'active li' : 'li'} to={'/apps/' + activeApp + '/scopes'}>
          Scopes
        </NavLink>
        <NavLink className={({ isActive, isPending }) => isPending ? 'pending li' : isActive ? 'active li' : 'li'} to={'/apps/' + activeApp + '/token'}>
          Token
        </NavLink>
      </ul>
      <span className='version'>{import.meta.env.VITE_APP_VERSION}</span>
    </div>
  )
}

export default Sidenav