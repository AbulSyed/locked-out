import './MainLayout.scss'

import Topnav from '../components/top-nav/Topnav'
import Home from '../pages/home/Home'
import AppDetails from '../components/app-details/AppDetails'

import { Navigate, Route, Routes, useLocation } from 'react-router-dom'
import { useEffect, useState } from 'react'
import { useAppDispatch } from '../store/hooks'
import { getApps } from '../store/app/appSlice'

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
  }, [])

  return (
    <>
      <Topnav />
      <div style={{ display: isShowingSidenav ? 'flex' : 'initial' }}>
        {
          isShowingSidenav ? <AppDetails /> : null
        }
        <div>
          <Routes>
            <Route path="/" element={<Navigate to="/home" />} />
            <Route path="/home" element={<Home />} />
          </Routes>
        </div>
      </div>
    </>
  )
}

export default MainLayout