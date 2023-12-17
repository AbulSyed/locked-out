import './MainLayout.scss'

import Topnav from '../components/top-nav/Topnav'
import Sidenav from '../components/side-nav/Sidenav'
import Home from '../pages/home/Home'

import { Navigate, Route, Routes, useLocation } from 'react-router-dom'
import { useEffect, useState } from 'react'
import { useAppDispatch } from '../store/hooks'
import { getApps } from '../store/app/appSlice'

const MainLayout: React.FC = () => {
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
          isShowingSidenav ? <Sidenav route='/apps/app-1' /> : null
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