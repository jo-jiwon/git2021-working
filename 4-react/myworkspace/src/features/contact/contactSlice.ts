import { createSlice, PayloadAction } from "@reduxjs/toolkit";

// 1열로 구성된 목록조회(순번, 이름, 전화번호, 이메일, 작성일시, 메모)

// 데이터구조
export interface ContactItem {
  id: number;
  name: string;
  phone: string;
  email: string;
  createdTime: number;
  memo: string;
}

// 이건 백엔드 연동때문에 설계하셨다함 NEXT
interface ContactState {
  data: ContactItem[]; // contact 아이템 배열
  isFetched: boolean; // 서버에서 데이터를 받아왔는지 여부
  isAddCompleted?: boolean; // 데이터 추가가 완료되었는지 여부
  isDeleteCompleted?: boolean; // 데이터 삭제가 완료되었는지 여부
  isEditCompleted?: boolean; // 데이터 수정이 완료되었는지 여부
}

// contact state 목록
const initialState: ContactState = {
  data: [],
  isFetched: false,
};

const contactSlice = createSlice({
  name: "contact",
  initialState,
  reducers: {
    // 추가 reducer
    addContact: (state, action: PayloadAction<ContactItem>) => {
      const contact = action.payload;
      state.data.unshift(contact);
      // 추가 표시
      state.isAddCompleted = true;
    },
    // payload 없는 reducer
    initialCompleted: (state) => {
      delete state.isAddCompleted;
      delete state.isDeleteCompleted;
      delete state.isEditCompleted;
    },

    // 삭제 reducer
    // payload 타입은 number
    DeleteContact: (state, action: PayloadAction<number>) => {
      const id = action.payload;
      // id에 해당하는 아이템의 index를 찾고 그 index로 splice한다.
      state.data.splice(
        state.data.findIndex((item) => item.id === id),
        1
      );
      // 삭제 표시
      state.isDeleteCompleted = true;
    },

    // 수정 reducer
    editContact: (state, action: PayloadAction<ContactItem>) => {
      // 생성해서 넘길 객체
      const editItem = action.payload;
      // state에 있는 객체 === 생성해서 넘길 객체이다
      const contactItem = state.data.find((item) => item.id === editItem.id);
      // state에 있는 객체의 속성을 생성할 객체의 속성으로 변경
      if (contactItem) {
        contactItem.name = editItem.name;
        contactItem.phone = editItem.phone;
        contactItem.email = editItem.email;
        contactItem.memo = editItem.memo;
      }
      // 변경 표시
      state.isEditCompleted = true;
    },
    // payload 값으로 state를 초기화하는 reducer 필요함
    initialContact: (state, action: PayloadAction<ContactItem[]>) => {
      const contacts = action.payload;
      // 백엔드에서 받아온 데이터
      state.data = contacts;
      // 데이터를 받아옴으로 값을 남김
      state.isFetched = true;
    },
  },
});

// 추가, 삭제, 수정 action payload export
export const {
  addContact,
  DeleteContact,
  editContact,
  initialContact,
  initialCompleted,
} = contactSlice.actions;

export default contactSlice.reducer;
