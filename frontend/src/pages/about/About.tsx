import './About.scss'

interface AboutProps {
}

const About: React.FC<AboutProps> = () => {
  return (
    <div>
      <div className='about-container'>
        <p className='s-heading py-1'>What is LockedOut?</p>
        <div>LockedOut is a Identity & Access Management (IAM) service that utilises the OAuth2.0 & OIDC protocol. Its main purpose is to be used as a standalone authorization server. Users and clients can be registered and their credentials can be used to obtain authorization tokens.</div>
        <p className='s-heading py-1'>Why should I use LockedOut?</p>
        <div>Building authorization servers can be challenging. LockedOut is a IAM service which can be easily integrated into your backend service.</div>
        <p className='s-heading py-1'>How to use LockedOut?</p>
        <div>See Docs.</div>
      </div>
    </div>
  )
}

export default About