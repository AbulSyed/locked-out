import './Sidenav.scss'

const Sidenav: React.FC = () => {
  return (
    <div className='sidenav'>
      <ul className='sidenav-list'>
        <li className='sidenav-list-item'>Home</li>
        <li className='sidenav-list-item'>Apps</li>
        <li className='sidenav-list-item'>Clients</li>
        <li className='sidenav-list-item'>Users</li>
      </ul>
      <span className='version'>{import.meta.env.VITE_APP_VERSION}</span>
    </div>
  )
}

export default Sidenav;