import './MainLayout.scss'
import Topnav from '../components/topnav/Topnav'
import Sidenav from '../components/sidenav/Sidenav'
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import Home from '../pages/home/Home'

const MainLayout: React.FC = () => {
  const isNotHome = false;

  return (
    <BrowserRouter>
      <Topnav />
      <div style={{ display: isNotHome ? 'flex' : 'initial' }}>
        {/* <Sidenav /> */}
        <div>
          <Routes>
            <Route path="/home" element={<Home />} />
          </Routes>
        </div>
      </div>
    </BrowserRouter>
  )
}

export default MainLayout;