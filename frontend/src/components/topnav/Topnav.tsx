import './Topnav.scss'
import { NavLink } from 'react-router-dom';

const Topnav: React.FC = () => {
	return (
		<nav className="navbar">
			<div className="container">
					<div className='logo'>LockedOut</div>
					<ul className="primary-links">
						<NavLink className={({ isActive, isPending }) => isPending ? "pending li" : isActive ? "active li" : "li"} to="/">
								Discover
						</NavLink>
						<NavLink className={({ isActive, isPending }) => isPending ? "pending li" : isActive ? "active li" : "li"} to="/myrepos">
								My repos
						</NavLink>
						<NavLink className={({ isActive, isPending }) => isPending ? "pending li" : isActive ? "active li" : "li"} to="/favourites">
								Favourites
						</NavLink>
					</ul>
					<ul className="secondary-links">
						<NavLink className={({ isActive, isPending }) => isPending ? "pending li" : isActive ? "active li" : "li"} to="/help">
								Help & Feedback
						</NavLink>
					</ul>
			</div>
		</nav>
	);
}
  
  export default Topnav;