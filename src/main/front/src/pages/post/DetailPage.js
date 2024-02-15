import axios from 'axios';
import React, { useEffect, useState } from 'react';
import {  useNavigate, useParams } from 'react-router-dom';
import BasicLayout from '../../layouts/BasicLayout';

const DetailPage = () => {
  const {id} = useParams(); //url id 매핑
  const navigate = useNavigate(); 
  const [title , setTitle] = useState('');
  const [content , setContent] = useState('');
  const [createdDate , setCreatedDate] = useState('');
  useEffect(() => {
    axios.get(`http://127.0.0.1:8080/api/post/detail/${id}`).then((response)=> {
      setTitle(response.data.title);
      setContent(response.data.content);
      setCreatedDate(new Date(response.data.createdDate).toLocaleString()); //날짜 포맷
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
            <h2>작성일</h2>
            <input type ='text' className="input input-bordered w-full max-w-xs" value={createdDate} readOnly></input>
            <br />
            <br />
            <div className="button-container" >
             <button className="btn btn-primary" style={{ marginRight: '10px'}} onClick={() => { navigate('/post/list') }}>뒤로가기</button>
            <button className="btn btn-primary" style={{ marginRight: '10px'}} onClick={() => { navigate(`/post/modify/${id}`) }}>수정페이지</button>
            <button className="btn btn-primary" onClick={deleteWrite}>삭제하기</button>
          </div>
            </div>
          </div>

            </BasicLayout>
  );
}

export default DetailPage;