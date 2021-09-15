// ----------------- state -------------------
// 프론트엔드 state: UI의 처리 바뀌게 하는 변수
//                  모달팝업상태(보이고 안보이고), 연락처 목록상태(갯수, 수정모드)

// 백엔드앤드 state: 비지니스 로직 처리가 바뀌게 하는 데이터
//                  주문상태(주문요청, 결제, 결제확인, 배송중, 배송완료)
//                  승인상태(제출, 검토중, 반려, 승인)

import { configureStore } from "@reduxjs/toolkit";
import profileReducer from "../features/profile/profileSlice";
import contactReducer from "../features/contact/contactSlice";
import photoReducer from "../features/photo/photoSlice";

// global state(전역 상태) 저장소
// global state: profile, todo, contact ..여러개 state가 있음
// ★ 이러한 state들은 다른 컴포넌트와 state가 공유 됨
export const store = configureStore({
  reducer: {
    // state이름: reducer이름
    // profile state처리하는 reducer를 등록
    profile: profileReducer,
    contact: contactReducer,
    photo: photoReducer,
  }, // 각 state별로 처리할 reducer목록
  devTools: true, // 개발툴 사용여부
});

// typescript에서는 몇가지 타입 선언을 해야함

// root state타입정의
// 가장 최상위 state ex) state.profile, state.contact...

export type RootState = ReturnType<typeof store.getState>;

// Dispatch 타입정의
// Dispatch 함수의 generic
export type AppDispatch = typeof store.dispatch;
