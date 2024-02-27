import React, { useEffect, useState } from 'react';
import { getOne } from '../../api/postApi';

const initState = {
  id: 0 , 
  title:'',
  content:'',
  createdDate:''
}

const DetailComponent = ({id}) => {

  const [post , setPost] = useState(initState)

  useEffect(() => {

      getOne(id).then(data => {

        console.log(data);

        setPost(data);
      })

  }, [id]);


  return (
    <div style={{ 
      display: 'flex', justifyContent: 'center', alignItems: 'center', 
      width: '100%', height: '80vh' , display:'flex', flexDirection:'column' 
      }}>
    <div >
            <h1>글 상세 페이지</h1>
            <br />
            <h2 >제목</h2>
            <input type ='text' placeholder="Type here" className="input input-bordered w-full max-w-xs" value={post.title} readOnly></input>
            <br />
            <br />
            <h2>내용</h2>
            <input type ='text' className="input input-bordered w-full max-w-xs" value={post.content} readOnly></input>
            <br />
            <br />
            <h2>작성일</h2>
            <input type ='text' className="input input-bordered w-full max-w-xs" value={post.createdDate} readOnly></input>
            <br />
            <br />
            <div className="button-container" >
             <button className="btn btn-primary" style={{ marginRight: '10px'}} onClick={moveToList}>뒤로가기</button>
            <button className="btn btn-primary" style={{ marginRight: '10px'}} onClick={() => moveToModify(id)}>수정페이지</button>
            <button className="btn btn-primary" onClick={deleteWrite}>삭제하기</button>
          </div>
            </div>

          </div>
  );
}


export default DetailComponent;