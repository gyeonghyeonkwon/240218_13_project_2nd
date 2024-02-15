import { Suspense, lazy } from "react";
const { createBrowserRouter, Navigate } = require("react-router-dom");

const Loading = <span className="loading loading-spinner loading-lg"></span>//로딩

const Login = lazy(() => import("../pages/member/LoginPage")) //보여질 로그인 페이지 
const PostList = lazy(() => import("../post/list/PostList")) //보여질 리스트 페이지 
const Write = lazy(() => import("../post/create/Write")) //보여질 글작성 페이지 
const PostModify = lazy(() => import("../post/modify/PostModify"))
const Detail = lazy(() => import("../post/detail/Detail"))



const root = createBrowserRouter ([
  
  {
    path: '',
    element: <Navigate replace={true} to= {'post/list'}/> //리다이렉션
  },
  {
    path: "member/login", //로그인 페이지 이동 
    element: <Suspense fallback={Loading}><Login/></Suspense>
  },
  {
    path: "post/list", //글 리스트 이동 
    element: <Suspense fallback={Loading}><PostList/></Suspense>
  },
  {
    path: "post/write", //글 작성 이동
    element: <Suspense fallback={Loading}><Write/></Suspense>
  },
  {
    path: 'post/modify/:id', //글 수정 이동
    element: <Suspense fallback={Loading}><PostModify/></Suspense>
    
  },
  {
    path: 'post/detail/:id', //글 상세 이동 
    element: <Suspense fallback={Loading}><Detail/></Suspense>
    
  }


])

export default root;