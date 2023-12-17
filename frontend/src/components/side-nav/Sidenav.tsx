import './Sidenav.scss'

import { useState } from 'react'
import { useLocation } from 'react-router-dom'
import { useAppSelector, useAppDispatch } from '../../store/hooks'
import { setActiveNavReducer } from '../../store/active-nav/activeNavSlice'

const Sidenav: React.FC = () => {
  const state = useAppSelector(state => state.app)
  const location = useLocation()
  const dispatch = useAppDispatch()
  const activeApp = location.pathname.split('/')[2]
  const [activeNav, setActiveNav] = useState('Overview')
  const navItems = ['Overview', 'Users', 'Clients', 'Roles', 'Scopes', 'Tokens']

  const updateActiveNav = (navItem: string) => {
    setActiveNav(navItem)
    dispatch(setActiveNavReducer(navItem))
  }

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

        <ul>
          {
            navItems.map(navItem => (
              <li className={activeNav == `${navItem}` ? 'active li' : 'li'} onClick={() => updateActiveNav(navItem)}>
                {navItem}
              </li>
            ))
          }
        </ul>
      </ul>
      <span className='version'>{import.meta.env.VITE_APP_VERSION}</span>
    </div>
  )
}

export default Sidenav