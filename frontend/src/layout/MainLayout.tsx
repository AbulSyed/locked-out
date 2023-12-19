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
import { useAppDispatch } from '../store/hooks'
import { getApps } from '../store/app/appSlice'
import { getRoles } from '../store/role/roleSlice'
import { getAuthorities } from '../store/authority/authoritySlice'

interface MainLayoutProps {
}

const MainLayout: React.FC<MainLayoutProps> = () => {
  const [isShowingSidenav, setIsShowingSidenav] = useState(true)
  const location = useLocation()
  const dispatch = useAppDispatch()

  useEffect(() => {
    if (location.pathname.startsWith('/apps')) {
      setIsShowingSidenav(true)
    } else {
      setIsShowingSidenav(false)
    }
  })

  useEffect(() => {
    dispatch(getApps())
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
            <Route path='/apps/:appname/overview' element={<Overview />} />
            <Route path='/apps/:appname/users' element={<Users />} />
            <Route path='/apps/:appname/clients' element={<Clients />} />
            <Route path='/apps/:appname/roles' element={<Roles />} />
            <Route path='/apps/:appname/authorities' element={<Authority />} />
            <Route path='/apps/:appname/scopes' element={<Scopes />} />
            <Route path='/apps/:appname/tokens' element={<Tokens />} />
          </Routes>
        </div>
      </div>
    </>
  )
}

export default MainLayout