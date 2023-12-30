import './Block.scss'

import { DeleteOutlined } from '@ant-design/icons'
import { useAppDispatch } from '../../store/hooks'

interface BlockProps {
  items: Item[];
  showDelete: boolean;
  action: any;
}

interface Item {
  id: string;
  name: string;
}

const Block: React.FC<BlockProps> = ({ items, showDelete, action }) => {
  const dispatch = useAppDispatch()

  const handleClick = (id: string) => {
    alert('This is a cascade delete. Are you sure you want to do this?')
    
    dispatch(action(id))
  }

  return ( 
    <div className='block'>
      {
        items.map(item => (
        <div className="block-item" key={item.id}>
          {item.name}
          {
            showDelete && <DeleteOutlined className='delete-icon' onClick={() => handleClick(item.id)} />
          }
        </div>
        ))
      }
    </div>
  );
}
 
export default Block