import './Scopes.scss'

import Block from '../../block/Block';

interface ScopesProps {
}

const Scopes: React.FC<ScopesProps> = () => {
  const scopes = [
    { 'id': '1', 'name': 'OPENID'},
    { 'id': '2', 'name': 'EMAIL'},
    { 'id': '3', 'name': 'PHONE'}
  ]

  return ( 
    <div className='scopes'>
      <p className='m-heading py-2'>Scopes</p>

      <p>Scopes can only be added to <i>Clients</i>. Scopes added to <i>Clients</i> will be present as a claim on the JWT. Only 3 scopes are supported.</p>

      <br />
      <hr />
      <br />

      <Block items={scopes} showDelete={false} />

      <p>Navigate to the 'Clients' page to add scopes.</p>
    </div>
  );
}
 
export default Scopes