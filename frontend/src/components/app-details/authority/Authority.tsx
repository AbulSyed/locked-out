import './Authority.scss'

import Block from '../../block/Block'
import SimpleForm from '../../form/simple-form/SimpleForm'

import { useAppSelector } from '../../../store/hooks'
import { deleteAuthorityCascade } from '../../../store/authority/authoritySlice'

interface AuthorityProps {
}

const Authority: React.FC<AuthorityProps> = () => {
  const authority = useAppSelector(state => state.authority)

  return ( 
    <div className='authority'>
      <p>Authorities can be added to both <i>Users</i> & <i>Clients</i>. Authorities added to either a <i>User</i> or <i>Client</i> will be present as a claim on the JWT.</p>

      <br />
      <hr />
      <br />

      <SimpleForm type='Create authority' />

      {
        authority.authorities.length > 0 ? (
          <>
            <p style={{ 'margin': '1rem 0', 'textAlign': 'center' }}>All authorities:</p>
            {
              <Block items={authority.authorities} showDelete={true} action={deleteAuthorityCascade} />
            }
          </>
        ) : <p style={{ 'margin': '1rem 0', 'textAlign': 'center' }}>No authorities created</p>
      }
    </div>
  );
}
 
export default Authority