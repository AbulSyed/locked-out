import './MainLayout.scss'
import Topnav from '../components/top-nav/Topnav'
import Sidenav from '../components/side-nav/Sidenav'
import { Route, Routes, useLocation } from 'react-router-dom'
import Home from '../pages/home/Home'
import { useEffect, useState } from 'react'

const MainLayout: React.FC = () => {
  const [isShowingSidenav, setIsShowingSidenav] = useState(true)
  const location = useLocation()

  useEffect(() => {
    if (location.pathname.startsWith('/apps')) {
      setIsShowingSidenav(true)
    } else {
      setIsShowingSidenav(false)
    }
  })

  return (
    <>
      <Topnav />
      <div style={{ display: isShowingSidenav ? 'flex' : 'initial' }}>
        {
          isShowingSidenav ? <Sidenav route='/apps/app-1' /> : null
        }
        <div>
          <Routes>
            <Route path="/home" element={<Home />} />
          </Routes>
        </div>
      </div>
    </>
  )
}

export default MainLayout;