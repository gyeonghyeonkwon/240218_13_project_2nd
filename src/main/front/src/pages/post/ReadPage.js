import React from 'react';
import { useNavigate, useParams } from 'react-router-dom';

function ReadPage() {
  
  const navigate = useNavigate();
  const {id} = useParams();
  
  const moveToModify = (id) => {
    navigate({pathname:`/post/modify/${id}`})
  }

  return (
    <div>
      <button onClick={() => moveToModify(id)}> 수정페이지 </button>
    </div>
  );
}

export default ReadPage