import axios from 'axios';
import React, { useEffect, useState } from 'react';
import { createSearchParams, useNavigate, useParams, useSearchParams} from 'react-router-dom';
import BasicLayout from '../../layouts/BasicLayout';

const DetailPage = (props) => {
  const {id} = useParams(); //url id 매핑
  const navigate = useNavigate(); 
  const [queryParams] = useSearchParams() //쿼리스트링 
  const [title , setTitle] = useState('');
  const [content , setContent] = useState('');
  const [memberName , setMemberName] = useState('');
  const [createdDate , setCreatedDate] = useState('');


  const page = queryParams.get('page') ? parseInt(queryParams.get('page')) : 1
  const size = queryParams.get('size') ? parseInt(queryParams.get('size')) : 10

  const queryStr = createSearchParams({page:page , size:size}).toString() // 경로이동시 쿼리스트링 사용가능 

  //수정페이지 이동 
  const moveToModify = (id) => {
    navigate({pathname:`/post/modify/${id}`,
              search:queryStr})
  }
  //리스트이동
  const moveToList = () => {
    navigate({pathname:'/post/list',
              search:queryStr})
  }
  
  useEffect(() => {
    axios.get(`http://127.0.0.1:8080/api/post/detail/${id}`).then((response)=> {
      setTitle(response.data.data.title);
      setContent(response.data.data.content);
      setMemberName(response.data.data.memberName);
      setCreatedDate(new Date(response.data.data.createdDate).toLocaleString()); //날짜 포맷
    })
      .catch((error) => {
          console.log('상세 글을 불러올수없습니다' , error)
      });
  }, [id]);

  //삭제
  const deleteWrite = () =>{
    if (window.confirm('게시글을 삭제하시겠습니까?')) {
     axios.delete(`http://127.0.0.1:8080/api/post/delete/${id}`).then((response) => {

     alert('삭제되었습니다');
     navigate('/post/list');
     })
    
     .catch((error) => {
        console.log('삭제 요청이 실패하였습니다',error)
     });
    }
  };
  return (
    <BasicLayout>
    <div style={{ 
      display: 'flex', justifyContent: 'center', alignItems: 'center', 
      width: '100%', height: '80vh' , display:'flex', flexDirection:'column' 
      }}>
    <div >
            <h1>글 상세 페이지</h1>
            <br />
            <h2 >제목</h2>
            <input type ='text' placeholder="Type here" className="input input-bordered w-full max-w-xs" value={title} readOnly></input>
            <br />
            <br />
            <h2>내용</h2>
            <input type ='text' className="input input-bordered w-full max-w-xs" value={content} readOnly></input>
            <br />
            <br />
            <h2>작성자</h2>
            <input type ='text' className="input input-bordered w-full max-w-xs" value={memberName} readOnly></input>
            <br />
            <br />
            <h2>작성일</h2>
            <input type ='text' className="input input-bordered w-full max-w-xs" value={createdDate} readOnly></input>
            <br />
            <br />
            <div className="button-container" >
             <button className="btn btn-primary" style={{ marginRight: '10px'}} onClick={moveToList}>뒤로가기</button>
            <button className="btn btn-primary" style={{ marginRight: '10px'}} onClick={() => moveToModify(id)}>수정페이지</button>
            <button className="btn btn-primary" onClick={deleteWrite}>삭제하기</button>
          </div>
            </div>

          </div>
            </BasicLayout>
  );
}

export default DetailPage;