import './Block.scss'

import { useState } from 'react';
import { DeleteOutlined } from '@ant-design/icons'
import { useAppDispatch } from '../../store/hooks'
import { Modal } from 'antd'

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
  const [isModalOpen, setIsModalOpen] = useState(false)

  const showModal = () => {
    setIsModalOpen(true)
  }

  const handleOk = (id: string) => {
    setIsModalOpen(false)
    dispatch(action(id))
  }

  const handleCancel = () => {
    setIsModalOpen(false)
  }

  return ( 
    <div className='block'>
      {
        items.map(item => (
          <div className="block-item" key={item.id}>
            {item.name}
            {
              showDelete && 
              <>
                <DeleteOutlined
                  className='delete-icon'
                  onClick={showModal}
                />
                <Modal
                  title="Deletion"
                  open={isModalOpen}
                  onOk={() => handleOk(item.id)}
                  onCancel={handleCancel}
                >
                  <p>This is a cascade delete. Are you sure you want to do this?</p>
                </Modal>
              </>
            }
          </div>
        ))
      }
    </div>
  );
}
 
export default Block