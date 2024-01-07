import './Sidenav.scss'

import { useEffect } from 'react'
import { NavLink, useLocation, useNavigate } from 'react-router-dom'
import { useAppSelector, useAppDispatch } from '../../store/hooks'
import { setActiveNavReducer } from '../../store/active-nav/activeNavSlice'
import { getAppDetails } from '../../store/app/appSlice'
import { getUsersByAppName } from '../../store/user/userSlice'
import { getClientsByAppName } from '../../store/client/clientSlice'

interface SidenavProps {
}

const Sidenav: React.FC<SidenavProps> = () => {
  const navItems = ['Overview', 'Users', 'Clients', 'Roles', 'Authorities', 'Scopes']
  
  const activeNav = useAppSelector(state => state.activeNav.activeNav)
  const state = useAppSelector(state => state.app)
  const location = useLocation()
  const dispatch = useAppDispatch()
  const activeApp = location.pathname.split('/')[2]

  const navigate = useNavigate()

  useEffect(() => {
    dispatch(setActiveNavReducer('Overview'))
  }, [])

  const updateActiveNav = (navItem: string) => {
    dispatch(setActiveNavReducer(navItem))
  }

  const handleSelectChange = (e: any) => {
    const app = e.target.value

    navigate(`/apps/${app}/overview`)

    dispatch(setActiveNavReducer('Overview'))

    dispatch(getAppDetails(app))
    dispatch(getUsersByAppName({
      "appName": app,
      "page": "1",
      "size": "100"
    }))
    dispatch(getClientsByAppName({
      "appName": app,
      "page": "1",
      "size": "100"
    }))
  }

  return (
    <div className='sidenav'>
      <div className='sidenav-list'>
        <select
          className='app-select'
          onChange={handleSelectChange}
        >
          {
            state.apps.map(app => (
              <option
                key={app.id}
                value={app.name}
                selected={app.name == activeApp}
              >
                {app.name}
              </option>
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
                <li 
                  key={navItem}
                  className={activeNav == `${navItem}` ? 'li active-item' : 'li'}
                  onClick={() => updateActiveNav(navItem)}
                >
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