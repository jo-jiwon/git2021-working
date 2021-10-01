import contactReducer, {
  addContact,
  initialCompleted,
  initialContact,
  DeleteContact,
  editContact,
  ContactItem,
} from "./contactSlice";
import { createAction, PayloadAction } from "@reduxjs/toolkit";
import { call, put, takeEvery, takeLatest } from "@redux-saga/core/effects";
import api, { ContactItemRequest, ContactItemResponse } from "./contactApi";
import { AxiosResponse } from "axios";
import {
  endProgress,
  startProgress,
} from "../../components/progress/progressSlice";

/* ==================== action 생성 ==================== */

/* 기능 1. saga action 생성 */
export const requestAddContact = createAction<ContactItem>(
  `${contactReducer.name}/requestAddContact`
);

/* fetch 1 */
export const requestFetchContacts = createAction(
  `${contactReducer.name}/requestFetchContacts`
);

/* Delete 1 */
export const requestDeleteContact = createAction<number>(
  `${contactReducer.name}/requestDeleteContact`
);

/* edit 1 */
export const requestEditContact = createAction<ContactItem>(
  `${contactReducer.name}/requestEditContact`
);

/* ==================== action 처리 ==================== */

/* 기능 3. saga action 처리 */
function* addData(action: PayloadAction<ContactItem>) {
  yield console.log("addData");

  try {
    // payload 객체
    const contactItemPayload = action.payload;

    // rest api로 보낼 요청 객체
    const contactItemRequest: ContactItemRequest = {
      name: contactItemPayload.name,
      phone: contactItemPayload.phone,
      email: contactItemPayload.email,
      memo: contactItemPayload.memo,
    };

    // 1. rest api에 post로 데이터 보냄
    // spinner 보여주기
    yield put(startProgress());

    const result: AxiosResponse<ContactItemResponse> = yield call(
      api.add,
      contactItemRequest
    );

    // spinner 안보여주기
    yield put(endProgress());

    // 2. redeux state를 변경함
    const contactItem: ContactItem = {
      id: result.data.id,
      name: result.data.name,
      phone: result.data.phone,
      email: result.data.email,
      memo: result.data.memo,
      createdTime: result.data.createdTime,
    };

    yield put(addContact(contactItem));

    // completed 속성 삭제
    yield put(initialCompleted());
  } catch (e: any) {
    // spinner 안보여주기
    yield put(endProgress());
  }
}

/* fetch 3 */
function* fetchData() {
  yield console.log("fetchData");

  // spinner 보여주기
  yield put(startProgress());

  // fetch 이후 백엔드 에서 데이터 받아오기
  const result: AxiosResponse<ContactItemResponse[]> = yield call(api.fetch);

  // spinner 안보여주기
  yield put(endProgress());

  // 응답데이터 배열을 액션페이로드 배열로 변환
  const contacts = result.data.map(
    (item) =>
      ({
        id: item.id,
        name: item.name,
        phone: item.phone,
        email: item.email,
        memo: item.memo,
        createdTime: item.createdTime,
      } as ContactItem)
  );

  // state 초기화 reducer 실행
  yield put(initialContact(contacts));
}

/* Delete 3  */
function* DeleteData(action: PayloadAction<number>) {
  yield console.log("DeleteData");

  // id 값
  const id = action.payload;

  // spinner 보여주기
  yield put(startProgress());

  // rest api 연동
  const result: AxiosResponse<boolean> = yield call(api.remove, id);

  // spinner 사라지게 하기
  yield put(endProgress());

  // 반환 값이 true이면
  if (result.data) {
    // state 변경(1건삭제)
    yield put(DeleteContact(id));
  }

  // completed 속성 삭제
  yield put(initialCompleted());
}

/* edit 3 */
function* editData(action: PayloadAction<ContactItem>) {
  yield console.log("editData");

  // action payload로 넘어온 객체
  const contactItemPayload = action.payload;

  // rest api로 보낼 요청 객체
  const contactItemRequest: ContactItemRequest = {
    name: contactItemPayload.name,
    phone: contactItemPayload.phone,
    email: contactItemPayload.email,
    memo: contactItemPayload.memo,
  };

  // spinner 보여주기
  yield put(startProgress());

  // rest api 연동
  const result: AxiosResponse<ContactItemResponse> = yield call(
    api.modify,
    contactItemPayload.id,
    contactItemRequest
  );

  // spinner 안보여주기
  yield put(endProgress());

  // redeux state를 변경
  const contactItem: ContactItem = {
    id: result.data.id,
    name: result.data.name,
    phone: result.data.phone,
    email: result.data.email,
    memo: result.data.memo,
    createdTime: result.data.createdTime,
  };

  // state 변경
  yield put(editContact(contactItem));

  // completed 속성 삭제
  yield put(initialCompleted());
}

/* ==================== action 감지 ==================== */

/* 기능 2. saga action을 감지 (take) */
export default function* contactSaga() {
  yield takeEvery(requestAddContact, addData);

  /* fetch 2 */
  yield takeLatest(requestFetchContacts, fetchData);

  /* Delete 2 */
  yield takeEvery(requestDeleteContact, DeleteData);

  /* edit 2 */
  yield takeEvery(requestEditContact, editData);
}
