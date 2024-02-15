import { Suspense, lazy } from "react";
const { createBrowserRouter, Navigate } = require("react-router-dom");

const Loading = <span className="loading loading-spinner loading-lg"></span>//로딩

const LoginPage = lazy(() => import("../pages/member/LoginPage")) //보여질 로그인 페이지 
const ListPage = lazy(() => import("../pages/post/ListPage")) //보여질 리스트 페이지 
const WritePage = lazy(() => import("../pages/post/WritePage")) //보여질 글작성 페이지 
const UpdatePage = lazy(() => import("../pages/post/UpdatePage"))
const DetailPage = lazy(() => import("../pages/post/DetailPage"))



const root = createBrowserRouter ([
  
  {
    path: '',
    element: <Navigate replace={true} to= {'post/list'}/> //리다이렉션
  },
  {
    path: "member/login", //로그인 페이지 이동 
    element: <Suspense fallback={Loading}><LoginPage/></Suspense>
  },
  {
    path: "post/list", //글 리스트 이동 
    element: <Suspense fallback={Loading}><ListPage/></Suspense>
  },
  {
    path: "post/write", //글 작성 이동
    element: <Suspense fallback={Loading}><WritePage/></Suspense>
  },
  {
    path: 'post/modify/:id', //글 수정 이동
    element: <Suspense fallback={Loading}><UpdatePage/></Suspense>
    
  },
  {
    path: 'post/detail/:id', //글 상세 이동 
    element: <Suspense fallback={Loading}><DetailPage/></Suspense>
    
  }


])

export default root;