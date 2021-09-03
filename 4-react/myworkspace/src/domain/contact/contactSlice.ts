import { createSlice, PayloadAction } from "@reduxjs/toolkit";

// 1열로 구성된 목록조회(순번, 이름, 전화번호, 이메일, 작성일시, 메모)

// 데이터구조
export interface ContactItem {
  id: number;
  name: string;
  phone: string;
  email: string;
  createTime: number;
  description: string;
}

// 이건 백엔드 연동때문에 설계하셨다함 NEXT
interface ContactState {
  data: ContactItem[];
  isFetched: boolean;
}

// contact state 목록
const initialState: ContactState = {
  data: [
    {
      id: 3,
      name: "JiWon Jo",
      phone: "010-9105-3768",
      email: "jjw3768@naver.com",
      createTime: new Date().getTime(),
      description: "연락처 조회 만들어보장",
    },
    {
      id: 2,
      name: "JiWon Jo",
      phone: "010-9105-3768",
      email: "jjw3768@naver.com",
      createTime: new Date().getTime(),
      description: "연락처 조회 만들어보장",
    },
    {
      id: 1,
      name: "JiWon Jo",
      phone: "010-9105-3768",
      email: "jjw3768@naver.com",
      createTime: new Date().getTime(),
      description: "연락처 조회 만들어보장",
    },
  ],
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
    },
    // 삭제 reducer
    // payload 타입은 number
    delContact: (state, action: PayloadAction<number>) => {
      const id = action.payload;
      // id에 해당하는 아이템의 index를 찾고 그 index로 splice한다.
      state.data.splice(
        state.data.findIndex((item) => item.id === id),
        1
      );
    },
    // 수정 reducer
    editContact: (state, action: PayloadAction<ContactItem>) => {
      // 생성해서 넘길 객체
      const modifyItem = action.payload;
      // state에 있는 객체 === 생성해서 넘길 객체이다
      const contactItem = state.data.find((item) => item.id === modifyItem.id);
      // state에 있는 객체의 속성을 생성할 객체의 속성으로 변경
      if (contactItem) {
        contactItem.name = modifyItem.name;
        contactItem.phone = modifyItem.phone;
        contactItem.email = modifyItem.email;
        contactItem.description = modifyItem.description;
      }
    },
  },
});

// 추가, 삭제, 수정 action payload export
export const { addContact, delContact, editContact } = contactSlice.actions;

export default contactSlice.reducer;
