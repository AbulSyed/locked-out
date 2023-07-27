import './Topnav.scss'
import { NavLink } from 'react-router-dom';

const Topnav: React.FC = () => {
	return (
		<nav className="navbar">
			<div className="container">
					<div className='logo'>LockedOut</div>
					<ul className="primary-links">
						<NavLink className={({ isActive, isPending }) => isPending ? "pending li" : isActive ? "active li" : "li"} to="/">
							Home
						</NavLink>
						<NavLink className={({ isActive, isPending }) => isPending ? "pending li" : isActive ? "active li" : "li"} to="/about">
							About
						</NavLink>
						<NavLink className={({ isActive, isPending }) => isPending ? "pending li" : isActive ? "active li" : "li"} to="/oauth">
							OAuth2.0
						</NavLink>
					</ul>
					<ul className="secondary-links">
						<NavLink className={({ isActive, isPending }) => isPending ? "pending li" : isActive ? "active li" : "li"} to="/docs">
							Docs
						</NavLink>
						<NavLink className={({ isActive, isPending }) => isPending ? "pending li" : isActive ? "active li" : "li"} to="/docss">
							Docs
						</NavLink>
					</ul>
			</div>
		</nav>
	);
}
  
  export default Topnav;