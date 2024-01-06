import './MainLayout.scss'

import Topnav from '../components/top-nav/Topnav'
import Home from '../pages/home/Home'
import Sidenav from '../components/side-nav/Sidenav'
import Overview from '../components/app-details/overview/Overview'
import Users from '../components/app-details/users/Users'
import Clients from '../components/app-details/clients/Clients'
import Roles from '../components/app-details/roles/Roles'
import Authority from '../components/app-details/authority/Authority'
import Scopes from '../components/app-details/scopes/Scopes'
import Tokens from '../components/app-details/tokens/Tokens'

import { Navigate, Route, Routes, useLocation } from 'react-router-dom'
import { useEffect, useState } from 'react'
import { useAppDispatch, useAppSelector } from '../store/hooks'
import { getAppDetails, getApps } from '../store/app/appSlice'
import { getRoles } from '../store/role/roleSlice'
import { getAuthorities } from '../store/authority/authoritySlice'
import { getUsersByAppName } from '../store/user/userSlice'
import { getClientsByAppName } from '../store/client/clientSlice'

interface MainLayoutProps {
}

const MainLayout: React.FC<MainLayoutProps> = () => {
  const [isShowingSidenav, setIsShowingSidenav] = useState(true)

  const location = useLocation()
  const dispatch = useAppDispatch()
  const apps = useAppSelector(state => state.app.apps)
  const activeApp = location.pathname.split('/')[2]
  const app = apps.find(app => app.name == activeApp)

  useEffect(() => {
    if (location.pathname.startsWith('/apps')) {
      setIsShowingSidenav(true)

      if (app) {
        dispatch(getAppDetails(activeApp))
        dispatch(getUsersByAppName(activeApp))
        dispatch(getClientsByAppName(activeApp))
      }
    } else {
      setIsShowingSidenav(false)
    }
  })

  useEffect(() => {
    dispatch(getApps({
      "page": "1",
      "size": "100"
    }))
    dispatch(getRoles())
    dispatch(getAuthorities())
  }, [])

  return (
    <>
      <Topnav />
      <div style={{ display: isShowingSidenav ? 'flex' : 'initial' }}>
        {
          isShowingSidenav ? <Sidenav /> : null
        }
        <div>
          <Routes>
            <Route path='/' element={<Navigate to='/home' />} />
            <Route path='/home' element={<Home />} />
            <Route path='/apps/:appname/overview' element={app ? <Overview /> : <Navigate to="/home" />} />
            <Route path='/apps/:appname/users' element={app ? <Users /> : <Navigate to="/home" />} />
            <Route path='/apps/:appname/clients' element={app ? <Clients /> : <Navigate to="/home" />} />
            <Route path='/apps/:appname/roles' element={app ? <Roles /> : <Navigate to="/home" />} />
            <Route path='/apps/:appname/authorities' element={app ? <Authority /> : <Navigate to="/home" />} />
            <Route path='/apps/:appname/scopes' element={app ? <Scopes /> : <Navigate to="/home" />} />
            <Route path='/apps/:appname/tokens' element={app ? <Tokens /> : <Navigate to="/home" />} />
          </Routes>
        </div>
      </div>
    </>
  )
}

export default MainLayout