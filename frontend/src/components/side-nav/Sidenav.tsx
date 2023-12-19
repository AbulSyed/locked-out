import './Sidenav.scss'

import { useEffect, useState } from 'react'
import { NavLink, useLocation } from 'react-router-dom'
import { useAppSelector, useAppDispatch } from '../../store/hooks'
import { setActiveNavReducer } from '../../store/active-nav/activeNavSlice'

interface SidenavProps {
}

const Sidenav: React.FC<SidenavProps> = () => {
  const state = useAppSelector(state => state.app)
  const location = useLocation()
  const dispatch = useAppDispatch()
  const activeApp = location.pathname.split('/')[2]
  const [activeNav, setActiveNav] = useState('Overview')
  const navItems = ['Overview', 'Users', 'Clients', 'Roles', 'Authorities', 'Scopes', 'Tokens']

  useEffect(() => {
    dispatch(setActiveNavReducer('Overview'))
  }, [])

  const updateActiveNav = (navItem: string) => {
    setActiveNav(navItem)
    dispatch(setActiveNavReducer(navItem))
  }

  return (
    <div className='sidenav'>
      <div className='sidenav-list'>
        <select className='app-select'>
          {
            state.apps.map(app => (
              <option key={app.id} value={app.name} selected={app.name == activeApp}>{app.name}</option>
            ))
          }
        </select>

        <hr className='sidenav-hr'/>

        <ul>
          {
            navItems.map(navItem => (
              <NavLink 
                key={navItem}
                to={`apps/${activeApp}/${navItem.toLowerCase()}`}
              >
                <li key={navItem} className={activeNav == `${navItem}` ? 'li active-item' : 'li'} onClick={() => updateActiveNav(navItem)}>
                  {navItem}
                </li>
              </NavLink>
            ))
          }
        </ul>
      </div>
      <span className='version'>{import.meta.env.VITE_APP_VERSION}</span>
    </div>
  )
}

export default Sidenav